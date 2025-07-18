package nju.ics.grammar.stocl;



import java.util.List;

public class STOCLRewriter extends STOCLBaseVisitor<String>{

    class VarNum {
        int num;
        VarNum(int num) {
            this.num = num;
        }
        int getVarNum() {
            return num++;
        }
    }
    VarNum varNum = new VarNum(0);

    @Override
    public String visitSpecification(STOCLParser.SpecificationContext ctx) {
        List<STOCLParser.InvExprContext> invExprContexts = ctx.invExpr();
        String id = ctx.ID().getText();
        StringBuilder result = new StringBuilder("Model " + id + " :\n");

        for (STOCLParser.InvExprContext invExprContext : invExprContexts) {
            result.append(visit(invExprContext));
        }

        System.out.println(result);
        return result.toString();
    }

    @Override
    public String visitInvExpr(STOCLParser.InvExprContext ctx) {

        String className = ctx.ID().getText();
        List<STOCLParser.InvContext> inv = ctx.inv();
        StringBuilder result = new StringBuilder("context " + className + "\n");


        for (int i = 0; i < inv.size(); i++) {
            result.append(visit(inv.get(i))).append("\n");
        }

        return result.toString();
    }

    @Override
    public String visitInv(STOCLParser.InvContext ctx) {
        varNum.num = 0;
        String oclBool = visit(ctx.oclBool());
        if (ctx.ID() == null) {
            return "inv : " + oclBool;
        } else {
            return "inv " + ctx.ID().getText() + ": " + oclBool;
        }
    }


    @Override
    public String visitOclBoolNot(STOCLParser.OclBoolNotContext ctx) {
        String oclBool = visit(ctx.oclBool());
        return " not " + oclBool + " ";
    }


    @Override
    public String visitOclBoolBoolOp(STOCLParser.OclBoolBoolOpContext ctx) {
        String oclBool1 = visit(ctx.oclBool(0));
        String oclBool2 = visit(ctx.oclBool(1));
        String op = ctx.boolOp().getText();

        String result;
        switch (op) {
            case "and":
                result = " " +  oclBool1 + " and " + oclBool2 + " ";
                break;
            case "or":
                result = " " +  oclBool1 + " or " + oclBool2 + " ";
                break;
            case "xor":
                result = " ( " +  oclBool1 + " and not " + oclBool2 + " ) or ( not " +  oclBool1 + " and " + oclBool2 + " ) ";
                break;
            case "implies":
                result = " not " + oclBool1 + " or " + oclBool2 + " ";
                break;


            default:
                result =  "error";
        }
        return result;
    }


    @Override
    public String visitOclBoolEqualityExpr(STOCLParser.OclBoolEqualityExprContext ctx) {
        return visit(ctx.equalityExpr());
    }


    @Override
    public String visitOclBoolBAttr(STOCLParser.OclBoolBAttrContext ctx) {
        String oclObject = visit(ctx.oclObject());
        String bAttr = visit(ctx.bAttr());
        return oclObject + "." + bAttr;
    }


    @Override
    public String visitOclBoolSetBoolFunc(STOCLParser.OclBoolSetBoolFuncContext ctx) {
        return visit(ctx.setBoolFunc());
    }


    @Override
    public String visitOclBoolParen(STOCLParser.OclBoolParenContext ctx) {
        String oclBool = visit(ctx.oclBool());
        return '(' + oclBool + ')';
    }



    @Override
    public String visitSetBoolFuncIncludesAll(STOCLParser.SetBoolFuncIncludesAllContext ctx) {
        String oclSet1 = visit(ctx.oclSet(0));
        String oclSet2 = visit(ctx.oclSet(1));
        return oclSet1 + "->intersection(" + oclSet2 + ")->size()=" + oclSet2 + "->size()";

    }

    @Override
    public String visitSetBoolFuncExcludesAll(STOCLParser.SetBoolFuncExcludesAllContext ctx) {
        String oclSet1 = visit(ctx.oclSet(0));
        String oclSet2 = visit(ctx.oclSet(1));
        return oclSet1 + "->intersection(" + oclSet2 + ")->size()=0";
    }

    @Override
    public String visitSetBoolFuncIncludes(STOCLParser.SetBoolFuncIncludesContext ctx) {
        String oclSet = visit(ctx.oclSet());
        String oclObject = visit(ctx.oclObject());
        String vn = "v" + varNum.getVarNum();
        return oclSet + "->select(" + vn + " | " + vn + "=" + oclObject + ")->size()>0";
    }

    @Override
    public String visitSetBoolFuncExcludes(STOCLParser.SetBoolFuncExcludesContext ctx) {
        String oclSet = visit(ctx.oclSet());
        String oclObject = visit(ctx.oclObject());
        String vn = "v" + varNum.getVarNum();
        return oclSet + "->select(" + vn + " | " + vn + "=" + oclObject + ")->size()=0";
    }

