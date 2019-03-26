package ipcpop3;

import java.io.IOException;

abstract public class POP3State {
    public POP3State(POP3Context context) {
        this.context = context;
    }

    // Les IOException renvoyées concernent les erreurs lors de l'envoi d'une réponse à l'utilisateur
    abstract public void apop(String[] parameters) throws IOException;
    abstract public void stat(String[] parameters) throws IOException;
    abstract public void retr(String[] parameters) throws IOException;
    abstract public void quit(String[] parameters) throws IOException;

    protected POP3Context context;
}
