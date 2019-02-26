package ipcpop3;

public class Authorization2State extends POP3State {
    public Authorization2State(POP3Context context) {
        super(context);
    }

    public void apop() {
        System.out.println("APOP Authorization2");
    }

    public void stat() {
        System.out.println("STAT Authorization2");
    }

    public void retr() {
        System.out.println("RETR Authorization2");
    }

    public void quit() {
        System.out.println("QUIT Authorization2");
    }
}
