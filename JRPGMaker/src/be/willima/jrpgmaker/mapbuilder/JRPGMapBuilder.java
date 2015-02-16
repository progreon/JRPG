/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.mapbuilder;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.JRPGDatabaseFactory;
import be.willima.jrpgmaker.mapbuilder.view.MapEditorPanel;
import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author marco
 */
public class JRPGMapBuilder extends JFrame {

    public JRPGMapBuilder() throws HeadlessException {
        super("JRPG Map Builder");
        
        JRPGDao dao = JRPGDatabaseFactory.getJRPGDao(JRPGDatabaseFactory.DaoType.DUMMY);
        
        this.setContentPane(new MapEditorPanel(dao.getActiveProject()));
        
        pack();
//        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JRPGMapBuilder mapBuilder = new JRPGMapBuilder();
        mapBuilder.setVisible(true);
    }
    
}
