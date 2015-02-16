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
import java.util.ArrayList;
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
    private Map<String, JRPGProject> projects; // URI -> JRPGProject
//    private Map<Integer, List<JRPGMap>> mapsPerProject; // Not really needed
    private Map<Integer, JRPGTile> tiles; // Tile-ID -> JRPGTile
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
    public void createNewProjectAndSetActive(String projectFolderLocation, String projectTitle, String gameTitle) {
        JRPGProject newProject = null;

        String projectFolderURI = projectFolderLocation + "/" + projectTitle + "/";

        if (!projects.containsKey(projectFolderURI)) {
//            newProject = new JRPGProject(this, projectFolderLocation, projectTitle, gameTitle, null);
            newProject = new JRPGProject(projectFolderLocation, projectTitle, gameTitle, null);
            activeProject = newProject;
        } else {
            Logger.getLogger(JRPGDaoDummy.class.toString()).log(Level.SEVERE, "Project folder name not unique!");
        }
    }

    @Override
    public void loadProjectByProjectFileURI(String projectFileURI) {
        String projectFolderURI = projectFileURI.substring(0, projectFileURI.lastIndexOf("/"));
        activeProject = projects.get(projectFolderURI);
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

    private void createDummyData() {
        // TODO
        createDummyTiles();
        JRPGProject project = createDummyProject();
        projects.put(project.getProjectFolderURI(), project);
        activeProject = project;
    }

    private JRPGProject createDummyProject() {
        List<JRPGMap> maps = new ArrayList<>();
        maps.add(createDummyMap());
        maps.add(createRandomDummyMap());
        return new JRPGProject("dummyfolderlocation", "Dummy project", "Dummy game", maps);
    }

    private JRPGMap createMapFromIDs(String mapName, int[][] mapTileIDs) {
        JRPGTile[][] mapTiles = new JRPGTile[mapTileIDs.length][mapTileIDs[0].length];
        for (int i = 0; i < mapTileIDs.length; i++) {
            for (int j = 0; j < mapTileIDs[i].length; j++) {
                mapTiles[i][j] = tiles.get(mapTileIDs[j][i]);
            }
        }
        JRPGMap map;
        try {
            map = new JRPGMap(mapName, mapTiles);
        } catch (Exception ex) {
            Logger.getLogger(JRPGDaoDummy.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("For now, using the default map instead ...");
            map = new JRPGMap(mapName, 0, 0);
        }
        return map;
    }

    private JRPGMap createDummyMap() {
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
        return createMapFromIDs("Dummy map", mapTileIDs);
    }

    private JRPGMap createRandomDummyMap() {
        Random rg = new Random();
        int size = rg.nextInt(20) + 10;
        int[][] mapTileIDs = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mapTileIDs[i][j] = rg.nextInt(numTiles);
            }
        }
        return createMapFromIDs("Random map", mapTileIDs);
    }

    private void createDummyTiles() {
        tiles = new TreeMap<>();
        numTiles = 0;
        // VOID-tile
        int voidTileID = numTiles;
        int[] voidPixels = new int[]{0, 1, 0, 1, 0, 1, 0, 1,
            1, 0, 1, 0, 1, 0, 1, 0,
            0, 1, 0, 1, 0, 1, 0, 1,
            1, 0, 1, 0, 1, 0, 1, 0,
            0, 1, 0, 1, 0, 1, 0, 1,
            1, 0, 1, 0, 1, 0, 1, 0,
            0, 1, 0, 1, 0, 1, 0, 1,
            1, 0, 1, 0, 1, 0, 1, 0};
        Color[] voidColors = new Color[]{Color.BLACK, Color.DARK_GRAY};
        JRPGTile voidTile = new JRPGTile(voidTileID, "VOID", voidPixels, voidColors);
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
        JRPGTile grassTile = new JRPGTile(grassTileID, "GRASS", grassPixels, grassColors);
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
        JRPGTile stoneTile = new JRPGTile(stoneTileID, "STONE", stonePixels, stoneColors);
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
        JRPGTile waterTile = new JRPGTile(waterTileID, "WATER", waterPixels, waterColors);
        tiles.put(waterTile.getTileID(), waterTile);

        numTiles++;
    }

}
