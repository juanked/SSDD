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
    private Vice vice;
    private File file;

    public ViceWriterImpl(final String fileName) throws RemoteException, FileNotFoundException {
        //this.vice = vice;
        String localizacion = AFSDir + fileName;
        this.file = new File(localizacion);
        escritor = new RandomAccessFile(localizacion, "rw");
    }

    public void write(final byte[] b) throws IOException {
        escritor.write(b);
    }

    public void close() throws RemoteException, IOException {
        escritor.close();
        //this.vice.unbind(this.file.getName());
        //this.vice.bind(this.file.getName()).writeLock().unlock();
    }

    public void changeLength(long l) throws RemoteException, IOException {
        this.escritor.setLength(l);
    }  
}
