package ipcpop3;

import java.io.IOException;

public class Authorization2State extends POP3State {
    public Authorization2State(POP3Context context) {
        super(context);
    }

    public void apop(String username, String password) throws IOException {
        System.out.println("APOP Authorization2");
    }

    public void stat() throws IOException {
        System.out.println("STAT Authorization2");
    }

    public void retr(String[] parameters) throws IOException {
        System.out.println("RETR Authorization2");
    }

    public void quit() throws IOException {
        System.out.println("QUIT Authorization2");
    }
}
