package ipcpop3;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

public class Mailbox {
    private List<Mail> mails;
    private InputStream in;
    private OutputStream out;
    private FileLock fli;
    private FileLock flo;

    public Mailbox(String mailboxName) {
        // TODO Ouvrir le fichier de la boîte mail (avec gestion des erreurs)

        // TODO Charger tous les mails dans la liste
        mails = new ArrayList<>();
    }

    public Mailbox(InputStream in, FileLock fli, OutputStream out, FileLock flo) {
        this.in = in;
        this.out = out;
        this.fli = fli;
        this.flo = flo;

        this.mails = new ArrayList<>();
    }
}
