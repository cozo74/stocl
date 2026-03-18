package nju.ics.grammar.translator;

import nju.ics.grammar.stocl.STOCLLexer;
import nju.ics.grammar.stocl.STOCLParser;

import nju.ics.grammar.translator.elements.Context;
import nju.ics.grammar.translator.elements.Inv;
import nju.ics.grammar.translator.elements.Specification;
import nju.ics.model.uml.UMLClassDiagram;
import nju.ics.optimize.RAOptimizer;
import nju.ics.optimize.rules.rbo.GetAttributeSelfJoinRule;
import nju.ics.optimize.rules.rbo.IntersectTwoFilterRule;
import nju.ics.optimize.rules.rbo.MinusTwoFilterRule;
import nju.ics.optimize.rules.rbo.TwoAttributeComparisonSelfJoinRule;
import nju.ics.optimize.rules.rbo.UnionTwoFilterRule;
import nju.ics.optimize.rules.rbo.UniversalSetMinusTableFilterRule;


import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.plan.hep.HepMatchOrder;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.rel2sql.RelToSqlConverter;
import org.apache.calcite.sql.SqlDialect;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.dialect.MysqlSqlDialect;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.apache.calcite.rel.rules.CoreRules;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class STOCLTranslatorTest {



    static UMLClassDiagram umlClassDiagram;
    static STOCLTranslator translator;
    static List<RelOptRule> rules;






    @BeforeAll
    public static void setUp() {
        
        String classDiagramPath = "src/test/resources/diagram/shenzhen0.json";
        umlClassDiagram = new UMLClassDiagram(classDiagramPath);

        translator = new STOCLTranslator(umlClassDiagram);
              

        rules = List.of(GetAttributeSelfJoinRule.Config.DEFAULT.toRule(),
                    TwoAttributeComparisonSelfJoinRule.Config.DEFAULT.toRule(),
                    UniversalSetMinusTableFilterRule.Config.DEFAULT.toRule(),
                    IntersectTwoFilterRule.Config.DEFAULT.toRule(),
                    UnionTwoFilterRule.Config.DEFAULT.toRule(),
                    MinusTwoFilterRule.Config.DEFAULT.toRule(),
                    
                    CoreRules.INTERSECT_FILTER_TO_FILTER,
                    CoreRules.UNION_FILTER_TO_FILTER,
                    CoreRules.FILTER_PROJECT_TRANSPOSE,
                    CoreRules.PROJECT_MERGE
                    );
    }


    public static void tranAndOptimize(String oclExpr, List<RelOptRule> rules, boolean showOCL, boolean showOrigRA, boolean showOptRA, boolean showSQL) throws IOException {


        InputStream is = new ByteArrayInputStream(oclExpr.getBytes());
        CharStream input = CharStreams.fromStream(is);
        STOCLLexer lexer = new STOCLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        STOCLParser parser = new STOCLParser(tokens);

        ParseTree tree = parser.specification(); // 你的入口规则
//        System.out.println(Trees.toStringTree(tree, Arrays.asList(parser.getRuleNames())));


        try {
            STOCLTranslator translator = new STOCLTranslator(umlClassDiagram);



            Specification spec = (Specification) translator.visit(tree);


            // 选择 MySQL 方言
            SqlDialect mysqlDialect = MysqlSqlDialect.DEFAULT;
            RelToSqlConverter mysqlConverter = new RelToSqlConverter(mysqlDialect);



            for(Context context : spec.getContexts()){
                for(Inv inv : context.getInvariants()){

                    if(showOCL) System.out.println("context " + context.getContextName() + " " + inv.getInvExprString() + "\n");

                    if(showOrigRA) System.out.println(RelOptUtil.toString(inv.getRelNode()));


                    RAOptimizer optimizer = new RAOptimizer(HepMatchOrder.BOTTOM_UP, 100);



                    optimizer.addRuleCollection(rules);


                    RelNode optimized = optimizer.findBestExp(inv.getRelNode());

                    if(showOptRA) System.out.println(RelOptUtil.toString(optimized));


                    SqlNode sqlNode = mysqlConverter.visitRoot(optimized).asStatement();

                    String sql = sqlNode.toSqlString(MysqlSqlDialect.DEFAULT).getSql();

                    if(showSQL) System.out.println(sql);
                    System.out.println();
                }


            }



        } catch (Exception e) {
            System.err.println("Error processing tree : " + e.getMessage());
        }





    }





    @Test
    public void attrComparison() throws IOException {



        String oclExpr =  """
                            Model shenzhen0:
                            context Car 
                                inv: self.speed >= 0
                                inv: self.speed <= 120 * 1.1
                                inv: self.direction >= 0
                                inv : self.direction <= 360
                                inv : self.status <= 100
                            context Location 
                                inv : self.latitude < self.longitude
                            """;


        tranAndOptimize(oclExpr, rules, true, false, false, true);



    }




    @Test
    public void arithOperation() throws IOException {



        String oclExpr =  """
                            Model shenzhen0:
                            context Car
                                inv: self.speed <= 120 * 1.1
                                inv: self.direction / 10 <= 36

                            context Location
                                inv: self.longitude > self.latitude - 1 
                                inv: self.latitude + 1 < self.longitude 
                            """;





        tranAndOptimize(oclExpr, rules, true, false, false, true);



    }




    @Test
    public void logicalOperation() throws IOException {



        String oclExpr =  """
                            Model shenzhen0:

                            context Car
                                inv : not (self.speed < 0 )
                                inv : self.speed > 0 and self.speed < 120
                                inv : self.status = 0 or self.status = 1
                                inv : self.status = 0 xor self.speed > 0
                                inv : self.speed > 120 implies self.status = 1
                                inv : self.direction >= 0 and self.direction <= 360


                            context Location
                                inv : self.longitude >= 113.7167 and self.longitude <= 114.6333
                                inv : self.latitude > 22.4000 and self.latitude < 22.8667

                            """;


        tranAndOptimize(oclExpr, rules, true, false, false, true);



    }






    @Test
    public void setFunction() throws IOException {



        String oclExpr =  """
                            Model shenzhen0:

                            context Car

                                inv : self.locate->notEmpty()
                                inv : self.locate->isEmpty()
                                inv : self.locate->collect(recordTime)->notEmpty()
                                
                                inv : self.locate->collect(recordTime)->isEmpty()

                                inv : self.locate->forAll(l | l.recordTime >= 0)
                                inv : self.locate->exists(l | l.recordTime >= 0)
                                inv : self.locate->one(l | l.recordTime >= 0)
                                inv : self.locate->isUnique(recordTime)

                                inv : self.locate->collect(recordTime)->includes(0)
                                inv : self.locate->collect(recordTime)->excludes(0)

                                -- single with single
                                inv : Location.allInstances()->includesAll(Location.allInstances()->select(l | l.recordTime >= 0))
                                -- single with grouped
                                inv : Location.allInstances()->includesAll(self.locate)
                                -- grouped with single
                                inv : self.locate->includesAll(Location.allInstances())
                                -- grouped with grouped
                                inv : self.locate->includesAll(self.locate)


                                inv : self.locate->size()>0

                            
                            context Location
                                -- isEmpty()和notEmpty()语义存在点问题，当为空时，为true，应返回所有对象，但没有对象，返回仍然为空
                                inv : Location.allInstances()->isEmpty()
                                inv : Location.allInstances()->notEmpty()


                                inv : Location.allInstances()->forAll(l | l.recordTime >= 0)
                                inv : Location.allInstances()->exists(l | l.recordTime >= 0)
                                inv : Location.allInstances()->one(l | l.recordTime >= 0)
                                inv : Location.allInstances()->isUnique(recordTime)

                            """;



        tranAndOptimize(oclExpr, rules, true, true, true, true);




    }




    @Test
    public void setOperation() throws IOException {



        String oclExpr =  """
                            Model shenzhen0:

                            context Car

                                -- single set
                                inv : Car.allInstances()->select(c | c.status = 0 or c.status = 1)->size() > 0
                                inv : Car.allInstances()->reject(c | c.status = 0)->size() > 0

                                -- grouped set
                                inv : self.locate->select(l | l.recordTime > 0)->size() > 0
                                inv : self.locate->reject(l | l.recordTime > 0)->size() > 0


                                -- single with single
                                inv : Car.allInstances()->select(c | c.status = 1)->union(Car.allInstances()->select(c | c.status = 0))->size() > 10
                                inv : Car.allInstances()->select(c | c.status = 1)->intersection(Car.allInstances()->select(c | c.status = 0))->size() > 10
                                inv : Car.allInstances()->select(c | c.status = 1)->difference(Car.allInstances()->select(c | c.status = 0))->size() > 10
                                inv : Car.allInstances()->select(c | c.status = 1)->symmetricDifference(Car.allInstances()->select(c | c.status = 0))->size() > 10
                                
                                -- single with grouped
                                inv : Location.allInstances()->union(self.locate)->size() < 10
                                inv : Location.allInstances()->intersection(self.locate)->size() < 10
                                inv : Location.allInstances()->difference(self.locate)->size() < 10
                                inv : Location.allInstances()->symmetricDifference(self.locate)->size() < 10

                                -- grouped with single
                                inv : self.locate->union(Location.allInstances())->size() < 10
                                inv : self.locate->intersection(Location.allInstances())->size() < 10
                                inv : self.locate->difference(Location.allInstances())->size() < 10
                                inv : self.locate->symmetricDifference(Location.allInstances())->size() < 10

                                -- grouped with grouped
                                inv : self.locate->union(self.locate)->size() < 10
                                inv : self.locate->intersection(self.locate)->size() < 10
                                inv : self.locate->difference(self.locate)->size() < 10
                                inv : self.locate->symmetricDifference(self.locate)->size() < 10


                                inv : Car.allInstances()->collect(status)->union(Bag {2,3,4,5,6})->size()>5
                                inv : Bag {2,3,4,5,6}->select(n | n>3)->size()>0
                                inv : Bag {2.1,3.1,4.1,5.1,6.1}->select(n | n>3)->size()>0
                                inv : Bag {"2","3","4","5"}->select(n | n="3")->size()>0
                            """;

        tranAndOptimize(oclExpr, rules, true, true, true, true);



    }




    @Test
    public void nested() throws IOException {


        String oclExpr =  """
                            Model shenzhen0:
                            context Car

                                inv : self.locate->forAll(l1, l2 | l1.recordTime <> l2.recordTime)
                                inv : Car.allInstances()->select(c | c.locate->select(l | l.recordTime > 0)->size() > 0)->size() > 10


                            """;

        tranAndOptimize(oclExpr, rules, true, true, true, true);



    }





    @Test
    public void arithOp() throws IOException {


        String oclExpr =  """
                            Model shenzhen0:
                            context Car

                                inv : self.plate.size() = 8
                                inv : self.direction.abs() <= 180
                                inv : 10.abs() <= 10
                                inv : (1+10).abs() <= 10
                                
                                inv : self.direction.floor() <= 180
                                inv : self.direction.round() <= 180
                                
                                inv : self.direction.max(10) <= 180
                                inv : 6.max(10) <= 180
                                inv : self.direction.min(10) <= 180
                                inv : self.direction.mod(10) <= 180
                                inv : self.direction.div(10) <= 180

                            """;

        tranAndOptimize(oclExpr, rules, true, false, false, true);



    }




    @Test
    public void stringOp() throws IOException {


        String oclExpr =  """
                            Model shenzhen0:
                            context Car

                                inv : self.plate.concat("1") = "1"
                                inv : "1".concat("1") = "11"
                                inv : self.plate.substring(1, 3) = "11"
                                inv : self.plate.toUpperCase() = "11"
                                inv : self.plate.toLowerCase() = "11"
                                inv : self.plate.at(1) = "1"


                            """;

        tranAndOptimize(oclExpr, rules, true, false, false, true);



    }







}
