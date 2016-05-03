/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpggame.slick;
import be.willima.jrpgdatabase.model.JRPGProject;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author marco
 */
public class MenuState extends BasicGameState {
    
    public static final int ID = 0;
    
    private String mouseText = "[?,?]";
    private int mouseTextX = 10;
    private int mouseTextY = 30;

    public MenuState(JRPGProject project, int mapID) {
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawString(mouseText, mouseTextX, mouseTextY);
        g.fillOval(50, 100, 120, 60);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int xpos = Mouse.getX();
        int ypos = mouseYToScreenY(container, Mouse.getY());
        mouseText = "[" + xpos + "," + ypos + "]";
        
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_UP)) mouseTextY--;
        if (input.isKeyDown(Input.KEY_DOWN)) mouseTextY++;
        if (input.isKeyDown(Input.KEY_LEFT)) mouseTextX--;
        if (input.isKeyDown(Input.KEY_RIGHT)) mouseTextX++;
        
        if ((xpos > 50 && xpos < 170) && (ypos > 100 && ypos < 160)) {
            mouseText += " --> GO?";
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                game.enterState(PlayState.ID);
            }
        }
    }
    
    private int mouseYToScreenY(GameContainer container, int mouseY) {
        return container.getHeight() - 1 - mouseY;
    }
    
}
