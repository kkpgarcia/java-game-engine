import Game.GameServer;

public class LaunchServer {
    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();
    }
}