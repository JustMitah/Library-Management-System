/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package clientRMI;

import constructors.LivreDetailsbis;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author Kazwell
 */
public interface UserFunctionsInterface extends Remote,Serializable {
    public int EmprunterLivre(String user,String isbn) throws RemoteException, SQLException;
    public  HashMap<Integer,LivreDetailsbis> ConsulterLivreCritere(String type,String data) throws RemoteException;
}
