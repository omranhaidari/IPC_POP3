package ipcpop3;

public class TransactionState extends POP3State {
    public TransactionState(POP3Context context) {
        super(context);
    }

    public void apop() {
        System.out.println("APOP Transaction");
    }

    public void stat() {
        System.out.println("STAT Transaction");
        // Stat
    }

    public void retr() {
        System.out.println("RETR Transaction");
    }

    public void quit() {
        System.out.println("QUIT Transaction");
    }
}
