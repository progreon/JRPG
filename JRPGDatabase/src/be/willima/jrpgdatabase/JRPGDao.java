/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase;

import be.willima.jrpgdatabase.model.JRPGProject;
import be.willima.jrpgdatabase.model.JRPGTile;

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
        NO_SAVE,
        NO_PROJECT
    }

    public String getInfo();

    /**
     * TODO This will create a new database for the project (and will also set
     * this project as the current active project).
     *
     * @param projectFolderLocation
     * @param projectTitle
     * @param gameTitle
     */
    public void createNewProject(String projectFolderLocation, String projectTitle, String gameTitle); // TODO return DaoError

    /**
     * TODO This opens and loads the project.
     *
     * @param projectFileURI
     * @return
     */
    public DaoError loadProjectByProjectFileURI(String projectFileURI);
    
    public DaoError loadProjectByProjectFolderURI(String projectFolderURI);

    /**
     * TODO
     *
     * @param tileID
     * @return
     */
    public JRPGTile getTile(int tileID);

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
