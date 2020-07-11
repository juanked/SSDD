// Implementación de la interfaz de servidor que define los métodos remotos
// para iniciar la carga y descarga de ficheros
package afs;

import java.io.FileNotFoundException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ViceImpl extends UnicastRemoteObject implements Vice {

  //private LockManager lockManager;
  //private Map<String, Set<VenusCB>> dowloadedFiles;

  public ViceImpl() throws RemoteException {
    //this.lockManager = new LockManager();
    //this.dowloadedFiles = new HashMap<>();
  }

    public ViceReader download(String fileName) throws RemoteException, FileNotFoundException {
  /*  this.bind(fileName).readLock().lock();
    // System.out.println("Bloqueo lectura.");
    if (dowloadedFiles.containsKey(fileName)) {
      dowloadedFiles.get(fileName).add(venusCB);
    } else {
      Set<VenusCB> clients = new HashSet<>();
      clients.add(venusCB);
      dowloadedFiles.put(fileName, clients);
    } */
    ViceReaderImpl lector = new ViceReaderImpl(fileName);
    return lector;
  }

    public ViceWriter upload(String fileName) throws RemoteException, FileNotFoundException {
    /*this.bind(fileName).writeLock().lock();
    
    // System.out.println("Bloqueo escritura.");
    if (dowloadedFiles.containsKey(fileName)) {
      for (VenusCB client : dowloadedFiles.get(fileName)) {
        if (!client.equals(venusCB)) {
          client.invalidate(fileName);
        }
      }
    } else {
      Set<VenusCB> clients = new HashSet<>();
      clients.add(venusCB);
      dowloadedFiles.put(fileName, clients);
    }*/
    ViceWriterImpl escritor = new ViceWriterImpl(fileName);
    return escritor;
  }
/*
  public synchronized ReentrantReadWriteLock bind(String fileName) throws RemoteException {
    return this.lockManager.bind(fileName);
  }

  public synchronized void unbind(String fileName) throws RemoteException {
    this.lockManager.unbind(fileName);
 }*/
}
