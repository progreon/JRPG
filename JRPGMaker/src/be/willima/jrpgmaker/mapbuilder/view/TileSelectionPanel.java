/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.mapbuilder.view;

import be.willima.jrpgdatabase.model.JRPGProject;
import javax.swing.JPanel;

/**
 *
 * @author marco
 */
public class TileSelectionPanel extends JPanel {
    
    private JRPGProject project;

    public TileSelectionPanel(JRPGProject project) {
        this.project = project;
    }
    
}
