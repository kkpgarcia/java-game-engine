import javax.swing.JFrame;

public class PhysicsEngineTest {
    public static void main(String[] args) {
        JFrame window = new JFrame("Physics Test");
        Screen screen = new Screen(500, 500);
        Input input = new Input(screen);

        GameEngine game = new GameEngine(screen);
        /*
        Player player = new Player();
        player.input = input;
        player.transform.position = new Vector2(0,0);
        player.color = java.awt.Color.BLACK;
        player.renderer.sprite = new Sprite();
        Rigidbody rigidbody = new Rigidbody(new Circle(10.0f), 0,0);
        player.rigidbody = rigidbody;
        //rigidbody.setStatic();
        player.bindInput();

        Enemy other = new Enemy();
        other.transform.position = new Vector2(-10,200);
        other.color = java.awt.Color.RED;
        other.renderer.sprite = new Sprite();
        Rigidbody rigidbody2 = new Rigidbody(new Circle(10.0f), -10, 200);
        rigidbody2.setStatic();
        rigidbody2.setOrient(0);

        Enemy other2 = new Enemy();
        other2.transform.position = new Vector2(10,200);
        other2.color = java.awt.Color.RED;
        other2.renderer.sprite = new Sprite();
        Rigidbody rigidbody3 = new Rigidbody(new Circle(10.0f), 20, 200);
        rigidbody3.setStatic();
        rigidbody3.setOrient(0);
        
        other.rigidbody = rigidbody2;
        other2.rigidbody = rigidbody3;

        game.addObject(player);
        game.addObject(other);
        game.addObject(other2);

        window.add(screen);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

        game.init();*/
    }
}