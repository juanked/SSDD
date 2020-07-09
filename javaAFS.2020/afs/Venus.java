// Clase de cliente que inicia la interacción con el servicio de
// ficheros remotos
package afs;

import java.rmi.*;

public class Venus {
    private String host;
    private int port;
    private int blocksize;

    public Venus() {
        this.host = System.getenv().get("REGISTRY_HOST");
        this.port = Integer.parseInt(System.getenv().get("REGISTRY_PORT"));
        this.blocksize = Integer.parseInt(System.getenv().get("BLOCKSIZE"));
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


}
