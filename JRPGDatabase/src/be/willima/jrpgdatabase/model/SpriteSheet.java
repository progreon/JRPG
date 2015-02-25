/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * TODO
 *
 * @author marco
 */
public class SpriteSheet {

    public final static int COLOR_COUNT = 4;

    private final String path;
    private final int width;
    private final int height;

    private int[] pixels;

    public SpriteSheet(String path) {
        this.path = path;

        BufferedImage image = null;
        try {
            System.out.println(path);
            image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.width = image.getWidth();
        this.height = image.getHeight();
        pixels = image.getRGB(0, 0, width, height, null, 0, width);

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = (pixels[i] & 0xff) / (256 / COLOR_COUNT);
        }
        
        for (int i=0; i<8; i++) {
            System.out.println(pixels[i]);
        }
    }

}
