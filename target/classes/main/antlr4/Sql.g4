grammar Sql;

query: select_clause from_clause where_clause? ;

select_clause: 'SELECT' column_list ;
from_clause: 'FROM' table_name ;
where_clause: 'WHERE' condition ;

column_list: IDENTIFIER (',' IDENTIFIER)* ;
table_name: IDENTIFIER ;
condition: expression ;

expression: IDENTIFIER '=' STRING_LITERAL
          | expression 'AND' expression
          | expression 'OR' expression
          ;

IDENTIFIER: [a-zA-Z_][a-zA-Z_0-9]* ;
STRING_LITERAL: '\'' ( ~'\'' | '\'\'' )* '\'' ;
WS: [ \t\r\n]+ -> skip ;
