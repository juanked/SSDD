// Clase de cliente que define la interfaz a las aplicaciones.
// Proporciona la misma API que RandomAccessFile.
package afs;

import java.rmi.*;
import java.io.*;

public class VenusFile {

    private Vice vice;
    private Venus venus;
    private ViceReader viceReader;
    private ViceWriter viceWriter;
    private static final String cacheDir = "Cache/";
    private RandomAccessFile lector;
    private boolean Actualizado;
    private String directorio;

    public VenusFile(Venus venus, String fileName, String mode) 
        throws RemoteException, IOException, FileNotFoundException {
        
        directorio = cacheDir + fileName;
        File tmpDir = new File(directorio);
        this.Actualizado = false;
        this.venus = venus;

        try {
            if(!tmpDir.exists()) {
                byte b [];
                vice = venus.getVice();
                viceReader = vice.download(directorio);
                lector = new RandomAccessFile(directorio, "rw");
                while ((b = viceReader.read(venus.getBlocksize())) != null) {
                    lector.write(b);
                }            
                viceReader.close();
                lector.close();
            }        
            // Se crea el RandomAccesFile con el modo que se especifica en el constructor
            lector = new RandomAccessFile(directorio, mode);
        } catch (FileNotFoundException e) {
            System.out.println("fichero no encontrado");
            lector = new RandomAccessFile(cacheDir + "fail", "rw");
        }        
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
    }

    public void close() throws RemoteException, IOException {
        if(Actualizado){
            lector.seek(0);
            byte [] b = new byte [this.venus.getBlocksize()];
            vice = venus.getVice();
            viceWriter = vice.upload(directorio);
            int aux;
            while((aux = lector.read(b)) > 0){
                if(this.venus.getBlocksize() > aux){
                    byte [] new_b = new byte [aux];
                    for (int i = 0; i < aux; i++) {
                        new_b [i] = b [i];
                    }
                    viceWriter.write(new_b);
                }
                else{
                    viceWriter.write(b);
                }
            }
        }
        lector.close();
    }
}
