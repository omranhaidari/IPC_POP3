package ipcpop3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class POP3Context {
    private final Reader in;
    private final OutputStream out;
    private POP3State state;
    private boolean running = true;

    public POP3Context(Reader in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void setState(POP3State state) {
        this.state = state;
    }

    public void init() throws IOException {
        StreamUtil.writeLine(out, "+OK POP3 server ready");
    }

    public void run() {
        String request = "";
        while(running) {
            try {
                char data;
                do
                {
                    data = (char) in.read();
                    request += data;
                }
                while(data != '\r');
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
            context = new POP3Context(in, out);
            context.setState(new Authorization1State(context));

            return context;
        } catch (IOException e) {
            e.printStackTrace();
            // TODO Gérer déconnexion impromptue
        }
        return null;
    }
}