/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.view.mapbuilder;

import be.willima.jrpgdatabase.model.JRPGMap;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * TODO
 *
 * @author marco
 */
class MapPropertiesPanel extends JPanel {
    
    private JRPGMap map;
    
    private JLabel lblName;
    private JLabel lblWidth;
    private JLabel lblHeight;

    public MapPropertiesPanel() {
        this(null);
    }

    public MapPropertiesPanel(JRPGMap map) {
        this.map = map;
        init();
    }
    
    private void init() {
        
    }
    
}
