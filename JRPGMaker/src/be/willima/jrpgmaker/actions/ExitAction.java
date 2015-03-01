/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.actions;

import be.willima.jrpgmaker.JRPGMaker;
import java.awt.event.ActionEvent;

/**
 *
 * @author marco
 */
public class ExitAction extends JRPGAction {

    public ExitAction(JRPGMaker maker) {
        super("Exit", maker);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        maker.exit();
    }

}
