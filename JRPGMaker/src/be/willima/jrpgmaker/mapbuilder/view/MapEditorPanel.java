/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.mapbuilder.view;

import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpgdatabase.model.JRPGProject;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
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

    public MapEditorPanel(JRPGProject project) {
        super(new BorderLayout());

        this.project = project;
        if (project != null && !project.getMaps().isEmpty()) {
            this.activeMap = project.getMaps().get(0);
        }
        mapPanel = new MapPanel();
//        loadMap();

        JPanel mapViewPanel = new JPanel();
        mapViewPanel.setLayout(new BoxLayout(mapViewPanel, BoxLayout.Y_AXIS));
        mapViewPanel.add(Box.createVerticalGlue());
        mapViewPanel.add(mapPanel);
        mapViewPanel.add(Box.createVerticalGlue());

        settingsPanel = new MapEditorSettingsPanel();

        JScrollPane scr = new JScrollPane(mapViewPanel);
        this.add(scr, BorderLayout.CENTER);
        this.add(settingsPanel, BorderLayout.WEST);

        this.setPreferredSize(new Dimension(800, 600));
//        this.add(viewPanel, BorderLayout.CENTER);
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

    private void loadMap() {
        mapPanel.setMap(activeMap);
        invalidate();
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

//        public void updateSelectedMapInTable(int index) {
//        }
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
            scr.setMaximumSize(new Dimension(125, 200));
            this.add(scr);

            slrScale = new JSlider(JSlider.HORIZONTAL, 1, 10, 3);
            slrScale.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    int newScale = ((JSlider) e.getSource()).getValue();
                    mapPanel.updateScale(newScale);
                }
            });
            this.add(slrScale);
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
                Object value = null;
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
