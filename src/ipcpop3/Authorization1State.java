package ipcpop3;

import java.io.*;
import java.nio.channels.FileLock;

public class Authorization1State extends POP3State {
    public Authorization1State(POP3Context context) {
        super(context);
    }

    public void apop() {
        System.out.println("APOP Authorization1");

        String user = "user";
        String pass = "pass";

        try {
            FileInputStream in = new FileInputStream(user + ".mail");
            FileOutputStream out = new FileOutputStream(user + ".mail");
            FileLock fli = in.getChannel().lock();
            FileLock flo = out.getChannel().lock();

            Mailbox mailbox = new Mailbox(in, fli, out, flo);
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

    public void retr(int messageNumber) {
        System.out.println("RETR Authorization1");
    }

    public void quit() {
        System.out.println("QUIT Authorization1");
        context.setRunning(false);
    }
}
