import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class NetworkTest {
    public static void main(String[] args) {
        NetworkTest test = new NetworkTest();
        test.runTest();
    }

    public void runTest() {
        JFrame window = new JFrame("Network Test");
        Screen screen = new Screen(500,500);
        Input input = new Input(screen);

        GameEngine game = new GameEngine(screen);

        NetworkClient networkClient = new NetworkClient();
        NetAlien netAlien = new NetAlien();
        netAlien.input = input;
        netAlien.bindInput();
        networkClient.addNetworkActors(netAlien.networkActor);

        game.addObject(netAlien);

        window.add(screen);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

        game.start();
        networkClient.connect();
        netAlien.networkActor.registerActor();
    }
}