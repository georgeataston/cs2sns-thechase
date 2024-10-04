package uk.hotten.thechase.client;

import uk.hotten.thechase.Utils;

import java.io.DataInputStream;
import java.net.Socket;

public class Client {

    public static Socket socket;

    public static void main(String[] args) {
        try {
            socket = new Socket("127.0.0.1", 17777);

            DataInputStream stream = new DataInputStream(socket.getInputStream());
            while (true) {
                Utils.print(stream.readUTF());
            }
        } catch (Exception e) {
            Utils.print("Failed to connect: " + e.getMessage());
        }
    }
}
