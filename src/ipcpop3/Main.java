package ipcpop3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            List<POP3Context> usersConnected = new ArrayList<>();
            ServerSocket serverSocket = new ServerSocket(8025);

            while(true) {
                System.out.println("Attente de connexion... (" + usersConnected.size() + " users currently connected)");
                Socket socket = serverSocket.accept();
                System.out.println("Connexion établie");

                POP3Context context = POP3Context.createContext(socket);
                if(context != null) {
                    usersConnected.add(context);
                    ((Runnable) () -> {
                        try {
                            context.init();
                            context.run();
                        } catch (IOException e) {
                            // Gère création du context et l'envoie de la première réponse
                            e.printStackTrace();
                        }
                    }).run();
                } else {
                    // TODO Log l'erreur (user n'a pas pu se connecter)
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
