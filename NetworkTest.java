import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class NetworkTest {
    public static void main(String[] args) {
        NetworkTest test = new NetworkTest();
        test.runTest();
    }

    public void runTest() {
        Screen screen = new Screen("Network Test",500,500);
        Input input = new Input(screen);

        GameEngine game = new GameEngine(screen);
        Camera camera = new Camera();

        NetworkClient networkClient = new NetworkClient();
        NetAlien netAlien = new NetAlien();
        netAlien.input = input;
        netAlien.bindInput();
        netAlien.networkActor = new NetworkActor("main", netAlien, networkClient);
        camera.follow(netAlien);

        game.addObject(camera);
        game.addObject(netAlien);

        networkClient.connect();
        game.start();
    }
}