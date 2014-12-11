/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase;

import be.willima.jrpgdatabase.model.JRPGProject;

/**
 *
 * @author marco
 */
public interface JRPGDao {
    
    public enum DaoError {
        NO_ERROR,
        NO_SAVE
    }
    
    public String getInfo();
    
    /**
     * This will create a new database for the project.
     * 
     * TODO
     */
    public JRPGProject createNewProject(String projectFolderLocation, String projectTitle, String gameTitle);
    
    /**
     * This opens and loads the project.
     * 
     * TODO
     */
    public JRPGProject getProjectByProjectFileURI(String projectFileURI);
    
    public DaoError saveProject(JRPGProject project);
    
}
