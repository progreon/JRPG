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
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author marco
 */
public class MapPanel extends JPanel {

    private JRPGMap map;
    private final int SCALE = 3;

//    public MapPanel() {
//        this(new JRPGMap());
//    }

    public MapPanel(JRPGMap map) {
        super(new GridLayout(map.getWidth(), map.getHeight()));
        this.map = map;

        init();
    }

    private void init() {
        int width = map.getWidth();
        int height = map.getHeight();
        this.setPreferredSize(new Dimension(width * JRPGMap.TILE_SIZE * SCALE + width * 2, height * JRPGMap.TILE_SIZE * SCALE + height * 2));
        this.setMaximumSize(new Dimension(width * JRPGMap.TILE_SIZE * SCALE + width * 2, height * JRPGMap.TILE_SIZE * SCALE + height * 2));
//        this.setPreferredSize(new Dimension(width * JRPGMap.TILE_SIZE * SCALE + width * 2, height * JRPGMap.TILE_SIZE * SCALE + height * 2));
        TileButton[][] buttons = new TileButton[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                buttons[x][y] = new TileButton(map.getTile(x, y), x, y);
                buttons[x][y].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(((TileButton) e.getSource()));
                    }
                });
            }
        }

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                this.add(buttons[x][y]);
            }
        }
    }

    private class TileButton extends JButton {

        private JRPGTile tile;
        private final int x, y;
        private Image image;
        private Image scaledImage;

        public TileButton(JRPGTile tile, int x, int y) {
            image = tile.getImage();
            scaledImage = image.getScaledInstance(image.getWidth(null) * SCALE, image.getHeight(null) * SCALE, Image.SCALE_FAST);
            setMargin(new Insets(0, 0, 0, 0));
            this.tile = tile;
            this.x = x;
            this.y = y;
        }
        
        public void setTile(JRPGTile tile) {
            this.tile = tile;
            image = tile.getImage();
            scaledImage = image.getScaledInstance(image.getWidth(null) * SCALE, image.getHeight(null) * SCALE, Image.SCALE_FAST);
        }
        
        public JRPGTile getTile() {
            return this.tile;
        }
        
        public int getXCoord() {
            return this.x;
        }
        
        public int getYCoord() {
            return this.y;
        }
        
        public void scaleChanged() {
            scaledImage = image.getScaledInstance(image.getWidth(null) * SCALE, image.getHeight(null) * SCALE, Image.SCALE_FAST);
        }

        @Override
        protected void paintBorder(Graphics g) {
            super.paintBorder(g); //To change body of generated methods, choose Tools | Templates.
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
        }

        @Override
        protected void paintComponent(Graphics g) {
//            super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
            Graphics2D g2 = (Graphics2D) g;

            g2.drawImage(scaledImage, 1, 1, null);
        }

        @Override
        public String toString() {
            String str = "Tile with id " + tile.getTileID() + " on location [" + x + ", " + y + "]";
            return str;
        }

    }

}
