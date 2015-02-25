/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpggame;

import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 *
 * @author Marco
 */
public class InputHandler implements KeyListener {

    public InputHandler(JRPGGame game) {
        game.addKeyListener(this); // TODO ugh.. 'this' here?
    }

//    public List<Key> keys = new ArrayList<>();
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    public void toggleKey(int keyCode, boolean isPressed) {
        if (keyCode == KeyEvent.VK_Z || keyCode == KeyEvent.VK_UP) {
            up.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            down.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_Q || keyCode == KeyEvent.VK_LEFT) {
            left.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            right.toggle(isPressed);
        }
    }

    public class Key {

        private boolean pressed = false;
//        private int numTimesPressed = 0;

        public void toggle(boolean isPressed) {
            this.pressed = isPressed;
//            if (pressed) {
//                numTimesPressed++;
//            }
        }

        public boolean isPressed() {
            return this.pressed;
        }
        
//        public int getNumTimesPressed() {
//            return this.numTimesPressed;
//        }

    }

}
