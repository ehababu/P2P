package p2p;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThredThred extends Thread {

    private ServerThred serverThred;
    private Socket socket;

    private PrintWriter printWriter;

    public ServerThredThred(Socket socket, ServerThred serverThred) {
        this.socket = socket;
        this.serverThred = serverThred;
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                serverThred.sendMessage(bufferedReader.readLine());
            }
        } catch (Exception e) {
            serverThred.getServerTT().remove(this);
        }
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

}
