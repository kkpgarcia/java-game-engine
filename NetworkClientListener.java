import java.net.Socket;
import java.io.DataInputStream;
import java.io.IOException;

public class NetworkClientListener implements Runnable {
    private Socket socket;
    private DataInputStream input;

    public NetworkClientListener(Socket socket) {
        this.socket = socket;

        try {
            input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(true) {
            try {
                String in = input.readUTF();

                NetworkTask networkTask = new NetworkTask();
                networkTask.type = TaskType.IN;
                networkTask.command = in;

                if(in != null || !in.equals("")) {
                    NetworkClient.instance.addNetworkTask(networkTask);
                }
                //Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}