// Clase de cliente que inicia la interacción con el servicio de
// ficheros remotos
package afs;

import java.net.MalformedURLException;
import java.rmi.*;

public class Venus {
    private String host;
    private int port;
    private int blocksize;
    private Vice ref;

    public Venus() throws MalformedURLException, RemoteException, NotBoundException {
        this.host = System.getenv().get("REGISTRY_HOST");
        this.port = Integer.parseInt(System.getenv().get("REGISTRY_PORT"));
        this.blocksize = Integer.parseInt(System.getenv().get("BLOCKSIZE"));
        this.ref = (Vice) Naming.lookup("//"+ this.host + ":" + this.port +"/AFS");
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getBlocksize() {
        return blocksize;
    }

    public Vice getVice(){
        return ref;
    }
}
