package Game;

import SKEngine.Core.Screen;
import SKEngine.Core.GameEngine;
import SKEngine.Core.Input;

public class Main {
    public static void main(String[] args) {
        Main game = new Main();
        game.startGame();
    }

    public void startGame() {
        Screen screen = new Screen("Game", 1000, 1000);
        Input input = new Input(screen);
        GameEngine game = new GameEngine(screen);
        
        Scene scene = new Scene();
        scene.input = input;
        scene.createScene();

        game.start();
    }
}