package ipcpop3;

import ipcpop3.Utils.POP3Utils;

import java.io.*;
import java.nio.channels.FileLock;

public class Authorization1State extends POP3State {
    public Authorization1State(POP3Context context) {
        super(context);
    }

    public void apop(String username, String password) {
        System.out.println("APOP Authorization1");

        String user = username;
        String pass = password;
        String mailboxPath = POP3Utils.MAILBOX_PATH + user + POP3Utils.MAILBOX_EXTENSION;

        try {
            FileInputStream in = new FileInputStream(mailboxPath);
            FileOutputStream out = new FileOutputStream(mailboxPath);
//            FileLock fli = in.getChannel().lock(); // FIXME
//            FileLock flo = out.getChannel().lock(); // FIXME

            Mailbox mailbox = new Mailbox(in, out);
            context.setMailbox(mailbox);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        context.setState(new TransactionState(context));
    }

    public void stat() {
        System.out.println("STAT Authorization1");
    }

    public void retr(String messageNumber) {
        System.out.println("RETR Authorization1");
    }

    public void quit() {
        System.out.println("QUIT Authorization1");
        context.setRunning(false);
    }
}
