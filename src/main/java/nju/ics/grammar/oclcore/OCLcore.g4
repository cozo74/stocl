grammar OCLcore;


@header {
    package nju.ics.grammar.oclcore;
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
        : 'not' oclBool                   # OclBoolNot
        | oclBool 'and' oclBool           # OclBoolAnd
        | oclBool 'or' oclBool            # OclBoolOr
        | '(' oclBool ')'                 # OclBoolParen
        | arithmeticExpr compOp arithmeticExpr      # EqualityExprArithmetic
        | oclStringValue op=( '=' | '<>') oclStringValue         # EqualityExprStringEqual
        | oclObject '.' attr              # OclBoolObjectAttr
        ;



arithmeticExpr
        : '-' arithmeticExpr                                 # ArithUnaryMinus
        | arithmeticExpr op=('*' | '/' ) arithmeticExpr      # ArithMultDiv
        | arithmeticExpr op=('+' | '-' ) arithmeticExpr      # ArithAddSub
        | '(' arithmeticExpr ')'                             # ArithParen
        | oclArithmeticValue                                 # ArithValue
        ;


oclArithmeticValue
        : INT_LITERAL                            # ArithValueIntLiteral
        | REAL_LITERAL                           # ArithValueRealLiteral
        | var                                    # ArithValueVar
        | oclObject '.' attr                     # ArithValueAttr
        | oclSet '->' aggOp '()'                 # ArithSetAggFunc
        ;


aggOp
        : 'min'
        | 'max'
        | 'sum'
        | 'avg'
        | 'size'
        ;


oclSet
        : ID '.allInstances()'                           # OclObjectSetAllInstances
        | oclSet '.' roleOrAttr                             # OclObjectSetRoleOrOclValueSetAttr
        | oclObject '.' nfRole                              # OclObjectSetNfRole
        | oclSet setOp '(' oclSet ')'                       # OclSetSetOp
        | oclSet '->select' '(' varList '|' oclBool ')'         # OclSetSelect
        ;

setOp
        : '->union'
        | '->intersection'
        | '->difference'
        ;


oclObject
        : oclObject '.' fRole           # OclObjectFRole
        | var                           # OclObjectVar
        | 'self'                        # OclObjectSelf
        | STRING_LITERAL                # OclObjectId
        ;


oclStringValue
        : STRING_LITERAL                # StringValueLiteral
        | var                           # StringValueVar
        | oclObject '.' attr           # StringValueFAttr
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



fRole   : ID
        ;
nfRole   : ID
        ;

attr    : ID
        ;


roleOrAttr : ID
           ;


INT_LITERAL
        : [0-9]+
        | NULL_LITERAL
        ;

REAL_LITERAL
        : Digits '.' Digits
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



CONSTANTID      : [A-Z$]+ [A-Z0-9_$]* ;


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