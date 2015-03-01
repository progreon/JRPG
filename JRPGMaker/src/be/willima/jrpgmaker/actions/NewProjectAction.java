/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.actions;

import be.willima.jrpgmaker.JRPGMaker;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author marco
 */
public class NewProjectAction extends JRPGAction {

    public NewProjectAction(JRPGMaker frame) {
        super("New project...", frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // TODO most of the code to the DAO
        String projectName = "Test Project Folder";
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showSaveDialog(null);
        File projectFolderParent = fileChooser.getSelectedFile();
        if (projectFolderParent != null) {
            System.out.println("Setting up project in " + projectFolderParent.getPath() + " ...");
            File projectFolder = new File(projectFolderParent.getPath() + File.separator + projectName);
            if (projectFolder.mkdir()) {
                System.out.println("Project folder was created succesfully!");
                System.out.println("Creating subfolders ...");
            } else {
                System.out.println("Failed to create project folder!");
            }
        }
    }

}
