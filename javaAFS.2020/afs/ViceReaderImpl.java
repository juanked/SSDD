// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {

    private static final String AFSDir = "AFSDir/";
    private File fileTMP;
    private Vice viceRef;
    private RandomAccessFile lector;

    /* añada los parámetros que requiera */
    public ViceReaderImpl(String fileName, Vice viceRef) throws FileNotFoundException, RemoteException {
        this.viceRef= viceRef;
        this.fileTMP = new File(AFSDir + fileName);
        this.lector = new RandomAccessFile(AFSDir + fileName, "r");
    }

    public byte[] read(int tam) throws RemoteException, IOException {
        long posicion = this.lector.getFilePointer();
        long size = this.lector.length();
        byte b[] = null;

        long cmp = size - posicion;
        if (cmp <= 0)  return b;
        
        cmp = posicion + tam;
        if (cmp <= size) {
            b = new byte[tam];
        } else {
            int newTam = (int) (size - posicion);
            b = new byte[newTam];
        }
        this.lector.read(b);
        return b;
    }

    public void close() throws RemoteException, IOException {
        this.lector.close();
        this.viceRef.bind(this.fileTMP.getName()).readLock().unlock();
        this.viceRef.unbind(this.fileTMP.getName());
    }
}
