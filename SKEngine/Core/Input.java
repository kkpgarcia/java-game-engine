package SKEngine.Core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.Map;

/**
 * <h2>Input</h2>
 * This class handles all keyboard input and maps them
 * according to their key code provided by the awt library.
 * <p>
 * @see InputAction - SKEngine.Core
 * @see KeyEvent - java.awt.event
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-02-02
 * */

public class Input extends KeyAdapter {
   
    private static final int NUM_KEY_CODES = 600;
    private InputAction[] keyActions = new InputAction[NUM_KEY_CODES];

    private java.awt.Component component;

    /**
     * Initializes input.
     * @param java.awt.Component screen
     * */
    public Input(java.awt.Component comp) {
        component = comp;
        component.addKeyListener(this);

        component.setFocusTraversalKeysEnabled(false);
    }

    /**
     * Maps a key code and input action.
     * @param InputAction keyboard action
     * @param int key code/index of the keyboard action
     * */
    public void mapToKey(InputAction inputAction, int keyCode) {
        keyActions[keyCode] = inputAction;
    }

    /**
     * Gets the input action based on the key code provided.
     * @param KeyEvent key event
     * */
    private InputAction getKeyAction(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode < keyActions.length) {
            return keyActions[keyCode];
        } else {
            return null;
        }
    }

    /**
     * Clears all the bindings of the Input.
     * @param InputAction input to be cleared
     * */
    public void clearMap(InputAction inputAction) {
        for(int i = 0; i < keyActions.length; i++) {
            if(keyActions[i] == inputAction) {
                keyActions[i] = null;
            }
        }

        inputAction.reset();
    }

    /**
     * Clears all input bindings.
     * */
    public void resetAllGameActions() {
        for(int i = 0; i < keyActions.length; i++) {
            if(keyActions[i] != null) {
                keyActions[i].reset();
            }
        }
    }

     @Override
    public void keyTyped(KeyEvent e) {
        e.consume();
    }
     @Override
    public void keyPressed(KeyEvent e) {
        InputAction inputAction = getKeyAction(e);
        if(inputAction != null) {
            inputAction.press();
        }
        e.consume();
    }
    @Override
    public void keyReleased(KeyEvent e) {
        InputAction inputAction = getKeyAction(e);
        if(inputAction != null) {
            inputAction.release();
        }
        e.consume();
    }
}