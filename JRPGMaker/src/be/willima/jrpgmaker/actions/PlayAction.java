/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.actions;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpggame.JRPGGame;
import be.willima.jrpgmaker.JRPGMakerS;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;

/**
 *
 * @author marco
 */
public class PlayAction extends JRPGAction {

    public PlayAction(JRPGMakerS frame) {
        super("Playtest", frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JRPGDao dao = frame.getDao();
        JRPGMap activeMap = frame.mapEditorPanel.getActiveMap();
        JRPGGame game;
        if (activeMap != null) {
            game = new JRPGGame(dao.getActiveProject(), activeMap.getMapID());
        } else {
            game = new JRPGGame(dao.getActiveProject());
        }
        game.setDefaultCloseOperateion(JFrame.DISPOSE_ON_CLOSE); // TODO stop game thread!!
        game.start(); // TODO ask for save!
    }

}
