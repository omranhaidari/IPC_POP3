package ipcpop3;

import ipcpop3.Utils.POP3Utils;
import ipcpop3.Utils.StreamUtil;

import java.io.*;
import java.nio.channels.FileLock;

public class Authorization1State extends POP3State {
    public Authorization1State(POP3Context context) {
        super(context);
    }

    public void apop(String[] parameters) throws IOException {
        System.out.println("APOP Authorization1");

        String user = parameters[1];
        String pass = parameters[2];
//        File mailboxFile = new File(mailboxPath);

        if(true) { //mailboxFile.exists()) { FIXME Comment tester l'existence ?
//            mailboxFile = null;
            if(true) { // FIXME Si le mdp est correct
                try {
                    Mailbox mailbox = Mailbox.open(user);
                    if(mailbox == null) {
                        // Alors la mailbox est lockée
                    } else {
                        context.setMailbox(mailbox);
                        this.context.answer(
                                "+OK mailbox has " + mailbox.getMailCount() + " message(s)" +
                                    " (" + mailbox.getMailboxSize() + " bytes)");
                        context.setState(new TransactionState(context));
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        this.context.answer("-ERR permission denied");
    }

    public void stat(String[] parameters) throws IOException {
        System.out.println("STAT Authorization1");

        this.context.answer("-ERR login first");
    }

    public void retr(String[] parameters) throws IOException {
        System.out.println("RETR Authorization1");

        this.context.answer("-ERR login first");
    }

    public void quit(String[] parameters) throws IOException {
        System.out.println("QUIT Authorization1");
        context.setRunning(false);
        this.context.answer("+OK POP3 server signing off");
    }
}
