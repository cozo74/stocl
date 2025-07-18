package nju.ics.grammar.oclcore;



import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;

import nju.ics.grammar.oclcore.elements.*;
import nju.ics.grammar.ra.RALexer;
import nju.ics.grammar.ra.RAParser;
import nju.ics.grammar.stocl.STOCLParser;
import nju.ics.grammar.utils.Utils;
import nju.ics.model.uml.UMLClassDiagram;

import static nju.ics.grammar.utils.Utils.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

public class OCLCore2RATranslator extends OCLcoreBaseVisitor<OCLElement>{


    UMLClassDiagram umlClassDiagram;

    // 当前所在select环境，即当前select中var的结构，记录varName
    Stack<String> currentVarName = new Stack<>();
    // 记录var名称与结构的map
    Map<String, OCLSet> varMap = new HashMap<>();

    boolean reverseFlag = false;

    // whether oclbool is in select context
    boolean selectFlag = false;


    public OCLCore2RATranslator(UMLClassDiagram umlClassDiagram) {
        this.umlClassDiagram = umlClassDiagram;
    }


    @Override
    public OCLElement visitSpecification(OCLcoreParser.SpecificationContext ctx) {
        List<OCLcoreParser.InvExprContext> invExprContexts = ctx.invExpr();
        String id = ctx.ID().getText();
        assert id.equals(this.umlClassDiagram.getName());

        StringBuilder result = new StringBuilder("Database " + id + " :\n");

        for (OCLcoreParser.InvExprContext invExprContext : invExprContexts) {
            result.append(visit(invExprContext).getRAExpr()).append(" \n");
        }

        System.out.println(result);
        return new OCLElement(result.toString());
    }


    @Override
    public OCLElement visitInvExpr(OCLcoreParser.InvExprContext ctx) {
        this.currentVarName.push("self");
//        String className = ctx.CLASS().getText();
        String className = ctx.ID().getText();

        assert umlClassDiagram.hasClass(className);

        // 初始化self集合
        String cid = className.toLowerCase(Locale.ENGLISH) + "_id";
        String RAExpr = "Projection { " + cid + " } { " + className + " }";
        ArrayList<String> attrList = new ArrayList<>();
        attrList.add(cid);
        ArrayList<String> assoList = new ArrayList<>();
        assoList.add(className);

        OCLSet self = new OCLSet(OCLSetType.OBJECT,
                                className,
                                null,
                                attrList,
                                assoList,
                                RAExpr,
                                RAExpr,
                        false,
                                "self");
        this.varMap.put("self", self);


        List<OCLcoreParser.InvContext> inv = ctx.inv();
        StringBuilder result = new StringBuilder();

        for (OCLcoreParser.InvContext invContext : inv) {
            result.append(visit(invContext).getRAExpr()).append(" ;\n");
        }

        return new OCLElement(result.toString());
    }



    @Override
    public OCLElement visitInv(OCLcoreParser.InvContext ctx) {

        // 得到的oclBool
        OCLSet oclBool = (OCLSet) visit(ctx.oclBool());
//        String universalSet = oclBool.getUniversalSet();

        return new OCLElement("qry: " + oclBool.getRAExpr());

    }


    // oclBool 分情况讨论

    @Override
    public OCLSet visitOclBoolNot(OCLcoreParser.OclBoolNotContext ctx) {

        if (selectFlag) {
            // 在select环境下，直接访问子节点
            OCLSet oclBool = (OCLSet) visit(ctx.oclBool());
            String RAExpr = "not " + oclBool.getRAExpr();
            return new OCLSet(null,
                    null,
                    null,
                    null,
                    null,
                    RAExpr,
                    null,
                    false);
        }


        // 若父节点是inv，做一次取反，返回not后的oclBool的访问结果，即直接访问not后的oclBool
        if(ctx.parent instanceof OCLcoreParser.InvContext) {
            return (OCLSet) visit(ctx.oclBool());
        } else {
            // 若子节点为 equalityExpr，先做一次取反，访问子节点时在做一次取反
            if(ctx.oclBool() instanceof OCLcoreParser.EqualityExprArithmeticContext){
                reverseFlag = true;
                return (OCLSet) visit(ctx.oclBool());
            }

            if(ctx.oclBool() instanceof OCLcoreParser.EqualityExprStringEqualContext){
                reverseFlag = true;
                return (OCLSet) visit(ctx.oclBool());
            }

            if(ctx.oclBool() instanceof OCLcoreParser.OclBoolObjectAttrContext){
                reverseFlag = true;
                return (OCLSet) visit(ctx.oclBool());
            }

            // 否则执行集合差操作
            OCLSet oclBool = (OCLSet) visit(ctx.oclBool());

            String universalSet = oclBool.getUniversalSet();
            String RAExpr = "(" + universalSet + " Difference " +  oclBool.getRAExpr() + ")";


            return new OCLSet(oclBool.getSetType(),
                    oclBool.getClassName(),
                    oclBool.getValueType(),
                    oclBool.getAttrList(),
                    oclBool.getAssoList(),
                    RAExpr,
                    universalSet,
                    oclBool.getIsEntirety());
        }


    }

    @Override
    public OCLSet visitOclBoolAnd(OCLcoreParser.OclBoolAndContext ctx) {

        if (selectFlag) {
            // 在select环境下，直接访问子节点
            OCLSet oclBool0 = (OCLSet) visit(ctx.oclBool(0));
            OCLSet oclBool1 = (OCLSet) visit(ctx.oclBool(1));
            String RAExpr = oclBool0.getRAExpr() + " and " + oclBool1.getRAExpr();
            return new OCLSet(null,
                    null,
                    null,
                    null,
                    null,
                    RAExpr,
                    null,
                    false);
        }



        // 若父节点是inv，做一次取反，返回not后的oclBool的访问结果
        if(ctx.parent instanceof OCLcoreParser.InvContext) {

            Token start = ctx.oclBool(0).getStart();
            Token stop = ctx.oclBool(0).getStop();
            String oclBool0 = start.getInputStream().getText(Interval.of(start.getStartIndex(), stop.getStopIndex()));

            start = ctx.oclBool(1).getStart();
            stop = ctx.oclBool(1).getStop();
            String oclBool1 = start.getInputStream().getText(Interval.of(start.getStartIndex(), stop.getStopIndex()));

//            String reExpr = "not (" + oclBool0 + ") or " + "not (" + oclBool1 + ")";
            String reExpr = "not (" + oclBool0 + " and " +  oclBool1 + ")";

            try{
                OCLCore2RATranslator oclTranslator = new OCLCore2RATranslator(umlClassDiagram);
                oclTranslator.varMap = this.varMap;
                InputStream is = new ByteArrayInputStream(reExpr.getBytes());
                CharStream input = CharStreams.fromStream(is);
                OCLcoreLexer lexer = new OCLcoreLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                OCLcoreParser parser = new OCLcoreParser(tokens);
                ParseTree tree = parser.oclBool();
                return (OCLSet) oclTranslator.visit(tree);


            } catch (Exception e) {
                throw new RuntimeException("Error: " + reExpr);
            }


        } else {


            OCLSet oclBool0 = (OCLSet) visit(ctx.oclBool(0));
            OCLSet oclBool1 = (OCLSet) visit(ctx.oclBool(1));
            assert oclBool0.getAttrList().equals(oclBool1.getAttrList());
            assert oclBool0.getIsEntirety() == oclBool1.getIsEntirety();

            String RAExpr = "(" + oclBool0.getRAExpr() + " Intersection " +  oclBool1.getRAExpr() + ")";


            return new OCLSet(oclBool0.getSetType(),
                    oclBool0.getClassName(),
                    oclBool0.getValueType(),
                    oclBool0.getAttrList(),
                    oclBool0.getAssoList(),
                    RAExpr,
                    oclBool0.getUniversalSet(),
                    oclBool0.getIsEntirety());
        }

    }

