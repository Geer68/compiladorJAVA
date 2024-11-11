/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorjava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 *
 * @author matie
 */
public class CodeBlock {
    private String code;
    private ArrayList<CodeBlock> codeBlockChilds;

    public CodeBlock(String var1) {
        this.code = var1;
        this.codeBlockChilds = new ArrayList();
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String var1) {
        this.code = var1;
    }

    public ArrayList<CodeBlock> getCodeBlockChilds() {
        return this.codeBlockChilds;
    }

    public void setCodeBlockChilds(ArrayList<CodeBlock> var1) {
        this.codeBlockChilds = var1;
    }

    public void addBCodeBlockChild(CodeBlock var1) {
        this.codeBlockChilds.add(var1);
    }

    public ArrayList<String> getBlocksOfCodeInOrderOfExec() {
        ArrayList var1 = new ArrayList();
        this.blocksOfCodeInOrderExecution(var1, this);
        var1.remove(0);
        return var1;
    }

    private void blocksOfCodeInOrderExecution(ArrayList<String> var1, CodeBlock var2) {
        var1.add(var2.getCode().replace("\n", "").replaceAll(" +", " ").trim());
        ArrayList var3 = var2.getCodeBlockChilds();
        String var4 = UUID.randomUUID().toString();
        if (!var3.isEmpty()) {
            var1.add("~" + var4 + "~");
        }

        Iterator var5 = var3.iterator();

        while(var5.hasNext()) {
            CodeBlock var6 = (CodeBlock)var5.next();
            this.blocksOfCodeInOrderExecution(var1, var6);
        }

        if (!var3.isEmpty()) {
            var1.add("~" + var4 + "~");
        }

    }

    public static boolean isMarker(String var0) {
        return var0.matches("\\~[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}\\~");
    }

    public static int[] getPositionOfBothMarkers(ArrayList<String> var0, String var1) {
        if (!isMarker(var1)) {
            System.out.println(Functions.ANSI_RED_BLACK + "El parámetro recibido no es un marcador");
            return null;
        } else {
            int var2 = -1;
            int var3 = -1;

            for(int var4 = 0; var4 < var0.size(); ++var4) {
                String var5 = (String)var0.get(var4);
                if (var2 == -1 && var5.equals(var1)) {
                    var2 = var4;
                } else if (var3 == -1 && var5.equals(var1)) {
                    var3 = var4;
                }

                if (var2 != -1 && var3 != -1) {
                    break;
                }
            }

            return new int[]{var2, var3};
        }
    }

    public String toString() {
        String var1 = "[" + this.code.split("\n").length + " líneas...]";
        String var2 = "";

        for(int var3 = 0; var3 < this.codeBlockChilds.size(); ++var3) {
            CodeBlock var4 = (CodeBlock)this.codeBlockChilds.get(var3);
            if (var3 == 0) {
                var2 = String.valueOf(var4);
            } else {
                var2 = var2 + ", " + String.valueOf(var4);
            }
        }

        if (!var2.isEmpty()) {
            var1 = var1 + "-->(" + var2 + ")";
        }

        return var1;
    }
}

