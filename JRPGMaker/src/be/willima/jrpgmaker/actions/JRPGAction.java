/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.actions;

import be.willima.jrpgmaker.JRPGMakerS;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author marco
 */
public abstract class JRPGAction extends AbstractAction {
    
    protected JRPGMakerS frame;

    protected JRPGAction(String name, JRPGMakerS frame) {
        super(name);
        this.frame = frame;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);
    
}
