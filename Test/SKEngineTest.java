package Test;

import SKEngine.Core.Camera;
import SKEngine.Core.GameEngine;
import SKEngine.Core.Screen;
import SKEngine.Core.Input;
import SKEngine.Core.GameObject;

import java.util.ArrayList;

public class SKEngineTest {
    public static void main(String[] args) {
        SKEngineTest test = new SKEngineTest();
        test.runTest();
    }
    public void runTest() {
        Screen screen = new Screen("Engine Test", 500, 500);
        Input input = new Input(screen);
        GameEngine game = new GameEngine(screen);
        Camera camera = new Camera();

        Scene scene = new Scene();
        scene.input = input;
        scene.createScene(camera);

        game.start();
    }
}