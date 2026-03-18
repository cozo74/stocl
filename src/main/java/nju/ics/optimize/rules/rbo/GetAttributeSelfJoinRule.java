package nju.ics.optimize.rules.rbo;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.plan.RelRule;
import org.apache.calcite.plan.hep.HepRelVertex;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.Join;
import org.apache.calcite.rel.core.JoinRelType;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.rel.logical.LogicalJoin;
import org.apache.calcite.rel.logical.LogicalProject;
import org.apache.calcite.rel.logical.LogicalTableScan;
import org.apache.calcite.rel.metadata.RelMetadataQuery;
import org.apache.calcite.rel.rules.SubstitutionRule;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rex.RexCall;
import org.apache.calcite.rex.RexInputRef;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.rex.RexShuttle;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.tools.RelBuilder;
import org.apache.calcite.tools.RelBuilderFactory;
import org.apache.calcite.util.ImmutableBitSet;
import org.immutables.value.Value;

import nju.ics.model.uml.UMLClassDiagram;


/*
            LogicalProject(car_id=[$0], direction=[$3])
                LogicalJoin(condition=[=($1, $0)], joinType=[inner])
                    LogicalProject(car_id=[$0])
                        LogicalTableScan(table=[[shenzhen, Car]])
                    LogicalTableScan(table=[[shenzhen, Car]])
        =>
            LogicalProject(car_id=[$0], direction=[$3])
                LogicalTableScan(table=[[shenzhen, Car]])
 */
@Value.Enclosing
public class GetAttributeSelfJoinRule extends RelRule<GetAttributeSelfJoinRule.Config> implements SubstitutionRule {


    protected GetAttributeSelfJoinRule(Config config) {
        super(config);
    }
    
    @Deprecated
    public GetAttributeSelfJoinRule(RelBuilderFactory relBuilderFactory) {
        this(Config.DEFAULT.withRelBuilderFactory(relBuilderFactory)
                .as(Config.class));
    }



    @Override public void onMatch(RelOptRuleCall call) {

        // 索引对应（按 operandSupplier 的顺序）：
        // 0=顶层 Project，1=Join，2=左 Project，3=左 Scan，4=右 Scan
        LogicalProject topProject = call.rel(0);
        // Join join = call.rel(1);
        LogicalProject leftProj = call.rel(2);
        // TableScan leftScan = call.rel(3);
        TableScan rightScan = call.rel(4);

        // 选择“存活”的扫描节点：右侧是裸 Scan，更省事
        TableScan survivorScan = rightScan;

        // 计算左右列数，用于把 Join 输出下标映射到各自输入
        int leftOutCount = leftProj.getRowType().getFieldCount();
        RelDataType survivorRowType = survivorScan.getRowType();

        // 建一个映射：Join 输出列 -> survivorScan 上对应列（用字段名对齐更稳）
        List<String> leftNames  = leftProj.getRowType().getFieldNames();
        List<String> rightNames = rightScan.getRowType().getFieldNames();
        List<String> survNames  = survivorRowType.getFieldNames();

        // 把字段名转为 index 的小工具
        Function<String, Integer> survIndexByName = name -> {
            for (int i = 0; i < survNames.size(); i++) {
                if (survNames.get(i).equals(name)) return i;
            }
            return -1;
        };

        // 把 “父 Project 的 RexInputRef(基于 Join 输出)” 映射为 “survivorScan 的 RexInputRef”
        // RexBuilder rexBuilder =
        //     call.builder().getRexBuilder();

        RexShuttle remap = new RexShuttle() {
            @Override 
            public RexNode visitInputRef(RexInputRef ref) {
                int joinIdx = ref.getIndex();
                int survIdx;

                if (joinIdx < leftOutCount) {
                    // 来自左 Project 的列（例如 $0=car_id）。用“列名”在 survivorScan 上找同名列
                    String name = leftNames.get(joinIdx);
                    survIdx = survIndexByName.apply(name);
                    if (survIdx < 0) {
                        throw new AssertionError("Cannot map left column '" + name + "' to survivor scan");
                    }
                } else {
                    // 来自右 Scan 的列：右侧相对下标 = joinIdx - leftOutCount
                    int rightRelIdx = joinIdx - leftOutCount;
                    String name = rightNames.get(rightRelIdx);
                    survIdx = survIndexByName.apply(name);
                    if (survIdx < 0) {
                        throw new AssertionError("Cannot map right column '" + name + "' to survivor scan");
                    }
                }
                return RexInputRef.of(survIdx, survivorRowType);
            }
        };

        // 重写父 Project 的投影表达式到 survivorScan 的列空间
        List<RexNode> newProjects =
            new ArrayList<>(topProject.getProjects().size());
        for (RexNode e : topProject.getProjects()) {
            newProjects.add(e.accept(remap));
        }

        // 用新的输入（survivorScan）和重写后的表达式，构建替代子树：Project(survivorScan)
        RelBuilder builder = call.builder();
        RelNode replacement = builder.push(survivorScan)
                                    .project(newProjects, topProject.getRowType().getFieldNames())
                                    .build();

        // 保持与原顶层 Project 相同的 convention
        replacement = convert(call.getPlanner(), replacement, topProject.getConvention());

        // 替换为 Project(Scan)
        call.transformTo(replacement);



    }




