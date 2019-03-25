package ipcpop3;

abstract public class POP3State {
    public POP3State(POP3Context context) {
        this.context = context;
    }

    abstract public void apop(String username, String password);
    abstract public void stat();
    abstract public void retr(String messageNumber);
    abstract public void quit();

    protected POP3Context context;
}
