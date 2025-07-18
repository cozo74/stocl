// Generated from OCLcore.g4 by ANTLR 4.13.2

    package nju.ics.grammar.oclcore;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link OCLcoreParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface OCLcoreVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link OCLcoreParser#specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecification(OCLcoreParser.SpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link OCLcoreParser#invExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvExpr(OCLcoreParser.InvExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link OCLcoreParser#inv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInv(OCLcoreParser.InvContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolNot}
	 * labeled alternative in {@link OCLcoreParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolNot(OCLcoreParser.OclBoolNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolParen}
	 * labeled alternative in {@link OCLcoreParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolParen(OCLcoreParser.OclBoolParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolAnd}
	 * labeled alternative in {@link OCLcoreParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolAnd(OCLcoreParser.OclBoolAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolOr}
	 * labeled alternative in {@link OCLcoreParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolOr(OCLcoreParser.OclBoolOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolObjectAttr}
	 * labeled alternative in {@link OCLcoreParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolObjectAttr(OCLcoreParser.OclBoolObjectAttrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExprStringEqual}
	 * labeled alternative in {@link OCLcoreParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExprStringEqual(OCLcoreParser.EqualityExprStringEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExprArithmetic}
	 * labeled alternative in {@link OCLcoreParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExprArithmetic(OCLcoreParser.EqualityExprArithmeticContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithParen}
	 * labeled alternative in {@link OCLcoreParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithParen(OCLcoreParser.ArithParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithUnaryMinus}
	 * labeled alternative in {@link OCLcoreParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithUnaryMinus(OCLcoreParser.ArithUnaryMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithMultDiv}
	 * labeled alternative in {@link OCLcoreParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithMultDiv(OCLcoreParser.ArithMultDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithAddSub}
	 * labeled alternative in {@link OCLcoreParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithAddSub(OCLcoreParser.ArithAddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValue}
	 * labeled alternative in {@link OCLcoreParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValue(OCLcoreParser.ArithValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueIntLiteral}
	 * labeled alternative in {@link OCLcoreParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueIntLiteral(OCLcoreParser.ArithValueIntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueRealLiteral}
	 * labeled alternative in {@link OCLcoreParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueRealLiteral(OCLcoreParser.ArithValueRealLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueVar}
	 * labeled alternative in {@link OCLcoreParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueVar(OCLcoreParser.ArithValueVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueAttr}
	 * labeled alternative in {@link OCLcoreParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueAttr(OCLcoreParser.ArithValueAttrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithSetAggFunc}
	 * labeled alternative in {@link OCLcoreParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithSetAggFunc(OCLcoreParser.ArithSetAggFuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link OCLcoreParser#aggOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAggOp(OCLcoreParser.AggOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectSetNfRole}
	 * labeled alternative in {@link OCLcoreParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectSetNfRole(OCLcoreParser.OclObjectSetNfRoleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclSetSetOp}
	 * labeled alternative in {@link OCLcoreParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclSetSetOp(OCLcoreParser.OclSetSetOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclSetSelect}
	 * labeled alternative in {@link OCLcoreParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclSetSelect(OCLcoreParser.OclSetSelectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectSetAllInstances}
	 * labeled alternative in {@link OCLcoreParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectSetAllInstances(OCLcoreParser.OclObjectSetAllInstancesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectSetRoleOrOclValueSetAttr}
	 * labeled alternative in {@link OCLcoreParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectSetRoleOrOclValueSetAttr(OCLcoreParser.OclObjectSetRoleOrOclValueSetAttrContext ctx);
	/**
	 * Visit a parse tree produced by {@link OCLcoreParser#setOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetOp(OCLcoreParser.SetOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectId}
	 * labeled alternative in {@link OCLcoreParser#oclObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectId(OCLcoreParser.OclObjectIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectSelf}
	 * labeled alternative in {@link OCLcoreParser#oclObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectSelf(OCLcoreParser.OclObjectSelfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectVar}
	 * labeled alternative in {@link OCLcoreParser#oclObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectVar(OCLcoreParser.OclObjectVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectFRole}
	 * labeled alternative in {@link OCLcoreParser#oclObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectFRole(OCLcoreParser.OclObjectFRoleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueLiteral}
	 * labeled alternative in {@link OCLcoreParser#oclStringValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueLiteral(OCLcoreParser.StringValueLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueVar}
	 * labeled alternative in {@link OCLcoreParser#oclStringValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueVar(OCLcoreParser.StringValueVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueFAttr}
	 * labeled alternative in {@link OCLcoreParser#oclStringValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueFAttr(OCLcoreParser.StringValueFAttrContext ctx);
	/**
	 * Visit a parse tree produced by {@link OCLcoreParser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompOp(OCLcoreParser.CompOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarListValue}
	 * labeled alternative in {@link OCLcoreParser#varList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarListValue(OCLcoreParser.VarListValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarID}
	 * labeled alternative in {@link OCLcoreParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarID(OCLcoreParser.VarIDContext ctx);
	/**
	 * Visit a parse tree produced by {@link OCLcoreParser#fRole}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFRole(OCLcoreParser.FRoleContext ctx);
	/**
	 * Visit a parse tree produced by {@link OCLcoreParser#nfRole}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNfRole(OCLcoreParser.NfRoleContext ctx);
	/**
	 * Visit a parse tree produced by {@link OCLcoreParser#attr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttr(OCLcoreParser.AttrContext ctx);
	/**
	 * Visit a parse tree produced by {@link OCLcoreParser#roleOrAttr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoleOrAttr(OCLcoreParser.RoleOrAttrContext ctx);
}