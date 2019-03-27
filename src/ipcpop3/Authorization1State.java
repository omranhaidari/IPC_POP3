package ipcpop3;

import ipcpop3.Utils.POP3Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authorization1State extends POP3State {
    public Authorization1State(POP3Context context) {
        super(context);
    }

    public void apop(String[] parameters) throws IOException {
        System.out.println("APOP Authorization1");

        String user, pass;

        try {
            user = parameters[1];
            pass = parameters[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            this.context.answer("-ERR permission denied"); // FIXME Renvoyer message plus détaillé ?
            return;
        }

        File passFile = new File(POP3Utils.PASS_PATH + user + POP3Utils.PASS_EXTENSION);

        if (passFile.exists()) {
            BufferedReader in = new BufferedReader(new FileReader(passFile));
            String retrievedPass = in.readLine();
            String checksum;
            try {
                checksum = new String(MessageDigest.getInstance("MD5").digest((this.context.getUniqueTimestamp() + retrievedPass).getBytes()));
            } catch (NoSuchAlgorithmException e) {
                System.err.println("Algorithm MD5 not found");
                checksum = retrievedPass;
            }

            if (pass.equals(checksum) || pass.equals(retrievedPass)) {
                try {
                    Mailbox mailbox = Mailbox.open(user);
                    if (mailbox == null) {
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
