/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.actions;

import be.willima.jrpgmaker.JRPGMaker;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author marco
 */
public class AddRowsAction extends JRPGAction {

    public AddRowsAction(JRPGMaker maker) {
        super("Add rows...", maker);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, e.getActionCommand());
    }

}
