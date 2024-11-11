/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorjava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 *
 * @author matie
 */
public class Grammar {
    private ArrayList<Production> producciones = new ArrayList();
    private final ArrayList<ErrorLSSL> errors;
    private boolean lineaColumnaIni;
    private boolean compIntManual;
    private boolean indexComponentInt;
    private boolean mostrarM;
    private boolean validacion;
    private int contProd = 0;

    public Grammar(ArrayList<Token> var1, ArrayList<ErrorLSSL> var2) {
        this.lineaColumnaIni = this.validacion = true;
        this.compIntManual = this.indexComponentInt = false;
        this.mostrarM = true;
        var1.forEach((var1x) -> {
            this.producciones.add(new Production(var1x));
        });
        this.errors = var2;
        String var3 = " Vera | Hidalgo ";
        String var4 = "Gramática generada con éxito, se crearon " + this.producciones.size() + " producciones";
        String var5 = "Todos los componentes están listos para su ejecución";
        int var6 = var4.length() + 6;
        String var10001 = Functions.ANSI_GREEN_BLACK;
        System.out.println("\n" + var10001 + Functions.repeat("-", var6));
        var10001 = Functions.ANSI_GREEN_BLACK;
        System.out.println(var10001 + "| " + Functions.centerWord(var4, " ", " ", var6 - 4) + " |");
        var10001 = Functions.ANSI_GREEN_BLACK;
        System.out.println(var10001 + "| " + Functions.centerWord(var5, " ", " ", var6 - 4) + " |");
        var10001 = Functions.ANSI_GREEN_BLACK;
        System.out.println(var10001 + Functions.centerWord(var3, "-", "-", var6) + Functions.ANSI_RESET + "\n");
    }

    public void initialLineColumn() {
        this.lineaColumnaIni = true;
    }

    public void finalLineColumn() {
        this.lineaColumnaIni = false;
    }

    private String[] eliminarCompRepNum(String[] var1) {
        ArrayList var2 = new ArrayList();
        var2.addAll(Arrays.asList(var1));

        int var3;
        for(var3 = 0; var3 < var2.size(); ++var3) {
            if (((String)var2.get(var3)).matches("[0-9]+")) {
                var2.remove(var3);
                --var3;
            }
        }

        int var4;
        for(var3 = 0; var3 < var2.size(); ++var3) {
            for(var4 = 0; var4 < var2.size(); ++var4) {
                if (var3 != var4 && ((String)var2.get(var3)).equals(var2.get(var4))) {
                    var2.remove(var4);
                    --var4;
                }
            }
        }

        if (var2.size() == var1.length) {
            return var1;
        } else {
            String[] var5 = new String[var2.size()];

            for(var4 = 0; var4 < var5.length; ++var4) {
                var5[var4] = (String)var2.get(var4);
            }

            return var5;
        }
    }

    private String[] separarComp(String var1) {
        return var1 == null ? null : this.eliminarCompRepNum(var1.replaceAll("[^0-9A-Za-zÑñÁÉÍÓÚáéíóúÜü_ ]", " ").trim().replaceAll(" +", " ").split(" "));
    }

