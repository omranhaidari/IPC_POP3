package ipcpop3;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;

public class StreamUtil {

    static public String readLine(Reader in) throws IOException {
        String line = "";
        char data;
        boolean crReceived = false;
        do
        {
            data = (char) in.read();
            line += data;
            if(crReceived && data != '\n') {
                crReceived = false;
            }
            if(data == '\r') {
                crReceived = true;
            }
        }
        while(crReceived && data != '\n');

        return line;
    }

    static public void write(OutputStream out, String data) throws IOException {
        out.write(data.getBytes());
        out.flush();
    }

    static public void writeLine(OutputStream out, String line) throws IOException {
        line += "\r\n";
        write(out, line);
    }

    static public void writeText(OutputStream out, String text) {
        String[] lines = text.split("\n");
    }
}
