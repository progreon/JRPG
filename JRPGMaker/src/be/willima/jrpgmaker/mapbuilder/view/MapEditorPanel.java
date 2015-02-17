/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.mapbuilder.view;

import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpgdatabase.model.JRPGProject;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author marco
 */
public class MapEditorPanel extends JPanel {

    private JRPGProject project;
    private JRPGMap activeMap = null;

    private final MapPanel mapPanel;
    private final MapEditorSettingsPanel settingsPanel;
    private final JPanel mapViewPanel;
    private final JScrollPane scrMap;
    
    private final int MIN_SCALE = 1;
    private final int MAX_SCALE = 10;

    public MapEditorPanel(JRPGProject project) {
        super(new BorderLayout());

        this.project = project;
//        if (project != null && !project.getMaps().isEmpty()) {
//            this.activeMap = project.getMaps().get(0);
//        }
        mapPanel = new MapPanel();
//        loadMap();

        mapViewPanel = new JPanel(null);
        mapViewPanel.setBorder(BorderFactory.createLineBorder(Color.red, 3));
        mapViewPanel.add(mapPanel);

        settingsPanel = new MapEditorSettingsPanel();

        scrMap = new JScrollPane(mapViewPanel);
        this.add(scrMap, BorderLayout.CENTER);
        this.add(settingsPanel, BorderLayout.WEST);

        this.setPreferredSize(new Dimension(800, 600));

        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                // TODO ?
            }

        });
    }

    public void setProject(JRPGProject project) {
        this.project = project;
        if (project != null && !project.getMaps().isEmpty()) {
            this.activeMap = project.getMaps().get(0);
        }
        loadMap();
    }

    public void changeToMap(int mapIndex) {
        if (mapIndex >= 0 && mapIndex < project.getMaps().size()) {
            activeMap = project.getMaps().get(mapIndex);
        } else {
            // TODO: logging
            activeMap = null;
        }
        loadMap();
    }

    public void changeScale(int newScale) {
        mapPanel.updateScale(newScale);
        mapViewPanel.setPreferredSize(mapPanel.getSize());
        
        this.updateUI();
    }

    private void loadMap() {
        mapPanel.setMap(activeMap);
        mapViewPanel.setPreferredSize(mapPanel.getSize());
        this.updateUI();
    }

    private class MapEditorSettingsPanel extends JPanel {

        private JButton btnUndo; // TODO
        private JButton btnRedo; // TODO

        private JTable tblMaps;
//        private int currentIndex = 0;
        private ListSelectionModel listSelectionModel;
        private JSlider slrScale;

        public MapEditorSettingsPanel() {
            this.setPreferredSize(new Dimension(150, 200));
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            init();
        }

        private void init() {
            MapsTableModel tableModel = new MapsTableModel();
            tblMaps = new JTable(tableModel);
            listSelectionModel = new DefaultListSelectionModel();
            listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listSelectionModel.addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
//                    int index = ((ListSelectionModel) e.getSource()).getMinSelectionIndex();
                        int index = tblMaps.getSelectedRow();
//                        if (index != currentIndex) {
//                            currentIndex = index;
                        changeToMap(index);
//                        }
                    }
                }
            });
            tblMaps.setSelectionModel(listSelectionModel);
            JScrollPane scr = new JScrollPane(tblMaps);
//            scr.setMaximumSize(new Dimension(125, 200));
            this.add(scr);

            slrScale = new JSlider(JSlider.HORIZONTAL, MIN_SCALE, MAX_SCALE, mapPanel.getScale());
//            slrScale.setMaximumSize(new Dimension(100, 20));
            slrScale.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    int newScale = ((JSlider) e.getSource()).getValue();
                    changeScale(newScale);
                }
            });
//            this.add(new JLabel("Zoom:"));
            this.add(slrScale);

            JCheckBox chkRaster = new JCheckBox("Raster");
            chkRaster.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    mapPanel.setShowRaster(((JCheckBox) e.getSource()).getModel().isSelected());
                }
            });
            this.add(chkRaster);
//            this.add(Box.createVerticalGlue());
        }

        private class MapsTableModel extends AbstractTableModel {

            private final boolean SHOW_ID = true;

            @Override
            public int getRowCount() {
                return project.getMaps().size();
            }

            @Override
            public int getColumnCount() {
                return 1;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Object value;
                if (SHOW_ID) {
                    value = project.getMaps().get(rowIndex).getMapID() + ": " + project.getMaps().get(rowIndex).getMapTitle();
                } else {
                    value = project.getMaps().get(rowIndex).getMapTitle();
                }
                return value;
            }

            @Override
            public String getColumnName(int column) {
                return "Maps";
            }

        }

    }

}
