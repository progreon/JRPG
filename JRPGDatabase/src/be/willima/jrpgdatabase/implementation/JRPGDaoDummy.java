/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.implementation;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.model.JRPGProject;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marco
 */
public class JRPGDaoDummy implements JRPGDao {
    
    //// THINGS THAT NORMALLY SHOULD BE IN ACTUAL DATABASE ////
    private Map<Integer, JRPGProject> projects;
//    private Map<Integer, List<JRPGMap>> mapsPerProject; // Not really needed
    
    //// END OF THOSE THINGS ////
    
    private final Set<Integer> newProjectIDs;

    public JRPGDaoDummy() {
        newProjectIDs = new TreeSet<>();
        createDummyData();
    }
    
    private void createDummyData() {
        // TODO
        projects = new TreeMap<>();
        // ...
    }

    @Override
    public String getInfo() {
        return "This is the dummy DAO, used for initial testing.";
    }
    
    @Override
    public Set<Integer> getUsedProjectIDs() {
        return projects.keySet();
    }

    @Override
    public JRPGProject createNewProject(String projectTitle, String gameTitle) {
        JRPGProject newProject = null;
        
        Set<Integer> usedIDs = getUsedProjectIDs();
        int projectID = Math.abs(new Random().nextInt());
        int numIterations = 0;
        while ((usedIDs.contains(projectID) || newProjectIDs.contains(projectID)) && numIterations < 1000) {
            projectID = Math.abs(new Random().nextInt());
            numIterations++;
        }
        if (numIterations == 1000) {
            Logger.getLogger(JRPGDaoDummy.class.toString()).log(Level.INFO, "Creating random unique ID for new project took long, trying creating one starting from 0.");
            projectID = 0;
            numIterations = 1;
            while ((usedIDs.contains(projectID) || newProjectIDs.contains(projectID)) && numIterations <= (usedIDs.size() + newProjectIDs.size())) {
                projectID++;
                numIterations++;
            }
            if (numIterations > usedIDs.size() + newProjectIDs.size() + 1) {
                Logger.getLogger(JRPGDaoDummy.class.toString()).log(Level.SEVERE, "Failed creating new project ID!");
            } else {
                newProject = new JRPGProject(this, projectID, projectTitle, gameTitle, null);
            }
        } else {
            newProject = new JRPGProject(this, projectID, projectTitle, gameTitle, null);
        }
        
        return newProject;
    }

    @Override
    public DaoError saveProject(JRPGProject project) {
        DaoError err = DaoError.NO_ERROR;
        
        Set<Integer> usedIDs = getUsedProjectIDs();
        if (this.newProjectIDs.contains(project.getProjectID())) {
            if (usedIDs.contains(project.getProjectID())) {
                Logger.getLogger(JRPGDaoDummy.class.toString()).log(Level.SEVERE, "Failed saving new project: project ID already exists!");
                err = DaoError.ID_ALREADY_EXISTS;
            } else {
                addProject(project);
            }
        } else {
            if (usedIDs.contains(project.getProjectID())) {
                updateProject(project);
            } else {
                Logger.getLogger(JRPGDaoDummy.class.toString()).log(Level.SEVERE, "Failed saving project: project ID doesn't exists!");
                err = DaoError.ID_NOT_FOUND;
            }
        }
        
        return err;
    }

    @Override
    public JRPGProject getProject(int projectID) {
        return this.projects.get(projectID);
    }
    
    private void addProject(JRPGProject project) {
        this.projects.put(project.getProjectID(), project);
    }
    
    private void updateProject(JRPGProject project) {
        this.projects.put(project.getProjectID(), project);
    }
    
}
