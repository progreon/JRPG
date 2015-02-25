/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.JRPGDatabaseFactory;
import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpggame.JRPGGame;
import be.willima.jrpgmaker.mapbuilder.view.MapEditorPanel;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * TODO
 *
 * @author marco
 */
class JRPGMaker extends JFrame {

    private final JRPGDao dao;

    // TODO: to ProjectPanel
    private final JTabbedPane tabbedPanel;
//    private final CardLayout cardLayout;
    private final MapEditorPanel mapEditorPanel;

    public JRPGMaker(JRPGDao dao) throws HeadlessException {
        super("JRPGMaker");
        this.dao = dao;
        // TODO debuggen door eens geen map aan te maken! (simuleert het programma zonder project)
        dao.createNewProject("dummyfolderlocation", "Dummy project", "Dummy game");
        mapEditorPanel = new MapEditorPanel(dao.getActiveProject());
//        cardLayout = new CardLayout();
//        cardsPanel = new JTabbedPane(cardLayout);
        tabbedPanel = new JTabbedPane();
        init();

//        this.setJMenuBar(new MainMenu("mainMenuTest.xml"));
//        this.setJMenuBar(MenuBuilder.createMenuBar("mainMenuTest.xml"));
        this.setContentPane(tabbedPanel);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(800, 600));
//        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void init() {
        tabbedPanel.add("Map editor", mapEditorPanel);
        tabbedPanel.add("Test", new JPanel());
        initMenu();
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu mnuFile = new JMenu("File");
        JMenuItem mniExit = new JMenuItem(new AbstractAction("Exit") {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mnuFile.add(mniExit);
        menuBar.add(mnuFile);

        JMenu mnuGame = new JMenu("Game");
        JMenuItem mniPlayTest = new JMenuItem(new AbstractAction("Playtest") {

            @Override
            public void actionPerformed(ActionEvent e) {
                JRPGMap activeMap = mapEditorPanel.getActiveMap();
                JRPGGame game;
                if (activeMap != null) {
                    game = new JRPGGame(dao.getActiveProject(), activeMap.getMapID());
                } else {
                    game = new JRPGGame(dao.getActiveProject());
                }
                game.setDefaultCloseOperateion(JFrame.HIDE_ON_CLOSE); // TODO stop game thread!!
                game.start(); // TODO ask for save!
            }
        });
        mnuGame.add(mniPlayTest);
        menuBar.add(mnuGame);
        this.setJMenuBar(menuBar);
    }

    /**
     * TODO
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JRPGDao dao = JRPGDatabaseFactory.getJRPGDao(JRPGDatabaseFactory.DaoType.DUMMY);
        System.out.println("Dao: " + dao.getInfo());

        JRPGMaker frame = new JRPGMaker(dao);
        frame.setVisible(true);
    }

}
