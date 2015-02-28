/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.JRPGDatabaseFactory;
import java.io.File;

/**
 *
 * @author marco
 */
public interface JRPGMaker {
    
    /**
     * TODO
     *
     * @return
     */
    public JRPGDao getDao();

    /**
     * TODO
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Temp debugging
        String jarFileURI = JRPGMakerS.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        String jarFileName = new File(jarFileURI).getName();
        int i = jarFileURI.lastIndexOf('/');
        String jarFolderURI = jarFileURI.substring(0, i);
        System.out.println(jarFileURI);
        System.out.println(jarFileName + " in folder " + jarFolderURI);
        // End temp debugging
        
        JRPGDao dao = JRPGDatabaseFactory.getJRPGDao(JRPGDatabaseFactory.DaoType.DUMMY);
        System.out.println("Dao: " + dao.getInfo());

        JRPGMakerS frame = new JRPGMakerS(dao);
        frame.setVisible(true);
    }
    
}
