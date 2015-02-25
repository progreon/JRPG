/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpggame.level.tiles;

import be.willima.jrpggame.gfx.Screen;
import be.willima.jrpggame.level.Level;

/**
 *
 * @author Marco
 */
public class BasicTile extends Tile {
    
    protected int tileId, x, y, tileColor;

    public BasicTile(int id, int x, int y, int tileColor) {
        super(id, false, false);
        this.tileId = x + y;
        this.tileColor = tileColor;
    }

    @Override
    public void render(Screen screen, Level level, int x, int y) {
        screen.render(x, y, tileId, tileColor);
    }
    
}
