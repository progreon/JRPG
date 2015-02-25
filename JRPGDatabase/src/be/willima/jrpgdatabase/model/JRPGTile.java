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

    private final int tileID;
    private final String tileName;
    private final int tileSize;
    private final int colorCount;

    // waar dit best bijhouden?
    // TODO: pixels => dubbele array
    private int[] pixels;
    private Color[] colors;
    // For animated sprite
//    private int[][] spriteSequence;

    private BufferedImage image = null;
    private Map<Integer, Image> scaledImages = null; // scale => scaled image

    /**
     * TODO private/public??
     *
     * @param tileID
     * @param name
     */
    public JRPGTile(int tileID, String name, int tileSize, int colorCount) {
        this.tileID = tileID;
        this.tileName = name;
        this.tileSize = tileSize;
        this.colorCount = colorCount;
        colors = new Color[colorCount];
        colors[0] = Color.BLACK;
        pixels = new int[tileSize * tileSize];
    }

    /**
     * TODO
     *
     * @param tileID
     * @param name
     * @param pixels
     * @param colors
     */
    public JRPGTile(int tileID, String name, int[] pixels, Color[] colors, int tileSize, int colorCount) {
        this(tileID, name, tileSize, colorCount);
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
        if (colorIndex >= 0 && colorIndex < colorCount) {
            colors[colorIndex] = color;
        } else {
            throw new IndexOutOfBoundsException("The color index must be between 0 (included) and " + colorCount + " (excluded)!");
        }
    }

    public Color getColor(int colorIndex) throws IndexOutOfBoundsException {
        Color color = null;
        if (colorIndex >= 0 && colorIndex < colorCount) {
            color = colors[colorIndex];
        } else {
            throw new IndexOutOfBoundsException("The color index must be between 0 (included) and " + colorCount + " (excluded)!");
        }
        return color;
    }

    // TODO:
    // Check array size
    // pixels = reference etc...
    // specific exception?
    private void setPixels(int[] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] < 0 || pixels[i] >= colorCount) {
                throw new RuntimeException("The pixel data must be between 0 (included) and " + colorCount + " (excluded)! (pixels[" + i + "] = " + pixels[i] + ")");
            }
        }
        this.pixels = pixels;
    }

    public int[] getPixels() {
        return this.pixels;
    }

    private void loadImage() {
        image = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_RGB);

        int[] rgbArray = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            rgbArray[i] = colors[pixels[i]].getRGB();
        }
        image.setRGB(0, 0, tileSize, tileSize, rgbArray, 0, tileSize);
    }

    public BufferedImage getImage() {
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
