package ipcpop3;

public class Authorization1State extends POP3State {
    public Authorization1State(POP3Context context) {
        super(context);
    }

    public void apop() {

        context.setState(new TransactionState(context));
    }

    public void stat() {

    }

    public void retr() {

    }

    public void quit() {
        // Quitter
    }
}
