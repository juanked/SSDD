// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la carga de un fichero
package afs;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.*;
import java.rmi.server.*;

public class ViceWriterImpl extends UnicastRemoteObject implements ViceWriter {

    private static final String AFSDir = "AFSDir/";
    private RandomAccessFile escritor;
    private Vice viceRef;
    private File fileTMP;

    public ViceWriterImpl(final String fileName, String mode, Vice viceRef) throws RemoteException, FileNotFoundException {
        this.viceRef = viceRef;
        String localizacion = AFSDir + fileName;
        this.fileTMP = new File(localizacion);
        this.escritor = new RandomAccessFile(localizacion, mode);
    }

    public void write(final byte[] b) throws IOException {
        this.escritor.write(b);
    }

    public void close() throws RemoteException, IOException {
        this.escritor.close();
        this.viceRef.unbind(this.fileTMP.getName());
        this.viceRef.bind(this.fileTMP.getName()).writeLock().unlock();
    }

    public void changeLength(long l) throws RemoteException, IOException {
        this.escritor.setLength(l);
    }  
}
