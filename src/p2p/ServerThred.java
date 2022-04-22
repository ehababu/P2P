package p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class ServerThred extends Thread {

    private ServerSocket serverSocket;
    private Set<ServerThredThred> serverTT = new HashSet<ServerThredThred>();

    public ServerThred(String portNum) throws IOException {
        serverSocket = new ServerSocket(Integer.valueOf(portNum));
    }

    public void run() {
        try {
            while (true) {
                ServerThredThred serverThredThred = new ServerThredThred(serverSocket.accept(), this);
                serverTT.add(serverThredThred);
                serverThredThred.start();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    void sendMessage(String message) {
        try {
            serverTT.forEach(t -> t.getPrintWriter().println(message));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Set<ServerThredThred> getServerTT() {
        return serverTT;
    }

}
