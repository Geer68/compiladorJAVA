package compiladorjava;

%%
%class Lexer
%type Token
%line
%column
%{
    private Token token(String lexeme, String lexicalComp, int line, int column){
        return new Token(lexeme, lexicalComp, line+1, column+1);
    }
%}

/* Variables básicas de comentarios y espacios */
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinDeLineaComentario = "//" {EntradaDeCaracter}* {TerminadorDeLinea}?
ContenidoComentario = ( [^*] | \*+ [^/*] )*
ComentarioDeDocumentacion = "/**" {ContenidoComentario} "*"+ "/"

/* Comentario */
Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

/* Identificador */
Letra = [A-Za-zÑñ_ÁÉÍÓÚáéíóúÜü]
Digito = [0-9]
Identificador = {Letra}({Letra}|{Digito})*

/* Número */
Numero = 0 | [1-9][0-9]*
Entero = {Numero}+
Decimal = {Numero}+ "." {Numero}+
%%

/* Comentarios o espacios en blanco */
{Comentario}|{EspacioEnBlanco} { /*Ignorar*/ }

/* Identificador */
\_{Identificador} { return token(yytext(), "IDENTIFICADOR", yyline, yycolumn); }

/* Tipos de dato */
double |
long { return token(yytext(), "TIPO_DATO", yyline, yycolumn); }

/* Número */
/*{Numero} { return token(yytext(), "NUMERO", yyline, yycolumn); }*/
{Decimal} { return token(yytext(), "NUMERO_DOUBLE", yyline, yycolumn); }
{Entero} { return token(yytext(), "NUMERO_LONG", yyline, yycolumn); }

/*Operadores de agupación */
"{" { return token(yytext(), "LLAVE_A", yyline, yycolumn); }
"}" { return token(yytext(), "LLAVE_C", yyline, yycolumn); }
"(" { return token(yytext(), "PARENTESIS_A", yyline, yycolumn); }
")" { return token(yytext(), "PARENTESIS_C", yyline, yycolumn); }

/* Signos de puntuación */
"," { return token(yytext(), "COMA", yyline, yycolumn); }
";" { return token(yytext(), "PUNTO_COMA", yyline, yycolumn); }

/* Operador de asignación */
"=" { return token(yytext(), "OP_ASIG", yyline, yycolumn); }

/* Entrada-Salida */
read { return token(yytext(), "READ", yyline, yycolumn); }
write { return token(yytext(), "WRITE", yyline, yycolumn); }

/* .:Control de flujo:. */
/* Sentencia condicional */
if { return token(yytext(), "IF", yyline, yycolumn); }
then { return token(yytext(), "THEN", yyline, yycolumn); }
else { return token(yytext(), "ELSE", yyline, yycolumn); }

/* While - Mientras */
while { return token(yytext(), "WHILE", yyline, yycolumn); }

/* Break - Fin */
break { return token(yytext(), "BREAK", yyline, yycolumn); }

/* .:Operadores:. */
/* Aritméticos */
"+" |
"-" |
"*" |
"/" { return token(yytext(), "OPE_ARIT", yyline, yycolumn); }

/* Lógicos y relacionales  */
">" |
"<" |
">=" |
"<=" |
"==" |
"!=" |
"<>" { return token(yytext(), "OPE_LOG", yyline, yycolumn); }

/* .:Errores:. */
/* Número - Los números no pueden comenzar con punto*/
"."{Entero} { return token(yytext(), "ERROR_1", yyline, yycolumn); }

/* Identificador - Debe empezar con _. No debe estar solo _*/
{Identificador} { return token(yytext(), "ERROR_2", yyline, yycolumn); }

. { return token(yytext(), "ERROR", yyline, yycolumn); }