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
     * @return 
     */
    public JRPGProject createNewProject(String projectFolderLocation, String projectTitle, String gameTitle);
    
    /**
     * TODO
     * This opens and loads the project.
     * 
     * @param projectFileURI
     * @return 
     */
    public JRPGProject getProjectByProjectFileURI(String projectFileURI);
    
    /**
     * TODO
     *
     * @param project
     * @return
     */
    public DaoError saveProject(JRPGProject project);
    
}
