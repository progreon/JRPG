/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase;

import be.willima.jrpgdatabase.model.JRPGProject;
import java.util.Set;

/**
 *
 * @author marco
 */
public interface JRPGDao {
    
    public enum DaoError {
        NO_ERROR,
        ID_CREATION_FAIL,
        ID_ALREADY_EXISTS,
        ID_NOT_FOUND
    }
    
    public String getInfo();
    
    /**
     * TODO<br>
     * 
     * DO NOT EDIT THIS SET
     */
    Set<Integer> getUsedProjectIDs();
    
    /**
     * This also creates a new database for the project!
     * 
     * TODO
     */
    public JRPGProject createNewProject(String projectTitle, String gameTitle);
    
    public DaoError saveProject(JRPGProject project);
    
    public JRPGProject getProject(int projectID);
    
}