    @Override
    public String visitSetBoolFuncForAll(STOCLParser.SetBoolFuncForAllContext ctx) {
        String oclSet = visit(ctx.oclSet());
        String varList = visit(ctx.varList());
        String oclBool = visit(ctx.oclBool());

        return oclSet + "->select(" + varList + " | not " + oclBool + " )->size()=0";

    }

    @Override
    public String visitSetBoolFuncExists(STOCLParser.SetBoolFuncExistsContext ctx) {
        String oclSet = visit(ctx.oclSet());
        String varList = visit(ctx.varList());
        String oclBool = visit(ctx.oclBool());

        return oclSet + "->select(" + varList + " | " + oclBool + " )->size()>0";

    }

    @Override
    public String visitSetBoolFuncIsEmpty(STOCLParser.SetBoolFuncIsEmptyContext ctx) {
        String oclSet = visit(ctx.oclSet());
        return oclSet + "->size()=0";
    }

    @Override
    public String visitSetBoolFuncNotEmpty(STOCLParser.SetBoolFuncNotEmptyContext ctx) {
        String oclSet = visit(ctx.oclSet());
        return oclSet + "->size()>0";
    }

    @Override
    public String visitSetBoolFuncOne(STOCLParser.SetBoolFuncOneContext ctx) {
        String oclSet = visit(ctx.oclSet());
        String varList = visit(ctx.varList());
        String oclBool = visit(ctx.oclBool());
        return oclSet + "->select(" + varList + " | " + oclBool + " ) " + "->size()=1";
    }

    @Override
    public String visitSetBoolFuncIsUnique(STOCLParser.SetBoolFuncIsUniqueContext ctx) {
        String oclSet = visit(ctx.oclSet());
        String attr = visit(ctx.attr());

        String v1 = "v" + varNum.getVarNum();
        String v2 = "v" + varNum.getVarNum();

        return oclSet + "->select(" + v1 + "," + v2 + "|" + v1 + "<>" + v2 + " and " + v1 + "." + attr + " = " + v2 + "." + attr + ")->size()=0";
    }




    @Override
    public String visitEqualityExprArithmetic(STOCLParser.EqualityExprArithmeticContext ctx) {
        String oclArith1 = visit(ctx.arithmeticExpr(0));
        String oclArith2 = visit(ctx.arithmeticExpr(1));
        String op = ctx.compOp().getText();
        return oclArith1 + " " + op + " " + oclArith2;
    }


    @Override
    public String visitEqualityExprStringEqual(STOCLParser.EqualityExprStringEqualContext ctx) {
        return visit(ctx.oclStringValue(0)) + " = " + visit(ctx.oclStringValue(1));
    }

    @Override
    public String visitEqualityExprStringNotEqual(STOCLParser.EqualityExprStringNotEqualContext ctx) {
        return visit(ctx.oclStringValue(0)) + " <> " + visit(ctx.oclStringValue(1));
    }


    @Override
    public String visitArithUnaryMinus(STOCLParser.ArithUnaryMinusContext ctx) {
        return "-" + visit(ctx.arithmeticExpr());
    }

    @Override
    public String visitArithMultDiv(STOCLParser.ArithMultDivContext ctx) {
        return visit(ctx.arithmeticExpr(0)) + " " + ctx.op.getText() + " " + visit(ctx.arithmeticExpr(1));
    }

    @Override
    public String visitArithAddSub(STOCLParser.ArithAddSubContext ctx) {
        return visit(ctx.arithmeticExpr(0)) + " " + ctx.op.getText() + " " + visit(ctx.arithmeticExpr(1));
    }

    @Override
    public String visitArithParen(STOCLParser.ArithParenContext ctx) {
        return "(" + visit(ctx.arithmeticExpr()) + ")";
    }

    @Override
    public String visitArithValue(STOCLParser.ArithValueContext ctx) {
        return visit(ctx.oclArithmeticValue());
    }



    @Override
    public String visitUnion(STOCLParser.UnionContext ctx) {
        String set1 = visit(ctx.oclSet(0));
        String set2 = visit(ctx.oclSet(1));
        return set1 + "->union(" + set2 + ")";
    }


    @Override
    public String visitIntersection(STOCLParser.IntersectionContext ctx) {
        String set1 = visit(ctx.oclSet(0));
        String set2 = visit(ctx.oclSet(1));
        return " " + set1 + "->intersection(" + set2 + ") ";
    }


