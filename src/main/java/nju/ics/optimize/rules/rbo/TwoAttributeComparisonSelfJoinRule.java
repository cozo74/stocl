package nju.ics.optimize.rules.rbo;


import java.util.ArrayList;
import java.util.List;

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

import org.apache.calcite.rex.RexCall;
import org.apache.calcite.rex.RexInputRef;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.tools.RelBuilder;
import org.apache.calcite.tools.RelBuilderFactory;
import org.apache.calcite.util.ImmutableBitSet;
import org.immutables.value.Value;

import nju.ics.model.uml.UMLClassDiagram;



@Value.Enclosing
public class TwoAttributeComparisonSelfJoinRule  extends RelRule<TwoAttributeComparisonSelfJoinRule.Config> implements SubstitutionRule {



    protected TwoAttributeComparisonSelfJoinRule(Config config) {
        super(config);
    }
    
    @Deprecated
    public TwoAttributeComparisonSelfJoinRule(RelBuilderFactory relBuilderFactory) {
        this(Config.DEFAULT.withRelBuilderFactory(relBuilderFactory)
                .as(Config.class));
    }



    @Override public void onMatch(RelOptRuleCall call) {


        LogicalProject topProject = call.rel(0);
        Join join = call.rel(1);
        LogicalProject leftProj = call.rel(2);
        LogicalProject rightProj = call.rel(4);

        TableScan rightScan = call.rel(5);


        List<RexNode> conj = RelOptUtil.conjunctions(join.getCondition());

        RexNode cond = conj.get(1);

        assert cond instanceof RexCall;
        RexCall rexCall = (RexCall) cond;


        String leftAttr =  leftProj.getRowType().getFieldNames().get(1);
        String rightAttr =  rightProj.getRowType().getFieldNames().get(1);

        List<String> tableFieldNames = rightScan.getRowType().getFieldNames();
        RelNode replacement;
        RelBuilder builder = call.builder();
        
        if (tableFieldNames.containsAll(leftProj.getRowType().getFieldNames())
            && tableFieldNames.containsAll(rightProj.getRowType().getFieldNames())
            ) {

            replacement = builder.push(rightScan)
                    .filter(builder.call(rexCall.getOperator(),
                            builder.field(leftAttr),
                            builder.field(rightAttr)
                            ))
                    .project(builder.field(0))
                    .build();


        } else {
            builder.push(rightScan);
            List<RexNode> proj = new ArrayList<>();
            proj.add(builder.field(0));
            proj.add(leftProj.getProjects().get(1));
            proj.add(rightProj.getProjects().get(1));
    
            List<String> alias = new ArrayList<>();
            alias.add( rightScan.getRowType().getFieldNames().get(0) );
            alias.add( leftAttr );
            alias.add( rightAttr );
    
            replacement = builder.project(proj, alias)
                    .filter(builder.call(rexCall.getOperator(),
                            builder.field(leftAttr),
                            builder.field(rightAttr)
                            ))
                    .project(builder.field(0))
                    .build();
    
        }



        replacement = convert(call.getPlanner(), replacement, topProject.getConvention());

        call.transformTo(replacement);

    }




    public static boolean isPrimaryKeySelfJoin(Join join) {
        
        if (join.getJoinType() != JoinRelType.INNER) return false;

        RelNode left  = ((HepRelVertex) join.getLeft()).getCurrentRel();
        RelNode right = ((HepRelVertex) join.getRight()).getCurrentRel();

        if (!(left  instanceof LogicalProject lp)) return false;
        if (!(right instanceof LogicalProject rp)) return false;

        if (lp.getRowType().getFieldCount() != 2) return false;
        if (rp.getRowType().getFieldCount() != 2) return false;


        RelNode leftScan  = ((HepRelVertex) lp.getInput()).getCurrentRel();
        RelNode rightScan = ((HepRelVertex) rp.getInput()).getCurrentRel();

        if (!(leftScan instanceof LogicalTableScan lscan)) return false;
        if (!(rightScan instanceof LogicalTableScan rscan)) return false;


        List<String> lq = lscan.getTable().getQualifiedName();
        List<String> rq = rscan.getTable().getQualifiedName();
        if (!lq.equals(rq)) return false;



        String tableLower = lq.get(lq.size() - 1);
        String idName = UMLClassDiagram.getObjectIDColumn(tableLower);



        List<RexNode> conj = RelOptUtil.conjunctions(join.getCondition());
        if (conj.size() != 2) return false;
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
        Config DEFAULT = ImmutableTwoAttributeComparisonSelfJoinRule.Config.builder().build()
            .withOperandSupplier(b -> b.operand(LogicalProject.class).oneInput(
                                        b0 -> b0.operand(LogicalJoin.class)
                                        .predicate(TwoAttributeComparisonSelfJoinRule::isPrimaryKeySelfJoin)
                                        .inputs(
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalTableScan.class).noInputs()),
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalTableScan.class).noInputs())
                                        )
                                    )
                                );



        @Override default TwoAttributeComparisonSelfJoinRule toRule() {
            return new TwoAttributeComparisonSelfJoinRule(this);
        }
    }


}
