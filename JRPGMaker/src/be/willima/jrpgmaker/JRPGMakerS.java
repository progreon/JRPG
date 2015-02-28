/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgmaker.mapbuilder.view.MapEditorPanel;
import be.willima.jrpgmaker.menus.MenuBuilder;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * TODO
 *
 * @author marco
 */
public class JRPGMakerS extends JFrame implements JRPGMaker {

    private final JRPGDao dao;

    // TODO to ProjectPanel
    private final JTabbedPane tabbedPanel;
    // TODO make private again
    public final MapEditorPanel mapEditorPanel;

    public JRPGMakerS(JRPGDao dao) throws HeadlessException {
        super("JRPGMaker");
        this.dao = dao;
        // TODO debuggen door eens geen map aan te maken! (simuleert het programma zonder project)
        dao.createNewProject("dummyfolderlocation", "Dummy project", "Dummy game");
        
        // TODO to init
        mapEditorPanel = new MapEditorPanel(dao.getActiveProject());
        tabbedPanel = new JTabbedPane();
        this.setContentPane(tabbedPanel);
        init();

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
    }

    private void init() {
        tabbedPanel.add("Map editor", mapEditorPanel);
        tabbedPanel.add("Test", new JPanel());
        
        MenuBuilder mb = new MenuBuilder(this);
        this.setJMenuBar(mb.createMenuBar("/mainMenuTest.xml"));

        this.addWindowListener(new WindowAdapterImpl());
    }
    
    @Override
    public JRPGDao getDao() {
        return this.dao;
    }

    private class WindowAdapterImpl extends WindowAdapter {
        
        private final String exitQuestion = "Are you sure you want to exit?";
        private final String exitTitle = "Exit";

        @Override
        public void windowClosing(WindowEvent e) {
            System.out.println("Closing JRPGMaker...");
            JFrame frame = JRPGMakerS.this;
            
            int result = JOptionPane.showConfirmDialog(frame, exitQuestion, exitTitle, JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        }
    }

}
