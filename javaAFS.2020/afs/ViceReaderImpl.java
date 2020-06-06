// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {
    private static final String AFSDir = "AFSDir/";
    private RandomAccessFile lector;

    public ViceReaderImpl(String fileName, String modoEjec) throws RemoteException {
        try {
            String directorio = AFSDir + fileName;
            this.lector = new RandomAccessFile(directorio, modoEjec);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public byte[] read(int tam) throws RemoteException {
        try {
            byte[] b = new byte[tam];
            for (int i = 0; i < b.length; i++) {
                int leido = lector.read(b);
                
            }
            
        } catch (IOException e) {
            //TODO: handle exception
        }

        return b;
    }

    public void close() throws RemoteException {
        return;
    }
}
