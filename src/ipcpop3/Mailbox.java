package ipcpop3;

import ipcpop3.Utils.POP3Utils;
import ipcpop3.Utils.StreamUtil;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mailbox {
    private List<Mail> mails;
    private File mailbox;
    private int mailboxSize;

    public Mailbox(String mailboxPath) throws FileNotFoundException {
        this.mailbox = new File(mailboxPath);

        // Charger tous les mails dans la liste
        mails = new ArrayList<>();
        this.loadMails();
    }

    private void loadMails() {
        String from = "";
        String to = "";
        String subject = "";
        Date date = null;
        String messageId = "";
        String body = "";

        String data = "";

        InputStream in = null;
        try {
            in = new FileInputStream(mailbox);
            this.mailboxSize = in.available();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (this.mailboxSize > 0) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                while(true) {
                    data = reader.readLine();
                    if(data == null) {
                        break;
                    }

                    String[] headerValue = getHeader(data);
                    switch (headerValue[0]) {
                        case "From":
                            from = headerValue[1];
                            break;
                        case "To":
                            to = headerValue[1];
                            break;
                        case "Subject":
                            subject = headerValue[1];
                            break;
                        case "Date":
                            date = POP3Utils.DATE_FORMATTER.parse(headerValue[1]);
                            break;
                        case "Message-ID":
                            messageId = headerValue[1];
                            break;
                        default:
                            if (data.equals(".")) {
                                mails.add(new Mail(from, to, subject, date, messageId, body));
                                body = "";
                            }
                            else {
                                body += data;
                            }
                    }
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void write() {
        try (OutputStream out = new FileOutputStream(mailbox)) {
            StreamUtil.write(out, "");
            for (Mail mail : mails) {
                if (!mail.getState().equals(MailStateEnum.DELETED)) {
                    out.write(mail.toString().getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {

    }

    public int getMailCount() {
        return mails.size();
    }

    public int getMailboxSize() {
        return this.mailboxSize;
    }

    /**
     * @param mailNumber le numéro du mail à récupérer. Ce numéro commence à 1
     * @return
     */
    public Mail getMail(int mailNumber) {
        return mails.get(mailNumber - 1);
    }

    /**
     * Récupère tout ce qui suit le nom du header (même si ça contient des ':')
     *
     * @param data
     * @return
     */
    private String[] getHeader(String data) {
        return data.split(": ", 2);
    }

    // ================== STATIC ========================
    public static Mailbox open(String mailboxName) {
        String mailboxPath = POP3Utils.MAILBOX_PATH + mailboxName + POP3Utils.MAILBOX_EXTENSION;

        try {
            return new Mailbox(mailboxPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
