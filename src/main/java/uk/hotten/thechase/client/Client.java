package uk.hotten.thechase.client;

import uk.hotten.thechase.utils.MessageType;
import uk.hotten.thechase.utils.Utils;
import uk.hotten.thechase.utils.QuestionData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static Socket socket;
    private static DataOutputStream dataOutputStream;
    public static QuestionData question;
    public static boolean running = true;
    public static boolean allowQuestionInput = false;
    public static boolean chaser;

    public static void main(String[] args) {
        try {
            Utils.print("Loading client and connecting to server...");
            socket = new Socket("127.0.0.1", 17777);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Utils.print("Connected.");
            Utils.print("Type 'Commands' for additional functionality or help.");
            new ClientDataReceiveThread(socket).start();

            while (running) {
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();

                if (allowQuestionInput) {
                    if (input.equalsIgnoreCase("a") || input.equalsIgnoreCase("b") || input.equalsIgnoreCase("c")) {
                        sendToServer(MessageType.PLAYER_ANSWER, input);
                        allowQuestionInput = false;
                        Utils.print("Answer locked in!");
                    } else {
                        Utils.print("Please choose either: A, B, or C and press ENTER!");
                    }

                    return;
                }

                if (input.equalsIgnoreCase("Leave")) {
                    Utils.print("Disconnecting...");
                    sendToServer(MessageType.DISCONNECT, "");
                    socket.close();
                    Utils.print("You've left the game, goodbye!");
                    running = false;
                }
                else if (input.equalsIgnoreCase("Rules")) {
                    Utils.print("To play the game, you will be given questions\nEach question will have a corresponding letter (E.G A: Bananas)\nTo lock in your answer, you can type 'A' but once you choose, your answer is locked in\nYou have a time limit to answer the question, you will be told if you got the answer right or wrong.");
                }
                else if (input.equalsIgnoreCase("Help")) {
                    Utils.print("If you cannot type nor connect, please restart your terminal.");
                }
                else if (input.equalsIgnoreCase("Commands")) {
                    Utils.print("Additional Commands:\nLeave: Disconnects You From The Server\nRules: Shows you the ruleset for the game.\nHelp: If it isn't working.\nCommands: This current command"); //\n makes spaces, just the commands for helping the player out
                }
                else {
                    Utils.print("That command wasn't recognised, please try again\n(Check your spelling!)\nYou can use 'Commands' for a list of available comands");
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
            //sendToServer(MessageType.TIMER, ""); //Probably going to have it so that it
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
