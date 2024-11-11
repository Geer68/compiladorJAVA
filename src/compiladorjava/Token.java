/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorjava;

/**
 *
 * @author matie
 */
public class Token {
    private final String lexema;
    private final String comp_lexico;
    private final int linea;
    private final int columna;

    public Token(String var1, String var2, int var3, int var4) {
        this.lexema = var1;
        this.comp_lexico = var2;
        this.linea = var3;
        this.columna = var4;
        if (this.lexema == null) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK + "Se ha creado un token que contiene un valor nulo como lexema, esto podría llegar a generar errores ó\n" + Functions.ANSI_RED_BLACK + "resultados incorrectos. El token referido es el siguiente:\n" + Functions.ANSI_BLUE_BLACK + this);
        } else if (this.lexema.isEmpty()) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK + "Se ha creado un token que contiene una cadena vacía como lexema, esto podría llegar a generar errores\n" + Functions.ANSI_RED_BLACK + "ó resultados incorrectos. El token referido es el siguiente:\n" + Functions.ANSI_BLUE_BLACK + this);
        }

        if (this.comp_lexico == null) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK + "Se ha creado un token que contiene un valor nulo como componente léxico, esto generará conflictos ó\n" + Functions.ANSI_RED_BLACK + "resultados incorrectos al momento de agrupar las producciones, proceda a corregirlo. El token referido\n" + Functions.ANSI_RED_BLACK + "es el siguiente:\n" + Functions.ANSI_BLUE_BLACK + this);
        } else if (this.comp_lexico.contains(" ")) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK + "Se ha creado un token que contiene uno o más espacios en el componente léxico, esto generará conflictos\n" + Functions.ANSI_RED_BLACK + "ó resultados incorrectos al momento de agrupar las producciones, proceda a corregirlo. El token referido\n" + Functions.ANSI_RED_BLACK + "es el siguiente:\n" + Functions.ANSI_BLUE_BLACK + this);
        } else if (this.comp_lexico.isEmpty()) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK + "Se ha creado un token que contiene una cadena vacía como componente léxico, esto generará conflictos ó\n" + Functions.ANSI_RED_BLACK + "resultados incorrectos al momento de agrupar las producciones, proceda a corregirlo. El token referido\n" + Functions.ANSI_RED_BLACK + "es el siguiente:\n" + Functions.ANSI_BLUE_BLACK + this);
        }

        if (this.linea <= 0 | this.columna <= 0) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK + "Se ha creado un token que contiene un número de línea y/o columna menor o igual que 0, lo cuál es ilógico, esto\n" + Functions.ANSI_RED_BLACK + "podría llegar a generar errores ó resultados incorrectos, proceda a corregirlo. El token referido es el\n" + Functions.ANSI_RED_BLACK + "siguiente:\n" + Functions.ANSI_BLUE_BLACK + this);
        }

    }

    public String getLexeme() {
        return this.lexema;
    }

    public String getLexicalComp() {
        return this.comp_lexico;
    }

    public int getLine() {
        return this.linea;
    }

    public int getColumn() {
        return this.columna;
    }

    public String toString() {
        return "Token(" + this.lexema + ", " + this.comp_lexico + ", " + this.linea + ", " + this.columna + ")";
    }
}

