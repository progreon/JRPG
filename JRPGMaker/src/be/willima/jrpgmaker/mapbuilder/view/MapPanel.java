/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.mapbuilder.view;

import be.willima.jrpgdatabase.model.JRPGMap;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
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
        if (showRaster) {
            int dimWidth = width * scaledTileSize + width + 1;
            int dimHeight = height * scaledTileSize + height + 1;
            this.setSize(new Dimension(dimWidth, dimHeight));
            this.setPreferredSize(new Dimension(dimWidth, dimHeight));
            this.setMinimumSize(new Dimension(dimWidth, dimHeight));
            this.setMaximumSize(new Dimension(dimWidth, dimHeight));
        } else {
            int dimWidth = width * scaledTileSize;
            int dimHeight = height * scaledTileSize;
            this.setSize(new Dimension(dimWidth, dimHeight));
            this.setPreferredSize(new Dimension(dimWidth, dimHeight));
            this.setMinimumSize(new Dimension(dimWidth, dimHeight));
            this.setMaximumSize(new Dimension(dimWidth, dimHeight));
        }
    }

    private Point getTilePixelPoint(int tileX, int tileY) {
        int scaledTileSize = JRPGMap.TILE_SIZE * scale;
        int x, y;
        if (showRaster) {
            x = tileX * (scaledTileSize + 1) + 1;
            y = tileY * (scaledTileSize + 1) + 1;
        } else {
            x = tileX * scaledTileSize;
            y = tileY * scaledTileSize;
        }
        return new Point(x, y);
    }

    private Point getMapPoint(int tilePixelX, int tilePixelY) {
        int scaledTileSize = JRPGMap.TILE_SIZE * scale;
        int x, y;
        if (showRaster) {
            int tileJump = scaledTileSize + 1;
            x = tilePixelX / tileJump;
            y = tilePixelY / tileJump;
            if (tilePixelX % tileJump == 0) {
                x = -1;
            }
            if (tilePixelY % tileJump == 0) {
                y = -1;
            }
        } else {
            x = tilePixelX / scaledTileSize;
            y = tilePixelY / scaledTileSize;
        }
        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

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
                g2.setColor(new Color(0xff00aadd));
                for (int x = 0; x < getWidth(); x += scaledTileSize + 1) { // Vertical lines
                    g2.drawLine(x, 0, x, getHeight() - 1);
                }
                for (int y = 0; y < getHeight(); y += scaledTileSize + 1) { // Horizontal lines
                    g2.drawLine(0, y, getWidth() - 1, y);
                }
            }
        }
    }

    private class MapMouseInputHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            Point p = getMapPoint(e.getX(), e.getY());
            if (p.x == -1 || p.y == -1) {
                System.out.println("Mouse clicked on raster.");
            } else {
                System.out.println("Mouse clicked on tile [" + p.x + ", " + p.y + "].");
            }
        }

    }

}
