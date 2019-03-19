package ipcpop3.Utils;

import java.text.SimpleDateFormat;

public class POP3Utils {

    public static final String HOST = "localhost";
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("EEEE MMMM d HH:mm:ss zzz yyyy");

    public static String createUniqueTimestamp() {
        return "<" + Thread.currentThread().getId() + "." + System.currentTimeMillis() + "@" + HOST + ">";
    }
}
