package ipcpop3;

public class Authorization1State extends POP3State {
    public Authorization1State(POP3Context context) {
        super(context);
    }

    public void apop() {
        System.out.println("APOP Authorization1");
        context.setState(new TransactionState(context));
    }

    public void stat() {
        System.out.println("STAT Authorization1");
    }

    public void retr() {
        System.out.println("RETR Authorization1");
    }

    public void quit() {
        System.out.println("QUIT Authorization1");
        // Quitter
    }
}
