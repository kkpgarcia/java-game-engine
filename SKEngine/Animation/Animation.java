package SKEngine.Animation;

import SKEngine.Core.Renderer;
import SKEngine.Core.Sprite;

public class Animation {
    public Frame[] frame;
    public Renderer renderer;
    public boolean onPlay = false;
    public boolean onLoop = false;

    private int currentIndex = 0;
    private float currentTime = 0;
    private final float INCREMENTAL = 0.1f;

    public Animation() {
        this.frame = null;
    }

    public Animation(Frame[] frame) {
        this.frame = frame;
    }

    public void updateAnimation() {
        if(!onPlay || frame == null || frame.length <= 0) {
            return;
        }

        if(renderer.sprite == null) {
            renderer.sprite = frame[currentIndex].sprite;
        }

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