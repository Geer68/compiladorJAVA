/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorjava;

import jflex.exceptions.SilentExit;

/**
 *
 * @author matie
 */
public class ExecuteJFlex {
    public static void main(String omega[]) {
        String lexerFile = System.getProperty("user.dir") + "/src/compiladorjava/Lexer.flex",
                lexerFileColor = System.getProperty("user.dir") + "/src/compiladorjava/LexerColor.flex";
        try {
            jflex.Main.generate(new String[]{lexerFile, lexerFileColor});
        } catch (SilentExit ex) {
            System.out.println("Error al compilar/generar el archivo flex: " + ex);
        }
    }
}
