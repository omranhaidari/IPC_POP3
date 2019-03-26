package ipcpop3;

import ipcpop3.Utils.Observer;
import ipcpop3.Utils.POP3Utils;
import ipcpop3.Utils.StreamUtil;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class POP3Context {
    private final Socket socket;
    private final Reader in;
    private final InputStream inSocket;
    private final OutputStream out;
    private final List<Observer> observers = new ArrayList<>();

    private POP3State state;
    private boolean running = true;
    private Mailbox mailbox;
    private String uniqueTimestamp;

    public POP3Context(Socket socket, Reader in, OutputStream out, InputStream inSocket) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.inSocket = inSocket;
    }

    public void setState(POP3State state) {
        this.state = state;
    }

    public void init() throws IOException {
        this.uniqueTimestamp = POP3Utils.createUniqueTimestamp();
        StreamUtil.writeLine(out, "+OK POP3 server ready " + this.uniqueTimestamp);
    }

    public void run() {
        String request;
        int numeroMessage = -1;
        String username = null, password = null;
        String[] command;
        while(running) {
            request = "";
            try {
                if(inSocket.available() > 0) {
                    request = StreamUtil.readLine(in);
                    request = request.trim();
                    System.out.println("User [" + this.hashCode() + "] sent : '" + request.trim() + "'");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!"".equals(request)) {
                command = getCommand(request);

                try {
                    switch (command[0].toLowerCase()) {
                        case "apop":
                            apop(command);
                            break;
                        case "stat":
                            stat(command);
                            break;
                        case "retr":
                            retr(command);
                            break;
                        case "quit":
                            quit(command);
                            break;
                        case "help":
                            help();
                            break;
                        default:
                            unknownCommand();
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    setRunning(false);
                }

            }

            if(!isRunning()) { // FIXME Ne Fonctionne pas -> Il faudrait tester si in.read() == -1
                this.notifyAllForRemoval();
                closing();
                System.err.println("Connection closed");
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private void closing() {
        try {
            in.close();
            inSocket.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void answer(String response) throws IOException {
        StreamUtil.writeLine(this.out, response);
    }

    public void answerText(String text) throws IOException {
        StreamUtil.write(this.out, text);
    }

    public void apop(String[] parameters) throws IOException {
        state.apop(parameters);
    }

    public void stat(String[] parameters) throws IOException {
        state.stat(parameters);
    }

    public void retr(String[] parameters) throws IOException {
        state.retr(parameters);
    }

    public void quit(String[] parameters) throws IOException {
        state.quit(parameters);
    }

    public void help() throws IOException {
        String availableCommands = "APOP, STAT, RETR [msg], QUIT"; // FIXME Utiliser POP3Utils.AVAILABLE_COMMANDS
        StreamUtil.writeLine(this.getOutputStream(), "+OK available commands are : " + availableCommands);
    }

    public void unknownCommand() throws IOException {
        StreamUtil.writeLine(this.getOutputStream(), "-ERR unknown command");
    }

    public static POP3Context createContext(Socket socket) {
        OutputStream out = null;
        POP3Context context;
        try {
            out = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            InputStream inSocket = socket.getInputStream();
            context = new POP3Context(socket, in, out, inSocket);
            context.setState(new Authorization1State(context));

            return context;
        } catch (IOException e) {
            e.printStackTrace();
            // TODO Gérer déconnexion impromptue
        }
        return null;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllForRemoval() {
        observers.forEach(observer -> observer.remove(this));
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    private String[] getCommand(String request) {
        return request.split(" ");
    }

    public OutputStream getOutputStream() {
        return this.out;
    }
}
