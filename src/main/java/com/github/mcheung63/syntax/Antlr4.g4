grammar Antlr4;

prog:   RULE ':' TOKENS ';'
		|
        EOF
    ;

RULE	:	[a-zA-Z0-9]+ ;
TOKENS	:	[a-zA-Z0-9]+ ;

NL      :   '\r'? '\n' ;
WS      :   [ \t\u000C]+ -> skip ;