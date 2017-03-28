/**
* Author: Kyle Kristopher P. Garcia
* Date: February 2, 2017
*/

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.util.EnumMap;
import java.util.Map;

public class Input extends KeyAdapter {
    private Vector2 m_axis = new Vector2();
    private Vector2 m_axis2 = new Vector2();
   
    private static final int NUM_KEY_CODES = 600;
    private InputAction[] m_keyActions = new InputAction[NUM_KEY_CODES];

    private java.awt.Component m_component;

    public Input(java.awt.Component comp) {
        m_component = comp;
        m_component.addKeyListener(this);

        m_component.setFocusTraversalKeysEnabled(false);
    }

    public void mapToKey(InputAction inputAction, int keyCode) {
        m_keyActions[keyCode] = inputAction;
    }

    private InputAction getKeyAction(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode < m_keyActions.length) {
            return m_keyActions[keyCode];
        } else {
            return null;
        }
    }

    public void clearMap(InputAction inputAction) {
        for(int i = 0; i < m_keyActions.length; i++) {
            if(m_keyActions[i] == inputAction) {
                m_keyActions[i] = null;
            }
        }

        inputAction.reset();
    }

    public void resetAllGameActions() {
        for(int i = 0; i < m_keyActions.length; i++) {
            if(m_keyActions[i] != null) {
                m_keyActions[i].reset();
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