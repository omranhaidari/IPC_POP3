package ipcpop3;

public class Main {

    public static void main(String[] args) {
        new Server().start();

        new SecureServer().start();
    }
}
