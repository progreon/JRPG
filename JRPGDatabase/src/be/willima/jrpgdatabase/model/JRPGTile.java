/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.model;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

/**
 * TODO check stuff here ... This can be better!
 *
 * @author marco
 */
public class JRPGTile {

    public final static int COLOR_COUNT = 4;

//    public static JRPGTile VOID = new JRPGTile(0, "VOID");
//    public static JRPGTile GRASS = new JRPGTile(1, "GRASS");

    private final int tileID;
    private final String tileName;

    // waar dit best bijhouden?
    // TODO: pixels => dubbele array
    private int[] pixels;
    private Color[] colors;
    // For animated sprite
//    private int[][] spriteSequence;

    private Image image = null;
    private Map<Integer, Image> scaledImages = null; // scale => scaled image

    /**
     * TODO private/public??
     *
     * @param tileID
     * @param name
     */
    public JRPGTile(int tileID, String name) {
        this.tileID = tileID;
        this.tileName = name;
        colors = new Color[COLOR_COUNT];
        colors[0] = Color.BLACK;
        pixels = new int[JRPGMap.TILE_SIZE * JRPGMap.TILE_SIZE];
    }

    /**
     * TODO
     *
     * @param tileID
     * @param name
     * @param pixels
     * @param colors
     */
    public JRPGTile(int tileID, String name, int[] pixels, Color[] colors) {
        this(tileID, name);
        setPixels(pixels);
        for (int i = 0; i < colors.length; i++) {
            setColor(i, colors[i]);
        }
    }

    public int getTileID() {
        return tileID;
    }

    public String getTileName() {
        return tileName;
    }

    public void setColor(int colorIndex, Color color) throws IndexOutOfBoundsException {
        if (colorIndex >= 0 && colorIndex < COLOR_COUNT) {
            colors[colorIndex] = color;
        } else {
            throw new IndexOutOfBoundsException("The color index must be between 0 (included) and " + COLOR_COUNT + " (excluded)!");
        }
    }

    public Color getColor(int colorIndex) throws IndexOutOfBoundsException {
        Color color = null;
        if (colorIndex >= 0 && colorIndex < COLOR_COUNT) {
            color = colors[colorIndex];
        } else {
            throw new IndexOutOfBoundsException("The color index must be between 0 (included) and " + COLOR_COUNT + " (excluded)!");
        }
        return color;
    }

    // TODO:
    // Check array size
    // pixels = reference etc...
    // specific exception?
    public void setPixels(int[] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] < 0 || pixels[i] >= COLOR_COUNT) {
                throw new RuntimeException("The pixel data must be between 0 (included) and " + COLOR_COUNT + " (excluded)! (pixels[" + i + "] = " + pixels[i] + ")");
            }
        }
        this.pixels = pixels;
    }

    public int[] getPixels() {
        return this.pixels;
    }

    private void loadImage() {
        image = new BufferedImage(JRPGMap.TILE_SIZE, JRPGMap.TILE_SIZE, BufferedImage.TYPE_INT_RGB);

        int[] rgbArray = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            rgbArray[i] = colors[pixels[i]].getRGB();
        }
        ((BufferedImage) image).setRGB(0, 0, JRPGMap.TILE_SIZE, JRPGMap.TILE_SIZE, rgbArray, 0, JRPGMap.TILE_SIZE);
    }

    public Image getImage() {
        if (image == null) {
            loadImage();
        }
        return image;
    }

    public Image getScaledImage(int scale) {
        if (scaledImages == null) {
            scaledImages = new TreeMap<>();
        }
        Image scaledImg = scaledImages.get(scale);
        if (scaledImg == null) { // Put it in the map and return it
            Image img = getImage();
            scaledImg = getImage().getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_FAST);
            scaledImages.put(scale, scaledImg);
        }
        return scaledImg;
    }

}
