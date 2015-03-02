/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase;

import be.willima.jrpgdatabase.implementation.JRPGDaoH2;
import be.willima.jrpgdatabase.implementation.JRPGDaoDummy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO
 *
 * @author marco
 */
public class JRPGDatabaseFactory {

    private static JRPGDao selectedDao = null;

    /**
     * TODO
     */
    public enum DaoType {

        DUMMY,
        H2DB
    }

    /**
     * TODO
     *
     * @param daoType
     * @return
     */
    public static JRPGDao getJRPGDao(DaoType daoType) {
        if (selectedDao != null) {
            throw new RuntimeException("You had already chosen a JRPGDao to use! If you choose a new one while the application is running, erros WILL occur!");
        }
        if (daoType == DaoType.DUMMY) {
            selectedDao = new JRPGDaoDummy();
        } else if (daoType == DaoType.H2DB) {
            try {
                selectedDao = new JRPGDaoH2();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JRPGDatabaseFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return selectedDao;
    }

}
