package ipcpop3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

public class POP3Context {
    private final Reader in;
    private final OutputStream out;
    private POP3State state;

    public POP3Context(Reader in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void setState(POP3State state) {
        this.state = state;
    }

    public void run() {
        String request = "";
        /*String data;
        do
        {
            data = in.read();
            request += data;
        }
        while(!data.equals(""));*/

        request = "QUIT";

        switch(request.toLowerCase()) {
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
}
