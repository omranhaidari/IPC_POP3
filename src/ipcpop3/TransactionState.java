package ipcpop3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TransactionState extends POP3State {
    public TransactionState(POP3Context context) {
        super(context);
    }

    public void apop() {
        System.out.println("APOP Transaction");
    }

    public void stat() {
        System.out.println("STAT Transaction");

        int nbMessage = this.context.getMailbox().getMailCount(); // nombre de message dans le mail
        int sizeMessage = this.context.getMailbox().getMailboxSize(); // taille du fichier en octets
        System.out.println("+OK" + " " + nbMessage + " " + sizeMessage);

    }

    public void retr() {
        System.out.println("RETR Transaction");
    }

    public void quit() {
        System.out.println("QUIT Transaction");
    }
}
