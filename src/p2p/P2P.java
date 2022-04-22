package p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import javax.json.Json;

public class P2P {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter The Username & Port Number for this Peer :");
        String[] setupValue = bufferedReader.readLine().split(" ");
        ServerThred serverThred = new ServerThred(setupValue[1]);
        serverThred.start();
        new P2P().updateListenToPeers(bufferedReader, setupValue[0], serverThred);

    }

    public void updateListenToPeers(BufferedReader bufferedReader, String username, ServerThred serverThred) throws IOException {

        System.out.println("Enter HostName:Port#");
        System.out.println("Peers to receive From (s to skip):");
        String input = bufferedReader.readLine();
        String[] inputValues = input.split(" ");
        if (!input.equals("s")) {
            for (int i = 0; i < inputValues.length; i++) {
                String[] address = inputValues[i].split(" ");
                Socket socket = null;
                try {
                    socket = new Socket(address[0], Integer.valueOf(address[1]));
                    new PeerThred(socket).start();
                } catch (Exception e) {
                    if (socket != null) {
                        socket.close();
                    } else {
                        System.out.println("Invalid Input ");
                    }
                }
            }
        }
        communicate(bufferedReader, username, serverThred);

    }

    public void communicate(BufferedReader bufferedReader, String username, ServerThred serverThred) {
        try {
            System.out.println("You can now commuicate (e to exit , c to change)");
            boolean flag = true;
            while (flag) {
                String message = bufferedReader.readLine();
                if (message.equals("e")) {
                    flag = false;
                    break;

                } else if (message.equals("c")) {
                    updateListenToPeers(bufferedReader, username, serverThred);
                } else {
                    StringWriter strinwriter = new StringWriter();
                    Json.createWriter(strinwriter).writeObject(Json.createObjectBuilder()
                            .add("username", username)
                            .add("message", message)
                            .build()
                    );
                   serverThred.sendMessage(strinwriter.toString());
                }
            }
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
