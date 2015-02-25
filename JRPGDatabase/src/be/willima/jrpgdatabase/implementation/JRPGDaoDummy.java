/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.implementation;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpgdatabase.model.JRPGProject;
import be.willima.jrpgdatabase.model.JRPGTile;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO
 *
 * @author marco
 */
public class JRPGDaoDummy implements JRPGDao {

    private JRPGProject activeProject = null;

    //// THINGS THAT NORMALLY SHOULD BE IN ACTUAL DATABASE ////
    private final Map<String, JRPGProject> projects; // URI -> JRPGProject
    private JRPGProject dummyProject;
//    private Map<Integer, List<JRPGMap>> mapsPerProject; // Not really needed
    private Map<Integer, JRPGTile> tiles; // Tile-ID -> JRPGTile // TODO Change to array!
    private int numTiles;
    //// END OF THOSE THINGS ////

    /**
     * TODO public?
     */
    public JRPGDaoDummy() {
        projects = new HashMap<>();
        createDummyData();
    }

    @Override
    public String getInfo() {
        return "This is the dummy DAO, used for initial testing.";
    }

    @Override
    public void createNewProject(String projectFolderLocation, String projectTitle, String gameTitle) {
        // Puts dummy data into the new project
        // TODO Make a new dummy project seperately, and open it from within the GUI?
        String projectFolderURI = projectFolderLocation + "/" + projectTitle + "/";
        if (!projects.containsKey(projectFolderURI)) {
            JRPGProject newProject = new JRPGProject(this, projectTitle, gameTitle, 8, 4, null);
            projects.put(projectFolderURI, newProject);
            List<JRPGMap> maps = newProject.getMaps();
            maps.add(createDummyMap(newProject));
            maps.add(createRandomDummyMap(newProject));
            activeProject = newProject;
        } else {
            Logger.getLogger(JRPGDaoDummy.class.toString()).log(Level.SEVERE, "Project folder name not unique!");
        }
    }

    @Override
    public DaoError loadProjectByProjectFileURI(String projectFileURI) {
        String projectFolderURI = projectFileURI.substring(0, projectFileURI.lastIndexOf("/"));
        if (projects.get(projectFolderURI) != null) {
            activeProject = projects.get(projectFolderURI);
            return DaoError.NO_ERROR;
        } else {
            return DaoError.NO_PROJECT;
        }
    }

    @Override
    public DaoError loadProjectByProjectFolderURI(String projectFolderURI) {
        if (projects.get(projectFolderURI) != null) {
            activeProject = projects.get(projectFolderURI);
            return DaoError.NO_ERROR;
        } else {
            activeProject = dummyProject;
            return DaoError.NO_ERROR;
        }
    }

    @Override
    public JRPGProject getActiveProject() {
        return this.activeProject;
    }

    @Override
    public DaoError saveActiveProject() {
        Logger.getLogger(JRPGDaoDummy.class.toString()).log(Level.WARNING, "You cannot save in dummy mode!");
        return DaoError.NO_SAVE;
    }

    @Override
    public JRPGTile getTile(int tileID) {
        return tiles.get(tileID);
    }

    private void createDummyData() {
        // TODO More dummy data!
        createDummyTiles();
        dummyProject = new JRPGProject(this, "Dummy Project", "Dummy Game", 8, 4, null);
        List<JRPGMap> maps = dummyProject.getMaps();
        maps.add(createDummyMap(dummyProject));
        maps.add(createRandomDummyMap(dummyProject));
    }

