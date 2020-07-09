// Clase de cliente que define la interfaz a las aplicaciones.
// Proporciona la misma API que RandomAccessFile.
package afs;

import java.rmi.*;
import java.io.*;

public class VenusFile {
    public static final String cacheDir = "Cache/";
    private RandomAccessFile lector;

    public VenusFile(Venus venus, String fileName, String mode)
            throws RemoteException, IOException, FileNotFoundException {
        try {
            String directorio = cacheDir + fileName;
            this.lector = new RandomAccessFile(directorio, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int read(byte[] b) throws RemoteException, IOException {
        return 0;
    }

    public void write(byte[] b) throws RemoteException, IOException {
        return;
    }

    public void seek(long p) throws RemoteException, IOException {
        return;
    }

    public void setLength(long l) throws RemoteException, IOException {
        return;
    }

    public void close() throws RemoteException, IOException {
        return;
    }
}
