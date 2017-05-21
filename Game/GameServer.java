package Game;

import SKEngine.Network.NetworkServer;

public class GameServer {

    public void start() {
        NetworkServer server = new NetworkServer();
        server.runServer();
    }
}