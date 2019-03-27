package ipcpop3;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

public class SecureServer extends Server {

    public SecureServer() {
        super(8043, true);
    }

    public SecureServer(int customPort) {
        super(customPort, true);
    }

    @Override
    public void init() throws Exception {
        super.init();

        ServerSocketFactory factory = SSLServerSocketFactory.getDefault();

        this.serverSocket = factory.createServerSocket(this.port);
    }
}
