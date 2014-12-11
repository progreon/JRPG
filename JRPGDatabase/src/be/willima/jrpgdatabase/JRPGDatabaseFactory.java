/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase;

import be.willima.jrpgdatabase.implementation.JRPGDaoDummy;

/**
 *
 * @author marco
 */
public class JRPGDatabaseFactory {
    
    private static JRPGDao selectedDao = null;
    
    public enum DaoType {
        DUMMY
    }
    
    public static JRPGDao getJRPGDao(DaoType daoType) {
        if (selectedDao != null) {
            throw new RuntimeException("You had already chosen a JRPGDao to use! If you choose a new one, erros WILL occur!");
        }
        if (daoType == DaoType.DUMMY) {
            selectedDao = new JRPGDaoDummy();
        }
        
        return selectedDao;
    }
    
}
