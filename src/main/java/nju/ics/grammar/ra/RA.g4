grammar RA;



@header {
    package nju.ics.grammar.ra;
}

raExpr
        : 'Database' ID ':' queryExpr* EOF
        ;

queryExpr
        : 'qry' ID? ':' query ';'
        ;

query
        : ID                                                                        # RelationID
        | query 'Join' query                                                        # Join
        | query 'Join' boolExpr query                                               # JoinWithCondition
        | query relationOp query                                                    # QueryRelationOp
        | 'Selection' '{' boolExpr '}' '{' query '}'                                # Selection
        | 'Projection' '{' arithmeticExpr (',' arithmeticExpr)* '}' '{' query '}'   # Projection
        | 'Rename' '{' renameList '}' '{' query '}'                                 # Rename
        | 'Group' '{' (fieldList ',')? aggFunc '(' field ')' '}' '{' query '}'         # Group
        | '(' query ')'                                                                # QueryParen
        ;

relationOp
        : 'Product'             # Product
        | 'Difference'          # Difference
        | 'Union'               # Union
        | 'Intersection'        # Intersection
        ;


boolExpr
        : 'not' boolExpr                # BoolNot
        | boolExpr 'and' boolExpr       # BoolAnd
        | boolExpr 'or' boolExpr        # BoolOr
        | equalityExpr                  # BoolEquality
        | '(' boolExpr ')'              # BoolParen
        ;


equalityExpr
        : arithmeticExpr compOp arithmeticExpr  # EqualityArithCompArith
        | '(' equalityExpr ')'                  # EqualityParen
        | stringValue '=' stringValue           # EqualityStringEqual
        | stringValue '<>' stringValue          # EqualityStringNotEqual
        ;


compOp
        : '='
        | '<'
        | '>'
        | '>='
        | '<='
        | '<>'
        ;


stringValue
        : STRING_LITERAL             # StringLiteral
        | field                      # StringField
        ;


arithmeticExpr
        : '-' arithmeticExpr                                # ArithmeticNegate
        | arithmeticExpr op=('*' | '/' ) arithmeticExpr     # ArithmeticMulDiv
        | arithmeticExpr op=('+' | '-' ) arithmeticExpr     # ArithmeticAddSub
        | '(' arithmeticExpr ')'                            # ArithmeticParen
        | arithmeticValue                                   # ArithmeticExprValue
        ;


arithmeticValue
        : INT_LITERAL                       # ArithmeticIntLiteral
        | REAL_LITERAL                      # ArithmeticRealLiteral
        | field                             # ArithmeticField
        | aggFunc '(' field ')'             # ArithmeticAggFuncField
        ;


aggFunc
        : 'min'
        | 'max'
        | 'sum'
        | 'avg'
        | 'count'
        ;


fieldList
        : field (',' field)*
        ;

renameList
        : renamePair (',' renamePair)*
        ;


renamePair
        : field '->' field
        ;

field   : ID ;


ID      : [a-zA-Z0-9_$]+ ;

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