/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorjava;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author matie
 */

public class Functions {
    public static String ANSI_BLACK = "\u001b[30m";
    public static String ANSI_RED = "\u001b[31m";
    public static String ANSI_RED_BLACK = "\u001b[31;2m";
    public static String ANSI_GREEN = "\u001b[32m";
    public static String ANSI_GREEN_BLACK = "\u001b[32;2m";
    public static String ANSI_YELLOW = "\u001b[33m";
    public static String ANSI_YELLOW_BLACK = "\u001b[33;2m";
    public static String ANSI_BLUE = "\u001b[34m";
    public static String ANSI_BLUE_BLACK = "\u001b[34;2m";
    public static String ANSI_PURPLE = "\u001b[35m";
    public static String ANSI_PURPLE_BLACK = "\u001b[35;2m";
    public static String ANSI_RESET = "\u001b[0m";

    public Functions() {
    }

    public static void clearDataInTable(JTable var0) {
        DefaultTableModel var1 = (DefaultTableModel)var0.getModel();
        var1.setRowCount(0);
    }

    public static void addRowDataInTable(JTable var0, Object[] var1) {
        DefaultTableModel var2 = (DefaultTableModel)var0.getModel();
        var2.addRow(var1);
    }

    public static void insertAsteriskInName(final JFrame var0, JTextComponent var1, final Runnable var2) {
        var1.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent var1) {
            }

            public void keyPressed(KeyEvent var1) {
            }

