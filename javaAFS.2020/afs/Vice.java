// Interfaz de servidor que define los métodos remotos para iniciar
// la carga y descarga de ficheros
package afs;

import java.io.FileNotFoundException;
import java.rmi.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public interface Vice extends Remote {
      public ViceReader download(String fileName,String mode, VenusCB refCB) throws RemoteException, FileNotFoundException;

      public ViceWriter upload(String fileName, VenusCB refCB) throws RemoteException, FileNotFoundException;

      public ReentrantReadWriteLock bind(String fileName) throws RemoteException;

      public void unbind(String fileName) throws RemoteException;
}
