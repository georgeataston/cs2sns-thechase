package uk.hotten.thechase;

import com.google.gson.Gson;
import uk.hotten.thechase.client.Client;
import uk.hotten.thechase.server.Server;
import uk.hotten.thechase.utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class TheChase {

    public static Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        if (args.length != 0 && args[0] != null) {
            if (args[0].equalsIgnoreCase("server"))
                Server.main(args);
            else if (args[0].equalsIgnoreCase("client"))
                Client.main(args);

            return;
        }

        Scanner scanner = new Scanner(System.in);// Create a Scanner object
        Utils.print("Please select either 'server' or 'client'.");

        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("server"))
            Server.main(args);
        else if (answer.equalsIgnoreCase("client"))
            Client.main(args);
        else
            Utils.error("Invalid selection.");
    }

}
