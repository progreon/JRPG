/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.JRPGDatabaseFactory;
import be.willima.jrpgmaker.mapbuilder.view.MapEditorPanel;
import be.willima.jrpgmaker.menus.MenuBuilder;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.JFrame;
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
