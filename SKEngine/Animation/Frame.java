package SKEngine.Animation;

import SKEngine.Core.Sprite;

public class Frame {
    public Sprite sprite;
    public float interval;
    public AnimationCallback callback;

    public Frame(Sprite sprite, float interval, AnimationCallback callback) {
        this.sprite = sprite;
        this.interval = interval;
        this.callback = callback;
    }
}