    @Override
    public OCLSet visitOclBoolOr(OCLcoreParser.OclBoolOrContext ctx) {


        if (selectFlag) {
            // 在select环境下，直接访问子节点
            OCLSet oclBool0 = (OCLSet) visit(ctx.oclBool(0));
            OCLSet oclBool1 = (OCLSet) visit(ctx.oclBool(1));
            String RAExpr = oclBool0.getRAExpr() + " or " + oclBool1.getRAExpr();
            return new OCLSet(null,
                    null,
                    null,
                    null,
                    null,
                    RAExpr,
                    null,
                    false);
        }


        // 若父节点是inv，做一次取反，返回not后的oclBool的访问结果
        if(ctx.parent instanceof OCLcoreParser.InvContext) {

            Token start = ctx.oclBool(0).getStart();
            Token stop = ctx.oclBool(0).getStop();
            String oclBool0 = start.getInputStream().getText(Interval.of(start.getStartIndex(), stop.getStopIndex()));

            start = ctx.oclBool(1).getStart();
            stop = ctx.oclBool(1).getStop();
            String oclBool1 = start.getInputStream().getText(Interval.of(start.getStartIndex(), stop.getStopIndex()));



//            String reExpr = "not (" + oclBool0 + ") and " + "not (" + oclBool1 + ")";
            String reExpr = "not (" + oclBool0 + " or " + oclBool1 + ")";

            try{
                OCLCore2RATranslator oclTranslator = new OCLCore2RATranslator(umlClassDiagram);
                oclTranslator.varMap = this.varMap;
                InputStream is = new ByteArrayInputStream(reExpr.getBytes());
                CharStream input = CharStreams.fromStream(is);
                OCLcoreLexer lexer = new OCLcoreLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                OCLcoreParser parser = new OCLcoreParser(tokens);
                ParseTree tree = parser.oclBool();
                return (OCLSet) oclTranslator.visit(tree);
            } catch (Exception e) {
                throw new RuntimeException("Error: " + reExpr);
            }


        } else {
            OCLSet oclBool0 = (OCLSet) visit(ctx.oclBool(0));
            OCLSet oclBool1 = (OCLSet) visit(ctx.oclBool(1));
            assert oclBool0.getAttrList().equals(oclBool1.getAttrList());
            assert oclBool0.getIsEntirety() == oclBool1.getIsEntirety();

            String RAExpr = "(" + oclBool0.getRAExpr() + " Union " +  oclBool1.getRAExpr() + ")";


            return new OCLSet(oclBool0.getSetType(),
                    oclBool0.getClassName(),
                    oclBool0.getValueType(),
                    oclBool0.getAttrList(),
                    oclBool0.getAssoList(),
                    RAExpr,
                    oclBool0.getUniversalSet(),
                    oclBool0.getIsEntirety());
        }

    }

    @Override
    public OCLSet visitOclBoolParen(OCLcoreParser.OclBoolParenContext ctx) {

        if (selectFlag) {
            // 在select环境下，直接访问子节点
            OCLSet oclBool = (OCLSet) visit(ctx.oclBool());
            String RAExpr = "(" +oclBool.getRAExpr() + ")";
            return new OCLSet(null,
                    null,
                    null,
                    null,
                    null,
                    RAExpr,
                    null,
                    false);
        }


        // 默认inv节点的oclBool不适用括号

        // 若父节点是inv，做一次取反，返回not后的oclBool的访问结果
        OCLSet oclBool = (OCLSet) visit(ctx.oclBool());
        if(ctx.parent instanceof OCLcoreParser.InvContext) {
            return new OCLSet(oclBool.getSetType(),
                    oclBool.getClassName(),
                    oclBool.getValueType(),
                    oclBool.getAttrList(),
                    oclBool.getAssoList(),
                    "not (" + oclBool.getRAExpr() + ")",
                    oclBool.getUniversalSet(),
                    oclBool.getIsEntirety());
        } else {
            return new OCLSet(oclBool.getSetType(),
                    oclBool.getClassName(),
                    oclBool.getValueType(),
                    oclBool.getAttrList(),
                    oclBool.getAssoList(),
                    "(" + oclBool.getRAExpr() + ")",
                    oclBool.getUniversalSet(),
                    oclBool.getIsEntirety());
        }

    }



//    @Override
//    public OCLSet visitOclBoolEquality(OCLcoreParser.OclBoolEqualityContext ctx) {
//        return (OCLSet) visit(ctx.equalityExpr());
//    }



    @Override
    public OCLSet visitOclBoolObjectAttr(OCLcoreParser.OclBoolObjectAttrContext ctx) {

        if (selectFlag) {
            // 在select环境下，直接访问子节点
            OCLSet attr = (OCLSet) visit(ctx.attr());
            String RAExpr = attr + " = true";
            return new OCLSet(null,
                    null,
                    null,
                    null,
                    null,
                    RAExpr,
                    null,
                    false);
        }



        OCLSet oclObjectSet = (OCLSet) visit(ctx.oclObject());
        String bAttr = ctx.attr().getText();
        StringBuilder attrs = concateAttrs(oclObjectSet.getAttrList(), true);
        attrs.append(", ").append(bAttr);

        String currentClass = oclObjectSet.getClassName();
        String RAExpr;

        if(ctx.parent instanceof OCLcoreParser.InvContext || ( ctx.parent instanceof OCLcoreParser.OclBoolNotContext && reverseFlag ) ) {
            RAExpr = "Selection {" + bAttr + " <> true } { " + oclObjectSet.getRAExpr() + " Join " + currentClass + " } ";
        } else {
            RAExpr = "Selection {" + bAttr + " = true } { " + oclObjectSet.getRAExpr() + " Join " + currentClass + " } ";
        }

        ArrayList<String> attrList = new ArrayList<>(oclObjectSet.getAttrList());
        attrList.add(bAttr);

        ArrayList<String> assoList = new ArrayList<>(oclObjectSet.getAssoList());
        assoList.add(currentClass);

        OCLSet contextVar = varMap.get(currentVarName.peek());
        String contextAttrs = concateAttrs(contextVar.getAttrList(), true).toString();


        return new OCLSet(contextVar.getSetType(),
                contextVar.getClassName(),
                contextVar.getValueType(),
                contextVar.getAttrList(),
                contextVar.getAssoList(),
                "Projection {" + contextAttrs + " } { " + RAExpr + " } ",
                contextVar.getRAExpr(),
                contextVar.getIsEntirety());
    }


    // equalityExpr

