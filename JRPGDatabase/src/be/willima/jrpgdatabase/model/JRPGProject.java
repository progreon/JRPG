/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgdatabase.model;

//import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.JRPGDao;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO TODO: change everything so that projectTitle is unique instead of
 * projectID (let that be uniquely generated).
 *
 * @author marco
 */
public class JRPGProject {

    private int tileSize = 8;
    private int tileColorCount = 4;

    private JRPGDao dao;
    private final String projectTitle;
    private String gameTitle;
    private final List<JRPGMap> maps;

    /**
     * TODO
     *
     * @param dao
     * @param projectTitle
     * @param gameTitle
     * @param maps
     */
    public JRPGProject(JRPGDao dao, String projectTitle, String gameTitle, int tileSize, int tileColorCount, List<JRPGMap> maps) {
        if (dao == null) {
            throw new RuntimeException("The JRPGDao cannot be null!");
        }
        this.dao = dao;
//        this.projectFolderLocation = projectFolderLocation;
        this.projectTitle = projectTitle;
        this.gameTitle = gameTitle;
        this.tileSize = tileSize;
        this.tileColorCount = tileColorCount;
        if (maps == null) {
            this.maps = new ArrayList<>();
        } else {
            this.maps = maps;
        }
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
    
    public int getTileSize() {
        return this.tileSize;
    }
    
    public int getTileColorCount() {
        return this.tileColorCount;
    }

    public List<JRPGMap> getMaps() {
        return this.maps;
    }

    public JRPGMap createNewMap(String mapTitle, Dimension dim) {
        int mapID = this.maps.size();
        JRPGMap newMap = new JRPGMap(this, mapID, mapTitle, dim.width, dim.height);
        maps.add(newMap);

        return newMap;
    }

    public JRPGTile getTile(int tileID) {
        return dao.getTile(tileID);
    }

//    public void save() {
//        this.dao.saveProject(this);
//    }
}