    public void group(String var1, String var2, String[] var3, boolean var4, int var5, String var6, int var7, ArrayList<Production> var8) {
        if (this.mostrarM) {
            System.out.println(Functions.repeat(".", 100));
        }

        ++this.contProd;
        if (this.validarAgr(var1, var2, var3, var7)) {
            var2 = var2.replace(" ", "");
            ArrayList var9 = new ArrayList();
            int var10 = this.producciones.size();
            int var11 = 0;
            if (this.indexComponentInt && var7 < 0) {
                var7 += var3.length;
            }

            int var12;
            for(var12 = 0; var12 < var10; ++var12) {
                Production var13 = (Production)this.producciones.get(var12);
                if (!var13.nameEqualTo(var3)) {
                    var9.add(var13);
                } else {
                    int var14 = -1;
                    String var15 = "";

                    for(int var16 = var12; var16 < var10; ++var16) {
                        var13 = (Production)this.producciones.get(var16);
                        Production var17;
                        Production var18;
                        int var19;
                        Production var20;
                        if (!var13.nameEqualTo(var3) || var14 != -1 && var4) {
                            if (var14 == -1) {
                                var17 = (Production)this.producciones.get(var12);
                                var9.add(var17);
                            } else {
                                var17 = new Production();
                                var18 = new Production();

                                for(var19 = var12; var19 <= var14; ++var19) {
                                    var20 = (Production)this.producciones.get(var19);
                                    var17.addTokens(var20);
                                    if (this.indexComponentInt && var19 - var12 == var7) {
                                        var18 = var20;
                                    }
                                }

                                var17.setName(var1);
                                var9.add(var17);
                                if (var8 != null) {
                                    var8.add(var17);
                                }

                                if (var6 != null) {
                                    if (this.indexComponentInt) {
                                        this.errors.add(new ErrorLSSL(var5, var6, var18, this.lineaColumnaIni));
                                    } else {
                                        this.errors.add(new ErrorLSSL(var5, var6, var17, this.lineaColumnaIni));
                                    }
                                }

                                var12 = var14;
                                ++var11;
                            }
                            break;
                        }

                        var15 = var15 + var13.getName();
                        if (var15.matches(var2)) {
                            var14 = var16;
                        }

                        if (var16 == var10 - 1) {
                            if (var14 != -1) {
                                var17 = new Production();
                                var18 = new Production();

                                for(var19 = var12; var19 <= var14; ++var19) {
                                    var20 = (Production)this.producciones.get(var19);
                                    var17.addTokens(var20);
                                    if (this.indexComponentInt && var19 - var12 == var7) {
                                        var18 = var20;
                                    }
                                }

                                var17.setName(var1);
                                var9.add(var17);
                                if (var8 != null) {
                                    var8.add(var17);
                                }

                                if (var6 != null) {
                                    if (this.indexComponentInt) {
                                        this.errors.add(new ErrorLSSL(var5, var6, var18, this.lineaColumnaIni));
                                    } else {
                                        this.errors.add(new ErrorLSSL(var5, var6, var17, this.lineaColumnaIni));
                                    }
                                }

                                var12 = var14;
                                ++var11;
                            } else {
                                var17 = (Production)this.producciones.get(var12);
                                var9.add(var17);
                            }
                        }
                    }
                }
            }

            if (this.mostrarM) {
                if (var11 > 0) {
                    var12 = this.producciones.size();
                    int var21 = var9.size();
                    System.out.println("**** Agrupación " + this.contProd + " \"" + var1 + "\" realizada con éxito ****\nCantidad de componentes: " + var3.length);
                    if (var12 == var21) {
                        System.out.println("No hubo reducción en la cantidad de producciones (" + var12 + ")\n");
                    } else {
                        System.out.println("La cantidad de producciones se redujo de " + var12 + " a " + var21 + "\n");
                    }
                } else {
                    System.out.println(Functions.ANSI_BLUE_BLACK + "**** Agrupación " + this.contProd + " \"" + var1 + "\" realizada, pero sin cambios ****\n" + Functions.ANSI_BLUE_BLACK + "Cantidad de componentes: " + var3.length + "\n");
                }
            }

            this.producciones = var9;
        }

    }

    public void loopForFunExecUntilChangeNotDetected(Runnable var1) {
        int var2 = this.producciones.size();

        while(true) {
            var1.run();
            if (var2 == this.producciones.size()) {
                return;
            }

            var2 = this.producciones.size();
        }
    }

