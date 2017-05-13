package SKEngine.Animation;

import SKEngine.Core.Sprite;

/**
 * <h2>Frame</h2>
 * Contains sprite information to be rendered, the interval on which
 * it will change to the next frame, and the callback after the frame is
 * interval is met.
 * <p>
 * @see Sprite - SKEngine.Core
 * @see AnimationCallback - SKEngine.SKEngine.Animation
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-05-13
 * */
public class Frame {
    public Sprite sprite;
    public float interval;
    public AnimationCallback callback;

    /**
     * Creates a frame that contains a sprite, an interval, and callback.
     * @param Sprite image shown in this frame
     * @param float how long this frame is shown
     * @param AnimationCallback the action performed after this frame is finished showing
     * */
    public Frame(Sprite sprite, float interval, AnimationCallback callback) {
        this.sprite = sprite;
        this.interval = interval;
        this.callback = callback;
    }
}