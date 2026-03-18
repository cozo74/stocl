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


        LogicalProject topProject = call.rel(0);
        LogicalProject leftProj = call.rel(2);
        TableScan rightScan = call.rel(4);

        TableScan survivorScan = rightScan;

        int leftOutCount = leftProj.getRowType().getFieldCount();
        RelDataType survivorRowType = survivorScan.getRowType();

        List<String> leftNames  = leftProj.getRowType().getFieldNames();
        List<String> rightNames = rightScan.getRowType().getFieldNames();
        List<String> survNames  = survivorRowType.getFieldNames();

        Function<String, Integer> survIndexByName = name -> {
            for (int i = 0; i < survNames.size(); i++) {
                if (survNames.get(i).equals(name)) return i;
            }
            return -1;
        };

        RexShuttle remap = new RexShuttle() {
            @Override 
            public RexNode visitInputRef(RexInputRef ref) {
                int joinIdx = ref.getIndex();
                int survIdx;

                if (joinIdx < leftOutCount) {
                    String name = leftNames.get(joinIdx);
                    survIdx = survIndexByName.apply(name);
                    if (survIdx < 0) {
                        throw new AssertionError("Cannot map left column '" + name + "' to survivor scan");
                    }
                } else {
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

        List<RexNode> newProjects =
            new ArrayList<>(topProject.getProjects().size());
        for (RexNode e : topProject.getProjects()) {
            newProjects.add(e.accept(remap));
        }

        RelBuilder builder = call.builder();
        RelNode replacement = builder.push(survivorScan)
                                    .project(newProjects, topProject.getRowType().getFieldNames())
                                    .build();

        replacement = convert(call.getPlanner(), replacement, topProject.getConvention());

        call.transformTo(replacement);



    }





    public static boolean isPrimaryKeySelfJoin(Join join) {
        if (join.getJoinType() != JoinRelType.INNER) return false;
        
        RelNode left  = ((HepRelVertex) join.getLeft()).getCurrentRel();
        RelNode right = ((HepRelVertex) join.getRight()).getCurrentRel();
        

        if (!(left instanceof LogicalProject lp)) return false;
        RelNode leftScan = ((HepRelVertex) lp.getInput()).getCurrentRel();
        if (!(leftScan instanceof LogicalTableScan lscan)) return false;
        if (!(right instanceof LogicalTableScan rscan)) return false;

        List<String> lq = lscan.getTable().getQualifiedName();
        List<String> rq = rscan.getTable().getQualifiedName();
        if (!lq.equals(rq)) return false;

        String tableLower = lq.get(lq.size() - 1);
        String idName = UMLClassDiagram.getObjectIDColumn(tableLower);

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

        int leftCount = left.getRowType().getFieldCount();
        int aIdx = ((RexInputRef) a).getIndex();
        int bIdx = ((RexInputRef) b).getIndex();
        boolean aFromLeft = aIdx < leftCount;
        boolean bFromLeft = bIdx < leftCount;
        if (aFromLeft == bFromLeft) return false;

        int lIdx = aFromLeft ? aIdx : bIdx;  
        int rIdx = aFromLeft ? bIdx - leftCount : aIdx - leftCount; 
        if (rIdx < 0) return false;

        String leftFieldName  = left.getRowType().getFieldNames().get(lIdx);
        String rightFieldName = right.getRowType().getFieldNames().get(rIdx);

        if (!idName.equals(leftFieldName) || !idName.equals(rightFieldName)) return false;

        RelMetadataQuery mq = join.getCluster().getMetadataQuery();

        ImmutableBitSet lKey = ImmutableBitSet.of(lIdx);
        ImmutableBitSet rKey = ImmutableBitSet.of(rIdx);

        boolean leftUnique  = mq.areColumnsUnique(left,  lKey);
        boolean rightUnique = mq.areColumnsUnique(right, rKey);

        return leftUnique && rightUnique;


    }



    @Override public boolean autoPruneOld() {
        return true;
    }



    @Value.Immutable
    public interface Config extends RelRule.Config {
        Config DEFAULT = ImmutableGetAttributeSelfJoinRule.Config.builder().build()
            .withOperandSupplier(b -> b.operand(LogicalProject.class).oneInput(
                                        b0 -> b0.operand(LogicalJoin.class)
                                        .predicate(GetAttributeSelfJoinRule::isPrimaryKeySelfJoin)
                                        .inputs(
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalTableScan.class).noInputs()),
                                            b1 -> b1.operand(LogicalTableScan.class).noInputs()
                                        )
                                    )
                                );

        @Override default GetAttributeSelfJoinRule toRule() {
            return new GetAttributeSelfJoinRule(this);
        }
    }

}
