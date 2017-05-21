package Game;

import SKEngine.Core.Screen;
import SKEngine.Core.GameEngine;
import SKEngine.Core.Input;

import SKEngine.Network.NetworkClient;
import SKEngine.Network.NetworkClientCallback;
import SKEngine.Network.NetworkTask;

public class Main {
    boolean isGameStarted = false;
    public static void main(String[] args) {
        Main game = new Main();
        game.startGame();
    }

    public void startGame() {
        Screen screen = new Screen("Game", 1000, 1000);
        Input input = new Input(screen);
        GameEngine game = new GameEngine(screen);

        NetworkClient networkClient = new NetworkClient();

        Scene scene = new Scene();
        scene.input = input;
        scene.networkClient = networkClient;
        scene.createScene();

        NetworkClientCallback onClientConnect = new NetworkClientCallback() {
            public void onExecute(NetworkTask task, NetworkClient client) {
                isGameStarted = true;
            }
        };
        networkClient.addNewClientConnectionAction(onClientConnect);

        networkClient.connect("localhost", 8888);
    
        while(!isGameStarted) {
            System.out.println("WAITING FOR ANOTHER CLIENT...");
        }

        networkClient.removeNewClientConnectionAction(onClientConnect);

        System.out.println("Game started!");
        game.start();
    }
}