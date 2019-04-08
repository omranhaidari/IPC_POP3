package ipcpop3;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SecureServer extends Server {

    public SecureServer() {
        this(8043);
    }

    public SecureServer(int customPort) {
        super(customPort, true, "POP3 SSL Server");
    }

    @Override
    public void init() throws Exception {
        this.usersConnected = new UsersConnections(this.serverName);

        ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
        SSLServerSocket sslServerSocket = (SSLServerSocket) factory.createServerSocket(this.port);

        List<String> cipherSuites = Arrays.asList(sslServerSocket.getSupportedCipherSuites());
        String[] anonCipherSuites = cipherSuites.stream().filter(cipherSuite -> cipherSuite.contains("_anon_")).toArray(String[]::new);
        sslServerSocket.setEnabledCipherSuites(anonCipherSuites);

        this.serverSocket = sslServerSocket;
    }
}
