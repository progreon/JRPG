/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpggame.slick;

import be.willima.jrpgdatabase.model.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author marco
 */
public class JRPGGameSlick extends StateBasedGame {

    public static final int WIDTH = 160;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 3;
    public static final String NAME = "Game";
    private JRPGProject project;
    private int startMapID = 0;

    public JRPGGameSlick(JRPGProject project) {
        super(NAME);
        this.addState(new MenuState());
        this.addState(new PlayState());
    }
    
    public JRPGGameSlick(JRPGProject project, int startMapID) {
        this(project);
        this.startMapID = startMapID;
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        this.getState(MenuState.ID).init(container, this);
        this.getState(PlayState.ID).init(container, this);
        this.enterState(MenuState.ID);
    }
    
}
