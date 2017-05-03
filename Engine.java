import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Engine implements Runnable {
    protected boolean isRunning;

    protected transient volatile GameObject[] objects;

    protected Thread engineThread;
    protected ReentrantLock lock;

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

    public void removeObject(GameObject obj) {
        lock.lock();
        
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
    
    public void run() {
        final double GAME_HERTZ = 30;
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER = 5;
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
                updateUIEngine();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}

			if(now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}

			lastRenderTime = now;

			int thisSecond = (int) (lastUpdateTime / 1000000000);

			if(thisSecond > lastSecondTime) {
				//System.out.println(this.getClass().getSimpleName() + " " + thisSecond);
				lastSecondTime = thisSecond;
			}

			while(now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
				Thread.yield();
				updateRenderEngine();
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

 
    public void updateMainEngine() {
    }

    public void updateRenderEngine() {
    }

    public void updateUIEngine() {
    }

    public void updateCollisionEngine() {
    }

    public void updatePhysicsEngine() {
    }
}