    /*
     * 上述的join模式中，谓词条件为： 
     * 1. 两个TableScan的table相同， 
     * 2. join类型为inner 
     * 3. join的condition为一个等值式，等值式两侧字段为Table名字小写+"_id" 
     * 4. 等值式两侧字段Table名字小写+"_id"为主键
     * 
     */
    public static boolean isPrimaryKeySelfJoin(Join join) {
        // 2) join 类型为 INNER
        if (join.getJoinType() != JoinRelType.INNER) return false;
        
        // left = Project(Scan), right = Scan
        RelNode left  = ((HepRelVertex) join.getLeft()).getCurrentRel();
        RelNode right = ((HepRelVertex) join.getRight()).getCurrentRel();
        

        if (!(left instanceof LogicalProject lp)) return false;
        RelNode leftScan = ((HepRelVertex) lp.getInput()).getCurrentRel();
        if (!(leftScan instanceof LogicalTableScan lscan)) return false;
        if (!(right instanceof LogicalTableScan rscan)) return false;

        // 1) 两个 TableScan 的表相同
        List<String> lq = lscan.getTable().getQualifiedName();
        List<String> rq = rscan.getTable().getQualifiedName();
        if (!lq.equals(rq)) return false;

        // 表名（最后一段）小写
        String tableLower = lq.get(lq.size() - 1);
        String idName = UMLClassDiagram.getObjectIDColumn(tableLower);

        // 3) 条件必须是等值
        List<RexNode> conj = RelOptUtil.conjunctions(join.getCondition());
        if (conj.size() != 1) return false;
        RexNode cond = conj.get(0);
        if (!(cond instanceof RexCall call)) return false;
        if (!(call.getKind() == SqlKind.EQUALS)) return false;
        if (call.getOperands().size() != 2) return false;

        RexNode a = call.getOperands().get(0);
        RexNode b = call.getOperands().get(1);
        if (!(a instanceof RexInputRef) ||
            !(b instanceof RexInputRef)) return false;

        // 连接两侧分别来自左输入、右输入
        int leftCount = left.getRowType().getFieldCount();
        int aIdx = ((RexInputRef) a).getIndex();
        int bIdx = ((RexInputRef) b).getIndex();
        boolean aFromLeft = aIdx < leftCount;
        boolean bFromLeft = bIdx < leftCount;
        if (aFromLeft == bFromLeft) return false; // 不是跨输入的等值

        // 取出左右侧**相对于各自输入**的字段索引与字段名
        int lIdx = aFromLeft ? aIdx : bIdx;               // 相对于 left(Project) 的索引
        int rIdx = aFromLeft ? bIdx - leftCount : aIdx - leftCount; // 相对于 right(Scan) 的索引
        if (rIdx < 0) return false;

        String leftFieldName  = left.getRowType().getFieldNames().get(lIdx);
        String rightFieldName = right.getRowType().getFieldNames().get(rIdx);

        // 3) 等值两侧字段名都等于 tableLower+"_id"
        if (!idName.equals(leftFieldName) || !idName.equals(rightFieldName)) return false;

        // 4) 两侧字段都为主键（唯一列）
        RelMetadataQuery mq = join.getCluster().getMetadataQuery();

        // 注意：左侧是 Project。Calcite 的元数据通常能把唯一性从输入传递上来；
        // 若左侧 Project 非恒等，需要你在规则整体设计里事先确保/约束它是恒等或直接引用该列。
        ImmutableBitSet lKey = ImmutableBitSet.of(lIdx);
        ImmutableBitSet rKey = ImmutableBitSet.of(rIdx);

        boolean leftUnique  = mq.areColumnsUnique(left,  lKey);
        boolean rightUnique = mq.areColumnsUnique(right, rKey);

        return leftUnique && rightUnique;


    }



    @Override public boolean autoPruneOld() {
        return true;
    }



    /** Rule pattern. */
    @Value.Immutable
    public interface Config extends RelRule.Config {
        Config DEFAULT = ImmutableGetAttributeSelfJoinRule.Config.builder().build()
            .withOperandSupplier(b -> b.operand(LogicalProject.class).oneInput(
                                        b0 -> b0.operand(LogicalJoin.class)
                                        .predicate(GetAttributeSelfJoinRule::isPrimaryKeySelfJoin)
                                        .inputs(
                                            // 左输入
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalTableScan.class).noInputs()),
                                            // 右输入
                                            b1 -> b1.operand(LogicalTableScan.class).noInputs()
                                        )
                                    )
                                );

        @Override default GetAttributeSelfJoinRule toRule() {
            return new GetAttributeSelfJoinRule(this);
        }
    }

}
