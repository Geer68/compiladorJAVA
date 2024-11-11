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
public class ErrorLSSL {
    private String desc;
    private String lexemas;
    private final int numero;
    private final int linea;
    private final int columna;

    public ErrorLSSL(int var1, String var2, Token var3) {
        this.numero = var1;
        this.desc = var2;
        this.lexemas = var3.getLexeme();
        this.linea = var3.getLine();
        this.columna = var3.getColumn();
    }

    public ErrorLSSL(int var1, String var2, Production var3, boolean var4) {
        this.numero = var1;
        this.desc = var2;
        this.lexemas = var3.lexemeRank(0, -1);
        if (var4) {
            this.linea = var3.getLine();
            this.columna = var3.getColumn();
        } else {
            this.linea = var3.getFinalLine();
            this.columna = var3.getFinalColumn();
        }

    }

    public int getLine() {
        return this.linea;
    }

    public int getColumn() {
        return this.columna;
    }

    public String toString() {
        if (this.lexemas == null) {
            this.lexemas = "~VALOR NULO~";
        }

        if (this.desc == null) {
            this.desc = "~DESCRIPCIÓN NULA~";
        }

        String var1 = Functions.formatString(this.desc, "[]", this.lexemas);
        var1 = Functions.formatString(var1, "{}", String.valueOf(this.numero));
        var1 = Functions.formatString(var1, "#", String.valueOf(this.linea));
        var1 = Functions.formatString(var1, "%", String.valueOf(this.columna));
        return var1;
    }
}

