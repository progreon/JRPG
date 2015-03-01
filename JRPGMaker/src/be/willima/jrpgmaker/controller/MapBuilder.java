/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.controller;

import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpgdatabase.model.JRPGProject;
import be.willima.jrpgmaker.view.mapbuilder.MapBuilderPanel;
import javax.swing.JPanel;

/**
 * TODO
 *
 * @author marco
 */
public final class MapBuilder {
    
    private final MapBuilderPanel view; // The view
    
    private JRPGProject project = null; // The model

    public MapBuilder() {
        this(null);
    }

    public MapBuilder(JRPGProject project) {
        this.view = new MapBuilderPanel(this);
        setProject(project);
    }

    public JPanel getView() {
        return view;
    }

    public void setProject(JRPGProject project) {
        this.project = project;
        view.setProject(project);
    }

    public JRPGProject getProject() {
        return project;
    }
    
    public JRPGMap getActiveMap() {
        return view.getActiveMap();
    }
    
}