    private boolean validarAgr(String var1, String var2, String[] var3, int var4) {
        if (!this.validacion) {
            return true;
        } else {
            int var5 = 0;
            byte var6 = 0;
            String var7 = "";
            if (var1 == null) {
                ++var5;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El nombre de la producción es un valor nulo, proceda a corregirla o eliminarla";
            } else if (var1.contains(" ")) {
                ++var5;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El nombre de la producción contiene uno o más espacios, proceda a eliminarlos:\n\"" + var1 + "\"";
            } else if (var1.equals("")) {
                ++var5;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El nombre de la producción es una cadena vacía, proceda a corregirla o eliminarla";
            } else if (var1.matches("[0-9]+")) {
                ++var5;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El nombre de la producción no puede ser un número, proceda a corregirla o eliminarla:\n\"" + var1 + "\"";
            } else if (!var1.matches("[A-Za-zÑñÁÉÍÓÚáéíóúÜü_][0-9A-Za-zÑñÁÉÍÓÚáéíóúÜü_]*")) {
                ++var5;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El nombre de la producción es inválido, debe de cumplir con la siguiente ER: [A-Za-z_][A-Za-z0-9_]*, proceda a corregirla o eliminarla:\n\"" + var1 + "\"";
            }

            if (var2 == null) {
                ++var5;
                var6 = -1;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". La expresión regular de la producción es un valor nulo, proceda a corregirla o eliminarla";
            } else if (var2.matches(" *")) {
                ++var5;
                var6 = -1;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". La expresión regular de la producción no contiene ningún componente ó expresión, proceda a corregirla o eliminarla";
            } else {
                try {
                    Pattern.compile(var2);
                } catch (Exception var12) {
                    ++var5;
                    var6 = -1;
                    var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". Formato de expresión regular inválido: " + var12.getLocalizedMessage();
                }
            }

            String[] var8;
            int var9;
            int var10;
            String var11;
            if (this.compIntManual) {
                var8 = var3;
                var9 = var3.length;

                for(var10 = 0; var10 < var9; ++var10) {
                    var11 = var8[var10];
                    if (var11.contains(" ")) {
                        ++var5;
                        var6 = -1;
                        var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". Uno de los componentes introducidos tiene uno o más espacios, favor de corregirlo o eliminarlo:\n\"" + var11 + "\"";
                        break;
                    }
                }
            }

            if (var6 != -1) {
                var8 = var3;
                var9 = var3.length;

                for(var10 = 0; var10 < var9; ++var10) {
                    var11 = var8[var10];
                    if (!var11.matches("[A-Za-zÑñÁÉÍÓÚáéíóúÜü_][0-9A-Za-zÑñÁÉÍÓÚáéíóúÜü_]*")) {
                        ++var5;
                        var6 = -1;
                        var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". Uno de los componentes no cumple con la siguiente ER: [A-Za-z_][A-Za-z0-9_]*, favor de corregirlo o eliminarlo:\n\"" + var11 + "\"";
                        break;
                    }
                }
            }

            if (this.indexComponentInt && var6 != -1) {
                if (var2 != null && !var2.matches("[0-9A-Za-zÑñÁÉÍÓÚáéíóúÜü_ ]+")) {
                    ++var5;
                    var6 = -1;
                    var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". En caso de querer la línea y columna de un componente en específico, no debe de mandar una expresión\n" + Functions.ANSI_RED_BLACK + Functions.repeat(" ", String.valueOf(var5).length()) + "  regular como parámetro, si no solo los componentes en un orden determinado, proceda a corregirla:\n\"" + var2 + "\"";
                }

                if (var6 != -1) {
                    int var13 = var3.length;
                    if (var4 >= 0) {
                        if (var4 > var13 - 1) {
                            ++var5;
                            var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El índice excede al tamaño de la cantidad de componentes: " + var4 + ">" + (var13 - 1);
                        }
                    } else if (var4 < -var13) {
                        ++var5;
                        var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El índice negativo es inferior al tamaño de la cantidad de componentes: " + var4 + "<-" + var13;
                    }
                }
            }

            if (var5 > 0) {
                System.out.println(Functions.ANSI_RED_BLACK + "**** Agrupación " + this.contProd + " \"" + var1 + "\" no realizada****\n" + Functions.ANSI_RED_BLACK + "Δ Resuelva los errores a continuación descritos Δ:" + var7 + "\n");
                return false;
            } else {
                return true;
            }
        }
    }

    public void group(String var1, String var2, int var3, String var4) {
        this.group(var1, var2, this.separarComp(var2), false, var3, var4, -1, (ArrayList)null);
    }

    public void group(String var1, String var2, int var3, String var4, int var5) {
        this.indexComponentInt = true;
        this.group(var1, var2, this.separarComp(var2), false, var3, var4, var5, (ArrayList)null);
        this.indexComponentInt = false;
    }

    public void group(String var1, String var2) {
        this.group(var1, var2, this.separarComp(var2), false, -1, (String)null, -1, (ArrayList)null);
    }

    public void group(String var1, String var2, String[] var3) {
        this.compIntManual = true;
        this.group(var1, var2, var3, false, -1, (String)null, -1, (ArrayList)null);
        this.compIntManual = false;
    }

