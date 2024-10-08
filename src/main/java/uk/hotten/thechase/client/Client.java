package uk.hotten.thechase.client;

import uk.hotten.thechase.utils.MessageType;
import uk.hotten.thechase.utils.Utils;
import uk.hotten.thechase.utils.QuestionData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;

import static uk.hotten.thechase.TheChase.gson;

public class Client {

    private static Socket socket;
    private static DataOutputStream dataOutputStream;
    public static QuestionData question;

    public static void main(String[] args) {
        try {
            Utils.print("Loading client and connecting to server...");
            socket = new Socket("127.0.0.1", 17777);
            Utils.print("Connected.");
            Utils.print("Additional Commands:\n Leave: Disconnects You From The Server\n Rules: Shows you the ruleset for the game.\n Help: Guidance on how to play.\n"); //\n makes spaces, just the commands for helping the player out
            new ClientDataReceiveThread(socket).start();

            while (true) {
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();

                Utils.print(input);

                if (input.equalsIgnoreCase("leave")) {
                    Utils.print("Disconnecting...");
                    sendToServer(MessageType.DISCONNECT, "");
                }
            }
        } catch (Exception e) {
            Utils.print("Connection failure: " + e.getMessage());
        }
    }

    public static void sendToServer(MessageType type, String message) {
        try {
            dataOutputStream.writeInt(type.id);
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void quitServer() {
        Scanner command1 = new Scanner(System.in);
        String command = command1.nextLine();

        //private boolean gameIdle = true;

        //while gameIdle = True;
        if (command.equalsIgnoreCase("Leave")) {
            Utils.print("Testing"); //When the functionality to respond is implemented, it'll respond with Testing when Leave is typed
            //gameIdle = false;
        } else if (command.equalsIgnoreCase("Play")) {
            Utils.print("You have" + "PLACEHOLDER" + "seconds remaining.\nYou can type 'Stop' to go back");
            //sendToServer(MessageType.TEXT, "Player Has Joined, counting down...");
            sendToServer(MessageType.TIMER, ""); //Probably going to have it so that it
            //if (command.equalsIgnoreCase("Stop")) {
                //Utils.print("Aborting Start...");
                //sendToServer(MessageType.TEXT, "Player Has Cancelled");
                //gameIdle = false;
            //}
        } else {
            Utils.print("Unknown command. \nAvailable Commands:\nPlay: Start the game\nLeave: Disconnects You From The Server\nHelp: Gives you guidance on how to play");
        }
    }
}
