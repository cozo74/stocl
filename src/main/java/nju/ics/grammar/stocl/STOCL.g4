grammar STOCL;

@header {
    package nju.ics.grammar.stocl;
}

specification
        : 'Model' ID ':'  invExpr* EOF
        ;


invExpr
        : 'context' ID (inv)+
        ;

inv     : 'inv' ID? ':' oclBool
        ;

oclBool
        : 'not' oclBool                                         # OclBoolNot
        | oclBool boolOp oclBool                                # OclBoolBoolOp
        | equalityExpr                                          # OclBoolEqualityExpr
        | oclObject '.' bAttr                                   # OclBoolBAttr
        | setBoolFunc                                           # OclBoolSetBoolFunc
        | '(' oclBool ')'                                       # OclBoolParen
        ;

setBoolFunc
        : oclSet '->includesAll(' oclSet ')'                 # SetBoolFuncIncludesAll
        | oclSet '->excludesAll(' oclSet ')'                 # SetBoolFuncExcludesAll
        | oclSet '->includes(' oclObject ')'                 # SetBoolFuncIncludes
        | oclSet '->excludes(' oclObject ')'                 # SetBoolFuncExcludes
        | oclSet '->forAll(' varList '|' oclBool ')'         # SetBoolFuncForAll
        | oclSet '->exists(' varList '|' oclBool ')'         # SetBoolFuncExists
        | oclSet '->isEmpty()'                                  # SetBoolFuncIsEmpty
        | oclSet '->notEmpty()'                                 # SetBoolFuncNotEmpty
        | oclSet '->one(' varList '|' oclBool ')'                # SetBoolFuncOne
        | oclSet '->isUnique(' attr ')'                      # SetBoolFuncIsUnique
        ;


equalityExpr
        : arithmeticExpr compOp arithmeticExpr                      # EqualityExprArithmetic
        | oclStringValue '=' oclStringValue                         # EqualityExprStringEqual
        | oclStringValue '<>' oclStringValue                        # EqualityExprStringNotEqual
        ;


arithmeticExpr
        : '-' arithmeticExpr                                            # ArithUnaryMinus
        | arithmeticExpr op=('*' | '/' ) arithmeticExpr                 # ArithMultDiv
        | arithmeticExpr op=('+' | '-' ) arithmeticExpr                 # ArithAddSub
        | '(' arithmeticExpr ')'                                        # ArithParen
        | oclArithmeticValue                                            # ArithValue
        ;


oclSet
        : oclSet '->union(' oclSet ')'                               # Union
        | oclSet '->intersection(' oclSet ')'                        # Intersection
        | oclSet '->symmetricDifference(' oclSet ')'                 # SymmetricDifference
        | oclSet '->difference(' oclSet ')'                          # Difference
        | oclSet '->select(' varList '|' oclBool ')'                     # Select
        | oclSet '->reject(' varList '|' oclBool ')'                     # Reject
        | oclSet '.' roleOrAttr                                               # SetRoleOrAttr
        | oclObject '.' nfRole                                          # NfRoleAndRole
        | ID ('.'|'::') 'allInstances()'                                       # AllInstances
        ;



oclObject
        : oclObject '.' fRole                                           # OclObjectFRole
        | var                                                           # OclObjectVar
        | 'self'                                                        # OclObjectSelf
        | STRING_LITERAL                                                # OclObjectId
        ;


oclArithmeticValue
        : INT_LITERAL                                                   # ArithValueIntLiteral
        | REAL_LITERAL                                                  # ArithValueRealLiteral
        | var                                                           # ArithValueVar
        | oclObject '.' attr                                           # ArithValueFAttr
        | oclSet '->min()'                                              # ArithValueMin
        | oclSet '->max()'                                              # ArithValueMax
        | oclSet '->size()'                                             # ArithValueSize
        | oclSet '->sum()'                                              # ArithValueSum
        | oclSet '->avg()'                                              # ArithValueAvg
        ;



oclStringValue
        : STRING_LITERAL                                                # StringValueLiteral
        | var                                                           # StringValueVar
        | oclObject '.' attr                                           # StringValueFAttr
        ;



boolOp
        : 'and'
        | 'or'
        | 'xor'
        | 'implies'
        ;


compOp
        : '<'
        | '<='
        | '='
        | '>='
        | '>'
        | '<>'
        ;



varList
        : var (',' var)*        # VarListValue
        ;


var     : ID (':' ID)?                    # VarID
        ;




roleOrAttr : ID             # RoleOrAttrID
           ;



fRole   : ID                    # FRoleID
        ;

nfRole  : ID                    # NfRoleID
        ;

attr    : ID                    # AttrID
        ;

bAttr   : ID                    # BAttrID
        ;




INT_LITERAL
        : ('-')? [0-9]+
        | NULL_LITERAL
        ;

REAL_LITERAL
        : ('-')? Digits '.' Digits
        | NULL_LITERAL
        ;

STRING_LITERAL
        : '"' (~["\\\r\n] | EscapeSequence)* '"'
        | '\'' (~["\\\r\n] | EscapeSequence)* '\''
        | NULL_LITERAL
        ;


NULL_LITERAL
    : 'null'
    ;


NEWLINE : [\r\n]+ -> skip ;

LINE_COMMENT : '--' ~[\r\n]* -> skip ;

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
