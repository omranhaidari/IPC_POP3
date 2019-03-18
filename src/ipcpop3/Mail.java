package ipcpop3;

import java.util.Date;

public class Mail {
    private String from;
    private String to;
    private String subject;
    private Date date;
    private String messageID;
    private String body; // Le corps du mail
    private State state;

    @Override
    public String toString() {
        // TODO : check with rfc
        return "Mail{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", date=" + date +
                ", messageID='" + messageID + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
