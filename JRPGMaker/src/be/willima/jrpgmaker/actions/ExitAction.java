/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.actions;

import be.willima.jrpgmaker.JRPGMakerS;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/**
 *
 * @author marco
 */
public class ExitAction extends JRPGAction {

    public ExitAction(JRPGMakerS frame) {
        super("Exit", frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

}
