// Generated from RA.g4 by ANTLR 4.13.2

    package nju.ics.grammar.ra;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RAParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RAVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RAParser#raExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRaExpr(RAParser.RaExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link RAParser#queryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExpr(RAParser.QueryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JoinWithCondition}
	 * labeled alternative in {@link RAParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinWithCondition(RAParser.JoinWithConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Group}
	 * labeled alternative in {@link RAParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroup(RAParser.GroupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryParen}
	 * labeled alternative in {@link RAParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryParen(RAParser.QueryParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Selection}
	 * labeled alternative in {@link RAParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelection(RAParser.SelectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RelationID}
	 * labeled alternative in {@link RAParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationID(RAParser.RelationIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Projection}
	 * labeled alternative in {@link RAParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProjection(RAParser.ProjectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Join}
	 * labeled alternative in {@link RAParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoin(RAParser.JoinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryRelationOp}
	 * labeled alternative in {@link RAParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryRelationOp(RAParser.QueryRelationOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Rename}
	 * labeled alternative in {@link RAParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRename(RAParser.RenameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Product}
	 * labeled alternative in {@link RAParser#relationOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProduct(RAParser.ProductContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Difference}
	 * labeled alternative in {@link RAParser#relationOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDifference(RAParser.DifferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Union}
	 * labeled alternative in {@link RAParser#relationOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnion(RAParser.UnionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Intersection}
	 * labeled alternative in {@link RAParser#relationOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntersection(RAParser.IntersectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolNot}
	 * labeled alternative in {@link RAParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolNot(RAParser.BoolNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolEquality}
	 * labeled alternative in {@link RAParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolEquality(RAParser.BoolEqualityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolParen}
	 * labeled alternative in {@link RAParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolParen(RAParser.BoolParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolOr}
	 * labeled alternative in {@link RAParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolOr(RAParser.BoolOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolAnd}
	 * labeled alternative in {@link RAParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolAnd(RAParser.BoolAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityArithCompArith}
	 * labeled alternative in {@link RAParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityArithCompArith(RAParser.EqualityArithCompArithContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityParen}
	 * labeled alternative in {@link RAParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityParen(RAParser.EqualityParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityStringEqual}
	 * labeled alternative in {@link RAParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityStringEqual(RAParser.EqualityStringEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityStringNotEqual}
	 * labeled alternative in {@link RAParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityStringNotEqual(RAParser.EqualityStringNotEqualContext ctx);
	/**
	 * Visit a parse tree produced by {@link RAParser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompOp(RAParser.CompOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link RAParser#stringValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(RAParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringField}
	 * labeled alternative in {@link RAParser#stringValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringField(RAParser.StringFieldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithmeticAddSub}
	 * labeled alternative in {@link RAParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticAddSub(RAParser.ArithmeticAddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithmeticNegate}
	 * labeled alternative in {@link RAParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticNegate(RAParser.ArithmeticNegateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithmeticExprValue}
	 * labeled alternative in {@link RAParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticExprValue(RAParser.ArithmeticExprValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithmeticParen}
	 * labeled alternative in {@link RAParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticParen(RAParser.ArithmeticParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithmeticMulDiv}
	 * labeled alternative in {@link RAParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticMulDiv(RAParser.ArithmeticMulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithmeticIntLiteral}
	 * labeled alternative in {@link RAParser#arithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticIntLiteral(RAParser.ArithmeticIntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithmeticRealLiteral}
	 * labeled alternative in {@link RAParser#arithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticRealLiteral(RAParser.ArithmeticRealLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithmeticField}
	 * labeled alternative in {@link RAParser#arithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticField(RAParser.ArithmeticFieldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithmeticAggFuncField}
	 * labeled alternative in {@link RAParser#arithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticAggFuncField(RAParser.ArithmeticAggFuncFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link RAParser#aggFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAggFunc(RAParser.AggFuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link RAParser#fieldList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldList(RAParser.FieldListContext ctx);
	/**
	 * Visit a parse tree produced by {@link RAParser#renameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRenameList(RAParser.RenameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link RAParser#renamePair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRenamePair(RAParser.RenamePairContext ctx);
	/**
	 * Visit a parse tree produced by {@link RAParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(RAParser.FieldContext ctx);
}