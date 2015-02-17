/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.mapbuilder.view;

import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpgdatabase.model.JRPGTile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.TreeMap;
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
//        if (map != null) {
        init2();
//        }
        this.addMouseListener(new MapMouseInputHandler());
    }

    public JRPGMap getMap() {
        return this.map;
    }

    public void setMap(JRPGMap map) {
        this.map = map;
        init2();
    }

    public void updateScale(int scale) {
        this.scale = scale;
        scaledImages = null;
        init2();
    }

    public int getScale() {
        return this.scale;
    }

    public void setShowRaster(boolean showRaster) {
        this.showRaster = showRaster;
        init2();
    }

    public boolean getShowRaster() {
        return this.showRaster;
    }

    private void init2() {
        // TODO: enkel sizes zetten voorlopig...
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
//        invalidate();
    }

    private Map<Integer, Image> scaledImages = null; // Tile ID => scaled image

    private Image getScaledImage(JRPGTile tile) {
        if (scaledImages == null) {
            scaledImages = new TreeMap<>();
        }
        Image img = scaledImages.get(tile.getTileID());
        if (img == null) { // Put it in the map and return it
            Image tempImage = tile.getImage();
            img = tempImage.getScaledInstance(tempImage.getWidth(null) * scale, tempImage.getHeight(null) * scale, Image.SCALE_FAST);
            scaledImages.put(tile.getTileID(), img);
        }
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2 = (Graphics2D) g;

        // Background for testing
        g2.setColor(Color.green);
        g2.fillRect(0, 0, getWidth(), getHeight());
        if (map != null) {
            int scaledTileSize = JRPGMap.TILE_SIZE * scale;
            int width = map.getWidth();
            int height = map.getHeight();
            if (showRaster) {
                // Draw tiles
                for (int y = 0; y < width; y++) {
                    for (int x = 0; x < height; x++) {
                        g2.drawImage(getScaledImage(map.getTile(x, y)), x * scaledTileSize + x + 1, y * scaledTileSize + y + 1, this);
                    }
                }
                // Draw raster
                g2.setColor(new Color(0xff00aadd));
                for (int x = 0; x < getWidth(); x += scaledTileSize + 1) { // Vertical lines
                    g2.drawLine(x, 0, x, getHeight() - 1);
//                    g2.fillRect(x-1, 0, 3, getHeight() - 1);
                }
                for (int y = 0; y < getWidth(); y += scaledTileSize + 1) { // Horizontal lines
                    g2.drawLine(0, y, getWidth() - 1, y);
//                    g2.fillRect(0, y-1, getWidth() - 1, 3);
                }
            } else {
                // Draw tiles
                for (int y = 0; y < width; y++) {
                    for (int x = 0; x < height; x++) {
                        g2.drawImage(getScaledImage(map.getTile(x, y)), x * scaledTileSize, y * scaledTileSize, this);
                    }
                }
            }
        }
    }

    private class MapMouseInputHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = getMapX(e);
            int y = getMapY(e);
            if (x == -1 || y == -1) {
                System.out.println("Mouse clicked on raster.");
            } else {
                System.out.println("Mouse clicked on tile [" + x + ", " + y + "].");
            }
        }

        private int getMapX(MouseEvent e) {
            int scaledTileSize = JRPGMap.TILE_SIZE * scale;
            if (showRaster) {
                int tileJump = scaledTileSize + 1;
                int x = e.getX() / tileJump;
                if (e.getX() % tileJump == 0) {
                    return -1;
                } else {
                    return x;
                }
            } else {
                int x = e.getX() / scaledTileSize;
                return x;
            }
        }

        private int getMapY(MouseEvent e) {
            int scaledTileSize = JRPGMap.TILE_SIZE * scale;
            if (showRaster) {
                int tileJump = scaledTileSize + 1;
                int y = e.getY() / tileJump;
                if (e.getY() % tileJump == 0) {
                    return -1;
                } else {
                    return y;
                }
            } else {
                int y = e.getY() / scaledTileSize;
                return y;
            }
        }

    }

//    private void init() {
//        int width = map.getWidth();
//        int height = map.getHeight();
//        this.setLayout(new GridLayout(map.getWidth(), map.getHeight()));
//        this.setPreferredSize(new Dimension(width * JRPGMap.TILE_SIZE * scale + width * 2, height * JRPGMap.TILE_SIZE * scale + height * 2));
//        this.setMaximumSize(new Dimension(width * JRPGMap.TILE_SIZE * scale + width * 2, height * JRPGMap.TILE_SIZE * scale + height * 2));
////        this.setPreferredSize(new Dimension(width * JRPGMap.TILE_SIZE * SCALE + width * 2, height * JRPGMap.TILE_SIZE * SCALE + height * 2));
//        TileButton[][] buttons = new TileButton[width][height];
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                buttons[x][y] = new TileButton(map.getTile(x, y), x, y);
//                buttons[x][y].addActionListener(new ActionListener() {
//
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        System.out.println(((TileButton) e.getSource()));
//                    }
//                });
//            }
//        }
//
//        for (int y = 0; y < width; y++) {
//            for (int x = 0; x < height; x++) {
//                this.add(buttons[x][y]);
//            }
//        }
//    }
//    private class TileButton extends JButton {
//
//        private JRPGTile tile;
//        private final int x, y;
//        private Image image;
//        private Image scaledImage;
//
//        public TileButton(JRPGTile tile, int x, int y) {
//            image = tile.getImage();
//            scaledImage = image.getScaledInstance(image.getWidth(null) * scale, image.getHeight(null) * scale, Image.SCALE_FAST);
//            setMargin(new Insets(0, 0, 0, 0));
//            this.tile = tile;
//            this.x = x;
//            this.y = y;
//        }
//
//        public void setTile(JRPGTile tile) {
//            this.tile = tile;
//            image = tile.getImage();
//            scaledImage = image.getScaledInstance(image.getWidth(null) * scale, image.getHeight(null) * scale, Image.SCALE_FAST);
//        }
//
//        public JRPGTile getTile() {
//            return this.tile;
//        }
//
//        public int getXCoord() {
//            return this.x;
//        }
//
//        public int getYCoord() {
//            return this.y;
//        }
//
//        public void scaleChanged() {
//            scaledImage = image.getScaledInstance(image.getWidth(null) * scale, image.getHeight(null) * scale, Image.SCALE_FAST);
//        }
//
//        @Override
//        protected void paintBorder(Graphics g) {
//            super.paintBorder(g); //To change body of generated methods, choose Tools | Templates.
//            g.setColor(Color.BLACK);
//            g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
////            super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
//            Graphics2D g2 = (Graphics2D) g;
//
//            g2.drawImage(scaledImage, 1, 1, null);
//        }
//
//        @Override
//        public String toString() {
//            String str = "Tile with id " + tile.getTileID() + " on location [" + x + ", " + y + "]";
//            return str;
//        }
//
//    }
}
