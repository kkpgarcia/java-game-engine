import javax.swing.JFrame;
import java.awt.image.BufferedImage;

public class TestEnvironment {
    public static void main(String[] args) {
        JFrame window = new JFrame("Test Environment");
        Screen screen = new Screen(500, 500);
        GameEngine game = new GameEngine(screen);
        Input input = new Input(screen);

        /***/

        Player player = new Player();
        player.input = input;
        player.bindInput();
        game.addObject(player);

        /***/

        window.add(screen);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		game.start();
    }
}