/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marco
 */
public class JRPGMap {
    
    private int width;
    private int height;
    
    private List<List<JRPGTile>> tiles;
    //obstacles

    public JRPGMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j < height; j++) {
                tiles.get(i).add(JRPGTile.VOID);
            }
        }
    }
    
}
