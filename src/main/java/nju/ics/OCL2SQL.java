package nju.ics;



import nju.ics.grammar.oclcore.OCLCore2RATranslator;
import nju.ics.grammar.oclcore.OCLcoreLexer;
import nju.ics.grammar.oclcore.OCLcoreParser;
import nju.ics.grammar.oclcore.elements.OCLElement;
import nju.ics.grammar.ra.*;
import nju.ics.grammar.ra.elements.ra.Query;
import nju.ics.grammar.ra.elements.sql.SQLQuery;
import nju.ics.grammar.stocl.STOCLLexer;
import nju.ics.grammar.stocl.STOCLParser;
import nju.ics.grammar.stocl.STOCLRewriter;
import nju.ics.model.uml.UMLClassDiagram;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;






public class OCL2SQL {


    STOCLRewriter stoclRewriter;
    OCLCore2RATranslator oclTranslator;

    RASpecificRewriter specificRewriter;
    RAGeneralRewriter generalRewriter;

    RA2SQLTranslator raTranslator;


    public OCL2SQL(String classDiagramPath) throws IOException {

        stoclRewriter = new STOCLRewriter();

        UMLClassDiagram umlClassDiagram = new UMLClassDiagram(classDiagramPath);

        oclTranslator = new OCLCore2RATranslator(umlClassDiagram);
        generalRewriter = new RAGeneralRewriter(umlClassDiagram.toRelationSchema());
        specificRewriter = new RASpecificRewriter(umlClassDiagram.toRelationSchema());
        raTranslator = new RA2SQLTranslator(umlClassDiagram.toRelationSchema());

    }


    private String oclRewrite(String raString) throws IOException {
        InputStream is = new ByteArrayInputStream(raString.getBytes());
        CharStream input = CharStreams.fromStream(is);
        STOCLLexer lexer = new STOCLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        STOCLParser parser = new STOCLParser(tokens);
        ParseTree tree = parser.specification();
        return stoclRewriter.visit(tree);
    }


    private String oclTranslate(String raString) throws IOException {
        InputStream is = new ByteArrayInputStream(raString.getBytes());
        CharStream input = CharStreams.fromStream(is);
        OCLcoreLexer lexer = new OCLcoreLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        OCLcoreParser parser = new OCLcoreParser(tokens);
        ParseTree tree = parser.specification();
        OCLElement raExpr = oclTranslator.visit(tree);
        return raExpr.getRAExpr();
    }


    private String genRewrite(String raString) throws IOException {
        InputStream is = new ByteArrayInputStream(raString.getBytes());
        CharStream input = CharStreams.fromStream(is);
        RALexer lexer = new RALexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RAParser parser = new RAParser(tokens);
        ParseTree tree = parser.raExpr();
        Query reExpr = generalRewriter.visit(tree);

        return reExpr.getRAExpr();
    }


    private String specRewrite(String raString) throws IOException {
        InputStream is = new ByteArrayInputStream(raString.getBytes());
        CharStream input = CharStreams.fromStream(is);
        RALexer lexer = new RALexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RAParser parser = new RAParser(tokens);
        ParseTree tree = parser.raExpr();
        Query raExpr = specificRewriter.visit(tree);

        return raExpr.getRAExpr();
    }


    private SQLQuery raTranslate(String raString) throws IOException {
        InputStream is = new ByteArrayInputStream(raString.getBytes());
        CharStream input = CharStreams.fromStream(is);
        RALexer lexer = new RALexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RAParser parser = new RAParser(tokens);
        ParseTree tree = parser.raExpr();

        return raTranslator.visit(tree);

    }


    public static String ocl2sql(String classDiagramPath, String oclExpr) throws IOException {

        OCL2SQL ocl2sql = new OCL2SQL(classDiagramPath);

        String expr, result;


        expr= ocl2sql.oclRewrite(oclExpr);

        expr = ocl2sql.oclTranslate(expr);

        String newExpr = expr;

        for(int i = 1; i < 10; i++) {
            System.out.println("SpecRewrite Iteration: " + i);
            newExpr = ocl2sql.specRewrite(expr);

            System.out.println("GenRewrite Iteration: " + i);
            newExpr = ocl2sql.genRewrite(newExpr);

            if(newExpr.equals(expr)) {
                break;
            } else {
                expr = newExpr;
            }
        }

        expr = ocl2sql.raTranslate(expr).getSQLQuery();
        System.out.println(expr);

        result = expr;



        return result;

    }



    public static void main(String[] args) throws IOException {

        String classDiagrampath = "src/main/resources/diagram/shenzhen.json";

        String oclExpr = "Model shenzhen: \n "
                         + "context Car inv: self.speed <= 120 \n"
                         + "inv: self.speed <= 120 * 1.1 \n"
                         + "inv: self.direction>=0 and self.direction<=360 \n"
                         + "inv: Car.allInstances()->forAll(c|c.speed<120) \n";


        String sql = OCL2SQL.ocl2sql(classDiagrampath, oclExpr);

        



    }





}
