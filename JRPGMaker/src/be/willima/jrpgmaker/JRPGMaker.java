/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.JRPGDatabaseFactory;
import be.willima.jrpgmaker.controller.MapBuilder;
import be.willima.jrpgmaker.menus.MenuBuilder;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * TODO
 *
 * @author marco
 */
public class JRPGMaker {

    private final JFrame mainFrame;

    private final JRPGDao dao;

    // TODO ProjectPanel
    private final JTabbedPane tabbedPanel;
    private final MapBuilder mapBuilder;

    public JRPGMaker(JRPGDao dao) {
//        super(dao);
        mainFrame = new JFrame("JRPGMaker");
        this.dao = dao;
        // TODO debuggen door eens geen map aan te maken! (simuleert het programma zonder project)
        dao.createNewProject("dummyfolderlocation", "Dummy project", "Dummy game");

        // TODO to init
//        mapEditorPanel = new MapBuilderPanel(dao.getActiveProject());
//        mapEditorPanel = new MapBuilder(dao.getActiveProject()).getView();
        mapBuilder = new MapBuilder(null);
        tabbedPanel = new JTabbedPane();
        mainFrame.setContentPane(tabbedPanel);
        init();

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setSize(new Dimension(800, 600));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void init() {
        tabbedPanel.add("Map editor", mapBuilder.getView());
        tabbedPanel.add("Test", new JPanel());

        MenuBuilder mb = new MenuBuilder(this);
        mainFrame.setJMenuBar(mb.createMenuBar("/mainMenuTest.xml"));

        mainFrame.addWindowListener(new WindowAdapterImpl());
    }

    public JRPGDao getDao() {
        return this.dao;
    }

    public MapBuilder getMapBuilder() {
        return mapBuilder;
    }

    public void exit() {
        mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
    }

    private class WindowAdapterImpl extends WindowAdapter {

        private final String exitQuestion = "Are you sure you want to exit?";
        private final String exitTitle = "Exit";

        @Override
        public void windowClosing(WindowEvent e) {
//            System.out.println("Closing JRPGMaker...");
            int result = JOptionPane.showConfirmDialog(mainFrame, exitQuestion, exitTitle, JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        }
    }

    /**
     * TODO
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Temp debugging
        String jarFileURI = JRPGMaker.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        String jarFileName = new File(jarFileURI).getName();
        int i = jarFileURI.lastIndexOf('/');
        String jarFolderURI = jarFileURI.substring(0, i);
        System.out.println(jarFileURI);
        System.out.println(jarFileName + " in folder " + jarFolderURI);
        // End temp debugging

        JRPGDao dao = JRPGDatabaseFactory.getJRPGDao(JRPGDatabaseFactory.DaoType.DUMMY);
        System.out.println("Dao: " + dao.getInfo());

        JRPGMaker maker = new JRPGMaker(dao);
    }
}
