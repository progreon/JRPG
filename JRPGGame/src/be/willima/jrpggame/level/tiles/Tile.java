/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpggame.level.tiles;

import be.willima.jrpggame.gfx.Colors;
import be.willima.jrpggame.gfx.Screen;
import be.willima.jrpggame.level.Level;

/**
 *
 * @author Marco
 */
public abstract class Tile {

    public static Tile[] tiles = new Tile[256];
    public static Tile VOID = new BasicTile(0, 0, 0, Colors.get(000, 222, -1, -1));
    public static Tile GRASS = new BasicTile(1, 1, 0, Colors.get(141, 131, -1, -1));
    public static Tile STONE = new BasicTile(2, 2, 0, Colors.get(222, 333, -1, -1));
    public static Tile WATER = new BasicTile(3, 3, 0, Colors.get(005, 055, -1, -1));
    public static Tile FANCY = new BasicTile(4, 4, 0, Colors.get(555, 005, 050, 500));
    public static Tile R = new BasicTile(5, 5, 0, Colors.get(555, 055, 505, 550));

    protected byte id;
    protected boolean solid;
    protected boolean emitter;

    public Tile(int id, boolean isSolid, boolean isEmitter) {
        this.id = (byte) id;
        if (tiles[id] != null) {
            throw new RuntimeException("Duplicate tile ID on " + id);
        } else {
            this.solid = isSolid;
            this.emitter = isEmitter;
            tiles[id] = this;
        }
    }
    
    public byte getId() {
        return this.id;
    }
    
    public boolean isSolid() {
        return this.solid;
    }
    
    public boolean isEmitter() {
        return this.emitter;
    }

    public abstract void render(Screen screen, Level level, int x, int y);

}
