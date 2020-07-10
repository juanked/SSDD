// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la carga de un fichero
package afs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.*;
import java.rmi.server.*;

public class ViceWriterImpl extends UnicastRemoteObject implements ViceWriter {
    private static final String AFSDir = "AFSDir/";
    private RandomAccessFile escritor;

    public ViceWriterImpl(final String fileName /* añada los parámetros que requiera */) throws RemoteException {
        try {
            String localizacion = AFSDir + fileName;
            escritor = new RandomAccessFile(localizacion, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void write(final byte[] b) throws RemoteException {
        try {
            escritor.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close() throws RemoteException {
        try {
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}       

