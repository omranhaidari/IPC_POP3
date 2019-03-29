package ipcpop3;

import ipcpop3.Utils.Observer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

    protected int port;
    protected boolean SSLEnabled = false;
    protected String serverName;

    protected ServerSocket serverSocket;
    protected UsersConnections usersConnected;

    public Server() {
        this(8025);
    }

    public Server(int customPort) {
        this(customPort, false, "POP3 Server");
    }

    public Server(int customPort, boolean secure, String serverName) {
        this.port = customPort;
        this.SSLEnabled = secure;
        this.serverName = serverName;
    }

    public void init() throws Exception {
        usersConnected = new UsersConnections(this.serverName);
        serverSocket = new ServerSocket(this.port);
    }

    public void run() {
        try {
            this.init();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            while (true) {
                System.err.println("["+ serverName +"]: Attente de connexion... (" + usersConnected.getUsersCount() + " users currently connected)");
                Socket socket = serverSocket.accept();
                System.err.println("["+ serverName +"]: Connexion établie");

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
                    // TODO Log l'erreur (user n'a pas pu se connecter)
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class UsersConnections implements Observer {

        List<POP3Context> usersConnected = new ArrayList<>();
        String serverName;

        public UsersConnections(String serverName) {
            this.serverName = serverName;
        }

        @Override
        public void remove(Object object) {
            if(object instanceof POP3Context) {
                usersConnected.remove(object);
            }

            System.err.println("[" + serverName + "]: Connection closed.");
            System.err.println("[" + serverName + "]: " + usersConnected.size() + " user(s) still connected.");
        }

        public void addUser(POP3Context user) {
            usersConnected.add(user);
        }

        public int getUsersCount() {
            return usersConnected.size();
        }
    }
}
