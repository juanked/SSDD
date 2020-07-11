// Clase de cliente que define la interfaz a las aplicaciones.
// Proporciona la misma API que RandomAccessFile.
package afs;

import java.rmi.*;
import java.io.*;

public class VenusFile {

    private Vice vice;
    private Venus venus;
    private ViceWriter viceWriter;
    private static final String cacheDir = "Cache/";
    private RandomAccessFile lector;
    private boolean Actualizado;
    private String directorio;
    private File fileTMP;

    public VenusFile(Venus venus, String fileName, String mode)
            throws RemoteException, IOException, FileNotFoundException {

        this.venus = venus;
        this.fileTMP = new File(cacheDir + fileName);
        ViceReader viceReader = this.venus.getVice().download(fileName);
        
        if (!fileTMP.exists()) {
            this.lector = new RandomAccessFile(this.fileTMP, "rw");
            byte b[];
            while ((b = viceReader.read(venus.getBlocksize())) != null) {
                lector.write(b);
            } 
            this.lector.close();
        }
        this.lector = new RandomAccessFile(cacheDir + fileName, mode);        
        viceReader.close();
    }

    public int read(byte[] b) throws RemoteException, IOException {
        return lector.read(b);
    }

    public void write(byte[] b) throws RemoteException, IOException {
        lector.write(b);
        Actualizado = true;
    }

    public void seek(long p) throws RemoteException, IOException {
        lector.seek(p);
    }

    public void setLength(long l) throws RemoteException, IOException {
        lector.setLength(l);
        Actualizado = true;
    }

    public void close() throws RemoteException, IOException {
        if (Actualizado) {

            lector.seek(0);
            byte[] b = new byte[this.venus.getBlocksize()];
            viceWriter = this.venus.getVice().upload(directorio);
            long tam = this.lector.length();
            viceWriter.changeLength(tam);
            int aux;

            while ((aux = lector.read(b)) > 0) {
                if (this.venus.getBlocksize() > aux) {
                    byte[] new_b = new byte[aux];
                    for (int i = 0; i < aux; i++) {
                        new_b[i] = b[i];
                    }
                    viceWriter.write(new_b);
                } else {
                    viceWriter.write(b);
                }
            }
            viceWriter.close();
        }
        lector.close();
    }
}