    @Override
    public String visitSymmetricDifference(STOCLParser.SymmetricDifferenceContext ctx) {
        String set1 = visit(ctx.oclSet(0));
        String set2 = visit(ctx.oclSet(1));
        return set1 + "->difference(" + set2 + " )->union(" + set2 + "->difference(" + set1 + "))";
    }


    @Override
    public String visitDifference(STOCLParser.DifferenceContext ctx) {
        String set1 = visit(ctx.oclSet(0));
        String set2 = visit(ctx.oclSet(1));
        return set1 + "->difference(" + set2 + ")";
    }


    @Override
    public String visitSelect(STOCLParser.SelectContext ctx) {
        String set = visit(ctx.oclSet());
        String varList = visit(ctx.varList());
        String bool = visit(ctx.oclBool());
        return set + "->select( " + varList + " | " + bool + " )";
    }


    @Override
    public String visitReject(STOCLParser.RejectContext ctx) {
        String set = visit(ctx.oclSet());
        String varList = visit(ctx.varList());
        String bool = visit(ctx.oclBool());
        return set + "->select( " + varList + " | not " + bool + " )";
    }



    @Override
    public String visitSetRoleOrAttr(STOCLParser.SetRoleOrAttrContext ctx) {
        String oclSet = visit(ctx.oclSet());
        String roleOrAttr = visit(ctx.roleOrAttr());
        return oclSet + "." + roleOrAttr;
    }




    @Override
    public String visitNfRoleAndRole(STOCLParser.NfRoleAndRoleContext ctx) {
        String oclObject = visit(ctx.oclObject());
        String nfRole = visit(ctx.nfRole());
        return oclObject + "." + nfRole;
    }




    @Override
    public String visitAllInstances(STOCLParser.AllInstancesContext ctx) {
        String className = ctx.ID().getText();
        return className + ".allInstances()";
    }





    @Override
    public String visitOclObjectFRole(STOCLParser.OclObjectFRoleContext ctx) {
        String oclObject = visit(ctx.oclObject());
        String fRole = visit(ctx.fRole());
        return oclObject + "." + fRole;
    }




    @Override
    public String visitOclObjectVar(STOCLParser.OclObjectVarContext ctx) {
        return visit(ctx.var());
    }


    @Override
    public String visitOclObjectSelf(STOCLParser.OclObjectSelfContext ctx) {
        return ctx.getText();
    }


    @Override
    public String visitOclObjectId(STOCLParser.OclObjectIdContext ctx) {
        return ctx.getText();
    }



    @Override
    public String visitArithValueIntLiteral(STOCLParser.ArithValueIntLiteralContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitArithValueRealLiteral(STOCLParser.ArithValueRealLiteralContext ctx) {
        return ctx.getText();
    }


    @Override
    public String visitArithValueVar(STOCLParser.ArithValueVarContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitArithValueFAttr(STOCLParser.ArithValueFAttrContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitArithValueMin(STOCLParser.ArithValueMinContext ctx) {
        String oclSet = visit(ctx.oclSet());
        return oclSet + "->min()";
    }

    @Override
    public String visitArithValueMax(STOCLParser.ArithValueMaxContext ctx) {
        String oclSet = visit(ctx.oclSet());
        return oclSet + "->max()";
    }

    @Override
    public String visitArithValueSize(STOCLParser.ArithValueSizeContext ctx) {
        return visit(ctx.oclSet()) + "->size()";
    }

    @Override
    public String visitArithValueSum(STOCLParser.ArithValueSumContext ctx) {
        return visit(ctx.oclSet()) + "->sum()";
    }


    @Override
    public String visitArithValueAvg(STOCLParser.ArithValueAvgContext ctx) {
        return visit(ctx.oclSet()) + "->avg()";
    }


    @Override
    public String visitStringValueLiteral(STOCLParser.StringValueLiteralContext ctx) {
        return ctx.getText();
    }


    @Override
    public String visitStringValueVar(STOCLParser.StringValueVarContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitStringValueFAttr(STOCLParser.StringValueFAttrContext ctx) {
        String oclObject = visit(ctx.oclObject());
        String attr = visit(ctx.attr());
        return oclObject + "." + attr;
    }


    @Override
    public String visitBoolOp(STOCLParser.BoolOpContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitCompOp(STOCLParser.CompOpContext ctx) {
        return ctx.getText();
    }


    @Override
    public String visitVarListValue(STOCLParser.VarListValueContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitVarID(STOCLParser.VarIDContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitFRoleID(STOCLParser.FRoleIDContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitNfRoleID(STOCLParser.NfRoleIDContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitAttrID(STOCLParser.AttrIDContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitBAttrID(STOCLParser.BAttrIDContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitRoleOrAttrID(STOCLParser.RoleOrAttrIDContext ctx) {
        return ctx.getText();
    }




}
