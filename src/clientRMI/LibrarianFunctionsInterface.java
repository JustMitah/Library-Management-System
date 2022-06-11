/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientRMI;

import constructors.Livre;
import constructors.LivreDetails;
import constructors.Usager;
import constructors.UsagerDetails;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;


/**
 *
 * @author Kazwell
 */
public interface LibrarianFunctionsInterface extends Remote,Serializable {
    public void AjouterLivre(String isbn,String titre,String auteur,String annee,String editeur) throws RemoteException,SQLException;
    public void SupprimerLivre(String isbn) throws RemoteException,SQLException;
    public HashMap<Integer,Livre> ConsulterLivres() throws RemoteException;
    public HashMap<Integer,LivreDetails> ConsulterLivreTitre(String titre) throws RemoteException;
    public HashMap<Integer,LivreDetails> ConsulterLivreISBN(String isbn) throws RemoteException;
    public void AjouterUsager(String username, String password,String address, String category) throws RemoteException;
    public void SupprimerUsager(int id) throws RemoteException;
    public HashMap<Integer,Usager> ConsulterUsagers() throws RemoteException;
    public String getURL() throws RemoteException;
    public HashMap<Integer,UsagerDetails> ConsulterUsagerInfo(String Nom) throws RemoteException;
}
