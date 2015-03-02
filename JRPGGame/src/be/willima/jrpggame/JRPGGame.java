/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpggame;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.JRPGDatabaseFactory;
import be.willima.jrpgdatabase.model.JRPGProject;
import be.willima.jrpggame.gfx.Screen;
import be.willima.jrpggame.level.Level;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Marco
 */
public class JRPGGame extends Canvas implements Runnable {

    public static final int WIDTH = 160;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 3;
    public static final String NAME = "Game";

    private JFrame frame;

    public boolean running = false;
    public int tickCount = 0;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private int[] colors = new int[6 * 6 * 6];

//    private SpriteSheet spriteSheet = new SpriteSheet("res/spritesheet.png");
    private JRPGProject project;
    private int startMapID = 0;
    private Screen screen;
    public InputHandler input;

    public Level level;

    public JRPGGame(JRPGProject project) {
        String jarFileURI = JRPGGame.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        String jarFileName = new File(jarFileURI).getName();
        int i = jarFileURI.lastIndexOf('/');
        String jarFolderURI = jarFileURI.substring(0, i);
        System.out.println(jarFileURI);
        System.out.println(jarFileName + " in folder " + jarFolderURI);
        this.project = project;

        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        frame = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.
                System.out.println("Game window closing...");
                stop();
            }
        });

        frame.setVisible(true);
        this.requestFocus();
    }
    
    public JRPGGame(JRPGProject project, int startMapID) {
        this(project);
        this.startMapID = startMapID;
    }

    public void init() { // TODO constants?
        int index = 0;
        for (int r = 0; r < 6; r++) {
            for (int g = 0; g < 6; g++) {
                for (int b = 0; b < 6; b++) {
                    int rr = (r * 255 / 5);
                    int gg = (g * 255 / 5);
                    int bb = (b * 255 / 5);

                    colors[index++] = rr << 16 | gg << 8 | bb;
                }
            }
        }

        screen = new Screen(project, WIDTH, HEIGHT);
        input = new InputHandler(this);
        level = new Level(project, startMapID);
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop() {
        running = false;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000d / 60d;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0; // How many nano-seconds have gone by so far. When it reaches 1, it is reset by substracting 1.

        init();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;
            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }
            try {
                Thread.sleep(3);
            } catch (InterruptedException ex) {
//                Logger.getLogger(JRPGGame.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                System.out.println(ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }
        }
    }

    private int x = 0, y = 0;

    public void tick() {
        tickCount++;

        if (input.up.isPressed()) {
            y--;
        }
        if (input.down.isPressed()) {
            y++;
        }
        if (input.left.isPressed()) {
            x--;
        }
        if (input.right.isPressed()) {
            x++;
        }

        level.tick();

//        screen.xOffset++;
//        screen.yOffset++;
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
        } else {
            Graphics g = bs.getDrawGraphics();

            int xOffset = x - screen.width / 2;
            int yOffset = y - screen.height / 2;
            level.renderTiles(screen, xOffset, yOffset);

            for (int y = 0; y < screen.height; y++) {
                for (int x = 0; x < screen.width; x++) {
                    int colorCode = screen.pixels[x + y * screen.width];
                    if (colorCode < 255) {
                        pixels[x + y * WIDTH] = colors[colorCode];
                    }
                }
            }
//            screen.render(pixels, 0, WIDTH);
//            screen.render2(image.getGraphics());

            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

            g.dispose();
            bs.show();
        }
    }
    
    public void setDefaultCloseOperateion(int operation) {
        frame.setDefaultCloseOperation(operation);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JRPGDao dao = JRPGDatabaseFactory.getJRPGDao(JRPGDatabaseFactory.DaoType.DUMMY);
        JRPGDao.DaoError error = dao.loadProjectByProjectFolderURI(".");
        if (error == JRPGDao.DaoError.NO_PROJECT) {

        } else {
            JRPGGame game = new JRPGGame(dao.getActiveProject());
            game.start();
        }
    }

}
