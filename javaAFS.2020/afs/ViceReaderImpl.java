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
        if(tam < 0) return null; 
        try {
            byte[] b = new byte[tam];            
            int tamaNo = lector.read(b);
            if (tamaNo == -1) return null;
            if (tamaNo < tam){
                byte[] new_b = new byte[tamaNo];
                for (int i = 0; i < new_b.length; i++) {
                    new_b[i] = b[i];  
                }
                return new_b;
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
