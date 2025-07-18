// Generated from STOCL.g4 by ANTLR 4.13.2

    package nju.ics.grammar.stocl;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link STOCLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface STOCLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link STOCLParser#specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecification(STOCLParser.SpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link STOCLParser#invExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvExpr(STOCLParser.InvExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link STOCLParser#inv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInv(STOCLParser.InvContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolBoolOp}
	 * labeled alternative in {@link STOCLParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolBoolOp(STOCLParser.OclBoolBoolOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolNot}
	 * labeled alternative in {@link STOCLParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolNot(STOCLParser.OclBoolNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolParen}
	 * labeled alternative in {@link STOCLParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolParen(STOCLParser.OclBoolParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolSetBoolFunc}
	 * labeled alternative in {@link STOCLParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolSetBoolFunc(STOCLParser.OclBoolSetBoolFuncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolEqualityExpr}
	 * labeled alternative in {@link STOCLParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolEqualityExpr(STOCLParser.OclBoolEqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolBAttr}
	 * labeled alternative in {@link STOCLParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolBAttr(STOCLParser.OclBoolBAttrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetBoolFuncIncludesAll}
	 * labeled alternative in {@link STOCLParser#setBoolFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBoolFuncIncludesAll(STOCLParser.SetBoolFuncIncludesAllContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetBoolFuncExcludesAll}
	 * labeled alternative in {@link STOCLParser#setBoolFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBoolFuncExcludesAll(STOCLParser.SetBoolFuncExcludesAllContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetBoolFuncIncludes}
	 * labeled alternative in {@link STOCLParser#setBoolFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBoolFuncIncludes(STOCLParser.SetBoolFuncIncludesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetBoolFuncExcludes}
	 * labeled alternative in {@link STOCLParser#setBoolFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBoolFuncExcludes(STOCLParser.SetBoolFuncExcludesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetBoolFuncForAll}
	 * labeled alternative in {@link STOCLParser#setBoolFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBoolFuncForAll(STOCLParser.SetBoolFuncForAllContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetBoolFuncExists}
	 * labeled alternative in {@link STOCLParser#setBoolFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBoolFuncExists(STOCLParser.SetBoolFuncExistsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetBoolFuncIsEmpty}
	 * labeled alternative in {@link STOCLParser#setBoolFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBoolFuncIsEmpty(STOCLParser.SetBoolFuncIsEmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetBoolFuncNotEmpty}
	 * labeled alternative in {@link STOCLParser#setBoolFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBoolFuncNotEmpty(STOCLParser.SetBoolFuncNotEmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetBoolFuncOne}
	 * labeled alternative in {@link STOCLParser#setBoolFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBoolFuncOne(STOCLParser.SetBoolFuncOneContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetBoolFuncIsUnique}
	 * labeled alternative in {@link STOCLParser#setBoolFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBoolFuncIsUnique(STOCLParser.SetBoolFuncIsUniqueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExprArithmetic}
	 * labeled alternative in {@link STOCLParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExprArithmetic(STOCLParser.EqualityExprArithmeticContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExprStringEqual}
	 * labeled alternative in {@link STOCLParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExprStringEqual(STOCLParser.EqualityExprStringEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExprStringNotEqual}
	 * labeled alternative in {@link STOCLParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExprStringNotEqual(STOCLParser.EqualityExprStringNotEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithParen}
	 * labeled alternative in {@link STOCLParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithParen(STOCLParser.ArithParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithUnaryMinus}
	 * labeled alternative in {@link STOCLParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithUnaryMinus(STOCLParser.ArithUnaryMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithMultDiv}
	 * labeled alternative in {@link STOCLParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithMultDiv(STOCLParser.ArithMultDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithAddSub}
	 * labeled alternative in {@link STOCLParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithAddSub(STOCLParser.ArithAddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValue}
	 * labeled alternative in {@link STOCLParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValue(STOCLParser.ArithValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Intersection}
	 * labeled alternative in {@link STOCLParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntersection(STOCLParser.IntersectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Reject}
	 * labeled alternative in {@link STOCLParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReject(STOCLParser.RejectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetRoleOrAttr}
	 * labeled alternative in {@link STOCLParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetRoleOrAttr(STOCLParser.SetRoleOrAttrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Select}
	 * labeled alternative in {@link STOCLParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelect(STOCLParser.SelectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SymmetricDifference}
	 * labeled alternative in {@link STOCLParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymmetricDifference(STOCLParser.SymmetricDifferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NfRoleAndRole}
	 * labeled alternative in {@link STOCLParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNfRoleAndRole(STOCLParser.NfRoleAndRoleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Difference}
	 * labeled alternative in {@link STOCLParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDifference(STOCLParser.DifferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AllInstances}
	 * labeled alternative in {@link STOCLParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllInstances(STOCLParser.AllInstancesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Union}
	 * labeled alternative in {@link STOCLParser#oclSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnion(STOCLParser.UnionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectId}
	 * labeled alternative in {@link STOCLParser#oclObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectId(STOCLParser.OclObjectIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectSelf}
	 * labeled alternative in {@link STOCLParser#oclObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectSelf(STOCLParser.OclObjectSelfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectVar}
	 * labeled alternative in {@link STOCLParser#oclObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectVar(STOCLParser.OclObjectVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectFRole}
	 * labeled alternative in {@link STOCLParser#oclObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectFRole(STOCLParser.OclObjectFRoleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueIntLiteral}
	 * labeled alternative in {@link STOCLParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueIntLiteral(STOCLParser.ArithValueIntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueRealLiteral}
	 * labeled alternative in {@link STOCLParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueRealLiteral(STOCLParser.ArithValueRealLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueVar}
	 * labeled alternative in {@link STOCLParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueVar(STOCLParser.ArithValueVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueFAttr}
	 * labeled alternative in {@link STOCLParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueFAttr(STOCLParser.ArithValueFAttrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueMin}
	 * labeled alternative in {@link STOCLParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueMin(STOCLParser.ArithValueMinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueMax}
	 * labeled alternative in {@link STOCLParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueMax(STOCLParser.ArithValueMaxContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueSize}
	 * labeled alternative in {@link STOCLParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueSize(STOCLParser.ArithValueSizeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueSum}
	 * labeled alternative in {@link STOCLParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueSum(STOCLParser.ArithValueSumContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueAvg}
	 * labeled alternative in {@link STOCLParser#oclArithmeticValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueAvg(STOCLParser.ArithValueAvgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueLiteral}
	 * labeled alternative in {@link STOCLParser#oclStringValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueLiteral(STOCLParser.StringValueLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueVar}
	 * labeled alternative in {@link STOCLParser#oclStringValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueVar(STOCLParser.StringValueVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueFAttr}
	 * labeled alternative in {@link STOCLParser#oclStringValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueFAttr(STOCLParser.StringValueFAttrContext ctx);
	/**
	 * Visit a parse tree produced by {@link STOCLParser#boolOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolOp(STOCLParser.BoolOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link STOCLParser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompOp(STOCLParser.CompOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarListValue}
	 * labeled alternative in {@link STOCLParser#varList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarListValue(STOCLParser.VarListValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarID}
	 * labeled alternative in {@link STOCLParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarID(STOCLParser.VarIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RoleOrAttrID}
	 * labeled alternative in {@link STOCLParser#roleOrAttr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoleOrAttrID(STOCLParser.RoleOrAttrIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FRoleID}
	 * labeled alternative in {@link STOCLParser#fRole}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFRoleID(STOCLParser.FRoleIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NfRoleID}
	 * labeled alternative in {@link STOCLParser#nfRole}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNfRoleID(STOCLParser.NfRoleIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttrID}
	 * labeled alternative in {@link STOCLParser#attr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrID(STOCLParser.AttrIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BAttrID}
	 * labeled alternative in {@link STOCLParser#bAttr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBAttrID(STOCLParser.BAttrIDContext ctx);
}