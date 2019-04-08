package ipcpop3.Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;

public class StreamUtil {

    static public String readLine(Reader in) throws IOException, TCPException {
        String line = "";
        char data;
        int returnData;
        boolean crReceived = false;
        do
        {
            returnData = in.read();
            data = (char) returnData;
            if(returnData == -1) {
                throw new TCPException("");
            }
            line += data;
            if(crReceived && data != '\n') {
                crReceived = false;
            }
            if(data == '\r') {
                crReceived = true;
            }
            if(data != '\n' && crReceived) {
                break;
            }
        }
        while(true);

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
}
