package SKEngine.Network;

/**
 * <h2>Network Client Callback</h2>
 * This is a callback interface for client actions when recieved,
 * updates from the server.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-09-05
 * */

public interface NetworkClientCallback {
    /**
     * Use this to execute, and get the task and client information
     * @param NetworkTask currentTask
     * @param NetworkClient the game client
     * */
    public void onExecute(NetworkTask task, NetworkClient client);
}