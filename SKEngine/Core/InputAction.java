package SKEngine.Core;

import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * <h2>Input Action</h2>
 * Input bindings and behaviors.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-02-02
 * */
public class InputAction {
    public static final int NORMAL = 0;
    public static final int ON_PRESS = 1;
    private static final int STATE_RELEASED = 0;
    private static final int STATE_PRESSED = 1;
    private static final int STATE_WAITING_FOR_RELEASE = 2;
    private String name;
    private int behavior;
    private int amount;
    private int state;

    /**
     * Constructs an input action with the default behavior
     * @param String name of the input
     * */
    public InputAction(String name) {
        this(name, NORMAL);
    }

    /**
     * Constructs an input action with the given behavior
     * @param String name of the input
     * @param int behavior
     * */
    public InputAction(String name, int behavior) {
        this.name = name;
        this.behavior = behavior;
        reset();
    }

    /**
     * Gets the name of the input action
     * @return String name
     * */
    public String getName() {
        return name;
    }

    /**
     * Resets this input action
     * */
    public void reset() {
        state = STATE_RELEASED;
        amount = 0;
    }

    /**
     * Digitally tap this action.
     * */
    public synchronized void tap() {
        press();
        release();
    }

    /**
     * Digitally press this action once.
     * */
    public synchronized void press() {
        press(1);
    }

    /**
     * Digitally press this action by the indicated amount.
     * @param int times to be pressed
     * */
    public synchronized void press(int amount) {
        if(state != STATE_WAITING_FOR_RELEASE) {
            this.amount += amount;
            state = STATE_PRESSED;
        }
    }

    /**
     * Releases this input action
     * */
    public synchronized void release() {
        state = STATE_RELEASED;
    }

    /**
     * Check if this input is pressed
     * @return boolean
     * */
    public synchronized boolean isPressed() {
        return (getAmount() != 0);
    }

    /**
     * Gets the amount this input action is pressed.
     * @return amount
     * */
    public synchronized int getAmount() {
        int retVal = amount;
        if(retVal != 0) {
            if(state == STATE_RELEASED) {
                amount = 0;
            } else if (behavior == ON_PRESS) {
                state = STATE_WAITING_FOR_RELEASE;
                amount = 0;
            }
        }

        return retVal;
    }
}