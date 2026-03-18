grammar STOCL;

@header {
    package nju.ics.grammar.stocl;
}


import OCL;




oclBool
        : 'not' oclBool                                 # OclBoolNot
        | oclBool boolOp=('and'|'or') oclBool           # OclBoolAndOr
        | oclBool boolOp=('implies'|'xor') oclBool      # OclBoolImpliesXor
        | equalExpr                                     # OclBoolEqualityExpr 
        | oclObj '.' bAttr                              # OclBoolBAttr  
        | bagPredicate                                   # OclBoolBagPredicate
        | '(' oclBool ')'                               # OclBoolParen
        | spatialPredicate                             # OclBoolSpatialPredicate
        | periodPredicate                               # OclBoolPeriodPredicate
        ;


equalExpr
        : objAttrValue compOp=('<' | '<=' | '=' | '>=' | '>' | '<>') objAttrValue  # EqualityExprObjAttrValue 
        | arithExpr compOp=('<' | '<=' | '=' | '>=' | '>' | '<>') arithExpr     # EqualityExprArithmetic
        | strValue compOp=('=' | '<>') strValue                                 # EqualityExprString
        | oclObj compOp=('=' | '<>') oclObj                                     # EqualityExprObject
        | timestamp compOp=('<' | '<=' | '=' | '>=' | '>' | '<>') timestamp     # EqualityExprTimestamp
        ;



arithExpr
       : '-' arithExpr                                            # ArithUnaryMinus
       | arithExpr op=('*' | '/' ) arithExpr                 # ArithMultDiv
       | arithExpr op=('+' | '-' ) arithExpr                 # ArithAddSub
       | '(' arithExpr ')'                                        # ArithParen
       | INT_LITERAL                                                   # ArithValueIntLiteral
       | REAL_LITERAL                                                  # ArithValueRealLiteral
       | objAttrValue                                                        # ArithValueObjAttrValue
       | oclBag aggFunc=('->min()'|'->max()'|'->size()'|'->sum()'|'->avg()')     # ArithValueAggFunc
       | strValue '.size()'                                                    # ArithValueStrSize
       | arithExpr '.abs()'                                                  # ArithValueAbs
       | arithExpr '.floor()'                                               # ArithValueFloor
       | arithExpr '.round()'                                               # ArithValueRound
       | arithExpr '.max(' arithExpr ')'                                 # ArithValueMax
       | arithExpr '.min(' arithExpr ')'                                 # ArithValueMin
       | arithExpr '.mod(' arithExpr ')'                                   # ArithValueMod
       | arithExpr '.div(' arithExpr ')'                                   # ArithValueDiv
       | geom '.distance(' geom ')'                   # GeometryDistance  // 距离
       ;


// geometry predicates
spatialPredicate
       : geom '.contains(' geom ')'                    # STContains
       | geom '.containsProperly(' geom ')'            # STContainsProperly
       | geom '.coveredBy(' geom ')'                   # STCoveredBy
       | geom '.covers(' geom ')'                      # STCovers
       | geom '.crosses('  geom ')'                    # STCrosses
       | geom '.disjoint(' geom ')'                    # STDisjoint
       | geom '.equals(' geom ')'                      # STEquals
       | geom '.intersects(' geom ')'                  # STIntersects
       | geom '.overlaps(' geom ')'                    # STOverlaps
       | geom '.touches(' geom ')'                     # STTouches
       | geom '.within(' geom ')'                      # STWithin
       | geom '.dWithin(' geom ',' num=(INT_LITERAL | REAL_LITERAL) ')'    # STDWithin
       | geom '.relate(' geom ',' STRING_LITERAL ')'   # STRelateWithGivenIMatrix
       ;


// Geometry operators (2D)
geom
       : POINT                                         # GeometryPoint
       | LINESTRING                                    # GeometryLinestring
       | POLYGON                                       # GeometryPolygon
       | objAttrValue                               # GeometryValueObjAttrValue
       | geom '.buffer(' num=( INT_LITERAL | REAL_LITERAL) ')'             # GeometryBuffer   // 缓冲区
       | geom '.union(' geom ')'                      # GeometryUnion
       | geom '.intersection(' geom ')'               # GeometryIntersection
       | geom '.difference(' geom ')'                 # GeometryDifference
       | geom '.symDifference(' geom ')'              # GeometrySymDifference
       | geom '.convexHull()'                         # GeometryConvexHull  // 最小凸多边形
       | geom '.centroid()'                           # GeometryCentroid  // 质心
       | geom '.envelope()'                   # GeometryEnvelope  // 单个对象最小外接矩形
       ;




periodPredicate
       : period '.contains(' timestamp ')'                       # PeriodContainsTimestamp
       | period '.contains(' period ')'                         # PeriodContainsPeriod
       | period '.overlaps(' period ')'                         # PeriodOverlaps
       | period '.equals(' period ')'                           # PeriodEquals
       | period '.precedes(' period ')'                         # PeriodPrecedes
       | period '.immediatelyPrecedes(' period ')'           # PeriodImmediatelyPrecedes
       | period '.succeeds(' period ')'                         # PeriodSucceeds
       | period '.immediatelySucceeds(' period ')'           # PeriodImmediatelySucceeds
       ;



period
       : lp=('('| '[') timestamp ',' timestamp rp=(')'| ']')            # PeriodTimestamp
       ;


timestamp
       : TIMESTAMP_LITERAL                              # TimestampLiteral
       | objAttrValue                              # TimestampValueObjAttrValue
       ;





POINT
       : 'POINT (' COORDINATES ')'
       ;

LINESTRING
       : 'LINESTRING (' COORDINATES ( ',' COORDINATES)+ ')'
       ;

POLYGON
       : 'POLYGON (' SPACE*? '('COORDINATES ',' COORDINATES ',' COORDINATES ( ',' COORDINATES)+ ')'
           SPACE*? ( ',' SPACE*? '(' COORDINATES ',' COORDINATES ',' COORDINATES ',' COORDINATES( ',' COORDINATES)+ ')' )*  SPACE*? ')'
       ;


TIMESTAMP_LITERAL
  : '"' Digit Digit Digit Digit  '-' Digit Digit '-' Digit Digit ' ' Digit Digit ':' Digit Digit ':' Digit Digit '"'
  ;


fragment COORDINATES
        : SPACE*? ( INT_LITERAL | REAL_LITERAL ) (SPACE ( INT_LITERAL | REAL_LITERAL ))+  SPACE*?
        ;

