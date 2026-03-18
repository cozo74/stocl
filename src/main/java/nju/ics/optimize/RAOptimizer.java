

package nju.ics.optimize;

import java.util.List;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.hep.HepMatchOrder;
import org.apache.calcite.plan.hep.HepPlanner;
import org.apache.calcite.plan.hep.HepProgram;
import org.apache.calcite.plan.hep.HepProgramBuilder;
import org.apache.calcite.rel.RelNode;




public class RAOptimizer {

    HepProgramBuilder programBuilder;



    public RAOptimizer(HepMatchOrder matchOrder, int limit){

        programBuilder = new HepProgramBuilder()
            // 决定匹配顺序和迭代方式
            .addMatchOrder(matchOrder) // 可选
            .addMatchLimit(limit);                   // 可选

    }




    public boolean addRuleInstance(RelOptRule rule){

        try{
            programBuilder.addRuleInstance(rule);
            return true;
        } catch(Exception e) {
            return false;
        }
    }



    public boolean addRuleCollection(List<RelOptRule> rules){

        try{
            programBuilder.addRuleCollection(rules);
            return true;
        } catch(Exception e) {
            return false;
        }
    }




    public RelNode findBestExp(RelNode rel){

        HepProgram program = programBuilder.build();

        HepPlanner planner = new HepPlanner(program);
        planner.setRoot(rel);
        RelNode optimized = planner.findBestExp(); // 应用完规则后的结果


        return optimized;

    }


            
}