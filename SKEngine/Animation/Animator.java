package SKEngine.Animation;

import SKEngine.Collections.Dictionary;

/**
 * <h2>Animator</h2>
 * Simple State Machine that interchange animations in runtime.
 * Animations are mapped using <b>Dictionary</b>.
 * <p>
 * @see Animation - SKEngine.Animation
 * @see Dictionary - SKEngine.Collectons
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-05-11
 * */
public class Animator {
    public String currentState;
    public Animation currentAnimation;
    public Dictionary<String, Animation> animations;

    /**
     * Creates an animator with an empty animation map, and empty state.
     * */
    public Animator() {
        animations = new Dictionary<String, Animation>();
        currentState = "";
        currentAnimation = null;
    }

    /**
     * Updates the animator, and the current animation.
     * */
    public void updateAnimator() {
        if(currentAnimation != null)
            currentAnimation.updateAnimation();
    }

    /**
     * Adds a new <b>Animation</b> and map it to its <b>Dictionary</b>.
     * @param String Animation State
     * @param Animation the animation to be bounded to the given state
     * @see Animation - SKEngine.Animation
     * */
    public void addAnimation(String state, Animation animation) {
        try {
            animations.add(state, animation);
        } catch (Exception e) {
            System.out.println("Duplicate animation state!");
        }
    }

    /**
     * Plays the current animation. If the current state of the animator
     * is not the same as the passed state, it will change the state of the
     * animator.
     * @param String state to be played
     * */
    public void play(String state) {
        if(currentAnimation != null) {
            currentAnimation.onPlay = true;
        }
        changeState(state);
    }

    /**
     * Changes the current state of the animator
     * @param String new state
     * */
    public void changeState(String state) {
        if(currentState.equals(state))
            return;

        if(currentAnimation != null)
            currentAnimation.onPlay = false;
            
        currentState = state;

        Animation prevAnimation = currentAnimation;
        currentAnimation = animations.getValue(currentState);

        if(currentAnimation == null) {
            currentAnimation = prevAnimation;
        }

        currentAnimation.onPlay = true;
    }

    /**
     * Stops the current animation from playing
     * */
    public void stop() {
        if(currentAnimation != null)
            currentAnimation.onPlay = false;
    }
}