    public void group(String var1, String var2, int var3, String var4, ArrayList<Production> var5) {
        this.group(var1, var2, this.separarComp(var2), false, var3, var4, -1, var5);
    }

    public void group(String var1, String var2, int var3, String var4, int var5, ArrayList<Production> var6) {
        this.indexComponentInt = true;
        this.group(var1, var2, this.separarComp(var2), false, var3, var4, var5, var6);
        this.indexComponentInt = false;
    }

    public void group(String var1, String var2, ArrayList<Production> var3) {
        this.group(var1, var2, this.separarComp(var2), false, -1, (String)null, -1, var3);
    }

    public void group(String var1, String var2, String[] var3, ArrayList<Production> var4) {
        this.compIntManual = true;
        this.group(var1, var2, var3, false, -1, (String)null, -1, var4);
        this.compIntManual = false;
    }

    public void group(String var1, String var2, boolean var3, int var4, String var5) {
        this.group(var1, var2, this.separarComp(var2), var3, var4, var5, -1, (ArrayList)null);
    }

    public void group(String var1, String var2, boolean var3, int var4, String var5, int var6) {
        this.indexComponentInt = true;
        this.group(var1, var2, this.separarComp(var2), var3, var4, var5, var6, (ArrayList)null);
        this.indexComponentInt = false;
    }

    public void group(String var1, String var2, boolean var3) {
        this.group(var1, var2, this.separarComp(var2), var3, -1, (String)null, -1, (ArrayList)null);
    }

    public void group(String var1, String var2, String[] var3, boolean var4) {
        this.compIntManual = true;
        this.group(var1, var2, var3, var4, -1, (String)null, -1, (ArrayList)null);
        this.compIntManual = false;
    }

    public void group(String var1, String var2, boolean var3, int var4, String var5, ArrayList<Production> var6) {
        this.group(var1, var2, this.separarComp(var2), var3, var4, var5, -1, var6);
    }

    public void group(String var1, String var2, boolean var3, int var4, String var5, int var6, ArrayList<Production> var7) {
        this.indexComponentInt = true;
        this.group(var1, var2, this.separarComp(var2), var3, var4, var5, var6, var7);
        this.indexComponentInt = false;
    }

    public void group(String var1, String var2, boolean var3, ArrayList<Production> var4) {
        this.group(var1, var2, this.separarComp(var2), var3, -1, (String)null, -1, var4);
    }

    public void group(String var1, String var2, String[] var3, boolean var4, ArrayList<Production> var5) {
        this.compIntManual = true;
        this.group(var1, var2, var3, var4, -1, (String)null, -1, var5);
        this.compIntManual = false;
    }

    public void delete(String var1, int var2, String var3) {
        if (this.mostrarM) {
            System.out.println(Functions.repeat(".", 100));
        }

        if (this.validateDeletion(var1)) {
            int var4 = 0;
            int var5 = this.producciones.size();

            for(int var6 = 0; var6 < this.producciones.size(); ++var6) {
                Production var7 = (Production)this.producciones.get(var6);
                if (var7.nameEqualTo(var1)) {
                    if (var3 != null) {
                        this.errors.add(new ErrorLSSL(var2, var3, var7, this.lineaColumnaIni));
                    }

                    this.producciones.remove(var6);
                    --var6;
                    ++var4;
                }
            }

            if (this.mostrarM) {
                if (var4 > 0) {
                    System.out.println("**** Se realizaron " + var4 + " eliminaciones de producciones llamadas \"" + var1 + "\" ****\nLa cantidad de producciones se redujo de " + var5 + " a " + this.producciones.size() + "\n");
                } else {
                    System.out.println(Functions.ANSI_BLUE_BLACK + "No se encontró alguna producción llamada \"" + var1 + "\" para su eliminación\n");
                }
            }
        }

    }

