/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.model;

import be.willima.jrpgdatabase.JRPGDao;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * TODO: change everything so that projectTitle is unique instead of projectID (let that be uniquely generated).
 * 
 * @author marco
 */
public class JRPGProject {

    private int projectID;
    private String projectTitle;
    private String gameTitle;
    private JRPGDao dao;
    private final List<JRPGMap> maps;

    public JRPGProject(JRPGDao dao, int projectID, String projectTitle, String gameTitle, List<JRPGMap> maps) {
        if (dao == null) {
            throw new RuntimeException("The JRPGDao cannot be null!");
        }
        this.dao = dao;
        this.projectID = projectID;
        this.projectTitle = projectTitle;
        this.gameTitle = gameTitle;
        if (maps == null) {
            maps = new ArrayList<>();
        }
        this.maps = maps;
    }
    
    public int getProjectID() {
        return this.projectID;
    }
    
    public String getProjectTitle() {
        return this.projectTitle;
    }
    
    public String getGameTitle() {
        return this.gameTitle;
    }
    
    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }
    
    public List<JRPGMap> getMaps() {
        return this.maps;
    }
    
    public JRPGMap createNewMap(String mapTitle, Dimension dim) {
        int mapID = this.maps.size();
        JRPGMap newMap = new JRPGMap(mapID, mapTitle, dim.width, dim.height);
        maps.add(newMap);
        
        return newMap;
    }
    
    public void save() {
        this.dao.saveProject(this);
    }

}
