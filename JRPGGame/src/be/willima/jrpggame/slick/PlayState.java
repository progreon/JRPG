/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpggame.slick;
import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpgdatabase.model.JRPGProject;
import be.willima.jrpggame.level.tiles.Tile;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.*;

/**
 *
 * @author marco
 */
public class PlayState extends BasicGameState {
    
    public static final int ID = 1;

    private final boolean stopsAtBorder = false;
    private boolean printed = false;

    private JRPGProject project;
    private JRPGMap currentMap;

    private int[] tiles;
    public int width;
    public int height;
    private Point offset = new Point(0, 0);

    public PlayState(JRPGProject project, int mapID) {
        if (project != null && mapID < project.getMaps().size() && mapID >= 0) {
            this.project = project;
            this.currentMap = project.getMaps().get(mapID);
            loadLevelFromMap();
        } else if (project != null) {
            this.project = project;
            currentMap = null;
            this.width = 64;
            this.height = 64;
            tiles = new int[width * height];
            generateLevel();
        } else {
            throw new RuntimeException("Project cannot be null!");
        }
    }

    private void loadLevelFromMap() {
        this.width = currentMap.getWidth();
        this.height = currentMap.getHeight();
        tiles = new int[width * height];

        for (int i = 0; i < width * height; i++) {
            tiles[i] = currentMap.getTile(i % width, i / width).getTileID();
//            System.out.println(tiles[i]);
        }
//        for (int i = 0; i < tiles.length; i++) {
//            System.out.print(tiles[i] + ", ");
//        }
//        System.out.println("");
    }

    private void generateLevel() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x * y % 10 < 5) {
                    tiles[x + y * width] = Tile.GRASS.getId(); // GRASS
                } else {
                    tiles[x + y * width] = Tile.STONE.getId(); // STONE
                }
//                System.out.println(tiles[x + y * width]);
            }
        }
    }

    public void renderTiles(GameContainer container, Graphics g) throws SlickException {
        if (stopsAtBorder) {
            if (offset.getX() < 0) {
                offset.setX(0);
            }
            if (offset.getX() > (width * 8) - container.getWidth()) {
                offset.setX((width * 8) - container.getWidth());
            }
            if (offset.getY() < 0) {
                offset.setY(0);
            }
            if (offset.getY() > (height * 8) - container.getHeight()) {
                offset.setY((height * 8) - container.getHeight());
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int tileID = tiles[x + y * width];
//                ByteArrayOutputStream os = new ByteArrayOutputStream();
//                try {
////                    project.getTile(tiles[x + y * width]).getScaledImage(3)
//                    ImageIO.write(project.getTile(tileID).getImage(), "png", os);
//                } catch (IOException ex) {
//                    Logger.getLogger(PlayState.class.getName()).log(Level.SEVERE, null, ex);
//                }
////                InputStream is2 = (InputStream) ImageIO.createImageInputStream(project.getTile(tiles[x + y * width]).getScaledImage(3));
//                InputStream is = new ByteArrayInputStream(os.toByteArray());
//                Image tileImage = new Image(is, "", false);
//                g.drawImage(tileImage, x * 8 * JRPGGameSlick.SCALE - offset.getX(), y * 8 * JRPGGameSlick.SCALE - offset.getY());
//                getTile(x, y).render(screen, this, x * 8, y * 8);
                
                if (!printed) {
                    System.out.println(tileID);
                }
            }
        }
        printed = true;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawString("Let's play!", 30, 30);
//        renderTiles(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    }
    
}
