package SKEngine.Animation;

import SKEngine.Collections.Dictionary;

public class Animator {
    public String currentState;
    public Animation currentAnimation;
    public Dictionary<String, Animation> animations;

    public Animator() {
        animations = new Dictionary<String, Animation>();
        currentState = "";
        currentAnimation = null;
    }

    public void updateAnimator() {
        if(currentAnimation != null)
            currentAnimation.updateAnimation();
    }

    public void addAnimation(String state, Animation animation) {
        try {
            animations.add(state, animation);
        } catch (Exception e) {
            System.out.println("Duplicate animation state!");
        }
    }

    public void play(String state) {
        if(currentAnimation != null) {
            currentAnimation.onPlay = true;
        }
        changeState(state);
    }

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

    public void stop() {
        if(currentAnimation != null)
            currentAnimation.onPlay = false;
    }
}