import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class NetworkServerDispatcher implements Runnable {
    private String id;
    private NetworkServerClientConnection connection;
    private Socket socket;
    private DataOutputStream output;
    private ArrayList<String> commands;
    private boolean connected;
    private ReentrantLock lock;

    public NetworkServerDispatcher(String id, Socket socket, NetworkServerClientConnection connection) {
        this.connection = connection;
        this.socket = socket;

        try {
            output = new DataOutputStream(socket.getOutputStream());
            connected = true;
            commands = new ArrayList<String>();

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

                if(newCommand != null) {
                    output.writeUTF(newCommand);
                    output.flush();
                }
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
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