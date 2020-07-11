// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {
    private static final String AFSDir = "AFSDir/";
    private RandomAccessFile lector;
    private File file;

    public ViceReaderImpl(String fileName) throws RemoteException, FileNotFoundException {
        String localizacion = AFSDir + fileName;
        this.file = new File(localizacion);
        this.lector = new RandomAccessFile(this.file, "r");
    }

    public byte[] read(int tam) throws IOException, RemoteException {
        
        long pos = this.lector.getFilePointer();
        long len = this.lector.length();

        byte buffer[];

        if (len - pos <= 0) {
            return null;
        }
        if (pos + tam <= len) {
            buffer = new byte[tam];
        } else {
            buffer = new byte[(int) (len - pos)];
        }
        this.lector.read(buffer);
        return buffer;

    }

    public void close() throws RemoteException {
        try {
            lector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
