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
	 * Visit a parse tree produced by the {@code OclBoolBagPredicate}
	 * labeled alternative in {@link STOCLParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolBagPredicate(STOCLParser.OclBoolBagPredicateContext ctx);
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
	 * Visit a parse tree produced by the {@code OclBoolAndOr}
	 * labeled alternative in {@link STOCLParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolAndOr(STOCLParser.OclBoolAndOrContext ctx);
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
	 * Visit a parse tree produced by the {@code OclBoolSpatialPredicate}
	 * labeled alternative in {@link STOCLParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolSpatialPredicate(STOCLParser.OclBoolSpatialPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolImpliesXor}
	 * labeled alternative in {@link STOCLParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolImpliesXor(STOCLParser.OclBoolImpliesXorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclBoolPeriodPredicate}
	 * labeled alternative in {@link STOCLParser#oclBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclBoolPeriodPredicate(STOCLParser.OclBoolPeriodPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExprObjAttrValue}
	 * labeled alternative in {@link STOCLParser#equalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExprObjAttrValue(STOCLParser.EqualityExprObjAttrValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExprArithmetic}
	 * labeled alternative in {@link STOCLParser#equalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExprArithmetic(STOCLParser.EqualityExprArithmeticContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExprString}
	 * labeled alternative in {@link STOCLParser#equalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExprString(STOCLParser.EqualityExprStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExprObject}
	 * labeled alternative in {@link STOCLParser#equalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExprObject(STOCLParser.EqualityExprObjectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExprTimestamp}
	 * labeled alternative in {@link STOCLParser#equalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExprTimestamp(STOCLParser.EqualityExprTimestampContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueAggFunc}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueAggFunc(STOCLParser.ArithValueAggFuncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueDiv}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueDiv(STOCLParser.ArithValueDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueAbs}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueAbs(STOCLParser.ArithValueAbsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueMin}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueMin(STOCLParser.ArithValueMinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithParen}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithParen(STOCLParser.ArithParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithUnaryMinus}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithUnaryMinus(STOCLParser.ArithUnaryMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithMultDiv}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithMultDiv(STOCLParser.ArithMultDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithAddSub}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithAddSub(STOCLParser.ArithAddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueRound}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueRound(STOCLParser.ArithValueRoundContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometryDistance}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryDistance(STOCLParser.GeometryDistanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueStrSize}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueStrSize(STOCLParser.ArithValueStrSizeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueFloor}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueFloor(STOCLParser.ArithValueFloorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueMax}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueMax(STOCLParser.ArithValueMaxContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueMod}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueMod(STOCLParser.ArithValueModContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueIntLiteral}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueIntLiteral(STOCLParser.ArithValueIntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueRealLiteral}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueRealLiteral(STOCLParser.ArithValueRealLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithValueObjAttrValue}
	 * labeled alternative in {@link STOCLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueObjAttrValue(STOCLParser.ArithValueObjAttrValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STContains}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTContains(STOCLParser.STContainsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STContainsProperly}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTContainsProperly(STOCLParser.STContainsProperlyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STCoveredBy}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTCoveredBy(STOCLParser.STCoveredByContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STCovers}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTCovers(STOCLParser.STCoversContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STCrosses}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTCrosses(STOCLParser.STCrossesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STDisjoint}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTDisjoint(STOCLParser.STDisjointContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STEquals}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTEquals(STOCLParser.STEqualsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STIntersects}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTIntersects(STOCLParser.STIntersectsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STOverlaps}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTOverlaps(STOCLParser.STOverlapsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STTouches}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTTouches(STOCLParser.STTouchesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STWithin}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTWithin(STOCLParser.STWithinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STDWithin}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTDWithin(STOCLParser.STDWithinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STRelateWithGivenIMatrix}
	 * labeled alternative in {@link STOCLParser#spatialPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTRelateWithGivenIMatrix(STOCLParser.STRelateWithGivenIMatrixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometryDifference}
	 * labeled alternative in {@link STOCLParser#geom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryDifference(STOCLParser.GeometryDifferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometrySymDifference}
	 * labeled alternative in {@link STOCLParser#geom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometrySymDifference(STOCLParser.GeometrySymDifferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometryLinestring}
	 * labeled alternative in {@link STOCLParser#geom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryLinestring(STOCLParser.GeometryLinestringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometryConvexHull}
	 * labeled alternative in {@link STOCLParser#geom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryConvexHull(STOCLParser.GeometryConvexHullContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometryPolygon}
	 * labeled alternative in {@link STOCLParser#geom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryPolygon(STOCLParser.GeometryPolygonContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometryValueObjAttrValue}
	 * labeled alternative in {@link STOCLParser#geom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryValueObjAttrValue(STOCLParser.GeometryValueObjAttrValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometryIntersection}
	 * labeled alternative in {@link STOCLParser#geom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryIntersection(STOCLParser.GeometryIntersectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometryEnvelope}
	 * labeled alternative in {@link STOCLParser#geom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryEnvelope(STOCLParser.GeometryEnvelopeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometryUnion}
	 * labeled alternative in {@link STOCLParser#geom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryUnion(STOCLParser.GeometryUnionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometryBuffer}
	 * labeled alternative in {@link STOCLParser#geom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryBuffer(STOCLParser.GeometryBufferContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometryCentroid}
	 * labeled alternative in {@link STOCLParser#geom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryCentroid(STOCLParser.GeometryCentroidContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeometryPoint}
	 * labeled alternative in {@link STOCLParser#geom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryPoint(STOCLParser.GeometryPointContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PeriodContainsTimestamp}
	 * labeled alternative in {@link STOCLParser#periodPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodContainsTimestamp(STOCLParser.PeriodContainsTimestampContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PeriodContainsPeriod}
	 * labeled alternative in {@link STOCLParser#periodPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodContainsPeriod(STOCLParser.PeriodContainsPeriodContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PeriodOverlaps}
	 * labeled alternative in {@link STOCLParser#periodPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodOverlaps(STOCLParser.PeriodOverlapsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PeriodEquals}
	 * labeled alternative in {@link STOCLParser#periodPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodEquals(STOCLParser.PeriodEqualsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PeriodPrecedes}
	 * labeled alternative in {@link STOCLParser#periodPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodPrecedes(STOCLParser.PeriodPrecedesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PeriodImmediatelyPrecedes}
	 * labeled alternative in {@link STOCLParser#periodPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodImmediatelyPrecedes(STOCLParser.PeriodImmediatelyPrecedesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PeriodSucceeds}
	 * labeled alternative in {@link STOCLParser#periodPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodSucceeds(STOCLParser.PeriodSucceedsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PeriodImmediatelySucceeds}
	 * labeled alternative in {@link STOCLParser#periodPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodImmediatelySucceeds(STOCLParser.PeriodImmediatelySucceedsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PeriodTimestamp}
	 * labeled alternative in {@link STOCLParser#period}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodTimestamp(STOCLParser.PeriodTimestampContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TimestampLiteral}
	 * labeled alternative in {@link STOCLParser#timestamp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimestampLiteral(STOCLParser.TimestampLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TimestampValueObjAttrValue}
	 * labeled alternative in {@link STOCLParser#timestamp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimestampValueObjAttrValue(STOCLParser.TimestampValueObjAttrValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link STOCLParser#specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecification(STOCLParser.SpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link STOCLParser#context}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContext(STOCLParser.ContextContext ctx);
	/**
	 * Visit a parse tree produced by {@link STOCLParser#inv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInv(STOCLParser.InvContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IncludesAll}
	 * labeled alternative in {@link STOCLParser#bagPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncludesAll(STOCLParser.IncludesAllContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExcludesAll}
	 * labeled alternative in {@link STOCLParser#bagPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExcludesAll(STOCLParser.ExcludesAllContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Includes}
	 * labeled alternative in {@link STOCLParser#bagPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncludes(STOCLParser.IncludesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Excludes}
	 * labeled alternative in {@link STOCLParser#bagPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExcludes(STOCLParser.ExcludesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsEmpty}
	 * labeled alternative in {@link STOCLParser#bagPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsEmpty(STOCLParser.IsEmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotEmpty}
	 * labeled alternative in {@link STOCLParser#bagPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotEmpty(STOCLParser.NotEmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForAll}
	 * labeled alternative in {@link STOCLParser#bagPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForAll(STOCLParser.ForAllContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Exists}
	 * labeled alternative in {@link STOCLParser#bagPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExists(STOCLParser.ExistsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code One}
	 * labeled alternative in {@link STOCLParser#bagPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOne(STOCLParser.OneContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsUnique}
	 * labeled alternative in {@link STOCLParser#bagPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsUnique(STOCLParser.IsUniqueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Intersection}
	 * labeled alternative in {@link STOCLParser#oclBag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntersection(STOCLParser.IntersectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Reject}
	 * labeled alternative in {@link STOCLParser#oclBag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReject(STOCLParser.RejectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BagRoleOrAttr}
	 * labeled alternative in {@link STOCLParser#oclBag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBagRoleOrAttr(STOCLParser.BagRoleOrAttrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Select}
	 * labeled alternative in {@link STOCLParser#oclBag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelect(STOCLParser.SelectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SymmetricDifference}
	 * labeled alternative in {@link STOCLParser#oclBag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymmetricDifference(STOCLParser.SymmetricDifferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Difference}
	 * labeled alternative in {@link STOCLParser#oclBag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDifference(STOCLParser.DifferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AllInstances}
	 * labeled alternative in {@link STOCLParser#oclBag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllInstances(STOCLParser.AllInstancesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BagElementsLiteral}
	 * labeled alternative in {@link STOCLParser#oclBag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBagElementsLiteral(STOCLParser.BagElementsLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Union}
	 * labeled alternative in {@link STOCLParser#oclBag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnion(STOCLParser.UnionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MultipleRole}
	 * labeled alternative in {@link STOCLParser#oclBag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultipleRole(STOCLParser.MultipleRoleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectSelf}
	 * labeled alternative in {@link STOCLParser#oclObj}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectSelf(STOCLParser.OclObjectSelfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectVar}
	 * labeled alternative in {@link STOCLParser#oclObj}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectVar(STOCLParser.OclObjectVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OclObjectRole}
	 * labeled alternative in {@link STOCLParser#oclObj}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOclObjectRole(STOCLParser.OclObjectRoleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObjAttrValueFAttr}
	 * labeled alternative in {@link STOCLParser#objAttrValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjAttrValueFAttr(STOCLParser.ObjAttrValueFAttrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObjAttrValueVar}
	 * labeled alternative in {@link STOCLParser#objAttrValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjAttrValueVar(STOCLParser.ObjAttrValueVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueObjAttrValue}
	 * labeled alternative in {@link STOCLParser#strValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueObjAttrValue(STOCLParser.StringValueObjAttrValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueToLowerCase}
	 * labeled alternative in {@link STOCLParser#strValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueToLowerCase(STOCLParser.StringValueToLowerCaseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueAt}
	 * labeled alternative in {@link STOCLParser#strValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueAt(STOCLParser.StringValueAtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueLiteral}
	 * labeled alternative in {@link STOCLParser#strValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueLiteral(STOCLParser.StringValueLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueConcat}
	 * labeled alternative in {@link STOCLParser#strValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueConcat(STOCLParser.StringValueConcatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueToUpperCase}
	 * labeled alternative in {@link STOCLParser#strValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueToUpperCase(STOCLParser.StringValueToUpperCaseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValueSubstring}
	 * labeled alternative in {@link STOCLParser#strValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValueSubstring(STOCLParser.StringValueSubstringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralInt}
	 * labeled alternative in {@link STOCLParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralInt(STOCLParser.LiteralIntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralReal}
	 * labeled alternative in {@link STOCLParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralReal(STOCLParser.LiteralRealContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralBoolean}
	 * labeled alternative in {@link STOCLParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralBoolean(STOCLParser.LiteralBooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralString}
	 * labeled alternative in {@link STOCLParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralString(STOCLParser.LiteralStringContext ctx);
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
	 * Visit a parse tree produced by the {@code RoleID}
	 * labeled alternative in {@link STOCLParser#role}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoleID(STOCLParser.RoleIDContext ctx);
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