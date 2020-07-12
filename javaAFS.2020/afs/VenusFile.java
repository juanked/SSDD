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
    private RandomAccessFile rAccessFile;
    private boolean Actualizado;
    private String directorio;
    private File fileTMP;

    public VenusFile(Venus venus, String fileName, String mode)
            throws RemoteException, IOException, FileNotFoundException {

        this.venus = venus;
        this.fileTMP = new File(cacheDir + fileName);
        ViceReader lector = this.venus.getVice().download(fileName, venus.getrefCB());
        
        if (!fileTMP.exists()) {
            this.rAccessFile = new RandomAccessFile(this.fileTMP, "rw");
            byte b[];
            while ((b = lector.read(venus.getBlocksize())) != null) {
                rAccessFile.write(b);
            } 
            this.rAccessFile.close();
        }
        this.rAccessFile = new RandomAccessFile(cacheDir + fileName, mode);        
        lector.close();
    }

    public int read(byte[] b) throws RemoteException, IOException {
        return rAccessFile.read(b);
    }

    public void write(byte[] b) throws RemoteException, IOException {
        rAccessFile.write(b);
        Actualizado = true;
    }

    public void seek(long p) throws RemoteException, IOException {
        rAccessFile.seek(p);
    }

    public void setLength(long l) throws RemoteException, IOException {
        rAccessFile.setLength(l);
        Actualizado = true;
    }

    public void close() throws RemoteException, IOException {
        if (Actualizado) {

            ViceWriter escritor = this.venus.getVice().upload(this.fileTMP.getName(), this.venus.getrefCB());
            long posicion = rAccessFile.getFilePointer();
            long size = rAccessFile.length();
            escritor.changeLength(size);
            this.rAccessFile.seek(0);
            byte b[];

            while (posicion + venus.getBlocksize() <= size) {
                b = new byte[venus.getBlocksize()];
                this.rAccessFile.read(b);
                escritor.write(b);
                posicion = rAccessFile.getFilePointer();
            }

            if (size - posicion > 0) {
                b = new byte[(int) (size - posicion)];
                this.rAccessFile.read(b);
                escritor.write(b);
            }
            escritor.close();
        }
        rAccessFile.close();
    }
}
