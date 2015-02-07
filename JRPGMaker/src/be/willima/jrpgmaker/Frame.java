/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.JRPGDatabaseFactory;
import javax.swing.JFrame;

/**
 *
 * @author marco
 */
class Frame extends JFrame {
    
    private static final String TITLE = "Testing JRPGMaker";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JRPGDao dao = JRPGDatabaseFactory.getJRPGDao(JRPGDatabaseFactory.DaoType.DUMMY);
        System.out.println(dao.getInfo());
        
        JFrame frame = new Frame();
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
