package SKEngine.Core;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <h2>Engine</h2>
 * Abstract engine of the game. This is the back bone of the different
 * engines that do different tasks. This class implements runnable. This
 * class also handles all the update, and loop time of the whole game.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-02-02
 * */
public abstract class Engine implements Runnable {
    protected boolean isRunning;

    protected transient volatile GameObject[] objects;

    protected Thread engineThread;
    protected ReentrantLock lock;

    /**
     * Initializes the engine.
     * */
    public Engine() {
        init();
    }

    public void init() {
        objects = new GameObject[0];
        if(engineThread == null) {
            engineThread = new Thread(this);
        }

        if(lock == null) {
            lock = new ReentrantLock();
        }
    }

    /**
     * Adds new object to its object pool.
     * @param GameObject new object
     * */
    public void addObject(GameObject obj) {
        lock.lock();
        
        try {
            GameObject[] elements = objects;
            int length = elements.length;
            GameObject[] newElements = Arrays.copyOf(elements, length+1);
            newElements[length] = obj;
            objects = newElements;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes a specific object from its object pool
     * @param GameObject object to be removed
     * */
    public void removeObject(GameObject obj) {
        lock.lock();
        
        if(objects.length == 0) {
            lock.unlock();
            return;
        } 

        try {
            GameObject[] elements = objects;
            ArrayList<GameObject> newElements = new ArrayList<GameObject>(Arrays.asList(elements));
            newElements.remove(obj);
            objects = newElements.toArray(new GameObject[elements.length-1]);
        } finally {
            lock.unlock();
        }
    }

    public void start() {
        isRunning = true;
        engineThread.start();
    }
    
    /**
     * This function runs the whole engine on a fixed frame rate to limit
     * unecessary high updates, and keep the frames to target a specific rate.
     * This also calls specific funtions for other engines to update to.
     * */
    public void run() {
        final double GAME_HERTZ = 30;
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER = 1;
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = System.nanoTime();
		final double TARGET_FPS = 30;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

		while(isRunning) {
			double now = System.nanoTime();
			int updateCount = 0;

			while(now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                updateMainEngine();
                updateCollisionEngine();
                updatePhysicsEngine();
                updateNetworkEngine();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}

			if(now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
                
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}

            
            updateRenderEngine();
			lastRenderTime = now;

			int thisSecond = (int) (lastUpdateTime / 1000000000);

			if(thisSecond > lastSecondTime) {
				//System.out.println(this.getClass().getSimpleName() + " " + thisSecond);
				lastSecondTime = thisSecond;
			}

			while(now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
				Thread.yield();
				
				try {
					Thread.sleep(1);
				} catch (Exception e) {

				}

				now = System.nanoTime();
			}
		}
    }

    public ReentrantLock getLock() {
        if(engineThread != null) {
            return lock;
        } else {
            System.out.println("Thread does not exists!");
            return null;
        }
    }

    /**
     * Updates the main engine, all the game object updates for the logic.
     * */
    public void updateMainEngine() {
    }

    /**
     * Updates the render engine, and just do repaint on the screen.
     * */
    public void updateRenderEngine() {
    }

    /**
     * Updates the collision engine, and check all object if there are colliding
     * objects.
     * */
    public void updateCollisionEngine() {
        
    }

    /**
     * Updates the physics engine, and check for all impulses, and apply proper
     * force, and velocity to an object.
     * */
    public void updatePhysicsEngine() {
    }

    public void updateNetworkEngine() {

    }
}