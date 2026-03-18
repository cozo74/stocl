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

public class STOCLTranslatorObtainViolatedTest {


    static UMLClassDiagram umlClassDiagram;
    static STOCLTranslator translator;
    static List<RelOptRule> rules;

    @BeforeAll
    public static void setUp() {
        
        String classDiagramPath = "src/test/resources/diagram/shenzhen0.json";
        umlClassDiagram = new UMLClassDiagram(classDiagramPath);

        translator = new STOCLTranslatorObtainViolated(umlClassDiagram);
              

        rules = List.of(GetAttributeSelfJoinRule.Config.DEFAULT.toRule(),
                    TwoAttributeComparisonSelfJoinRule.Config.DEFAULT.toRule(),
                    UniversalSetMinusTableFilterRule.Config.DEFAULT.toRule(),
                    IntersectTwoFilterRule.Config.DEFAULT.toRule(),
                    UnionTwoFilterRule.Config.DEFAULT.toRule(),
                    MinusTwoFilterRule.Config.DEFAULT.toRule(),
                    
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
        ParseTree tree = parser.specification();
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








}
