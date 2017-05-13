package SKEngine.Animation;

import SKEngine.Core.Renderer;
import SKEngine.Core.Sprite;

/**
 * <h2>Animation</h2>
 * Contains animation information of a game object. 
 * It interchange sprites in runtime based on a <b>frame</b>'s interval, 
 * and executes commands provided by the frame.
 * <p>
 * @see Frame - SKEngine.Animation
 * @see Animator - SKEngine.Animation
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-05-11
 * */
public class Animation {
    public Frame[] frame;
    public Renderer renderer;
    public boolean onPlay = false;
    public boolean onLoop = false;

    private int currentIndex = 0;
    private float currentTime = 0;
    private final float INCREMENTAL = 0.1f;

    /**
     * Constructs an Animation class with empty parameters
     * */
    public Animation() {
        this.frame = null;
    }

    /**
     * Constructs an Animation class that applies the frame array
     * @param Frame[] collection of frames
     * */
    public Animation(Frame[] frame) {
        this.frame = frame;
    }

    /**
     * Updates the animation class and interchanges the frames when the timer
     * is more than or equal to the interval set on the current frame
     * */
    public void updateAnimation() {
        if(!onPlay || frame == null || frame.length <= 0)
            return;

        if(renderer.sprite == null)
            renderer.sprite = frame[currentIndex].sprite;

        if(currentTime < frame[currentIndex].interval) {
            currentTime += INCREMENTAL;
        } else {
            if(currentIndex < frame.length - 1)
                currentIndex++;
            else
                currentIndex = 0;

            currentTime = 0;

            Frame currentFrame = frame[currentIndex];
            renderer.sprite = currentFrame.sprite;

            if(currentFrame.callback != null)
                currentFrame.callback.onExecute();
        }
    }
}