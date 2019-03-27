package ipcpop3;

import ipcpop3.Utils.POP3Utils;

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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("From: ").append(from).append('\r').append('\n');
        stringBuilder.append("To: ").append(to).append('\r').append('\n');
        stringBuilder.append("Subject: ").append(subject).append('\r').append('\n');
        stringBuilder.append("Date: ").append(POP3Utils.DATE_FORMATTER.format(date)).append('\r').append('\n');
        //stringBuilder.append("Message-ID: ").append(messageID).append('\r').append('\n');
        stringBuilder.append('\r').append('\n');
        stringBuilder.append(body).append('\r').append('\n');
        stringBuilder.append('.').append('\r').append('\n');

        return stringBuilder.toString();
    }

    public MailStateEnum getState() {
        return state;
    }
}
