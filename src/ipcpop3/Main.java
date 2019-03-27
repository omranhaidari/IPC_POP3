package ipcpop3;

import ipcpop3.Utils.Observer;
import ipcpop3.Utils.POP3Utils;

import javax.net.ssl.SSLServerSocket;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        new Server().start();
    }
}