    private JRPGMap createMapFromIDs(JRPGProject project, int mapID, String mapName, int[][] mapTileIDs) {
        JRPGTile[][] mapTiles = new JRPGTile[mapTileIDs[0].length][mapTileIDs.length];
        for (int i = 0; i < mapTileIDs.length; i++) {
            for (int j = 0; j < mapTileIDs[i].length; j++) {
                mapTiles[j][i] = tiles.get(mapTileIDs[i][j]);
            }
        }
        JRPGMap map;
        try {
            map = new JRPGMap(project, mapID, mapName, mapTiles);
        } catch (Exception ex) {
            Logger.getLogger(JRPGDaoDummy.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("For now, using the default map instead ...");
            map = new JRPGMap(project, mapID, mapName, mapTiles.length, mapTiles[0].length);
        }
        return map;
    }

    private JRPGMap createDummyMap(JRPGProject project) {
        int[][] mapTileIDs = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 3, 3, 3, 3, 3, 3, 3, 3, 0},
            {0, 3, 3, 1, 1, 1, 1, 3, 3, 0},
            {0, 3, 1, 1, 2, 1, 1, 1, 3, 0},
            {0, 3, 1, 1, 1, 1, 2, 1, 3, 0},
            {0, 3, 1, 1, 1, 1, 1, 1, 3, 0},
            {0, 3, 1, 1, 1, 2, 1, 1, 3, 0},
            {0, 3, 3, 1, 1, 1, 1, 3, 3, 0},
            {0, 3, 3, 3, 3, 3, 3, 3, 3, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        return createMapFromIDs(project, 0, "Dummy map", mapTileIDs);
    }

    private JRPGMap createRandomDummyMap(JRPGProject project) {
        Random rg = new Random();
        int size = rg.nextInt(20) + 10;
        int[][] mapTileIDs = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mapTileIDs[i][j] = rg.nextInt(numTiles);
            }
        }
        return createMapFromIDs(project, 1, "Random map", mapTileIDs);
    }

    private void createDummyTiles() {
        tiles = new TreeMap<>();
        numTiles = 0;
        // VOID-tile
        int voidTileID = numTiles;
        int[] voidPixels = new int[]{0, 0, 1, 1, 0, 0, 1, 1,
            0, 0, 1, 1, 0, 0, 1, 1,
            1, 1, 0, 0, 1, 1, 0, 0,
            1, 1, 0, 0, 1, 1, 0, 0,
            0, 0, 1, 1, 0, 0, 1, 1,
            0, 0, 1, 1, 0, 0, 1, 1,
            1, 1, 0, 0, 1, 1, 0, 0,
            1, 1, 0, 0, 1, 1, 0, 0};
        Color[] voidColors = new Color[]{Color.BLACK, Color.DARK_GRAY};
        JRPGTile voidTile = new JRPGTile(voidTileID, "VOID", voidPixels, voidColors, 8, 4);
        tiles.put(voidTile.getTileID(), voidTile);

        numTiles++;

        // GRASS-tile
        int grassTileID = numTiles;
        int[] grassPixels = new int[]{0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 0, 0,
            0, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 1, 0,
            0, 0, 1, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0};
        Color[] grassColors = new Color[]{Color.GREEN, Color.GRAY};
        JRPGTile grassTile = new JRPGTile(grassTileID, "GRASS", grassPixels, grassColors, 8, 4);
        tiles.put(grassTile.getTileID(), grassTile);

        numTiles++;

        // STONE-tile
        int stoneTileID = numTiles;
        int[] stonePixels = new int[]{0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 1, 0,
            0, 0, 0, 0, 0, 1, 1, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 1, 0, 0,
            0, 1, 1, 0, 1, 1, 0, 0,
            0, 1, 1, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0};
        Color[] stoneColors = new Color[]{Color.GRAY, Color.LIGHT_GRAY};
        JRPGTile stoneTile = new JRPGTile(stoneTileID, "STONE", stonePixels, stoneColors, 8, 4);
        tiles.put(stoneTile.getTileID(), stoneTile);

        numTiles++;

        // WATER-tile
        int waterTileID = numTiles;
        int[] waterPixels = new int[]{0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 1, 1, 0, 0, 0, 0,
            0, 1, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 1, 0,
            0, 0, 1, 1, 0, 0, 0, 0,
            0, 1, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 1, 0,
            0, 0, 0, 0, 0, 0, 0, 0};
        Color[] waterColors = new Color[]{Color.BLUE, Color.CYAN};
        JRPGTile waterTile = new JRPGTile(waterTileID, "WATER", waterPixels, waterColors, 8, 4);
        tiles.put(waterTile.getTileID(), waterTile);

        numTiles++;

        // FANCY-tile
        int fancyTileID = numTiles;
        int[] fancyPixels = new int[]{3, 3, 3, 3, 3, 3, 3, 3,
            3, 2, 2, 2, 2, 2, 2, 3,
            3, 2, 1, 1, 1, 1, 2, 3,
            3, 2, 1, 0, 0, 1, 2, 3,
            3, 2, 1, 0, 0, 1, 2, 3,
            3, 2, 1, 1, 1, 1, 2, 3,
            3, 2, 2, 2, 2, 2, 2, 3,
            3, 3, 3, 3, 3, 3, 3, 3};
        Color[] fancyColors = new Color[]{new Color(0xffffff), new Color(0xffff00), new Color(0x00ffff), new Color(0xff00ff)};
        JRPGTile fancyTile = new JRPGTile(fancyTileID, "FANCY", fancyPixels, fancyColors, 8, 4);
        tiles.put(fancyTile.getTileID(), fancyTile);

        numTiles++;

        // FANCY-tile
        int RTileID = numTiles;
        int[] RPixels = new int[]{3, 2, 2, 2, 2, 2, 2, 3,
            2, 0, 1, 1, 1, 1, 0, 2,
            2, 0, 1, 0, 0, 1, 0, 2,
            2, 0, 1, 1, 1, 1, 0, 2,
            2, 0, 1, 1, 0, 0, 0, 2,
            2, 0, 1, 0, 1, 0, 0, 2,
            2, 0, 1, 0, 0, 1, 0, 2,
            3, 2, 2, 2, 2, 2, 2, 3};
        Color[] RColors = new Color[]{new Color(0xffffff), new Color(0xffff00), new Color(0x00ffff), new Color(0xff00ff)};
        JRPGTile RTile = new JRPGTile(RTileID, "R", RPixels, RColors, 8, 4);
        tiles.put(RTile.getTileID(), RTile);

        numTiles++;
        System.out.println("numTiles: " + numTiles);
    }

}
