package ipcpop3;

import ipcpop3.Utils.POP3Utils;
import ipcpop3.Utils.StreamUtil;

import java.io.*;
import java.nio.channels.FileLock;
import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Mailbox {
    private List<Mail> mails;
    private InputStream in;
    private OutputStream out;
    private FileLock fli;
    private FileLock flo;
    private int mailboxSize;

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

        this.readMessages();
    }

    private void readMessages() {
        String from = "";
        String to = "";
        String subject = "";
        Date date = null;
        String messageId = "";
        String body = "";

        String data = "";

        Reader reader = new InputStreamReader(in);
        while(true) {
            try {
                data = StreamUtil.readLine(reader);
                String[] headerValue = data.split(": ");
                switch(headerValue[0]) {
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
                        if(data.equals(".")) {
                            mails.add(new Mail(from, to, subject, date, messageId, body));
                        }
                        body += data;
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public int getMailCount() {
        return mails.size();
    }

    public int getMailboxSize() {
        return this.mailboxSize;
    }

    public Mail getMail(int mailNumber) {
        return mails.get(mailNumber);
    }
}
