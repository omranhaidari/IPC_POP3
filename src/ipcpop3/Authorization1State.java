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
                        StreamUtil.writeLine(this.context.getOutputStream(), "+OK ");
                        context.setState(new TransactionState(context));
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        StreamUtil.writeLine(this.context.getOutputStream(), "+ERR permission denied");
    }

    public void stat(String[] parameters) throws IOException {
        System.out.println("STAT Authorization1");
    }

    public void retr(String[] parameters) throws IOException {
        System.out.println("RETR Authorization1");
    }

    public void quit(String[] parameters) throws IOException {
        System.out.println("QUIT Authorization1");
        context.setRunning(false);
    }
}