    @Override
    public OCLSet visitEqualityExprArithmetic(OCLcoreParser.EqualityExprArithmeticContext ctx) {


        if (selectFlag) {
            // 在select环境下，直接访问子节点
            OCLArithmeticExpr arithExpr0 = (OCLArithmeticExpr) visit(ctx.arithmeticExpr(0));
            OCLArithmeticExpr arithExpr1 = (OCLArithmeticExpr) visit(ctx.arithmeticExpr(1));
            String compOp = ctx.compOp().getText();

            String RAExpr = arithExpr0.getRAExpr() + compOp + arithExpr1.getRAExpr();
            return new OCLSet(null,
                    null,
                    null,
                    null,
                    null,
                    RAExpr,
                    null,
                    false);
        }


        OCLArithmeticExpr arithExpr0 = (OCLArithmeticExpr) visit(ctx.arithmeticExpr(0));
        OCLArithmeticExpr arithExpr1 = (OCLArithmeticExpr) visit(ctx.arithmeticExpr(1));
        String compOp = ctx.compOp().getText();
        String reCompOp;
        // 若父节点是inv，做一次取反，比较符取反
        if(ctx.parent instanceof OCLcoreParser.InvContext || ( ctx.parent instanceof OCLcoreParser.OclBoolNotContext && reverseFlag ) ) {
            reCompOp = Utils.reverseComOp(compOp);
        } else {
            reCompOp = compOp;
        }
        if (arithExpr0.getExprType() == OCLArithmeticExprType.SET && arithExpr1.getExprType() == OCLArithmeticExprType.LITERAL) {
            assert arithExpr0.getOclSet().getSetType() == OCLSetType.VALUE;
            assert arithExpr0.getOclSet().getValueType() == OCLValueType.INTEGER || arithExpr0.getOclSet().getValueType() == OCLValueType.REAL;

            String attr = arithExpr0.getOclSet().getAttrList().get(arithExpr0.getOclSet().getAttrList().size() - 1);
            String RAExpr1 = "Selection { " + attr + reCompOp + arithExpr1.getRAExpr() + " } { " + arithExpr0.getRAExpr() + " } ";
            return new OCLSet(OCLSetType.VALUE,
                    arithExpr0.getOclSet().getClassName(),
                    arithExpr0.getOclSet().getValueType(),
                    arithExpr0.getOclSet().getAttrList(),
                    arithExpr0.getOclSet().getAssoList(),
                    RAExpr1,
                    arithExpr0.getOclSet().getRAExpr(),
                    arithExpr0.getOclSet().getIsEntirety()
            );

        } else if (arithExpr0.getExprType() == OCLArithmeticExprType.LITERAL && arithExpr1.getExprType() == OCLArithmeticExprType.SET) {
            assert arithExpr1.getOclSet().getSetType() == OCLSetType.VALUE;
            assert arithExpr1.getOclSet().getValueType() == OCLValueType.INTEGER || arithExpr1.getOclSet().getValueType() == OCLValueType.REAL;

            String attr = arithExpr1.getOclSet().getAttrList().get(arithExpr1.getOclSet().getAttrList().size() - 1);

            String RAExpr1 = "Selection { " + attr + switchComOp(reCompOp) + arithExpr0.getRAExpr() + " } { " + arithExpr1.getRAExpr() + " } ";
            return new OCLSet(OCLSetType.VALUE,
                    arithExpr1.getOclSet().getClassName(),
                    arithExpr1.getOclSet().getValueType(),
                    arithExpr1.getOclSet().getAttrList(),
                    arithExpr1.getOclSet().getAssoList(),
                    RAExpr1,
                    arithExpr1.getOclSet().getRAExpr(),
                    arithExpr1.getOclSet().getIsEntirety());

        } else if (arithExpr0.getExprType() == OCLArithmeticExprType.SET && arithExpr1.getExprType() == OCLArithmeticExprType.SET) {

            // attr set op attr set
            if (arithExpr0.getOclSet().getSetType() == OCLSetType.VALUE && arithExpr1.getOclSet().getSetType() == OCLSetType.VALUE) {
                assert arithExpr0.getOclSet().getValueType() == OCLValueType.INTEGER || arithExpr0.getOclSet().getValueType() == OCLValueType.REAL;
                assert arithExpr1.getOclSet().getValueType() == OCLValueType.INTEGER || arithExpr1.getOclSet().getValueType() == OCLValueType.REAL;

                StringBuilder joinCondition = new StringBuilder();


                String varName0 = arithExpr0.getOclSet().getVarName();
                String varName1 = arithExpr1.getOclSet().getVarName();
                String lJoinAttr = arithExpr0.getOclSet().getAttrList().get(arithExpr0.getOclSet().getAttrList().size()-1);
                String rJoinAttr = arithExpr1.getOclSet().getAttrList().get(arithExpr1.getOclSet().getAttrList().size()-1);
                OCLValueType setType = arithExpr0.getOclSet().getValueType() == OCLValueType.INTEGER && arithExpr1.getOclSet().getValueType() == OCLValueType.INTEGER ? OCLValueType.INTEGER : OCLValueType.REAL;

                if ( varName0 !=null && varName0.equals(varName1)) {

                    joinCondition.append(lJoinAttr).append(reCompOp).append(rJoinAttr);

                    String RAExpr =  "Selection { " + joinCondition + " }{ "  + arithExpr0.getOclSet().getRAExpr() + " }";

                    return new OCLSet(OCLSetType.VALUE,
                            arithExpr0.getOclSet().getClassName(),
                            setType,
                            arithExpr0.getOclSet().getAttrList(),
                            arithExpr0.getOclSet().getAssoList(),
                            RAExpr,
                            arithExpr0.getOclSet().getRAExpr(),
                            arithExpr0.getOclSet().getIsEntirety());


                } else {
                    joinCondition.append(lJoinAttr).append(reCompOp).append(rJoinAttr);
                    String RAExpr =  arithExpr0.getOclSet().getRAExpr() + " Join " + joinCondition + " " + arithExpr1.getOclSet().getRAExpr();

                    return new OCLSet(OCLSetType.VALUE,
                            arithExpr0.getOclSet().getClassName(),
                            setType,
                            arithExpr0.getOclSet().getAttrList(),
                            arithExpr0.getOclSet().getAssoList(),
                            RAExpr,
                            arithExpr0.getOclSet().getRAExpr() + " Join " + arithExpr1.getOclSet().getRAExpr(),
                            arithExpr0.getOclSet().getIsEntirety());

                }



            // obj set op obj set
            } else if (arithExpr0.getOclSet().getSetType() == OCLSetType.OBJECT && arithExpr1.getOclSet().getSetType() == OCLSetType.OBJECT) {

                assert "=".equals(reCompOp) || "<>".equals(reCompOp) : "obj must '=' or '<>' to obj " ;

                String joinCondition = arithExpr0.getOclSet().getObjectId() + " " + reCompOp + " " + arithExpr1.getOclSet().getObjectId();

                String raExpr = arithExpr0.getOclSet().getRAExpr() + " Join " + joinCondition + " " + arithExpr1.getOclSet().getRAExpr();

                return new OCLSet(OCLSetType.VALUE,
                        arithExpr0.getOclSet().getClassName(),
                        null,
                        arithExpr0.getOclSet().getAttrList(),
                        arithExpr0.getOclSet().getAssoList(),
                        raExpr,
                        arithExpr0.getOclSet().getRAExpr() + " Join " + arithExpr1.getOclSet().getRAExpr(),
                        arithExpr0.getOclSet().getIsEntirety());

            } else {
                throw new RuntimeException("Error: OBJECT compare with VALUE");
            }

        } else {
            throw new RuntimeException("Error: literal compare with literal");
        }


    }


