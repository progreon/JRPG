/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author marco
 */
public class JRPGMap {
    
    private final int mapID;
    private String mapTitle;
    
    private int width;
    private int height;
    
    private final List<List<JRPGTile>> tiles;
    // Obstacles: TODO
    
    
    protected JRPGMap(int mapID, String mapTitle, List<List<JRPGTile>> tiles) {
        this.mapID = mapID;
        this.mapTitle = mapTitle;
        this.width = tiles.size();
        this.height = (width==0?0:tiles.get(0).size());
        this.tiles = tiles;
    }

    protected JRPGMap(int mapID, String mapTitle, int width, int height) {
        this.mapID = mapID;
        this.mapTitle = mapTitle;
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
    
    public int getMapID() {
        return this.mapID;
    }
    
    public String getMapTitle() {
        return this.mapTitle;
    }
    
    public void setMapTitle(String mapTitle) {
        this.mapTitle = mapTitle;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public JRPGTile getTile(int x, int y) throws IndexOutOfBoundsException {
        JRPGTile tile = null;
        
        if (x < 0 || x >= this.width || y < 0 || y >= this.width) {
            throw new IndexOutOfBoundsException("Tile not within map region!");
        } else {
            tile = this.tiles.get(x).get(y);
        }
        
        return tile;
    }
    
    public void addRow(int rowNo) {
        // TODO
        this.height++;
        throw new UnsupportedOperationException("addRow(int rowNo): Not supported yet.");
    }
    
    public void deleteRow(int rowNo) {
        // TODO
        this.height--;
        throw new UnsupportedOperationException("deleteRow(int rowNo): Not supported yet.");
    }
    
    public void addColumn(int colNo) {
        // TODO
        this.width++;
        throw new UnsupportedOperationException("addColumn(int colNo): Not supported yet.");
    }
    
    public void deleteColumn(int colNo) {
        // TODO
        this.width--;
        throw new UnsupportedOperationException("deleteColumn(int colNo): Not supported yet.");
    }
    
}
