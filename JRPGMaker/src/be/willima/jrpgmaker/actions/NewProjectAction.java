/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.actions;

import be.willima.jrpgmaker.JRPGMaker;
import be.willima.jrpgmaker.view.NewProjectDialog;
import java.awt.event.ActionEvent;

/**
 *
 * @author marco
 */
public class NewProjectAction extends JRPGAction {

    private NewProjectDialog npDialog;

    public NewProjectAction(JRPGMaker maker) {
        super("New project...", maker);
        npDialog = new NewProjectDialog(maker.getFrame());
    }

    @Override
    public void actionPerformed(ActionEvent e) { // TODO most of the code to the DAO
//        npDialog.setVisible(true);
        int result = npDialog.showDialog();
        if (result == NewProjectDialog.FINISH) {
            // TODO zonder getDao()!
            maker.getDao().createNewProject(npDialog.getProjectFolderURI(), npDialog.getProjectName(), npDialog.getProjectName());
        }
    }

}