    @Override
    public OCLSet visitEqualityExprStringEqual(OCLcoreParser.EqualityExprStringEqualContext ctx) {



        if (selectFlag) {
            // 在select环境下，直接访问子节点
            OCLArithmeticExpr oclStringValue0 = (OCLArithmeticExpr) visit(ctx.oclStringValue(0));
            OCLArithmeticExpr oclStringValue1 = (OCLArithmeticExpr) visit(ctx.oclStringValue(1));
            String op = ctx.op.getText();


            String RAExpr = oclStringValue0.getRAExpr() + op + oclStringValue1.getRAExpr();
            return new OCLSet(null,
                    null,
                    null,
                    null,
                    null,
                    RAExpr,
                    null,
                    false);
        }


        OCLStringValue arith0 = (OCLStringValue) visit(ctx.oclStringValue(0));
        OCLStringValue arith1 = (OCLStringValue) visit(ctx.oclStringValue(1));
        String compOp = ctx.op.getText();
        // 若父节点是inv，做一次取反，返回not后的oclBool的访问结果
        if(ctx.parent instanceof OCLcoreParser.InvContext || ( ctx.parent instanceof OCLcoreParser.OclBoolNotContext && reverseFlag ) ) {
            compOp = "=".equals(compOp) ? "<>" : "=";
        }

        if (arith0.getValueType() == OCLStringValueType.SET && arith1.getValueType() == OCLStringValueType.LITERAL) {
            assert arith0.getOclSet().getSetType() == OCLSetType.VALUE;
            assert arith0.getOclSet().getValueType() == OCLValueType.STRING;

            String attr = arith0.getOclSet().getAttrList().get(arith0.getOclSet().getAttrList().size() - 1);
            String RAExpr1 = "Selection { " + attr + compOp + arith1.getRAExpr() + " } { " + arith0.getRAExpr() + " } ";
            return new OCLSet(OCLSetType.VALUE,
                    arith0.getOclSet().getClassName(),
                    OCLValueType.STRING,
                    arith0.getOclSet().getAttrList(),
                    arith0.getOclSet().getAssoList(),
                    RAExpr1,
                    arith0.getOclSet().getRAExpr(),
                    arith0.getOclSet().getIsEntirety());

        } else if (arith0.getValueType() == OCLStringValueType.LITERAL && arith1.getValueType() == OCLStringValueType.SET) {
            assert arith1.getOclSet().getSetType() == OCLSetType.VALUE;
            assert arith1.getOclSet().getValueType() == OCLValueType.STRING;

            String attr = arith1.getOclSet().getAttrList().get(arith1.getOclSet().getAttrList().size() - 1);
            String RAExpr1 = "Selection {" + attr + compOp + arith0.getRAExpr() + " } { " + arith1.getRAExpr() + " } ";
            return new OCLSet(OCLSetType.VALUE,
                    arith1.getOclSet().getClassName(),
                    OCLValueType.STRING,
                    arith1.getOclSet().getAttrList(),
                    arith1.getOclSet().getAssoList(),
                    RAExpr1,
                    arith1.getOclSet().getRAExpr(),
                    arith1.getOclSet().getIsEntirety());


        } else if (arith0.getValueType() == OCLStringValueType.SET && arith1.getValueType() == OCLStringValueType.SET) {
            assert arith0.getOclSet().getSetType() == OCLSetType.VALUE;
            assert arith0.getOclSet().getValueType() == OCLValueType.STRING;

            assert arith1.getOclSet().getSetType() == OCLSetType.VALUE;
            assert arith1.getOclSet().getValueType() == OCLValueType.STRING;


            StringBuilder joinCondition = new StringBuilder();


            String varName0 = arith0.getOclSet().getVarName();
            String varName1 = arith1.getOclSet().getVarName();
            String lJoinAttr = arith0.getOclSet().getAttrList().get(arith0.getOclSet().getAttrList().size()-1);
            String rJoinAttr = arith1.getOclSet().getAttrList().get(arith1.getOclSet().getAttrList().size()-1);
            OCLValueType setType = arith0.getOclSet().getValueType() == OCLValueType.INTEGER && arith1.getOclSet().getValueType() == OCLValueType.INTEGER ? OCLValueType.INTEGER : OCLValueType.REAL;

            if ( varName0 !=null && varName0.equals(varName1)) {

                joinCondition.append(lJoinAttr).append(compOp).append(rJoinAttr);

                String RAExpr =  "Selection { " + joinCondition + " }{ "  + arith0.getOclSet().getRAExpr() + " }";

                return new OCLSet(OCLSetType.VALUE,
                        arith0.getOclSet().getClassName(),
                        OCLValueType.STRING,
                        arith0.getOclSet().getAttrList(),
                        arith0.getOclSet().getAssoList(),
                        RAExpr,
                        arith0.getOclSet().getRAExpr() + " Join " + arith1.getOclSet().getRAExpr(),
                        arith0.getOclSet().getIsEntirety());


            } else {
                joinCondition.append(lJoinAttr).append(compOp).append(rJoinAttr);
                String RAExpr =  arith0.getOclSet().getRAExpr() + " Join " + joinCondition + " " + arith1.getOclSet().getRAExpr();

                return new OCLSet(OCLSetType.VALUE,
                        arith0.getOclSet().getClassName(),
                        setType,
                        arith0.getOclSet().getAttrList(),
                        arith0.getOclSet().getAssoList(),
                        RAExpr,
                        arith0.getOclSet().getRAExpr() + " Join " + arith1.getOclSet().getRAExpr(),
                        arith0.getOclSet().getIsEntirety());

            }

        } else {
            throw new RuntimeException("Error: literal compare literal");
        }

    }


    // arithmeticExpr
    @Override
    public OCLArithmeticExpr visitArithUnaryMinus(OCLcoreParser.ArithUnaryMinusContext ctx) {
        OCLArithmeticExpr arithExpr = (OCLArithmeticExpr) visit(ctx.arithmeticExpr());
        if (arithExpr.getExprType() == OCLArithmeticExprType.LITERAL) {
            return new OCLArithmeticExpr(OCLArithmeticExprType.LITERAL,
                                        null,
                                        "-" + arithExpr.getLiteral(),
                                        "-" + arithExpr.getRAExpr()
                                        );
        } else {
            ArrayList<String> attrList = new ArrayList<>(arithExpr.getOclSet().getAttrList());
            String lastAttr = "-" + attrList.get(attrList.size()-1);
            attrList.set(attrList.size()-1, lastAttr);
            StringBuilder attrs = concateAttrs(attrList, true);
            OCLSet oclSet = new OCLSet(OCLSetType.VALUE,
                                        arithExpr.getOclSet().getClassName(),
                                        arithExpr.getOclSet().getValueType(),
                                        attrList,
                                        arithExpr.getOclSet().getAssoList(),
                                        "Projection { " + attrs + " } { " + arithExpr.getOclSet().getUniversalSet() + " } ",
                                        arithExpr.getOclSet().getUniversalSet(),
                                        arithExpr.getOclSet().getIsEntirety(),
                                        arithExpr.getOclSet().getVarName()
                                        );
            return new OCLArithmeticExpr(OCLArithmeticExprType.SET,
                                        oclSet,
                                        null,
                                        oclSet.getRAExpr());
        }
    }


