/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.implementation;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.model.JRPGProject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marco
 */
public class JRPGDaoDummy implements JRPGDao {
    
    //// THINGS THAT NORMALLY SHOULD BE IN ACTUAL DATABASE ////
    private Map<String, JRPGProject> projects;
//    private Map<Integer, List<JRPGMap>> mapsPerProject; // Not really needed
    
    //// END OF THOSE THINGS ////

    public JRPGDaoDummy() {
        createDummyData();
    }
    
    private void createDummyData() {
        // TODO
        projects = new HashMap<>();
        // ...
    }

    @Override
    public String getInfo() {
        return "This is the dummy DAO, used for initial testing.";
    }
    
//    public Set<Integer> getUsedProjectIDs() {
//        return projects.keySet();
//    }

    @Override
    public JRPGProject createNewProject(String projectFolderLocation, String projectTitle, String gameTitle) {
        JRPGProject newProject = null;
        
        String projectFolderURI = projectFolderLocation + "/" + projectTitle + "/";
        
        if (!projects.containsKey(projectFolderURI)) {
            newProject = new JRPGProject(this, projectFolderLocation, projectTitle, gameTitle, null);
        } else {
            Logger.getLogger(JRPGDaoDummy.class.toString()).log(Level.SEVERE, "Project folder name not unique!");
        }
        
        return newProject;
    }

    @Override
    public JRPGProject getProjectByProjectFileURI(String projectFileURI) {
        String projectFolderURI = projectFileURI.substring(0, projectFileURI.lastIndexOf("/"));
        return this.projects.get(projectFolderURI);
    }

    @Override
    public DaoError saveProject(JRPGProject project) {
        Logger.getLogger(JRPGDaoDummy.class.toString()).log(Level.WARNING, "You cannot save in dummy mode!");
        return DaoError.NO_SAVE;
    }
    
}
