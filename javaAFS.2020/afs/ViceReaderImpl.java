// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {

    private static final String AFSDir = "AFSDir/";
    private File fileTMP;
    private Vice vice;
    private RandomAccessFile lector;

    /* añada los parámetros que requiera */
    public ViceReaderImpl(String fileName) throws FileNotFoundException, RemoteException {
        //this.vice= vice;
        this.fileTMP = new File(AFSDir + fileName);
        this.lector = new RandomAccessFile(this.fileTMP, "r");
    }

    public byte[] read(int tam) throws RemoteException, IOException {
        byte[] b = new byte[tam];
        int result = lector.read(b);

        if (result == -1) {
            return null;
        }
        if (result < tam) {
            byte[] new_b = new byte[result];
            for (int i = 0; i < result; i++) {
                new_b[i] = b[i];
            }
            return new_b;
        }
        return b;
    }

    public void close() throws RemoteException, IOException {
        this.lector.close();
        //this.vice.bind(this.fileTMP.getName()).readLock().unlock();
        //this.vice.unbind(this.fileTMP.getName());
    }
}
