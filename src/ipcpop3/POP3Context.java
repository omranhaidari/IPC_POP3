package ipcpop3;

import java.io.IOException;
import java.net.ServerSocket;

public class POP3Context {
    private POP3State state;
    private ServerSocket serverSocket;

    public POP3Context() {
        try {
            serverSocket = new ServerSocket(8025);
            System.out.println("Attente de connexion...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setState(POP3State state) {
        this.state = state;
    }

    public void apop() {
        state.apop();
    }

    public void stat() {
        state.stat();
    }

    public void retr() {
        state.retr();
    }

    public void quit() {
        state.quit();
    }
}
