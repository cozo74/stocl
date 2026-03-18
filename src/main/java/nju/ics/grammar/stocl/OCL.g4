grammar OCL;


specification
        : 'Model' ID ':'  context* EOF
        ;


context
        : 'context' ID (inv)+
        ;

inv     : 'inv' ID? ':' oclBool
        ;

oclBool
        : 'not' oclBool                                 # OclBoolNot
        | oclBool boolOp=('and'|'or') oclBool           # OclBoolAndOr
        | oclBool boolOp=('implies'|'xor') oclBool      # OclBoolImpliesXor
        | equalExpr                                     # OclBoolEqualityExpr 
        | oclObj '.' bAttr                              # OclBoolBAttr        
        | bagPredicate                                   # OclBoolBagPredicate
        | '(' oclBool ')'                               # OclBoolParen
        ;

bagPredicate
        : oclBag '->includesAll(' oclBag ')'                 # IncludesAll
        | oclBag '->excludesAll(' oclBag ')'                 # ExcludesAll
        | oclBag '->includes(' literal ')'                 # Includes
        | oclBag '->excludes(' literal ')'                 # Excludes
        | oclBag '->isEmpty()'                                  # IsEmpty
        | oclBag '->notEmpty()'                                 # NotEmpty
        | oclBag '->forAll(' varList '|' oclBool ')'         # ForAll
        | oclBag '->exists(' varList '|' oclBool ')'         # Exists
        | oclBag '->one(' var '|' oclBool ')'                # One
        | oclBag '->isUnique(' attr ')'                      # IsUnique
        ;


oclBag
        : oclBag '->union(' oclBag ')'                               # Union
        | oclBag '->intersection(' oclBag ')'                        # Intersection
        | oclBag '->difference(' oclBag ')'                          # Difference
        | oclBag '->symmetricDifference(' oclBag ')'                 # SymmetricDifference
        | oclBag '->select(' var '|' oclBool ')'                 # Select
        | oclBag '->reject(' var '|' oclBool ')'                 # Reject
        | oclBag '->collect(' roleOrAttr ')'                         # BagRoleOrAttr
        | ID '.' 'allInstances()'                                    # AllInstances
        | oclObj '.' role                                            # MultipleRole
        | 'Bag {' literal (',' literal)* '}'                             # BagElementsLiteral
        ;



equalExpr
        : objAttrValue compOp=('<' | '<=' | '=' | '>=' | '>' | '<>') objAttrValue  # EqualityExprObjAttrValue
        | arithExpr compOp=('<' | '<=' | '=' | '>=' | '>' | '<>') arithExpr     # EqualityExprArithmetic
        | strValue compOp=('=' | '<>') strValue                                 # EqualityExprString
        | oclObj compOp=('=' | '<>') oclObj                                     # EqualityExprObject
        ;


arithExpr
        : '-' arithExpr                                                         # ArithUnaryMinus
        | arithExpr op=('*' | '/' ) arithExpr                                   # ArithMultDiv
        | arithExpr op=('+' | '-' ) arithExpr                                   # ArithAddSub
        | '(' arithExpr ')'                                                     # ArithParen
        | INT_LITERAL                                                           # ArithValueIntLiteral
        | REAL_LITERAL                                                          # ArithValueRealLiteral
        | objAttrValue                                                        # ArithValueObjAttrValue
        | oclBag aggFunc=('->min()'|'->max()'|'->size()'|'->sum()'|'->avg()')   # ArithValueAggFunc
        | strValue '.size()'                                                    # ArithValueStrSize
        | arithExpr '.abs()'                                                  # ArithValueAbs
        | arithExpr '.floor()'                                               # ArithValueFloor
        | arithExpr '.round()'                                               # ArithValueRound
        | arithExpr '.max(' arithExpr ')'                                 # ArithValueMax
        | arithExpr '.min(' arithExpr ')'                                 # ArithValueMin
        | arithExpr '.mod(' arithExpr ')'                                   # ArithValueMod
        | arithExpr '.div(' arithExpr ')'                                   # ArithValueDiv
        ;




oclObj
        : oclObj '.' role                                           # OclObjectRole
        | var                                                           # OclObjectVar
        | 'self'                                                        # OclObjectSelf
        ;

objAttrValue
        : oclObj '.' attr                                           # ObjAttrValueFAttr
        | var                                                           # ObjAttrValueVar
        ;


strValue
        : STRING_LITERAL                                                # StringValueLiteral
        | objAttrValue                                              # StringValueObjAttrValue
        | strValue '.concat(' strValue ')'                          # StringValueConcat
        | strValue '.substring(' INT_LITERAL ',' INT_LITERAL ')'    # StringValueSubstring
        | strValue '.toUpperCase()'                                 # StringValueToUpperCase
        | strValue '.toLowerCase()'                                 # StringValueToLowerCase
        | strValue '.at(' INT_LITERAL ')'                           # StringValueAt
        ;




literal
        : INT_LITERAL                        # LiteralInt
        | REAL_LITERAL                       # LiteralReal
        | BOOLEAN_LITERAL                     # LiteralBoolean
        | STRING_LITERAL                     # LiteralString
        ;


varList
        : var (',' var)*        # VarListValue
        ;


var     : ID (':' ID)?                    # VarID
        ;



roleOrAttr : ID             # RoleOrAttrID
           ;


role   : ID                    # RoleID
        ;


attr    : ID                    # AttrID
        ;

bAttr   : ID                    # BAttrID
        ;



INT_LITERAL
        : ('-')? [0-9]+
        ;

REAL_LITERAL
        : ('-')? Digits '.' Digits
        ;

STRING_LITERAL
        : '"' (~["\\\r\n] | EscapeSequence)*? '"'
        | '\'' (~["\\\r\n] | EscapeSequence)*? '\''
        ;

BOOLEAN_LITERAL
    : 'true'
    | 'false'
    ;




NEWLINE : [\r\n]+ -> skip ;

LINE_COMMENT : '--' ~[\r\n]* -> skip ;


PARA_COMMENT
    : '/*' (PARA_COMMENT | .)*? '*/' -> skip
    ;

WS      : [ \t\n\r]+ -> channel(HIDDEN) ;

SPACE   : ' ' ;



ID      : [a-zA-Z0-9_$]+ ;


CONSTANTID      : [A-Z$][A-Z0-9_$]* ;



fragment EscapeSequence
        : '\\' [btnfr"'\\]
        | '\\' ([0-3]? [0-7])? [0-7]
        | '\\' 'u'+ HexDigit HexDigit HexDigit HexDigit
        ;

fragment HexDigits
        : HexDigit ((HexDigit | '_')* HexDigit)?
        ;

fragment HexDigit
        : [0-9a-fA-F]
        ;

fragment Digits
        : [0-9]+
        ;

fragment Digit
        : [0-9]
        ;