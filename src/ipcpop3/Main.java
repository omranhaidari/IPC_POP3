package ipcpop3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8025);

            while(true) {
                System.out.println("Attente de connexion...");
                Socket socket = serverSocket.accept();
                System.out.println("Connexion établie");

                ((Runnable) () -> {
                    OutputStream out = null;
                    try {
                        out = socket.getOutputStream();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        POP3Context context = new POP3Context(in, out);
                        context.setState(new Authorization1State(context));
                        context.run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
