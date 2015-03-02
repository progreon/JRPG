/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 * TODO
 *
 * @author marco
 */
public class NewProjectDialog extends JDialog {

    public static final int CANCEL = -1;
    public static final int FINISH = 0;
    public static final int NEXT = 1;
    private int result = CANCEL;

    private final JFileChooser fileChooser;

    private final JTextField txtProjectName;
    private final JTextField txtFolderLocation;
    private final JTextField txtProjectFolderURI;

    public NewProjectDialog(JFrame owner) {
        this.setTitle("New project");
        String projectName = "New JRPG Project";
        this.setModal(true);

        fileChooser = new JFileChooser(System.getProperty("user.home"));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        this.setLayout(new BorderLayout(5, 5));
        JPanel pnlSettings = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel lblProjectName = new JLabel("Project Name:");
        txtProjectName = new JTextField(projectName, 20);
        txtProjectName.addCaretListener(new CaretListener() {

            @Override
            public void caretUpdate(CaretEvent e) {
                updateProjectFolderURI();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlSettings.add(lblProjectName, gbc);
        gbc.gridx = 1;
        pnlSettings.add(txtProjectName, gbc);
        JLabel lblFolderLocation = new JLabel("Project Location:");
        txtFolderLocation = new JTextField(System.getProperty("user.home"), 20);
        txtFolderLocation.setEditable(false);
        JButton btnBrowse = new JButton(new AbstractAction("Browse") {

            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.showOpenDialog(NewProjectDialog.this);
                File projectFolderParent = fileChooser.getSelectedFile();
                if (projectFolderParent != null) {
                    txtFolderLocation.setText(projectFolderParent.getPath());
                    updateProjectFolderURI();
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlSettings.add(lblFolderLocation, gbc);
        gbc.gridx = 1;
        pnlSettings.add(txtFolderLocation, gbc);
        gbc.gridx = 2;
        pnlSettings.add(btnBrowse, gbc);
        JLabel lblProjectFolderURI = new JLabel("Project Folder:");
        txtProjectFolderURI = new JTextField(20);
        txtProjectFolderURI.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlSettings.add(lblProjectFolderURI, gbc);
        gbc.gridx = 1;
        pnlSettings.add(txtProjectFolderURI, gbc);
        this.add(pnlSettings, BorderLayout.CENTER);
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnFinish = new JButton(new AbstractAction("Finish") {

            @Override
            public void actionPerformed(ActionEvent e) {
                finish();
            }
        });
        JButton btnNext = new JButton(new AbstractAction("Next") {

            @Override
            public void actionPerformed(ActionEvent e) {
                next();
            }
        });
        btnNext.setEnabled(false);
        JButton btnCancel = new JButton(new AbstractAction("Cancel") {

            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        pnlButtons.add(btnFinish);
        pnlButtons.add(btnNext);
        pnlButtons.add(btnCancel);
        this.add(pnlButtons, BorderLayout.SOUTH);
        
        updateProjectFolderURI();

        this.pack();
        this.setResizable(false);
        setLocationRelativeTo(owner);
    }

    public String getProjectName() {
        return this.txtProjectName.getText();
    }

    public String getProjectFolderURI() {
        return this.txtProjectFolderURI.getText();
    }

    public int showDialog() {
        this.setVisible(true);
        return result;
    }
    
    // TODO error: projectFolderURI already exists!
    private void updateProjectFolderURI() {
        txtProjectFolderURI.setText(txtFolderLocation.getText() + File.separator + txtProjectName.getText());
    }

    private void finish() {
        result = FINISH;
        NewProjectDialog.this.setVisible(false);
    }

    private void next() {
        result = NEXT;
        NewProjectDialog.this.setVisible(false);
    }

    private void cancel() {
        result = CANCEL;
        NewProjectDialog.this.setVisible(false);
    }

}
