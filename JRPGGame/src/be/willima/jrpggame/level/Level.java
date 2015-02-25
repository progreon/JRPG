/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpggame.level;

import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpgdatabase.model.JRPGProject;
import be.willima.jrpggame.gfx.Screen;
import be.willima.jrpggame.level.tiles.Tile;

/**
 *
 * @author Marco
 */
public class Level {

    private boolean stopsAtBorder = false;

    private JRPGProject project;
    private JRPGMap currentMap;

    private byte[] tiles;
    public int width;
    public int height;

    public Level(JRPGProject project, int mapID) {
        if (project != null && mapID < project.getMaps().size() && mapID >= 0) {
            this.project = project;
            this.currentMap = project.getMaps().get(mapID);
            loadLevelFromMap();
        } else if (project != null) {
            this.project = project;
            currentMap = null;
            this.width = 64;
            this.height = 64;
            tiles = new byte[width * height];
            generateLevel();
        } else {
            throw new RuntimeException("Project cannot be null!");
        }
    }

    private void loadLevelFromMap() {
        this.width = currentMap.getWidth();
        this.height = currentMap.getHeight();
        tiles = new byte[width * height];

        for (int i = 0; i < width * height; i++) {
            tiles[i] = (byte) currentMap.getTile(i % width, i / width).getTileID();
        }
    }

    private void generateLevel() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x * y % 10 < 5) {
                    tiles[x + y * width] = Tile.GRASS.getId(); // GRASS
                } else {
                    tiles[x + y * width] = Tile.STONE.getId(); // STONE
                }
            }
        }
    }

    public void tick() {

    }

    public void renderTiles(Screen screen, int xOffset, int yOffset) {
        if (stopsAtBorder) {
            if (xOffset < 0) {
                xOffset = 0;
            }
            if (xOffset > (width * 8) - screen.width) {
                xOffset = (width * 8) - screen.width;
            }
            if (yOffset < 0) {
                yOffset = 0;
            }
            if (yOffset > (height * 8) - screen.height) {
                yOffset = (height * 8) - screen.height;
            }
        }

        screen.setOffset(xOffset, yOffset);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getTile(x, y).render(screen, this, x * 8, y * 8);
            }
        }
    }

    private Tile getTile(int x, int y) {
        if (x < 0 || x > width || y < 0 || y > height) {
            return Tile.VOID;
        } else {
            return Tile.tiles[tiles[x + y * width]];
        }
    }

}