    @Override
    public OCLArithmeticExpr visitArithAddSub(OCLcoreParser.ArithAddSubContext ctx) {
        OCLArithmeticExpr arithExpr0 = (OCLArithmeticExpr) visit(ctx.arithmeticExpr(0));
        OCLArithmeticExpr arithExpr1 = (OCLArithmeticExpr) visit(ctx.arithmeticExpr(1));
        String op = ctx.op.getText();


        if (arithExpr0.getExprType() == OCLArithmeticExprType.LITERAL && arithExpr1.getExprType() == OCLArithmeticExprType.LITERAL){
            return new OCLArithmeticExpr(OCLArithmeticExprType.LITERAL,
                    null,
                    arithExpr0.getLiteral() + op + arithExpr1.getLiteral(),
                    arithExpr0.getLiteral() + op + arithExpr1.getLiteral());
        } else if (arithExpr0.getExprType() == OCLArithmeticExprType.SET && arithExpr1.getExprType() == OCLArithmeticExprType.LITERAL) {
            ArrayList<String> attrList = new ArrayList<>(arithExpr0.getOclSet().getAttrList());
            String lastAttr = attrList.get(attrList.size()-1) + op + arithExpr1.getLiteral();
            attrList.set(attrList.size()-1, lastAttr);
            StringBuilder attrs = concateAttrs(attrList, true);
            OCLSet oclSet = new OCLSet(OCLSetType.VALUE,
                    arithExpr0.getOclSet().getClassName(),
                    arithExpr0.getOclSet().getValueType(),
                    attrList,
                    arithExpr0.getOclSet().getAssoList(),
                    "Projection { " + attrs + " } { " + arithExpr0.getOclSet().getUniversalSet() + " } ",
                    arithExpr0.getOclSet().getUniversalSet(),
                    arithExpr0.getOclSet().getIsEntirety(),
                    arithExpr0.getOclSet().getVarName());
            return new OCLArithmeticExpr(OCLArithmeticExprType.SET,
                    oclSet,
                    null,
                    oclSet.getRAExpr());

        } else if (arithExpr0.getExprType() == OCLArithmeticExprType.LITERAL && arithExpr1.getExprType() == OCLArithmeticExprType.SET) {
            ArrayList<String> attrList = new ArrayList<>(arithExpr1.getOclSet().getAttrList());
            String lastAttr = attrList.get(attrList.size()-1) + op + arithExpr0.getLiteral();
            attrList.set(attrList.size()-1, lastAttr);
            StringBuilder attrs = concateAttrs(attrList, true);

            OCLSet oclSet = new OCLSet(OCLSetType.VALUE,
                    arithExpr1.getOclSet().getClassName(),
                    arithExpr1.getOclSet().getValueType(),
                    attrList,
                    arithExpr1.getOclSet().getAssoList(),
                    "Projection { " + attrs + " } { " + arithExpr1.getOclSet().getUniversalSet() + " } ",
                    arithExpr1.getOclSet().getUniversalSet(),
                    arithExpr1.getOclSet().getIsEntirety(),
                    arithExpr1.getOclSet().getVarName());
            return new OCLArithmeticExpr(OCLArithmeticExprType.SET,
                    oclSet,
                    null,
                    oclSet.getRAExpr());



        } else if (arithExpr0.getExprType() == OCLArithmeticExprType.SET && arithExpr1.getExprType() == OCLArithmeticExprType.SET) {
            ArrayList<String> lAttrList = new ArrayList<>(arithExpr0.getOclSet().getAttrList());
            ArrayList<String> rAttrList = new ArrayList<>(arithExpr1.getOclSet().getAttrList());
            assert lAttrList.subList(0, lAttrList.size()-1).equals(rAttrList.subList(0, rAttrList.size()-1));

            String lastAttr = lAttrList.get(lAttrList.size()-1) + op + rAttrList.get(rAttrList.size()-1);

            lAttrList.set(lAttrList.size()-1, lastAttr);
            StringBuilder attrs = concateAttrs(lAttrList, true);

            OCLSet oclSet = new OCLSet(OCLSetType.VALUE,
                    arithExpr0.getOclSet().getClassName(),
                    arithExpr0.getOclSet().getValueType(),
                    lAttrList,
                    arithExpr0.getOclSet().getAssoList(),
                    "Projection { " + attrs + " } { " + arithExpr0.getOclSet().getUniversalSet() + " } ",
                    arithExpr0.getOclSet().getUniversalSet(),
                    arithExpr0.getOclSet().getIsEntirety(),
                    arithExpr0.getOclSet().getVarName());
            return new OCLArithmeticExpr(OCLArithmeticExprType.SET,
                    oclSet,
                    null,
                    oclSet.getRAExpr());
        } else {
            throw new RuntimeException("Error: ");
        }
    }


    @Override
    public OCLArithmeticExpr visitArithMultDiv(OCLcoreParser.ArithMultDivContext ctx) {
        OCLArithmeticExpr arithExpr0 = (OCLArithmeticExpr) visit(ctx.arithmeticExpr(0));
        OCLArithmeticExpr arithExpr1 = (OCLArithmeticExpr) visit(ctx.arithmeticExpr(1));
        String op = ctx.op.getText();

        if (arithExpr0.getExprType() == OCLArithmeticExprType.LITERAL && arithExpr1.getExprType() == OCLArithmeticExprType.LITERAL){
            return new OCLArithmeticExpr(OCLArithmeticExprType.LITERAL,
                    null,
                    arithExpr0.getLiteral() + op + arithExpr1.getLiteral(),
                    arithExpr0.getLiteral() + op + arithExpr1.getLiteral());
        } else if (arithExpr0.getExprType() == OCLArithmeticExprType.SET && arithExpr1.getExprType() == OCLArithmeticExprType.LITERAL) {
            ArrayList<String> attrList = new ArrayList<>(arithExpr0.getOclSet().getAttrList());
            String lastAttr = attrList.get(attrList.size()-1) + op + arithExpr1.getLiteral();
            attrList.set(attrList.size()-1, lastAttr);
            StringBuilder attrs = concateAttrs(attrList, true);

            OCLSet oclSet = new OCLSet(OCLSetType.VALUE,
                                        arithExpr0.getOclSet().getClassName(),
                                        arithExpr0.getOclSet().getValueType(),
                                        attrList,
                                        arithExpr0.getOclSet().getAssoList(),
                                        "Projection { " + attrs + " } { " + arithExpr0.getOclSet().getUniversalSet() + " } ",
                                        arithExpr0.getOclSet().getUniversalSet(),
                                        arithExpr0.getOclSet().getIsEntirety(),
                                        arithExpr0.getOclSet().getVarName());
            return new OCLArithmeticExpr(OCLArithmeticExprType.SET,
                    oclSet,
                    null,
                    oclSet.getRAExpr());

        } else if (arithExpr0.getExprType() == OCLArithmeticExprType.LITERAL && arithExpr1.getExprType() == OCLArithmeticExprType.SET) {
            ArrayList<String> attrList = new ArrayList<>(arithExpr1.getOclSet().getAttrList());
            String lastAttr = attrList.get(attrList.size()-1) + op + arithExpr0.getLiteral();
            attrList.set(attrList.size()-1, lastAttr);
            StringBuilder attrs = concateAttrs(attrList, true);

            OCLSet oclSet = new OCLSet(OCLSetType.VALUE,
                    arithExpr1.getOclSet().getClassName(),
                    arithExpr1.getOclSet().getValueType(),
                    attrList,
                    arithExpr1.getOclSet().getAssoList(),
                    "Projection {" + attrs + " } { " + arithExpr1.getOclSet().getUniversalSet() + " } ",
                    arithExpr1.getOclSet().getUniversalSet(),
                    arithExpr1.getOclSet().getIsEntirety(),
                    arithExpr1.getOclSet().getVarName());
            return new OCLArithmeticExpr(OCLArithmeticExprType.SET,
                    oclSet,
                    null,
                    oclSet.getRAExpr());



        } else if (arithExpr0.getExprType() == OCLArithmeticExprType.SET && arithExpr1.getExprType() == OCLArithmeticExprType.SET) {
            ArrayList<String> lAttrList = new ArrayList<>(arithExpr0.getOclSet().getAttrList());
            ArrayList<String> rAttrList = new ArrayList<>(arithExpr1.getOclSet().getAttrList());
            assert lAttrList.subList(0, lAttrList.size()-1).equals(rAttrList.subList(0, rAttrList.size()-1));

            String lastAttr = lAttrList.get(lAttrList.size()-1) + op + rAttrList.get(rAttrList.size()-1);

            lAttrList.set(lAttrList.size()-1, lastAttr);
            StringBuilder attrs = concateAttrs(lAttrList, true);

            OCLSet oclSet = new OCLSet(OCLSetType.VALUE,
                    arithExpr0.getOclSet().getClassName(),
                    arithExpr0.getOclSet().getValueType(),
                    lAttrList,
                    arithExpr0.getOclSet().getAssoList(),
                    "Projection { " + attrs + " } { " + arithExpr0.getOclSet().getUniversalSet() + " } ",
                    arithExpr0.getOclSet().getUniversalSet(),
                    arithExpr0.getOclSet().getIsEntirety(),
                    arithExpr0.getOclSet().getVarName());
            return new OCLArithmeticExpr(OCLArithmeticExprType.SET,
                    oclSet,
                    null,
                    oclSet.getRAExpr());
        } else {
            throw new RuntimeException("Error: ");
        }


    }


