public class UITest {
    public static void main(String[] args) {
        UITest test = new UITest();
        test.runTest();
    }

    public void runTest() {
        Screen screen = new Screen("UI Test", 500, 500);
        Input input = new Input(screen);
        GameEngine game = new GameEngine(screen);

        game.start();
    }
}