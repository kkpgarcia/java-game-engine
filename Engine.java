public abstract class Engine implements Runnable {
    protected boolean isRunning;
    protected Thread engineThread;

    public void init() {
        isRunning = true;

        if(engineThread == null) {
            engineThread = new Thread(this);
        }

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
                //Check InvokeLater
                updateMainEngine();
                updateCollisionEngine();
                updatePhysicsEngine();
                updateUIEngine();
                updateAudioEngine();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}

			if(now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}

			lastRenderTime = now;

			int thisSecond = (int) (lastUpdateTime / 1000000000);

			if(thisSecond > lastSecondTime) {
				System.out.println(this.getClass().getSimpleName() + " " + thisSecond);
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

    public void updateAudioEngine() {
    }
}