/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package clientRMI;

import constructors.LivreDetailsbis;
import constructors.MyConnection;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;

/**
 *
 * @author Kazwell
 */
@WebService(name="User")
public class UserImplements extends UnicastRemoteObject implements UserFunctionsInterface, Serializable {
    PreparedStatement pst,ps;
    ResultSet rs;
    private HashMap<Integer,LivreDetailsbis> UsagerBook = new HashMap<>();
    public UserImplements() throws RemoteException
    { 
        super();
    }
    @Override
    public int EmprunterLivre(String user,String isbn) throws RemoteException, SQLException{
         int count_book =0;
         try {
                MyConnection cnx = new MyConnection();    
                Connection con = cnx.getConnection();
                pst = con.prepareStatement("UPDATE books_details SET Borrower =? WHERE ISBN = ?");
                pst.setString(1,user);
                pst.setString(2,isbn);
                pst.executeUpdate();
                ResultSet rsGet;
                rsGet = con.createStatement().executeQuery("SELECT COUNT(*) FROM `books_details`,`users` WHERE `Borrower`= `Username` AND `Username` ="+user+";");
                while (rsGet.next()) {
                     ps = con.prepareStatement("UPDATE users SET BooksBorrowed =? WHERE Username = ?");
                     count_book = rsGet.getInt(1)+1;
                     ps.setInt(1,count_book);
                     ps.setString(2,user);
                     ps.executeUpdate();
                }
         }catch(SQLException ex) {
                Logger.getLogger(UserImplements.class.getName()).log(Level.SEVERE, null, ex);
          }
        return count_book;
    }
    @Override
    public HashMap<Integer,LivreDetailsbis> ConsulterLivreCritere(String type,String data) throws RemoteException{
        try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("SELECT * FROM books_details WHERE "+type+"='"+data+"';");
            while (rsGet.next()) {
             UsagerBook.put(0, new LivreDetailsbis(rsGet.getString(1),rsGet.getString(2),rsGet.getString(3),rsGet.getString(4),rsGet.getString(5),rsGet.getString(6),rsGet.getString(7),rsGet.getString(8)));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(LibrarianImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return UsagerBook;
    }
}
