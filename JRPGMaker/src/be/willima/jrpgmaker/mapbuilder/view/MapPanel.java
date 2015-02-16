/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.mapbuilder.view;

import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpgdatabase.model.JRPGTile;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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

    public MapPanel() {
        this(null);
    }

    public MapPanel(JRPGMap map) {
        super();
        this.map = map;
        if (map != null) {
            init2();
        }
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

    private void init2() {
        // TODO: enkel size zetten voorlopig...
        int width = map == null ? 0 : map.getWidth();
        int height = map == null ? 0 : map.getHeight();
        this.setSize(new Dimension(width * JRPGMap.TILE_SIZE * scale, height * JRPGMap.TILE_SIZE * scale));
        this.setPreferredSize(new Dimension(width * JRPGMap.TILE_SIZE * scale, height * JRPGMap.TILE_SIZE * scale));
        this.setMinimumSize(new Dimension(width * JRPGMap.TILE_SIZE * scale, height * JRPGMap.TILE_SIZE * scale));
        this.setMaximumSize(new Dimension(width * JRPGMap.TILE_SIZE * scale, height * JRPGMap.TILE_SIZE * scale));
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

        if (map != null) {
            int scaledTileSize = JRPGMap.TILE_SIZE * scale;

            int width = map.getWidth();
            int height = map.getHeight();
            for (int y = 0; y < width; y++) {
                for (int x = 0; x < height; x++) {
                    g2.drawImage(getScaledImage(map.getTile(x, y)), x * scaledTileSize, y * scaledTileSize, this);
                }
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
