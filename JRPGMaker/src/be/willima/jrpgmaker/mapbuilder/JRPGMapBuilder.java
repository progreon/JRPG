/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.mapbuilder;

import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpgdatabase.model.JRPGProject;

/**
 *
 * @author marco
 */
public interface JRPGMapBuilder {

    void changeScale(int newScale);

    void changeToMap(int mapIndex);

    JRPGMap getActiveMap();

    void setProject(JRPGProject project);
    
}