    @Override
    public OCLArithmeticExpr visitArithParen(OCLcoreParser.ArithParenContext ctx) {
        OCLArithmeticExpr arithExpr = (OCLArithmeticExpr) visit(ctx.arithmeticExpr());

        if (arithExpr.getExprType() == OCLArithmeticExprType.LITERAL) {
            return new OCLArithmeticExpr(OCLArithmeticExprType.LITERAL,
                                        null,
                                        "(" + arithExpr.getRAExpr() + ")",
                                        "(" + arithExpr.getRAExpr() + ")");

        } else {
            ArrayList<String> attrList = new ArrayList<>(arithExpr.getOclSet().getAttrList());
            String lastAttr = " ( " + attrList.get(attrList.size()-1) + " )";
            attrList.set(attrList.size()-1, lastAttr);
            StringBuilder attrs = concateAttrs(attrList, true);

            OCLSet oclSet = new OCLSet(OCLSetType.VALUE,
                    arithExpr.getOclSet().getClassName(),
                    arithExpr.getOclSet().getValueType(),
                    attrList,
                    arithExpr.getOclSet().getAssoList(),
                    "Projection { " + attrs + " } { " + arithExpr.getOclSet().getUniversalSet() + " } ",
                    arithExpr.getOclSet().getUniversalSet(),
                    arithExpr.getOclSet().getIsEntirety(),
                    arithExpr.getOclSet().getVarName());
            return new OCLArithmeticExpr(OCLArithmeticExprType.SET,
                    oclSet,
                    null,
                    oclSet.getRAExpr());
        }
    }


    @Override
    public OCLArithmeticExpr visitArithValue(OCLcoreParser.ArithValueContext ctx) {
        return (OCLArithmeticExpr) visit(ctx.oclArithmeticValue());
    }


    @Override
    public OCLArithmeticExpr visitArithValueIntLiteral(OCLcoreParser.ArithValueIntLiteralContext ctx) {
        return new OCLArithmeticExpr(OCLArithmeticExprType.LITERAL,
                                                    null,
                                                    ctx.getText(),
                                                    ctx.getText());
    }


    @Override
    public OCLArithmeticExpr visitArithValueRealLiteral(OCLcoreParser.ArithValueRealLiteralContext ctx) {
        return new OCLArithmeticExpr(OCLArithmeticExprType.LITERAL,
                                                    null,
                                                    ctx.getText(),
                                                    ctx.getText());
    }


    @Override
    public OCLArithmeticExpr visitArithValueVar(OCLcoreParser.ArithValueVarContext ctx) {

        if (selectFlag) {
            // 在select环境下，直接访问子节点
            String var = ctx.var().getText();
            OCLSet oclSet = varMap.get(var);
            String className = oclSet.getClassName();

            String RAExpr = className.toLowerCase() + "_id";
            return new OCLArithmeticExpr(null,
                    null,
                    null,
                    RAExpr);
        }


        String var = ctx.var().getText();
        OCLSet oclSet = varMap.get(var);
        return new OCLArithmeticExpr(OCLArithmeticExprType.SET,
                                        oclSet,
                                        null,
                                        oclSet.getRAExpr());
    }



    @Override
    public OCLArithmeticExpr visitArithValueAttr(OCLcoreParser.ArithValueAttrContext ctx) {

        if (selectFlag) {
            // 在select环境下，直接访问子节点

            String RAExpr = ctx.attr().getText();
            return new OCLArithmeticExpr(null,
                    null,
                    null,
                    RAExpr);
        }





        OCLSet oclObjectSet = (OCLSet) visit(ctx.oclObject());
        String attr = ctx.attr().getText();
        StringBuilder attrs = concateAttrs(oclObjectSet.getAttrList(), true);
        attrs.append(", ").append(attr);

        String currentClass = oclObjectSet.getClassName();
        ArrayList<String> attrList = new ArrayList<>(oclObjectSet.getAttrList());
        attrList.add(attr);

        ArrayList<String> assoList = new ArrayList<>(oclObjectSet.getAssoList());
        assoList.add(currentClass);

        OCLValueType valueType = umlClassDiagram.getAttrType(currentClass, attr);

        String RAExpr = "Projection { " + attrs + " } { " + oclObjectSet.getRAExpr() + " Join " + oclObjectSet.getClassName() + " } ";

        OCLSet oclSet = new OCLSet(OCLSetType.VALUE,
                                    currentClass,
                                    valueType,
                                    attrList,
                                    assoList,
                                    RAExpr,
                                    RAExpr,
                                    oclObjectSet.getIsEntirety(),
                                    oclObjectSet.getVarName()    );

        return new OCLArithmeticExpr(OCLArithmeticExprType.SET,
                                            oclSet,
                                            null,
                                            oclSet.getRAExpr());

    }


