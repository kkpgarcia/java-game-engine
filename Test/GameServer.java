package Test;

import SKEngine.Network.NetworkServer;

public class GameServer {
    public static void main(String[] args) {
        NetworkServer server = new NetworkServer();
        server.runServer();
    }
}