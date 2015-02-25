/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpggame.gfx;

import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpgdatabase.model.JRPGProject;
import be.willima.jrpgdatabase.model.JRPGTile;
import java.awt.Graphics;

/**
 *
 * @author Marco
 */
public class Screen {

    public static final int MAP_WIDTH = 64;
    public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
    public static final int COLOR_COUNT = 4;

    public int[] pixels;

    //////////////////////
    public int[] tiles = new int[MAP_WIDTH * MAP_WIDTH];
    public int[] colors = new int[MAP_WIDTH * MAP_WIDTH * COLOR_COUNT];
    //////////////////////
    public int xOffset = 0, yOffset = 0;

    public int width;
    public int height;

//    public SpriteSheet sheet;
    private JRPGProject project;
    private JRPGMap currentMap;

    public Screen(JRPGProject project, int width, int height) {
        this.project = project;
        this.width = width;
        this.height = height;

        pixels = new int[width * height];

        ///////////////
        for (int i = 0; i < MAP_WIDTH * MAP_WIDTH; i++) {
            colors[i * COLOR_COUNT + 0] = 0xff00ff;
            colors[i * COLOR_COUNT + 1] = 0x00ffff;
            colors[i * COLOR_COUNT + 2] = 0xffff00;
            colors[i * COLOR_COUNT + 3] = 0xffffff;
        }
    }

    public void render2(Graphics g) {
        int tileSize = project.getTileSize();
        for (int yTile = yOffset / tileSize; yTile <= (yOffset + height) / tileSize; yTile++) {
            for (int xTile = xOffset / tileSize; xTile <= (xOffset + width) / tileSize; xTile++) {
//                JRPGTile tile = ...
                JRPGTile tile = project.getTile(0);
                int y = yTile * tileSize - yOffset;
                int x = xTile * tileSize - xOffset;
//                image.getGraphics().drawImage(tile.getImage(), x, y, null);
                g.drawImage(tile.getImage(), x, y, null);
            }
        }
    }

    public void render(int[] pixels, int offset, int row) {
        // Render tiles
        int tileSize = project.getTileSize();
        for (int yTile = yOffset / tileSize; yTile <= (yOffset + height) / tileSize; yTile++) { // De y coords van de zichtbare tiles
            // Per tile, de min en max y coords vd pixels op het scherm
            int yMin = yTile * tileSize - yOffset;
            int yMax = yMin + tileSize;
            // Afknippen wat buiten het scherm gaat:
            if (yMin < 0) {
                yMin = 0;
            }
            if (yMax > height) {
                yMax = height;
            }
            for (int xTile = xOffset / tileSize; xTile <= (xOffset + width) / tileSize; xTile++) { // De x coords van de zichtbare tiles
                // Per tile, de min en max x coords vd pixels op het scherm
                int xMin = xTile * tileSize - xOffset;
                int xMax = xMin + tileSize;
                // Afknippen wat buiten het scherm gaat:
                if (xMin < 0) {
                    xMin = 0;
                }
                if (xMax > width) {
                    xMax = width;
                }

                int tileIndex = (xTile % (MAP_WIDTH)) + (yTile % (MAP_WIDTH)) * MAP_WIDTH;
                JRPGTile tile = project.getTile(4);
                int[] tilePixels = tile.getPixels();

                for (int y = yMin; y < yMax; y++) {
                    int sheetPixel = ((y + yOffset) % tileSize) * tileSize + ((xMin + xOffset) % tileSize);
                    int tilePixel = offset + xMin + y * row;
                    for (int x = xMin; x < xMax; x++) {
                        int color = tileIndex * 4 + tilePixels[sheetPixel++];
                        pixels[tilePixel++] = colors[color];
                    }
                }
            }
        }
        // End of rendering tiles
    }

    // Tile index on the sheet
    // TODO constants?!?
    // TODO flipping: https://www.youtube.com/watch?v=gWSx7S8YiPQ
    // TODO fonts: https://www.youtube.com/watch?v=XSBTWhV75hM
    public void render(int xPos, int yPos, int tileIndex, int color) {
        xPos -= xOffset;
        yPos -= yOffset;

//        int xTile = tileIndex % 32;
//        int yTile = tileIndex / 32;
        JRPGTile tile = project.getTile(tileIndex);
        
//        int tileOffset = (xTile * 8) + (yTile * 8) * project.getTileSize();
        for (int y = 0; y < 8; y++) {
            if (y + yPos < 0 || y + yPos >= height) {
                continue; // TODO ugh
            }
            int ySheet = y;
            for (int x = 0; x < 8; x++) {
                if (x + xPos < 0 || x + xPos >= width) {
                    continue; // TODO ugh
                }
                int xSheet = x;
                int col = (color >> (tile.getPixels()[xSheet + ySheet * project.getTileSize()] * 8)) & 255;
                if (col < 255) {
                    pixels[x + xPos + (y + yPos) * width] = col;
                }
            }
        }
    }

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

}
