import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class NetworkClientDispatcher implements Runnable {
    private Socket socket;
    private DataOutputStream output;
    private boolean connected;
    private ArrayList<String> commands;
    private ReentrantLock lock;

    public NetworkClientDispatcher(Socket socket) {
        this.socket = socket;

        try {
            output = new DataOutputStream(socket.getOutputStream());
            commands = new ArrayList<String>();
            connected = true;

            if(lock == null) {
                lock = new ReentrantLock();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dispatch(String command) {
        lock.lock();
        try {
            commands.add(command);
        } finally {
            lock.unlock();
        }
    }

    public void run() {
        while(connected) {
            try {
                if(commands.size() == 0)
                    continue;

                String newCommand = commands.get(0);
                dequeueTopCommand();

                //System.out.println(newCommand);
                output.writeUTF(newCommand);
                output.flush();
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void dequeueTopCommand() {
        lock.lock();
        try {
            if(commands.size() == 0)
                commands.remove(0);
        } finally {
            lock.unlock();
        }
    }
}