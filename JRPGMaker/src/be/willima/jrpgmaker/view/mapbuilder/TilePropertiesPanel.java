/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.view.mapbuilder;

import be.willima.jrpgdatabase.model.JRPGTile;
import javax.swing.JPanel;

/**
 * TODO Use this???
 *
 * @author marco
 */
class TilePropertiesPanel extends JPanel {
    
    private JRPGTile tile;

    public TilePropertiesPanel() {
        this(null);
    }

    public TilePropertiesPanel(JRPGTile tile) {
        this.tile = tile;
        init();
    }
    
    private void init() {
        
    }
    
}
