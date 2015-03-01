/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.actions;

import be.willima.jrpgmaker.JRPGMaker;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author marco
 */
public abstract class JRPGAction extends AbstractAction {

    protected JRPGMaker maker;

    protected JRPGAction(String name, JRPGMaker maker) {
        super(name);
        this.maker = maker;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);

}
