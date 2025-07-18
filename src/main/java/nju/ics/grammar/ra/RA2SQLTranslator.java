package nju.ics.grammar.ra;


import org.antlr.v4.runtime.misc.Pair;

import nju.ics.grammar.ra.elements.sql.*;
import nju.ics.model.ra.RelationSchema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RA2SQLTranslator extends RABaseVisitor<SQLQuery>{

    RelationSchema relationSchema;


    class SubQueryAlias {

        int subQueryNum = 0;

        String get() {

            return "sq" + subQueryNum++;
        }

    }

    SubQueryAlias sq = new SubQueryAlias();


    boolean isGroupContext = false;
    String groupFieldAlias = null;
    String groupField = null;


    boolean joinConditionFlag = false;
    String leftAlias, rightAlias;
    boolean leftAliasFlag;



    public RA2SQLTranslator(RelationSchema relationSchema) {
        this.relationSchema = relationSchema;
    }



    @Override
    public SQLQuery visitRaExpr(RAParser.RaExprContext ctx) {
        List<RAParser.QueryExprContext> queryExprContexts = ctx.queryExpr();
        String database = ctx.ID().getText();
        StringBuilder result = new StringBuilder("USE " + database + ";\n");

        for (RAParser.QueryExprContext queryExprContext : queryExprContexts) {
            SQLQuery query = visit(queryExprContext);

            query.setNeedUseAlias(false);
            result.append(query.getSQLQuery()).append(";\n");
        }

        return new SQLQuery(result.toString(), null);
    }


    @Override
    public SQLQuery visitQueryExpr(RAParser.QueryExprContext ctx) {
        sq.subQueryNum = 0; 
        return visit(ctx.query());
    }


    @Override
    public SQLQuery visitRelationID(RAParser.RelationIDContext ctx) {
        String id = ctx.ID().getText();

        List<Pair<String, String>> colList = new ArrayList<>();
        for(String attr : relationSchema.getRelationAttributes(id)) {
            colList.add(new Pair<>(attr, null));
        }

        return new SQLTable(sq.get(), id, colList);
    }


    @Override
    public SQLQuery visitJoin(RAParser.JoinContext ctx) {
        SQLQuery leftQuery = visit(ctx.query(0));
        SQLQuery rightQuery = visit(ctx.query(1));

        List<Pair<String, String>> colList = new ArrayList<>();
        colList.addAll(leftQuery.getColList());

        List<Pair<String, String>> rColList = new ArrayList<>();

        for (Pair<String, String> col : rightQuery.getColList()) {
            int index = colList.indexOf(col);
            if (index != -1) {

                rColList.add(new Pair<>(rightQuery.getAlias() + "." + col.a, col.a + "_r"));
                colList.set(index, new Pair<>(leftQuery.getAlias() + "." + col.a, col.a));
            } else {

                rColList.add(col);
            }
        }

        colList.addAll(rColList);


        leftQuery.setNeedUseAlias(true);
        rightQuery.setNeedUseAlias(true);



        return new SQLJoin(sq.get(), null, leftQuery, rightQuery, colList);
    }


    @Override
    public SQLQuery visitJoinWithCondition(RAParser.JoinWithConditionContext ctx) {
        SQLQuery leftQuery = visit(ctx.query(0));
        SQLQuery rightQuery = visit(ctx.query(1));

        leftQuery.setNeedUseAlias(true);
        rightQuery.setNeedUseAlias(true);


        joinConditionFlag = true;
        leftAlias = leftQuery.getAlias();
        rightAlias = rightQuery.getAlias();

        SQLQuery sqlQuery = visit(ctx.boolExpr());

        joinConditionFlag = false;
        String condition = sqlQuery.getSQLQuery();




        List<Pair<String, String>> colList = new ArrayList<>();
        colList.addAll(leftQuery.getColList());

        List<Pair<String, String>> rColList = new ArrayList<>();

        for (Pair<String, String> col : rightQuery.getColList()) {
            int index = colList.indexOf(col);
            if (index != -1) {

                String colName = col.b != null ? col.b : col.a;
                rColList.add(new Pair<>(rightQuery.getAlias() + "." + colName, colName + "_r"));
                colList.set(index, new Pair<>(leftQuery.getAlias() + "." + colName, colName)); 
            } else {

                rColList.add(col);
            }
        }

        colList.addAll(rColList);

        leftQuery.setNeedUseAlias(true);
        rightQuery.setNeedUseAlias(true);



        return new SQLJoin(sq.get(),  condition, leftQuery, rightQuery, colList);

    }




    @Override
    public SQLQuery visitQueryRelationOp(RAParser.QueryRelationOpContext ctx) {
        SQLQuery leftQuery = visit(ctx.query(0));
        SQLQuery rightQuery = visit(ctx.query(1));
        SQLQuery op = visit(ctx.relationOp());


        Map<String, String> relationOps = new HashMap<>();
        relationOps.put("Product", " CROSS JOIN ");
        relationOps.put("Difference", " EXCEPT ");
        relationOps.put("Union", " UNION ");
        relationOps.put("Intersection", " INTERSECT ");

        List<Pair<String, String>> colList = new ArrayList<>();

        List<String> lCols = leftQuery.getCols();
        List<String> rCols = rightQuery.getCols();


        for (String col : lCols) {
            if (rCols.contains(col)) {
                colList.add(new Pair<>(col, null));
            }
        }

        List<Pair<String, String> > lColList = new ArrayList<>();
        List<Pair<String, String> > rColList = new ArrayList<>();



        for (int i = 0; i < lCols.size(); i++) {
            String col = lCols.get(i);
            int index = rCols.indexOf(col);
            if (index != -1) {
                lColList.add(new Pair<>(col, null));
                rColList.add(new Pair<>(col, null));
            }
        }


        SQLQuery lQuery, rQuery;

        if (lCols.size() !=lColList.size()) {
            leftQuery.setNeedUseAlias(true);
            lQuery = new SQLSelect(sq.get(), lColList, leftQuery, null);

        } else {
            lQuery = leftQuery;
            lQuery.setNeedUseAlias(false);
        }

        if (rCols.size() != rColList.size()) {
            rightQuery.setNeedUseAlias(true);
            rQuery = new SQLSelect(sq.get(), rColList, rightQuery, null);
        } else {
            rQuery = rightQuery;
            rQuery.setNeedUseAlias(false);
        }



        return new SQLRelationOp(sq.get(), relationOps.get(op.getSQLQuery()), lQuery, rQuery, colList, false);


    }


    @Override
    public SQLQuery visitSelection(RAParser.SelectionContext ctx) {
        SQLQuery query = visit(ctx.query());
        SQLQuery boolExpr = visit(ctx.boolExpr());

        String boolExprStr = boolExpr.getSQLQuery();
        if (isGroupContext) {
            boolExprStr = boolExprStr.replace(groupField, groupFieldAlias);
            isGroupContext = false;
            groupField = null;
            groupFieldAlias = null;
        }

        if (!(query instanceof SQLTable )) {
            query.setNeedUseAlias(true);
        }

        return new SQLSelect( sq.get(), null ,query, boolExprStr);


    }


    @Override
    public SQLQuery visitProjection(RAParser.ProjectionContext ctx) {

        List<RAParser.ArithmeticExprContext> arithmeticExprContexts = ctx.arithmeticExpr();
        SQLQuery query = visit(ctx.query());

        List<Pair<String, String> > arithmeticExprList = new ArrayList<>();
        for (RAParser.ArithmeticExprContext arithmeticExprContext : arithmeticExprContexts) {
            arithmeticExprList.add(new Pair<>(arithmeticExprContext.getText(), null));
        }


        if(query instanceof SQLSelect ) {
            SQLSelect sqlSelect = (SQLSelect) query;
            return new SQLSelect(sq.get(),
                    arithmeticExprList,
                    sqlSelect.getSubQuery(),
                    sqlSelect.getConditions());

        } else if (query instanceof SQLTable) {
            return new SQLSelect(sq.get(),
                    arithmeticExprList,
                    query,
                    null);
        } else {
            query.setNeedUseAlias(true); 
            return new SQLSelect(sq.get(), arithmeticExprList, query, null);
        }

    }



    @Override
    public SQLQuery visitGroup(RAParser.GroupContext ctx) {

        String field = ctx.field().getText();
        String aggFunc = ctx.aggFunc().getText();

        if(aggFunc.equals("size")) {
            aggFunc = "COUNT";
        } else {
            aggFunc = aggFunc.toUpperCase();
        }

        String alias = field + "_" + aggFunc.toLowerCase();

        isGroupContext = true;
        groupFieldAlias = alias;
        groupField = aggFunc.toLowerCase() + "(" + field + ")";


        SQLQuery query = visit(ctx.query());
        if (!(query instanceof SQLTable )) {
            query.setNeedUseAlias(true);
        }


        List<Pair<String, String> > colList = new ArrayList<>();


        if (ctx.fieldList() != null) {
            System.out.println("Field list in group: " + ctx.fieldList().getText());
            SQLQuery fieldList = visit(ctx.fieldList());
            String[] cols = fieldList.getSQLQuery().split(",");
            for (String col : cols) {
                colList.add(new Pair<>(col.trim(), null ));
            }
            colList.add(new Pair<>(aggFunc + "(" + field + ")",  alias) );
            return new SQLSelect(sq.get(), colList, query, null, fieldList.getSQLQuery());
        }
        else {
            colList.add(new Pair<>(aggFunc + "(" + field + ")",  alias) );
            return new SQLSelect(sq.get(), colList, query, null);
        }

    }


    @Override
    public SQLQuery visitQueryParen(RAParser.QueryParenContext ctx) {
        return visit(ctx.query());
    }



    @Override
    public SQLQuery visitProduct(RAParser.ProductContext ctx) {
        return new SQLQuery("Product", null);
    }

    @Override
    public SQLQuery visitDifference(RAParser.DifferenceContext ctx) {
        return new SQLQuery("Difference", null);
    }

    @Override
    public SQLQuery visitUnion(RAParser.UnionContext ctx) {
        return new SQLQuery("Union", null);
    }

    @Override
    public SQLQuery visitIntersection(RAParser.IntersectionContext ctx) {
        return new SQLQuery("Intersection", null);
    }


    @Override
    public SQLQuery visitBoolNot(RAParser.BoolNotContext ctx) {
        return new SQLQuery("NOT " + visit(ctx.boolExpr()).getSQLQuery() , null);
    }


    @Override
    public SQLQuery visitBoolAnd(RAParser.BoolAndContext ctx) {
        return new SQLQuery(visit(ctx.boolExpr(0)).getSQLQuery() + " AND " + visit(ctx.boolExpr(1)).getSQLQuery() , null);
    }


    @Override
    public SQLQuery visitBoolOr(RAParser.BoolOrContext ctx) {
        return new SQLQuery(visit(ctx.boolExpr(0)).getSQLQuery()  + " OR " + visit(ctx.boolExpr(1)).getSQLQuery()  , null);
    }


    @Override
    public SQLEqualityExpr visitBoolEquality(RAParser.BoolEqualityContext ctx) {

        return (SQLEqualityExpr) visit(ctx.equalityExpr());

    }


    @Override
    public SQLQuery visitBoolParen(RAParser.BoolParenContext ctx) {
        return new SQLQuery("(" + visit(ctx.boolExpr()).getSQLQuery() + ")" , null);
    }



    @Override
    public SQLEqualityExpr visitEqualityArithCompArith(RAParser.EqualityArithCompArithContext ctx) {

        if (joinConditionFlag) {
            leftAliasFlag = true;
            SQLQuery leftQuery = visit(ctx.arithmeticExpr(0));
            leftAliasFlag = false;
            SQLQuery rightQuery = visit(ctx.arithmeticExpr(1));


            return new SQLEqualityExpr(
                    leftQuery.getSQLQuery(),
                    rightQuery.getSQLQuery(),
                    ctx.compOp().getText()
            );
        }



        return new SQLEqualityExpr(
                ctx.arithmeticExpr(0).getText(),
                ctx.arithmeticExpr(1).getText(),
                ctx.compOp().getText()
        );
    }



    @Override
    public SQLEqualityExpr visitEqualityParen(RAParser.EqualityParenContext ctx) {
        return (SQLEqualityExpr) visit(ctx.equalityExpr());
    }



    @Override
    public SQLEqualityExpr visitEqualityStringEqual(RAParser.EqualityStringEqualContext ctx) {
        return new SQLEqualityExpr(
                ctx.stringValue(0).getText(),
                ctx.stringValue(1).getText(),
                "="
        );
    }





    @Override
    public SQLEqualityExpr visitEqualityStringNotEqual(RAParser.EqualityStringNotEqualContext ctx) {
        return new SQLEqualityExpr(
                ctx.stringValue(0).getText(),
                ctx.stringValue(1).getText(),
                "<>"
        );
    }


    @Override
    public SQLQuery visitStringLiteral(RAParser.StringLiteralContext ctx) {
        return new SQLQuery(ctx.getText());
    }


    @Override
    public SQLQuery visitStringField(RAParser.StringFieldContext ctx) {
        if (joinConditionFlag) {
            if (leftAliasFlag) {

                return new SQLQuery(leftAlias + "." + ctx.field().getText());
            } else {
                return new SQLQuery(rightAlias + "." + ctx.field().getText());
            }
        } else {
            return new SQLQuery(ctx.field().getText());
        }
    }



    @Override
    public SQLQuery visitArithmeticNegate(RAParser.ArithmeticNegateContext ctx) {
        return new SQLQuery("-" + visit(ctx.arithmeticExpr()).getSQLQuery());
    }


    @Override
    public SQLQuery visitArithmeticMulDiv(RAParser.ArithmeticMulDivContext ctx) {
        return new SQLQuery(visit(ctx.arithmeticExpr(0)).getSQLQuery() + " " + ctx.op + " " + visit(ctx.arithmeticExpr(1)).getSQLQuery() );
    }


    @Override
    public SQLQuery visitArithmeticAddSub(RAParser.ArithmeticAddSubContext ctx) {
        return new SQLQuery(visit(ctx.arithmeticExpr(0)).getSQLQuery() + " " + ctx.op + " " + visit(ctx.arithmeticExpr(1)).getSQLQuery() );

    }

    @Override
    public SQLQuery visitArithmeticParen(RAParser.ArithmeticParenContext ctx) {
        return new SQLQuery("(" + visit(ctx.arithmeticExpr()).getSQLQuery() + ")");
    }

    @Override
    public SQLQuery visitArithmeticExprValue(RAParser.ArithmeticExprValueContext ctx) {
        return visit(ctx.arithmeticValue());
    }


    @Override
    public SQLQuery visitArithmeticIntLiteral(RAParser.ArithmeticIntLiteralContext ctx) {
        return new SQLQuery(ctx.getText());
    }

    @Override
    public SQLQuery visitArithmeticRealLiteral(RAParser.ArithmeticRealLiteralContext ctx) {
        return new SQLQuery(ctx.getText());
    }

    @Override
    public SQLQuery visitArithmeticField(RAParser.ArithmeticFieldContext ctx) {
        if (joinConditionFlag) {
            if (leftAliasFlag) {
                return new SQLQuery(leftAlias + "." + ctx.field().getText());
            } else {
                return new SQLQuery(rightAlias + "." + ctx.field().getText());
            }
        } else {
            return new SQLQuery(ctx.field().getText());
        }

    }

    @Override
    public SQLQuery visitArithmeticAggFuncField(RAParser.ArithmeticAggFuncFieldContext ctx) {
        return new SQLQuery(ctx.getText());
    }

    @Override
    public SQLQuery visitFieldList(RAParser.FieldListContext ctx) {
        List<RAParser.FieldContext> fields = ctx.field();
        StringBuilder result = new StringBuilder();
        for (RAParser.FieldContext field : fields) {
            result.append(field.getText()).append(",");
        }
        return new SQLQuery(result.substring(0, result.length() - 1) , null);

    }

    @Override
    public SQLQuery visitRenameList(RAParser.RenameListContext ctx) {
        List<RAParser.RenamePairContext> renamePairContexts = ctx.renamePair();
        StringBuilder result = new StringBuilder();
        for (RAParser.RenamePairContext renamePairContext : renamePairContexts) {
            result.append(visit(renamePairContext)).append(",");
        }
        return new SQLQuery(result.substring(0, result.length() - 1) , null);

    }

    @Override
    public SQLQuery visitRenamePair(RAParser.RenamePairContext ctx) {
        return new SQLQuery(ctx.field(0).getText() + " AS " + ctx.field(1).getText() , null);
    }










}
