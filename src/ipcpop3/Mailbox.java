package ipcpop3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mailbox {
    private List<Mail> mails;
    private int mailboxSize;

    public Mailbox(String mailboxName) {
        // TODO Ouvrir le fichier de la boîte mail (avec gestion des erreurs)


        // TODO Charger tous les mails dans la liste
        mails = new ArrayList<>();
        
    }

    public int getMailCount() {
        return mails.size();
    }

    public int getMailboxSize() {
        return this.mailboxSize;
    }
}
