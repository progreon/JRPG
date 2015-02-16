/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase;

import be.willima.jrpgdatabase.model.JRPGProject;

/**
 * TODO
 *
 * @author marco
 */
public interface JRPGDao {
    
    /**
     * TODO
     */
    public enum DaoError {
        NO_ERROR,
        NO_SAVE
    }
    
    public String getInfo();
    
    /**
     * TODO
     * This will create a new database for the project.
     * 
     * @param projectFolderLocation
     * @param projectTitle
     * @param gameTitle 
     */
    public void createNewProjectAndSetActive(String projectFolderLocation, String projectTitle, String gameTitle);
    
    /**
     * TODO
     * This opens and loads the project.
     * 
     * @param projectFileURI 
     */
    public void loadProjectByProjectFileURI(String projectFileURI);
    
    /**
     * TODO
     *
     * @return
     */
    public DaoError saveActiveProject();
    
    /**
     * TODO
     *
     * @return
     */
    public JRPGProject getActiveProject();
    
}
