// Clase de cliente que define la interfaz a las aplicaciones.
// Proporciona la misma API que RandomAccessFile.
package afs;

import java.rmi.*;
import java.io.*;

public class VenusFile {

    public Vice vice;
    public ViceReader viceReader;
    public static final String cacheDir = "Cache/";
    private RandomAccessFile lector;

    public VenusFile(Venus venus, String fileName, String mode) throws RemoteException, IOException, FileNotFoundException {
        
        String directorio = cacheDir + fileName;
        File tmpDir = new File(directorio);

        if(!tmpDir.exists()) {
            byte b [];
            lector = new RandomAccessFile(directorio, "rw");
            vice = venus.getVice();
            viceReader = vice.download(directorio);
            while ((b = viceReader.read(venus.getBlocksize())) != null) {
                lector.write(b);
            }            
            viceReader.close();
            lector.close();
        }        
        // Se crea el RandomAccesFile con el modo que se especifica en el constructor
        lector = new RandomAccessFile(directorio, mode);
    }

    public int read(byte[] b) throws RemoteException, IOException {
        return lector.read(b);
    }

    public void write(byte[] b) throws RemoteException, IOException {
        lector.write(b);
    }

    public void seek(long p) throws RemoteException, IOException {
        lector.seek(p);
    }

    public void setLength(long l) throws RemoteException, IOException {
        lector.setLength(l);
    }

    public void close() throws RemoteException, IOException {
        lector.close();
    }
}
