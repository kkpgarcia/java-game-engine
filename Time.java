public class Time {
    public static double time;
    public static double deltaTime;

    private Thread timeThread;
    private boolean isRunning;

    public void init() {
        time = 0.0;
        deltaTime = 0.01;
        isRunning = true;

        if(timeThread == null) {
            timeThread = new Thread() {
                public void run() {
                    updateTime();
                }
            };
        }

        timeThread.start();
    }

    public void updateTime() {
        double lastTime = System.nanoTime();
        float speed = 1f;
        float x = 1.0f;

        while(isRunning) {
            time = System.nanoTime();

            deltaTime = (time - lastTime) / 10000000;
            lastTime = time;
            
            //System.out.println(deltaTime);
            Thread.yield();
            try {
                Thread.sleep(1);
            } catch (Exception e) {

            }
        }
    }

    public static void main(String args[]) {
        Time time = new Time();
        time.init();
    }
}