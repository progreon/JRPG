/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpggame.gfx;

/**
 * TODO constants?
 *
 * @author Marco
 */
public class Colors {

    public static int get(int color1, int color2, int color3, int color4) {
        return ((get(color4) << 24) + (get(color3) << 16) + (get(color2) << 8) + get(color1));
    }

    public static int get(int color) {
        if (color < 0) {
            return 255;
        } else {
            int r = color / 100 % 10;
            int g = color / 10 % 10;
            int b = color % 10;
            return r * 36 + g * 6 + b;
        }
    }

}
