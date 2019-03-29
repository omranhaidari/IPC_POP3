package ipcpop3;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

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

        this.serverSocket = factory.createServerSocket(this.port);
    }
}
