// Interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;

import java.rmi.*;
import java.io.*;

public interface ViceReader extends Remote {
    public byte[] read(int tam) throws RemoteException, IOException;
    public void close() throws RemoteException;
    /* añada los métodos remotos que requiera */
    // public ViceReaderImpl(String fileName, char[] modoEjec) throws RemoteException;
}       

