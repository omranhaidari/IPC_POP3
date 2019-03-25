package ipcpop3;

import java.io.IOException;

abstract public class POP3State {
    public POP3State(POP3Context context) {
        this.context = context;
    }

    // Les IOException renvoyées concernent les erreurs lors de l'envoi d'une réponse à l'utilisateur
    abstract public void apop(String username, String password) throws IOException;
    abstract public void stat() throws IOException;
    abstract public void retr(String messageNumber) throws IOException;
    abstract public void quit() throws IOException;

    protected POP3Context context;
}
