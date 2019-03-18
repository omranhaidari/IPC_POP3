package ipcpop3;

import ipcpop3.Utils.Observer;

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
            UsersConnections usersConnected = new UsersConnections();
            ServerSocket serverSocket = new ServerSocket(8025);

            while(true) {
                System.err.println("Attente de connexion... (" + usersConnected.getUsersCount() + " users currently connected)");
                Socket socket = serverSocket.accept();
                System.err.println("Connexion établie");

                POP3Context context = POP3Context.createContext(socket);
                if(context != null) {
                    usersConnected.addUser(context);
                    context.addObserver(usersConnected);
                    // Lance le nouveau Thread
                    new Thread(() -> {
                        try {
                            context.init();
                            // Gère création du context et l'envoi de la première réponse
                            context.run();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else {
                    // TODO Log l'erreur (user n'a pas pu se connecter)
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class UsersConnections implements Observer {

        List<POP3Context> usersConnected = new ArrayList<>();

        @Override
        public void remove(Object object) {
            if(object instanceof POP3Context) {
                usersConnected.remove(object);
            }
        }

        public void addUser(POP3Context user) {
            usersConnected.add(user);
        }

        public int getUsersCount() {
            return usersConnected.size();
        }
    }
}
