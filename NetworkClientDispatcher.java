import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkClientDispatcher implements Runnable {
    private Socket socket;
    private DataOutputStream output;
    private boolean connected;
    private ArrayList<String> commands;

    public NetworkClientDispatcher(Socket socket) {
        this.socket = socket;

        try {
            output = new DataOutputStream(socket.getOutputStream());
            commands = new ArrayList<String>();
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dispatch(String command) {
        commands.add(command);
    }

    public void run() {
        while(connected) {
            try {
                if(commands.size() == 0)
                    return;

                String newCommand = commands.get(0);
                output.writeUTF(newCommand);
                output.flush();
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}