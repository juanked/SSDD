// Implementación de la interfaz de servidor que define los métodos remotos
// para iniciar la carga y descarga de ficheros
package afs;

import java.rmi.*;
import java.rmi.server.*;

public class ViceImpl extends UnicastRemoteObject implements Vice {
  public ViceImpl() throws RemoteException {
  }

  public ViceReader download(String fileName) throws RemoteException {
    ViceReaderImpl lector = new ViceReaderImpl(fileName);
    return lector;
  }

  public ViceWriter upload(String fileName /* añada los parámetros que requiera */) throws RemoteException {
    ViceWriterImpl escritor = new ViceWriterImpl(fileName);
    return escritor;
  }
}
