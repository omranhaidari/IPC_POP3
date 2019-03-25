package ipcpop3;

import java.util.Date;

public class Mail {
    private String from;
    private String to;
    private String subject;
    private Date date;
    private String messageID;
    private String body; // Le corps du mail
    private MailStateEnum state;

    public Mail(String from, String to, String subject, Date date, String messageID, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.date = date;
        this.messageID = messageID;
        this.body = body;
        this.state = MailStateEnum.ACTIVE; // Fixme
    }

    @Override
    public String toString() {
        return "From: " + from + '\n' +
                "To: " + to + '\n' +
                "Subject: " + subject + '\n' +
                "Date: " + date + '\n' +
                "MessageID: " + messageID + '\n' +
                body + '\n' +
                '.' + '\n';
    }

    public MailStateEnum getState() {
        return state;
    }
}