    public void delete(String[] var1, int var2, String var3) {
        String[] var4 = var1;
        int var5 = var1.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            this.delete(var7, var2, var3);
        }

    }

    public void delete(String var1, int var2) {
        if (this.mostrarM) {
            System.out.println(Functions.repeat(".", 100));
        }

        if (this.validateDeletion(var1)) {
            int var3 = 0;
            int var4 = this.producciones.size();

            for(int var5 = 0; var5 < this.producciones.size(); ++var5) {
                Production var6 = (Production)this.producciones.get(var5);
                if (var6.nameEqualTo(var1)) {
                    if (var6.getSizeTokens() > 1) {
                        this.errors.add(new ErrorLSSL(var2, " × Error sintáctico {}: No se esperaba encontrar los tokens \"[]\", favor de eliminarlos [#, %]", var6, this.lineaColumnaIni));
                    } else {
                        this.errors.add(new ErrorLSSL(var2, " × Error sintáctico {}: No se esperaba encontrar el token \"[]\", favor de eliminarlo [#, %]", var6, this.lineaColumnaIni));
                    }

                    this.producciones.remove(var5);
                    --var5;
                    ++var3;
                }
            }

            if (this.mostrarM) {
                if (var3 > 0) {
                    System.out.println("**** Se realizaron " + var3 + " eliminaciones de producciones llamadas \"" + var1 + "\" ****\nLa cantidad de producciones se redujo de " + var4 + " a " + this.producciones.size() + "\n");
                } else {
                    System.out.println(Functions.ANSI_BLUE_BLACK + "No se encontró alguna producción llamada \"" + var1 + "\" para su eliminación\n");
                }
            }
        }

    }

    public void delete(String[] var1, int var2) {
        String[] var3 = var1;
        int var4 = var1.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            this.delete(var6, var2);
        }

    }

    public void delete(String var1) {
        this.delete((String)var1, -1, (String)null);
    }

    public void delete(String[] var1) {
        this.delete((String[])var1, -1, (String)null);
    }

    private boolean validateDeletion(String var1) {
        if (!this.validacion) {
            return true;
        } else {
            int var2 = 0;
            String var3 = "";
            if (var1 == null) {
                ++var2;
                var3 = var3 + "\n" + Functions.ANSI_RED_BLACK + "1. El nombre de la producción a eliminar es un valor nulo, proceda a corregirla";
            } else if (var1.contains(" ")) {
                ++var2;
                var3 = var3 + "\n" + Functions.ANSI_RED_BLACK + "1. El nombre de la producción a eliminar contiene uno o más espacios, proceda a corregirla:\n\"" + var1 + "\"";
            } else if (var1.equals("")) {
                ++var2;
                var3 = var3 + "\n" + Functions.ANSI_RED_BLACK + "1. El nombre de la producción a eliminar es una cadena vacía, proceda a corregirla";
            } else if (var1.matches("[0-9]+")) {
                ++var2;
                var3 = var3 + "\n" + Functions.ANSI_RED_BLACK + var2 + ". El nombre de la producción a eliminar no puede ser un número, proceda a corregirla:\n\"" + var1 + "\"";
            } else if (!var1.matches("[A-Za-zÑñÁÉÍÓÚáéíóúÜü_][0-9A-Za-zÑñÁÉÍÓÚáéíóúÜü_]*")) {
                ++var2;
                var3 = var3 + "\n" + Functions.ANSI_RED_BLACK + var2 + ". El nombre de la producción a eliminar es inválido, debe de cumplir con la siguiente ER: [A-Za-z_][A-Za-z0-9_]*, proceda a corregirla:\n\"" + var1 + "\"";
            }

            if (var2 > 0) {
                System.out.println(Functions.ANSI_RED_BLACK + "**** Eliminación de la producción \"" + var1 + "\" no realizada****\n" + Functions.ANSI_RED_BLACK + "Δ Resuelva los errores a continuación descritos Δ:" + var3 + "\n");
                return false;
            } else {
                return true;
            }
        }
    }

    public void show() {
        System.out.println(this);
    }

    public void activateMessages() {
        this.mostrarM = true;
    }

    public void disableMessages() {
        this.mostrarM = false;
    }

    public void activateValidations() {
        this.validacion = true;
    }

    public void disableValidations() {
        this.validacion = false;
    }

    public String toString() {
        System.out.println("\n" + Functions.ANSI_PURPLE_BLACK + "**** Mostrando gramáticas ****\n");
        String var1 = "";
        var1 = (String)this.producciones.stream().map((var0) -> {
            String var10000 = Functions.repeat(".", 100);
            return var10000 + "\n" + var0 + "\n\n";
        }).reduce(var1, String::concat);
        return var1;
    }
}
