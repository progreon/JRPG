/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.mapbuilder.view;

import be.willima.jrpgdatabase.model.JRPGMap;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author marco
 */
public class MapEditorPanel extends JPanel {

    public MapEditorPanel(JRPGMap map) {
        super(new BorderLayout());
        
        MapPanel mapPanel = new MapPanel(map);
        
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.add(Box.createVerticalGlue());
        viewPanel.add(mapPanel);
        viewPanel.add(Box.createVerticalGlue());
        
        JScrollPane scr = new JScrollPane(viewPanel);
        this.setPreferredSize(new Dimension(800, 600));
        this.add(scr, BorderLayout.CENTER);
    }
    
}
