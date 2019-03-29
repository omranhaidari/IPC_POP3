package ipcpop3;

import ipcpop3.Utils.Observer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    protected int port;
    protected boolean SSLEnabled = false;

    protected ServerSocket serverSocket;
    protected UsersConnections usersConnected;

    public Server() {
        this.port = 8025;
    }

    public Server(int customPort) {
        this.port = customPort;
    }

    public Server(int customPort, boolean secure) {
        this.port = customPort;
        this.SSLEnabled = secure;
    }

    public void start() {
        try {
            this.init();

            this.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() throws Exception {
        usersConnected = new UsersConnections();
        serverSocket = new ServerSocket(this.port);
    }

    public void run() {
        try {
            while (true) {
                System.err.println("Attente de connexion... (" + usersConnected.getUsersCount() + " users currently connected)");
                Socket socket = serverSocket.accept();
                System.err.println("Connexion établie");

                POP3Context context = POP3Context.createContext(socket);
                if (context != null) {
                    usersConnected.addUser(context);
                    context.addObserver(usersConnected);
                    // Lance le nouveau Thread
                    new Thread(() -> {
                        try {
                            context.init(); // Gère création du context et l'envoi de la première réponse
                            context.run();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else {
                    System.out.println("Error: L'utilisateur na pas pu se connecter");
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
