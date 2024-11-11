/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorjava;

import java.awt.Color;

/**
 *
 * @author matie
 */
public class TextColor {
    private final int start;
    private final int size;
    private final Color color;

    public TextColor(int var1, int var2, Color var3) {
        this.start = var1;
        this.size = var2;
        this.color = var3;
    }

    public int getStart() {
        return this.start;
    }

    public int getSize() {
        return this.size;
    }

    public Color getColor() {
        return this.color;
    }

    public String toString() {
        return "TextColor{start=" + this.start + ", size=" + this.size + ", color=" + this.color + "}";
    }
}

