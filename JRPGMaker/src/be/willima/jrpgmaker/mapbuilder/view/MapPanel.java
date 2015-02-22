/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.mapbuilder.view;

import be.willima.jrpgdatabase.model.JRPGMap;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * TODO comments
 *
 * @author marco
 */
public class MapPanel extends JPanel {

    private JRPGMap map;
    private int scale = 3;
    private boolean showRaster = false;

    public MapPanel() {
        this(null);
    }

    public MapPanel(JRPGMap map) {
        super();
        this.map = map;
        init();
        this.addMouseListener(new MapMouseInputHandler());
    }

    public JRPGMap getMap() {
        return this.map;
    }

    public void setMap(JRPGMap map) {
        this.map = map;
        init();
    }

    public void updateScale(int scale) {
        this.scale = scale;
        init();
    }

    public int getScale() {
        return this.scale;
    }

    public void setShowRaster(boolean showRaster) {
        this.showRaster = showRaster;
        init();
    }

    public boolean getShowRaster() {
        return this.showRaster;
    }

    private void init() { // TODO ?, enkel sizes zetten voorlopig...
        int width = map == null ? 0 : map.getWidth();
        int height = map == null ? 0 : map.getHeight();
        int scaledTileSize = JRPGMap.TILE_SIZE * scale;
        int dimWidth = width * scaledTileSize;
        int dimHeight = height * scaledTileSize;
        this.setSize(new Dimension(dimWidth, dimHeight));
        this.setPreferredSize(new Dimension(dimWidth, dimHeight));
        this.setMinimumSize(new Dimension(dimWidth, dimHeight));
        this.setMaximumSize(new Dimension(dimWidth, dimHeight));
        
        this.repaint();
    }

    private Point getTilePixelPoint(int tileX, int tileY) {
        int scaledTileSize = JRPGMap.TILE_SIZE * scale;
        int x = tileX * scaledTileSize;
        int y = tileY * scaledTileSize;
        
        return new Point(x, y);
    }

    private Point getMapPoint(int tilePixelX, int tilePixelY) {
        int scaledTileSize = JRPGMap.TILE_SIZE * scale;
        int x = tilePixelX / scaledTileSize;
        int y = tilePixelY / scaledTileSize;
        
        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
        
        // Double buffering ...
        BufferedImage buff = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = buff.createGraphics();

        g2.setColor(Color.red);
        g2.fillRect(0, 0, getWidth(), getHeight()); // Background for testing

        if (map != null) { // Draw map
            int scaledTileSize = JRPGMap.TILE_SIZE * scale;
            int width = map.getWidth();
            int height = map.getHeight();
            for (int y = 0; y < height; y++) { // Draw tiles
                for (int x = 0; x < width; x++) {
                    g2.drawImage(map.getTile(x, y).getScaledImage(scale), getTilePixelPoint(x, y).x, getTilePixelPoint(x, y).y, this);
                }
            }
            if (showRaster) { // Draw raster
//                g2.setColor(new Color(0x7700aadd));
                g2.setColor(new Color(0x666666));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

                for (int x = 0; x <= getWidth(); x += scaledTileSize) { // Vertical lines
                    g2.drawLine(x - 1, 0, x - 1, getHeight());
                    g2.drawLine(x, 0, x, getHeight());
                }
                for (int y = 0; y <= getHeight(); y += scaledTileSize) { // Horizontal lines
                    g2.drawLine(0, y - 1, getWidth(), y - 1);
                    g2.drawLine(0, y, getWidth(), y);
                }
            }
        }
        
        g.drawImage(buff, 0, 0, null);
        g2.dispose();
    }

    private class MapMouseInputHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            Point p = getMapPoint(e.getX(), e.getY());
            System.out.println("Mouse clicked on tile [" + p.x + ", " + p.y + "].");
        }

    }

}
