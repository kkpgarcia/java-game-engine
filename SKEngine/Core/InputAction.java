package SKEngine.Core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class InputAction {
    public static final int NORMAL = 0;
    public static final int ON_PRESS = 1;
    private static final int STATE_RELEASED = 0;
    private static final int STATE_PRESSED = 1;
    private static final int STATE_WAITING_FOR_RELEASE = 2;
    private String m_name;
    private int m_behavior;
    private int m_amount;
    private int m_state;

    public InputAction(String name) {
        this(name, NORMAL);
    }

    public InputAction(String name, int behavior) {
        m_name = name;
        m_behavior = behavior;
        reset();
    }

    public String getName() {
        return m_name;
    }

    public void reset() {
        m_state = STATE_RELEASED;
        m_amount = 0;
    }

    public synchronized void tap() {
        press();
        release();
    }

    public synchronized void press() {
        press(1);
    }

    public synchronized void press(int amount) {
        if(m_state != STATE_WAITING_FOR_RELEASE) {
            m_amount += amount;
            m_state = STATE_PRESSED;
        }
    }

    public synchronized void release() {
        m_state = STATE_RELEASED;
    }

    public synchronized boolean isPressed() {
        return (getAmount() != 0);
    }

    public synchronized int getAmount() {
        int retVal = m_amount;
        if(retVal != 0) {
            if(m_state == STATE_RELEASED) {
                m_amount = 0;
            } else if (m_behavior == ON_PRESS) {
                m_state = STATE_WAITING_FOR_RELEASE;
                m_amount = 0;
            }
        }

        return retVal;
    }
}