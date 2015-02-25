/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.model;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Marco
 */
public class TileManager {
    
    private final Map<Integer, Map<Integer, JRPGTile>> tiles;

    public TileManager() {
        tiles = new TreeMap<>();
    }
    
}
