/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.actions;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgdatabase.model.JRPGMap;
import be.willima.jrpggame.JRPGGame;
import be.willima.jrpgmaker.JRPGMaker;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;

/**
 *
 * @author marco
 */
public class PlayAction extends JRPGAction {

    public PlayAction(JRPGMaker frame) {
        super("Playtest", frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO start game with the jar in the project folder and say that it is debug mode and select which map via command line options!
        JRPGDao dao = maker.getDao();
        JRPGMap activeMap = maker.getMapBuilder().getActiveMap(); // TODO ??
        JRPGGame game;
        if (activeMap != null) {
            game = new JRPGGame(dao.getActiveProject(), activeMap.getMapID());
        } else {
            game = new JRPGGame(dao.getActiveProject());
        }
        game.setDefaultCloseOperateion(JFrame.DISPOSE_ON_CLOSE);
        game.start(); // TODO ask for save!
    }

}
