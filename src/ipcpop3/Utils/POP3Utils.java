package ipcpop3.Utils;

import java.text.SimpleDateFormat;

public class POP3Utils {

    public static final String HOST = "localhost";
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("EEEE d MMMM yyyy, HH:mm:ss zzz");
    public static final String MAILBOX_EXTENSION = "";
    public static final String MAILBOX_PATH = "messages/";

    public static String createUniqueTimestamp() {
        return "<" + Thread.currentThread().getId() + "." + System.currentTimeMillis() + "@" + HOST + ">";
    }
}
