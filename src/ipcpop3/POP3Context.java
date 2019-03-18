package ipcpop3;

import ipcpop3.Utils.Observer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class POP3Context {
    private final Socket socket;
    private final Reader in;
    private final InputStream inSocket;
    private final OutputStream out;
    private final List<Observer> observers = new ArrayList<>();

    private POP3State state;
    private boolean running = true;
    private Mailbox mailbox;

    public POP3Context(Socket socket, Reader in, OutputStream out, InputStream inSocket) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.inSocket = inSocket;
    }

    public void setState(POP3State state) {
        this.state = state;
    }

    public void init() throws IOException {
        StreamUtil.writeLine(out, "+OK POP3 server ready");
    }

    public void run() {
        String request;
        while(running) {
            request = "";
            try {
                if(inSocket.available() > 0) {
                    request = StreamUtil.readLine(in);
                    request.substring(0, request.length() - 1); // FIXME Pourquoi ?
                    System.out.println("User [" + this.hashCode() + "] sent : '" + request.trim() + "'");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (request.toLowerCase()) {
                case "apop":
                    apop();
                    break;
                case "stat":
                    stat();
                    break;
                case "retr":
                    retr();
                    break;
                case "quit":
                    quit();
                    break;
            }

            if(socket.isClosed()) {
                this.setRunning(false);
                this.notifyRemoveAll();
                System.err.println("Connection closed"); // FIXME Ne Fonctionne pas
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private String readLine() {
        return "";
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

    public static POP3Context createContext(Socket socket) {
        OutputStream out = null;
        POP3Context context;
        try {
            out = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            InputStream inSocket = socket.getInputStream();
            context = new POP3Context(socket, in, out, inSocket);
            context.setState(new Authorization1State(context));

            return context;
        } catch (IOException e) {
            e.printStackTrace();
            // TODO Gérer déconnexion impromptue
        }
        return null;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyRemoveAll() {
        observers.forEach(observer -> observer.remove(this));
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }
}
