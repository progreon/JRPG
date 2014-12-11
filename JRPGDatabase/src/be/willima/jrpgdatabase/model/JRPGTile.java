/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.model;

/**
 *
 * @author marco
 */
public class JRPGTile {

    public static JRPGTile VOID = new JRPGTile(0);
    public static JRPGTile GRASS = new JRPGTile(1);

    private final int tileID;
    // Texture (= 2D tabel van color?): TODO

    private JRPGTile() {
        tileID = 0;
    }

    public JRPGTile(int tileID) {
        this.tileID = tileID;
    }

    public int getTileID() {
        return tileID;
    }

}
