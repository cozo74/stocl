package nju.ics.grammar.ra;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

import nju.ics.grammar.ra.elements.ra.*;
import nju.ics.model.ra.RelationSchema;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class RASpecificRewriter extends RABaseVisitor<Query>{


    RelationSchema relationSchema;


    public RASpecificRewriter(RelationSchema relationSchema) {
        this.relationSchema = relationSchema;
    }




    @Override
    public Query visitRaExpr(RAParser.RaExprContext ctx) {
        List<RAParser.QueryExprContext> queryExprContexts = ctx.queryExpr();
        String database = ctx.ID().getText();
        StringBuilder result = new StringBuilder("Database " + database + ":\n");

        for (RAParser.QueryExprContext queryExprContext : queryExprContexts) {
            Query query = visit(queryExprContext);
            result.append(query.getRAExpr()).append(";\n");
        }

        System.out.println(result);
        return new Query(result.toString());
    }



    @Override
    public Query visitQueryExpr(RAParser.QueryExprContext ctx) {
        return new Query("qry : " + visit(ctx.query()).getRAExpr());
    }



    @Override
    public Query visitRelationID(RAParser.RelationIDContext ctx) {
        String id = ctx.ID().getText();
        return new Table(id, relationSchema.getRelationAttributes(id));
    }


    @Override
    public Query visitJoin(RAParser.JoinContext ctx) {
        Query leftQuery = visit(ctx.query(0));
        Query rightQuery = visit(ctx.query(1));

        if(leftQuery instanceof Projection && rightQuery instanceof Table) {
            Query originalQuery = ((Projection) leftQuery).getOriginalQuery();
            if(originalQuery instanceof Table && originalQuery.getRAExpr().equals(rightQuery.getRAExpr()) ) {
                return new Table(rightQuery.getRAExpr(), rightQuery.getAttrList());
            }
        }

        return new QueryJoin(leftQuery, rightQuery);
    }

    @Override
    public Query visitJoinWithCondition(RAParser.JoinWithConditionContext ctx) {
        Query leftQuery = visit(ctx.query(0));
        Query rightQuery = visit(ctx.query(1));
        Token start = ctx.boolExpr().getStart();
        Token stop = ctx.boolExpr().getStop();
        String boolExpr = start.getInputStream().getText(Interval.of(start.getStartIndex(), stop.getStopIndex()));


        return new QueryJoin(leftQuery, rightQuery, boolExpr);

    }


    @Override
    public Query visitQueryRelationOp(RAParser.QueryRelationOpContext ctx) {
        Query leftQuery = visit(ctx.query(0));
        Query rightQuery = visit(ctx.query(1));
        RelationOp relationOp = (RelationOp) visit(ctx.relationOp());


        if (leftQuery instanceof Selection && rightQuery instanceof Selection &&
                ((Selection) leftQuery).getOriginalQuery().getRAExpr().equals(((Selection) rightQuery).getOriginalQuery().getRAExpr())) {
            if ("Union".equals(relationOp.getRelationOp()) ) {
                String condition = ((Selection) leftQuery).getCondition() + " or " + ((Selection) rightQuery).getCondition();
                return new Selection(condition, ((Selection) leftQuery).getOriginalQuery());
            } else if ("Intersection".equals(relationOp.getRelationOp())) {
                String condition = ((Selection) leftQuery).getCondition() + " and " + ((Selection) rightQuery).getCondition();
                return new Selection(condition, ((Selection) leftQuery).getOriginalQuery());
            } else if ("Difference".equals(relationOp.getRelationOp())) {
                String condition = ((Selection) leftQuery).getCondition() + " and not " + ((Selection) rightQuery).getCondition();
                return new Selection(condition, ((Selection) leftQuery).getOriginalQuery());
            }

        }






        return new QueryOpWithoutJoin(leftQuery, rightQuery, relationOp.getRelationOp());



    }


    @Override
    public Query visitSelection(RAParser.SelectionContext ctx) {
        Query originalQuery = visit(ctx.query());
        Token start = ctx.boolExpr().getStart();
        Token stop = ctx.boolExpr().getStop();
        String boolExpr = start.getInputStream().getText(Interval.of(start.getStartIndex(), stop.getStopIndex()));


        return new Selection(boolExpr, originalQuery);
    }

    @Override
    public Query visitProjection(RAParser.ProjectionContext ctx) {

        Query originalQuery = visit(ctx.query());
        List<RAParser.ArithmeticExprContext> arithmeticExprContexts = ctx.arithmeticExpr();

        List<String> projAttrList = new ArrayList<>();
        for (RAParser.ArithmeticExprContext arithmeticExprContext : arithmeticExprContexts) {
            projAttrList.add(arithmeticExprContext.getText());
        }

        return new Projection(projAttrList, originalQuery);
    }


    @Override
    public Query visitRename(RAParser.RenameContext ctx) {
        Query originalQuery = visit(ctx.query());
        RenameList renamePairs = (RenameList) visit(ctx.renameList());

        return new Rename(renamePairs.getOldAttrList(), renamePairs.getNewAttrList(), originalQuery);
    }


    @Override
    public Query visitGroup(RAParser.GroupContext ctx) {
        Query originalQuery = visit(ctx.query());

        String aggFunc = ctx.aggFunc().getText();
        String field = ctx.field().getText();

        List<String> groupAttrList;
        if (ctx.fieldList() == null) {
            groupAttrList = new ArrayList<>();
        } else {
            groupAttrList = new ArrayList<>(asList(ctx.fieldList().getText().split(",")));
        }

        return new Group(groupAttrList,
                new ArrayList<>(asList(aggFunc)),
                new ArrayList<>(asList(field)),
                originalQuery);

    }


    @Override
    public Query visitQueryParen(RAParser.QueryParenContext ctx) {

        return visit(ctx.query());
    }


    @Override
    public Query visitProduct(RAParser.ProductContext ctx) {
        return new RelationOp(ctx.getText());
    }

    @Override
    public Query visitDifference(RAParser.DifferenceContext ctx) {
        return new RelationOp(ctx.getText());
    }

    @Override
    public Query visitUnion(RAParser.UnionContext ctx) {
        return new RelationOp(ctx.getText());
    }

    @Override
    public Query visitIntersection(RAParser.IntersectionContext ctx) {
        return new RelationOp(ctx.getText());
    }


}
