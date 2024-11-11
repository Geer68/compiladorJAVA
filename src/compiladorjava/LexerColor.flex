package compiladorjava;

import java.awt.Color;

%%
%class LexerColor
%type TextColor
%char
%{
    private TextColor textColor(long start, int size, Color color){
        return new TextColor((int) start, size, color);
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
{Comentario} { return textColor(yychar, yylength(), new Color(146, 146, 146)); }
{EspacioEnBlanco} { /*Ignorar*/ }

/* Identificador */
\_{Identificador} { /*Ignorar*/ }

/* Tipos de dato */
double |
long { return textColor(yychar, yylength(), new Color(148, 58, 173)); } 

/* Número */
/*{Numero} { return textColor(yychar, yylength(), new Color(0, 255, 54)); }*/
{Decimal} { return textColor(yychar, yylength(), new Color(0, 255, 54)); }
{Entero} { return textColor(yychar, yylength(), new Color(0, 255, 54)); }

/*Operadores de agupación */
"{" |
"}" |
"(" |
")" { return textColor(yychar, yylength(), new Color(144, 99, 176)); }

/* Signos de puntuación */
"," |
";" { return textColor(yychar, yylength(), new Color(144, 99, 176)); }

/* Operador de asignación */
"=" { return textColor(yychar, yylength(), new Color(144, 99, 176)); }

/* Entrada-Salida */
read |
write { return textColor(yychar, yylength(), new Color(199, 0, 57)); }

/* .:Control de flujo:. */
/* Sentencia condicional */
if |
then |
else { return textColor(yychar, yylength(), new Color(17, 94, 153)); }

/* While - Mientras */
while { return textColor(yychar, yylength(), new Color(17, 94, 153)); }

/* Break - Fin */
break { return textColor(yychar, yylength(), new Color(17, 94, 153)); }

/* .:Operadores:. */
/* Aritméticos */
"+" |
"-" |
"*" |
"/" { return textColor(yychar, yylength(), new Color(150, 0, 80)); }

/* Lógicos y relacionales  */
">" |
"<" |
">=" |
"<=" |
"==" |
"!=" |
"<>" { return textColor(yychar, yylength(), new Color(121, 107, 255)); }

/* .:Errores:. */
/* Número - Los números no pueden comenzar con punto*/
"."{Entero} { /*Ignorar*/ }

/* Identificador - Debe empezar con _. No debe estar solo _*/
{Identificador} { /*Ignorar*/ }

. { /* Ignorar */ }