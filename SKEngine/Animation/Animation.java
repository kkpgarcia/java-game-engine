package SKEngine.Animation;

import SKEngine.Core.Renderer;
import SKEngine.Core.Sprite;

public class Animation {
    public Sprite[] sprites;
    public float[] interval;
    public AnimationCallback[] callbacks;
    public Renderer renderer;
    public boolean onPlay = false;
    public boolean onLoop = false;;
    public int frames = 0;

    private int currentFrame = 0;
    private float currentTime = 0;
    private final float incremental = 0.1f;

    public Animation() {
        this.sprites = null;
        this.interval = null;
        this.frames = 0;
        this.callbacks = null;
    }

    public Animation(Sprite[] sprites, float[] interval, AnimationCallback[] callbacks, int frames) {
        this.sprites = sprites;
        this.interval = interval;
        this.frames = frames;
        this.callbacks = callbacks;
    }

    public void updateAnimation() {
        if(!onPlay || sprites == null || sprites.length <= 0 
        || interval == null || interval.length <= 0
        || frames == 0) {
            return;
        }

        if(renderer.sprite == null) {
            renderer.sprite = sprites[currentFrame];
        }

        if(currentTime < interval[currentFrame]) {
            currentTime += incremental;
        } else {
            if(currentFrame < sprites.length - 1) {
                currentFrame++;
            } else {
                currentFrame = 0;
            }

            currentTime = 0;

            Sprite currentSprite = sprites[currentFrame];
            renderer.sprite = currentSprite;

            if(callbacks != null) {
                AnimationCallback callback = callbacks[currentFrame];
                
                if(callback != null)
                    callback.onExecute();
            }
        }
    }
}