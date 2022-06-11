/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package serverRMI;

import clientRMI.LibrarianImplements;
import clientRMI.UserImplements;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author Kazwell
 */
public class ServeurRMI{
    /**
     * @param who type of user
     * @throws java.rmi.RemoteException
     * @throws java.net.MalformedURLException
     */
    public static void main(String who) throws RemoteException, MalformedURLException  {
            try{
                if (who.equals("Librarian")){
                    LibrarianImplements lf = new LibrarianImplements();
                    LocateRegistry.createRegistry(2003);
                    System.out.println("Bibliothecaire veut se connecter dans RMIregistry");
                    Naming.rebind("rmi://localhost:2003/CurrentLibrarian",lf);
                    System.out.println("Attente d'operations du bibliothecaire");
            } else {
                    UserImplements ui = new UserImplements();
                    LocateRegistry.createRegistry(2003);
                    System.out.println("Usager veut se connecter dans RMIregistry");
                    Naming.rebind("rmi://localhost:2003/CurrentUsager",ui);
                    System.out.println("Attente d'operations du bibliothecaire");
                }
            }
            catch (MalformedURLException | RemoteException e) {
            System.out.println("ERREUR DE LIAISON AVEC LE SERVEUR");
            System.out.println(e.toString());
            }
    }
}
