/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.JRPGDatabaseFactory;
import be.willima.jrpgmaker.menus.MainMenu;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 * TODO
 *
 * @author marco
 */
class Frame extends JFrame {
    
    private final JRPGDao dao;

    public Frame(JRPGDao dao) throws HeadlessException {
        super("JRPGMaker");
        this.dao = dao;
        
        this.setJMenuBar(new MainMenu("mainMenuTest.xml"));
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 300));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
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
        
        Frame frame = new Frame(dao);
        frame.setVisible(true);
    }
    
}
