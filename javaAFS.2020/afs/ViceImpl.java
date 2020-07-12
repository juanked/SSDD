// Implementación de la interfaz de servidor que define los métodos remotos
// para iniciar la carga y descarga de ficheros
package afs;

import java.io.FileNotFoundException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ViceImpl extends UnicastRemoteObject implements Vice {

  private LockManager lock;
  private Map<String, Set<VenusCB>> dowloadedFiles;

  public ViceImpl() throws RemoteException {
    this.lock = new LockManager();
    this.dowloadedFiles = new HashMap<>();
  }

    public ViceReader download(String fileName,VenusCB refCB) throws RemoteException, FileNotFoundException {
    bind(fileName).readLock().lock();
    if (dowloadedFiles.containsKey(fileName)) {
      dowloadedFiles.get(fileName).add(refCB);
    } else {
      Set<VenusCB> clients = new HashSet<>();
      clients.add(refCB);
      dowloadedFiles.put(fileName, clients);
    } 
    ViceReaderImpl lector = new ViceReaderImpl(fileName, this);
    return lector;
  }

    public ViceWriter upload(String fileName,VenusCB refCB) throws RemoteException, FileNotFoundException {
    bind(fileName).writeLock().lock();
    if (dowloadedFiles.containsKey(fileName)) {
      for (VenusCB client : dowloadedFiles.get(fileName)) {
        if (!client.equals(refCB)) {
          client.invalidate(fileName);
        }
      }
    } else {
      Set<VenusCB> clients = new HashSet<>();
      clients.add(refCB);
      dowloadedFiles.put(fileName, clients);
    }
    ViceWriterImpl escritor = new ViceWriterImpl(fileName, this);
    return escritor;
  }

  public synchronized ReentrantReadWriteLock bind(String fileName) throws RemoteException {
    return this.lock.bind(fileName);
  }

  public synchronized void unbind(String fileName) throws RemoteException {
    this.lock.unbind(fileName);
 }
}
