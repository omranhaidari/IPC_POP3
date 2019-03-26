package ipcpop3;

import java.io.IOException;

public class Authorization2State extends POP3State {
    public Authorization2State(POP3Context context) {
        super(context);
    }

    public void apop(String[] parameters) throws IOException {
        System.out.println("APOP Authorization2");
    }

    public void stat(String[] parameters) throws IOException {
        System.out.println("STAT Authorization2");
    }

    public void retr(String[] parameters) throws IOException {
        System.out.println("RETR Authorization2");
    }

    public void quit(String[] parameters) throws IOException {
        System.out.println("QUIT Authorization2");
    }
}