    @Override
    public OCLArithmeticExpr visitArithSetAggFunc(OCLcoreParser.ArithSetAggFuncContext ctx) {
        OCLSet oclSet = (OCLSet) visit(ctx.oclSet());
        String aggOp = ctx.aggOp().getText();
        if(aggOp.equals("size")) {
            aggOp = "count";
        }

        assert oclSet.getSetType() == OCLSetType.VALUE
                                        || ( oclSet.getSetType() == OCLSetType.OBJECT && aggOp.equals("size") );

        if (oclSet.getIsEntirety()) {
            ArrayList<String> attrList = new ArrayList<>(oclSet.getAttrList());
            String lastAttr = aggOp + "(" + attrList.get(attrList.size()-1) + ")";

            // 字段中别名
            String lastAttrAlias = attrList.get(attrList.size()-1) + "_" + aggOp;

            String RAExpr = "Group {" + lastAttr + " } { " + oclSet.getRAExpr() + " } ";

            OCLSet oclValueSet = new OCLSet(OCLSetType.VALUE,
                    null,
                    OCLValueType.REAL,
                    new ArrayList(Arrays.asList(lastAttrAlias)),
                    null,
                    RAExpr,
                    RAExpr,
                    true);

            return new OCLArithmeticExpr(OCLArithmeticExprType.SET,
                    oclValueSet,
                    null,
                    RAExpr);
        } else {

            ArrayList<String> attrList = new ArrayList<>(oclSet.getAttrList());
            String lastAttr = aggOp + "(" + attrList.get(attrList.size()-1) + ")";
            attrList.set(attrList.size()-1, lastAttr);
            StringBuilder attrs = concateAttrs(attrList, true);

            String RAExpr = "Group { " + attrs + " } { " + oclSet.getRAExpr() + " } ";

            OCLSet oclValueSet = new OCLSet(OCLSetType.VALUE,
                    oclSet.getClassName(),
                    OCLValueType.REAL,
                    attrList,
                    oclSet.getAssoList(),
                    RAExpr,
                    RAExpr,
                    false);
            return new OCLArithmeticExpr(OCLArithmeticExprType.SET,
                    oclValueSet,
                    null,
                    RAExpr);
        }

    }




    @Override
    public OCLSet visitOclObjectSetAllInstances(OCLcoreParser.OclObjectSetAllInstancesContext ctx) {
//        String clazz = ctx.CLASS().getText();
        String clazz = ctx.ID().getText();
        String cid = clazz.toLowerCase(Locale.ENGLISH) + "_id";
        assert umlClassDiagram.hasClass(clazz);

        String RAExpr = "Projection {" + cid + " } { " + varMap.get("self").getClassName() + " } ";

        return new OCLSet(
                OCLSetType.OBJECT,
                clazz,
                null,
                new ArrayList<String>(Arrays.asList(cid)),
                new ArrayList<String>(Arrays.asList(clazz)),
                RAExpr,
                RAExpr,
                true);

    }


    @Override
    public OCLSet visitOclObjectSetRoleOrOclValueSetAttr(OCLcoreParser.OclObjectSetRoleOrOclValueSetAttrContext ctx) {
        OCLSet oclObjectSet = (OCLSet) visit(ctx.oclSet());

        assert oclObjectSet.getSetType() == OCLSetType.OBJECT;

        String roleOrAttr = ctx.roleOrAttr().getText();
        String currentClass = oclObjectSet.getClassName();
        boolean isRole = umlClassDiagram.hasRole(currentClass, roleOrAttr);


        if (isRole) {
            String role = roleOrAttr;
            String newAssoClass = umlClassDiagram.getAssoClassWithRole(currentClass, role);
            String newClassName = umlClassDiagram.getAssoEndClassWithRole(currentClass, role);
            String cid = newClassName.toLowerCase(Locale.ENGLISH) + "_id";

            ArrayList<String> attrList = new ArrayList<>(oclObjectSet.getAttrList());
            attrList.add(cid);

            ArrayList<String> assoList = new ArrayList<>(oclObjectSet.getAssoList());
            assoList.add(newAssoClass);

            StringBuilder attrs = concateAttrs(attrList, true);

            String RAExpr = "Projection { " + attrs + " } { " + oclObjectSet.getRAExpr() + " Join " + newAssoClass + " } ";

            return new OCLSet(
                    OCLSetType.OBJECT,
                    newClassName,
                    null,
                    attrList,
                    assoList,
                    RAExpr,
                    RAExpr,
                    oclObjectSet.getIsEntirety(),
                    oclObjectSet.getVarName());
        } else {
            assert umlClassDiagram.hasAttr(currentClass, roleOrAttr);

            String attr = roleOrAttr;

            StringBuilder attrs = concateAttrs(oclObjectSet.getAttrList(), true);
            attrs.append(", ").append(attr);

            ArrayList<String> attrList = new ArrayList<>(oclObjectSet.getAttrList());
            attrList.add(attr);

            ArrayList<String> assoList = new ArrayList<>(oclObjectSet.getAssoList());
            assoList.add(currentClass);

            OCLValueType valueType = umlClassDiagram.getAttrType(currentClass, attr);

            String RAExpr = "Projection { " + attrs + " } { " + oclObjectSet.getRAExpr() + " Join " + oclObjectSet.getClassName() + " } ";

            return new OCLSet(OCLSetType.VALUE,
                    currentClass,
                    valueType,
                    attrList,
                    assoList,
                    RAExpr,
                    RAExpr,
                    oclObjectSet.getIsEntirety(),
                    oclObjectSet.getVarName());
        }


    }


    @Override
    public OCLElement visitOclObjectSetNfRole(OCLcoreParser.OclObjectSetNfRoleContext ctx) {
        OCLSet oclObject = (OCLSet) visit(ctx.oclObject());
        String nfRole = ctx.nfRole().getText();
        String currentClass = oclObject.getClassName();
        String newAssoClass = umlClassDiagram.getAssoClassWithRole(currentClass, nfRole);
        String newClassName = umlClassDiagram.getAssoEndClassWithRole(currentClass, nfRole);
        String cid = newClassName.toLowerCase(Locale.ENGLISH) + "_id";

        ArrayList<String> attrList = new ArrayList<>(oclObject.getAttrList());
        attrList.add(cid);

        ArrayList<String> assoList = new ArrayList<>(oclObject.getAssoList());
        assoList.add(newAssoClass);

        StringBuilder attrs = concateAttrs(attrList, true);

        String RAExpr = "Projection { " + attrs + " } { " + oclObject.getRAExpr() + " Join " + newAssoClass + " } ";


        return new OCLSet(
                OCLSetType.OBJECT,
                newClassName,
                null,
                attrList,
                assoList,
                RAExpr,
                oclObject.getRAExpr(),
                oclObject.getIsEntirety(),
                oclObject.getVarName());
    }




    @Override
    public OCLSet visitOclSetSetOp(OCLcoreParser.OclSetSetOpContext ctx) {
        OCLSet oclSet0 = (OCLSet) visit(ctx.oclSet(0));
        OCLSet oclSet1 = (OCLSet) visit(ctx.oclSet(1));
        String setOp = ctx.setOp().getText();


        assert oclSet0.getSetType() == oclSet1.getSetType();
        assert oclSet0.getAttrList().equals(oclSet1.getAttrList());

        if("->union".equals(setOp)) {
            return new OCLSet(
                    oclSet0.getSetType(),
                    oclSet0.getClassName(),
                    oclSet0.getValueType(),
                    oclSet0.getAttrList(),
                    oclSet0.getAssoList(),
                    "(" + oclSet0.getRAExpr() + ") Union (" + oclSet1.getRAExpr() + ")",
                    "(" + oclSet0.getRAExpr() + ") Union (" + oclSet1.getRAExpr() + ")",
                    oclSet0.getIsEntirety() && oclSet1.getIsEntirety()
            );
        } else if ("->intersection".equals(setOp)) {
            return new OCLSet(
                    oclSet0.getSetType(),
                    oclSet0.getClassName(),
                    oclSet0.getValueType(),
                    oclSet0.getAttrList(),
                    oclSet0.getAssoList(),
                    "(" + oclSet0.getRAExpr() + ") Intersection (" + oclSet1.getRAExpr() + ")",
                    oclSet0.getRAExpr() + " Union " + oclSet1.getRAExpr(),
                    oclSet0.getIsEntirety() && oclSet1.getIsEntirety()
            );
        } else if ("->difference".equals(setOp)) {
            return new OCLSet(
                    oclSet0.getSetType(),
                    oclSet0.getClassName(),
                    oclSet0.getValueType(),
                    oclSet0.getAttrList(),
                    oclSet0.getAssoList(),
                    "(" + oclSet0.getRAExpr() + ") Difference (" + oclSet1.getRAExpr() + ")",
                    oclSet0.getRAExpr() + " Union " + oclSet1.getRAExpr(),
                    oclSet0.getIsEntirety() && oclSet1.getIsEntirety()
            );
        } else {
            throw new RuntimeException("Error: ");
        }

    }




