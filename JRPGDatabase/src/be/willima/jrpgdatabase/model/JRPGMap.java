/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.model;

/**
 * TODO
 *
 * @author marco
 */
public class JRPGMap {

    // TODO Waar dit best zetten? Project!

    // Dit zou niet nodig moeten zijn op deze plek?
    public static final int DEFAULT_ID = -1;
    public static final int DEFAULT_WIDTH = 16;
    public static final int DEFAULT_HEIGHT = DEFAULT_WIDTH;
    public static final String DEFAULT_TITLE = "New map";
    
    private final JRPGProject project;

    private final int mapID;
    private String mapTitle;

    private final int width;
    private final int height;

//    private JRPGTile[] usedTiles;
    // ?? vervangen door: private int[][] tiles; // de tile-ID's ??
    private final JRPGTile[][] tiles;
    // TODO Obstacles
    // TODO Events

    /**
     * Constructor for use within a project.
     *
     * @param mapID
     * @param mapTitle
     * @param tiles
     * @throws java.lang.Exception
     */
    public JRPGMap(JRPGProject project, int mapID, String mapTitle, JRPGTile[][] tiles) throws Exception { // TODO: Specific exception?
        this.project = project;
        this.mapID = mapID;
        this.mapTitle = mapTitle;
        this.width = tiles.length;
        // Check if tiles is a triangular 2D-array
        int tempHeight = tiles[0].length;
        for (int i = 1; i < width; i++) {
            if (tiles[i].length != tempHeight) {
                throw new Exception("The 2D tiles array is not triangular!");
            }
        }
        this.height = tempHeight;
        this.tiles = tiles;
    }

    /**
     * Constructor for use within a project.
     *
     * @param mapID
     * @param mapTitle
     * @param width
     * @param height
     */
    public JRPGMap(JRPGProject project, int mapID, String mapTitle, int width, int height) {
        this.project = project;
        this.mapID = mapID;
        this.mapTitle = mapTitle;
        this.width = width;
        this.height = height;
        this.tiles = new JRPGTile[width][height];
        // Fill the map with void tiles:
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = project.getTile(0);
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

    /**
     * Get the width as a number of tiles.
     *
     * @return
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get the height as a number of tiles.
     *
     * @return
     */
    public int getHeight() {
        return this.height;
    }

    public JRPGTile getTile(int x, int y) throws IndexOutOfBoundsException {
        JRPGTile tile = null;

        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            throw new IndexOutOfBoundsException("Tile not within map region!");
        } else {
            tile = this.tiles[x][y];
        }

        return tile;
    }
    
    public JRPGProject getProject() {
        return this.project;
    }

//    public void setTile(JRPGTile tile, int x, int y) {
//        // TODO change tile in array, notify watchers I'm changed?
//    }
}