            public void keyReleased(KeyEvent var1) {
                int var2x = var1.getKeyCode();
                if (var2x == 10 || var2x >= 65 && var2x <= 90 || var2x >= 48 && var2x <= 57 || var2x >= 97 && var2x <= 122 || var2x != 27 && (var2x < 37 || var2x > 40) && (var2x < 16 || var2x > 18) && var2x != 524 && var2x != 20) {
                    if (!var0.getTitle().contains("*")) {
                        var0.setTitle(var0.getTitle() + "*");
                    }

                    var2.run();
                }

            }
        });
    }

    public static void insertAsteriskInName(JFrame var0, JTextComponent var1) {
        insertAsteriskInName(var0, var1, () -> {
        });
    }

     public static String repeat(String str, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(str);
        }
        return result.toString();
    }
    
    public static String centerWord(String var0, String var1, String var2, int var3) {
        int var4 = var0.length();
        if (var3 <= var4) {
            return var0;
        } else {
            var3 -= var4;
            String var5;
            String var6;
            if (var3 % 2 == 0) {
                var3 /= 2;
                var5 = repeat(var1, var3);
                var6 = repeat(var2, var3);
                return var5 + var0 + var6;
            } else {
                var3 = (var3 - 1) / 2;
                var5 = repeat(var1, var3 + 1);
                var6 = repeat(var2, var3);
                return var5 + var0 + var6;
            }
        }
    }

    public static String formatString(String var0, String var1, String var2) {
        String var3 = "";

        while(true) {
            int var4 = var0.indexOf("\\" + var1);
            if (var4 == -1) {
                var3 = var3 + var0.substring(0, var0.length()).replace(var1, var2);
                return var3;
            }

            var3 = var3 + var0.substring(0, var4).replace(var1, var2) + var1;
            var0 = var0.substring(var4 + var1.length() + 1, var0.length());
        }
    }

    public static void colorTextPane(ArrayList<TextColor> var0, JTextPane var1, Color var2) {
        StyledDocument var3 = var1.getStyledDocument();
        StyleContext var4 = StyleContext.getDefaultStyleContext();
        AttributeSet var5 = var4.addAttribute(var4.getEmptySet(), StyleConstants.Foreground, var2);
        var3.setCharacterAttributes(0, var3.getLength(), var5, true);
        Iterator var6 = var0.iterator();

        while(var6.hasNext()) {
            TextColor var7 = (TextColor)var6.next();
            var5 = var4.addAttribute(var4.getEmptySet(), StyleConstants.Foreground, var7.getColor());
            var3.setCharacterAttributes(var7.getStart(), var7.getSize(), var5, true);
        }

    }

    public static boolean isLetter(String var0) {
        return var0.matches("[A-Za-zÑñÁÉÍÓÚáéíóúÜü]");
    }

    public static boolean isWord(String var0) {
        return var0.matches("[A-Za-zÑñÁÉÍÓÚáéíóúÜü]+");
    }

    public static boolean isDigit(String var0) {
        return var0.matches("[0-9]");
    }

    public static boolean isNumber(String var0) {
        return var0.matches("0|[1-9][0-9]*");
    }

    public static boolean isSpace(String var0) {
        return var0.matches("[ \t\f]+");
    }

    public static boolean isSpaceOrSaltLine(String var0) {
        return var0.matches("[ \t\f\r\n]+");
    }

   

    private static String formatCode(String var0, String var1, String var2, String var3) {
        var0 = var0.replaceAll("[\r\t\f]", "");
        var0 = var0.replace(var1, "\n" + var1 + "\n").replace(var2, "\n" + var2 + "\n").replace(var3, "\n" + var3 + "\n").replaceAll("[\n]+", "\n").replaceAll(" +", " ");
        return var0;
    }

    public static void sortErrorsByLineAndColumn(ArrayList<ErrorLSSL> var0) {
        quicksortErrorsByLine(var0, 0, var0.size() - 1);
        sortsErrorByColumnGroupedOnLine(var0);
    }

    private static void quicksortErrorsByLine(ArrayList<ErrorLSSL> var0, int var1, int var2) {
        ErrorLSSL var3 = (ErrorLSSL)var0.get(var1);
        int var4 = var1;
        int var5 = var2;

        while(var4 < var5) {
            while(((ErrorLSSL)var0.get(var4)).getLine() <= var3.getLine() && var4 < var5) {
                ++var4;
            }

            while(((ErrorLSSL)var0.get(var5)).getLine() > var3.getLine()) {
                --var5;
            }

            if (var4 < var5) {
                ErrorLSSL var6 = (ErrorLSSL)var0.get(var4);
                var0.set(var4, (ErrorLSSL)var0.get(var5));
                var0.set(var5, var6);
            }
        }

        var0.set(var1, (ErrorLSSL)var0.get(var5));
        var0.set(var5, var3);
        if (var1 < var5 - 1) {
            quicksortErrorsByLine(var0, var1, var5 - 1);
        }

        if (var5 + 1 < var2) {
            quicksortErrorsByLine(var0, var5 + 1, var2);
        }

    }

    private static void quicksortErrorsByColumn(ArrayList<ErrorLSSL> var0, int var1, int var2) {
        ErrorLSSL var3 = (ErrorLSSL)var0.get(var1);
        int var4 = var1;
        int var5 = var2;

        while(var4 < var5) {
            while(((ErrorLSSL)var0.get(var4)).getColumn() <= var3.getColumn() && var4 < var5) {
                ++var4;
            }

            while(((ErrorLSSL)var0.get(var5)).getColumn() > var3.getColumn()) {
                --var5;
            }

            if (var4 < var5) {
                ErrorLSSL var6 = (ErrorLSSL)var0.get(var4);
                var0.set(var4, (ErrorLSSL)var0.get(var5));
                var0.set(var5, var6);
            }
        }

        var0.set(var1, (ErrorLSSL)var0.get(var5));
        var0.set(var5, var3);
        if (var1 < var5 - 1) {
            quicksortErrorsByColumn(var0, var1, var5 - 1);
        }

        if (var5 + 1 < var2) {
            quicksortErrorsByColumn(var0, var5 + 1, var2);
        }

    }

    private static void sortsErrorByColumnGroupedOnLine(ArrayList<ErrorLSSL> var0) {
        int var1 = var0.size();

        for(int var2 = 0; var2 < var1; ++var2) {
            ErrorLSSL var3 = (ErrorLSSL)var0.get(var2);
            int var5 = -1;

            for(int var6 = var2 + 1; var6 < var1; var5 = var6++) {
                ErrorLSSL var7 = (ErrorLSSL)var0.get(var6);
                if (var3.getLine() != var7.getLine()) {
                    if (var5 != -1) {
                        quicksortErrorsByColumn(var0, var2, var5);
                        var2 = var5;
                        var5 = -1;
                    }
                    break;
                }
            }

            if (var5 != -1) {
                quicksortErrorsByColumn(var0, var2, var5);
            }
        }

    }
}
