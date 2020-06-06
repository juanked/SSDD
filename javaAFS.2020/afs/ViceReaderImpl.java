// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {
    private static final String AFSDir = "AFSDir/";
    private RandomAccessFile lector;

    public ViceReaderImpl(String fileName) throws RemoteException {
        try {
            String directorio = AFSDir + fileName;
            this.lector = new RandomAccessFile(directorio, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public byte[] read(int tam) throws RemoteException {
        try {
            byte[] b = new byte[tam];
            int tamaNo;
            tamaNo = lector.read(b);
            for (int i = 0; i < b.length && tamaNo != -1; i++) {
                b[i] = lector.readByte();
            }
            return b;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() throws RemoteException {
        try {
            lector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