    @Override
    public OCLSet visitOclSetSelect(OCLcoreParser.OclSetSelectContext ctx) {
        OCLSet oclSet = (OCLSet) visit(ctx.oclSet());

//        String var = ctx.var().getText();
//        String varList = ctx.varList().getText();
        String[] varList = ctx.varList().getText().split(",");

        for(String var : varList){

            this.varMap.put(var, new OCLSet(oclSet.getSetType(),
                                            oclSet.getClassName(),
                                            oclSet.getValueType(),
                                            oclSet.getAttrList(),
                                            oclSet.getAssoList(),
                                            oclSet.getRAExpr(),
                                            oclSet.getUniversalSet(),
                                            oclSet.getIsEntirety(),
                                            var));
        }


        OCLSet resultSet;

        if (varList.length == 2) {
            selectFlag = true;
            // oclbool in select context
            // 访问oclBool时，var变为类名小写加_id, var.attr变为attr
            OCLSet oclBool = (OCLSet) visit(ctx.oclBool());
            selectFlag = false;

            String condition = oclBool.getRAExpr();
            String raExpr = "(" + oclSet.getRAExpr() + " Join " +  oclSet.getClassName() + ")"
                    + " Join " + condition + " "
                    + "(" + oclSet.getRAExpr() + " Join " +  oclSet.getClassName() + ")";

            resultSet = new OCLSet(
                    oclSet.getSetType(),
                    oclSet.getClassName(),
                    null,
                    oclSet.getAttrList(),
                    oclSet.getAssoList(),
                    raExpr,
                    oclSet.getRAExpr(),
                    oclSet.getIsEntirety(),
                    oclSet.getVarName()
            );


        } else {

            OCLSet oclBool = (OCLSet) visit(ctx.oclBool());
            StringBuilder attrs = concateAttrs(oclSet.getAttrList(), true);
            assert new HashSet<>(oclBool.getAttrList()).containsAll(oclSet.getAttrList());

            resultSet = new OCLSet(
                    oclSet.getSetType(),
                    oclSet.getClassName(),
                    null,
                    oclSet.getAttrList(),
                    oclSet.getAssoList(),
                    "Projection { " + attrs + " } { " + oclBool.getRAExpr() + " }",
                    oclSet.getRAExpr(),
                    oclSet.getIsEntirety(),
                    oclSet.getVarName()
            );
        }


        // 清除varMap中的变量
        for(String var : varList){
            this.varMap.remove(var);
        }


        return resultSet;
    }




    @Override
    public OCLSet visitOclObjectFRole(OCLcoreParser.OclObjectFRoleContext ctx) {
        OCLSet oclObject = (OCLSet) visit(ctx.oclObject());
        String role = ctx.fRole().getText();
        String currentClass = oclObject.getClassName();
        String newAssoClass = umlClassDiagram.getAssoClassWithRole(currentClass, role);
        String newClassName = umlClassDiagram.getAssoEndClassWithRole(currentClass, role);
        String cid = newClassName.toLowerCase(Locale.ENGLISH) + "_id";

        ArrayList<String> attrList = new ArrayList<>(oclObject.getAttrList());
        attrList.add(cid);

        ArrayList<String> assoList = new ArrayList<>(oclObject.getAssoList());
        assoList.add(newAssoClass);


        StringBuilder attrs = concateAttrs(attrList, true);
        String RAExpr =  "Projection { " + attrs + " } { " + oclObject.getRAExpr() + " Join " + newAssoClass + " } ";

        return new OCLSet(
                    OCLSetType.OBJECT,
                    newClassName,
                    null,
                    attrList,
                    assoList,
                    RAExpr,
                    RAExpr,
                    oclObject.getIsEntirety());

    }


    @Override
    public OCLSet visitOclObjectVar(OCLcoreParser.OclObjectVarContext ctx) {
        return varMap.get(ctx.var().getText());
    }


    @Override
    public OCLSet visitOclObjectSelf(OCLcoreParser.OclObjectSelfContext ctx) {
        return this.varMap.get("self");
    }



    @Override
    public OCLStringValue visitOclObjectId(OCLcoreParser.OclObjectIdContext ctx) {
        return new OCLStringValue(
                OCLStringValueType.LITERAL,
                null,
                ctx.getText(),
                ctx.getText()
        );
    }


    @Override
    public OCLStringValue visitStringValueLiteral(OCLcoreParser.StringValueLiteralContext ctx) {
        return new OCLStringValue(
                OCLStringValueType.LITERAL,
                null,
                ctx.getText(),
                ctx.getText()
        );
    }

    @Override
    public OCLStringValue visitStringValueVar(OCLcoreParser.StringValueVarContext ctx) {

        if (selectFlag) {
            // 在select环境下，直接访问子节点

            OCLSet oclSet = varMap.get(ctx.var().getText());
            String className = oclSet.getClassName();

            String RAExpr = className.toLowerCase() + "_id";
            return new OCLStringValue(null,
                    null,
                    null,
                    RAExpr);
        }


        return new OCLStringValue(
                OCLStringValueType.SET,
                varMap.get(ctx.var().getText()),
                null,
                varMap.get(ctx.var().getText()).getRAExpr()
        );
    }

    @Override
    public OCLStringValue visitStringValueFAttr(OCLcoreParser.StringValueFAttrContext ctx) {

        if (selectFlag) {
            // 在select环境下，直接访问子节点

            String RAExpr = ctx.attr().getText();
            return new OCLStringValue(null,
                    null,
                    null,
                    RAExpr);
        }




        OCLSet oclObjectSet = (OCLSet) visit(ctx.oclObject());
        String attr = ctx.attr().getText();
        StringBuilder attrs = concateAttrs(oclObjectSet.getAttrList(), true);
        attrs.append(", ").append(attr);

        String currentClass = oclObjectSet.getClassName();
        ArrayList<String> attrList = new ArrayList<>(oclObjectSet.getAttrList());
        attrList.add(attr);

        ArrayList<String> assoList = new ArrayList<>(oclObjectSet.getAssoList());
        assoList.add(currentClass);

        assert umlClassDiagram.getAttrType(currentClass, attr)==OCLValueType.STRING;

        String RAExpr = "Projection { " + attrs + " } { " + oclObjectSet.getRAExpr() + " Join " + currentClass + " } ";

        OCLSet oclSet = new OCLSet(OCLSetType.VALUE,
                currentClass,
                OCLValueType.STRING,
                attrList,
                assoList,
                RAExpr,
                RAExpr,
                oclObjectSet.getIsEntirety());

        return new OCLStringValue(OCLStringValueType.SET,
                oclSet,
                null,
                oclSet.getRAExpr());

    }
}
