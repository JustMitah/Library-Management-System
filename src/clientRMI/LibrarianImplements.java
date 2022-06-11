package clientRMI;

import constructors.Livre;
import constructors.LivreDetails;
import constructors.MyConnection;
import constructors.Usager;
import constructors.UsagerDetails;
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
import javax.jws.WebMethod;

/**
 *
 * @author Kazwell
 */
public class LibrarianImplements extends UnicastRemoteObject implements LibrarianFunctionsInterface, Serializable{

/**
 *
 * @author Kazwell
 */
    PreparedStatement pst;
    ResultSet rs;
    private HashMap<Integer,Livre> Livres = new HashMap<>();
    private HashMap<Integer,LivreDetails> BookDetailed = new HashMap<>();
    private HashMap<Integer,Usager> Usagers = new HashMap<>();
    private HashMap<Integer,UsagerDetails> UsagerInfo = new HashMap<>();
    private String img_URL ="";
    /**
     *
     * @throws RemoteException
     */
    public LibrarianImplements() throws RemoteException
    { 
        super();
    }
    
    /**
     *
     * @param isbn
     * @param titre
     * @param auteur
     * @param annee
     * @param editeur
     * @throws RemoteException
     * @throws SQLException
     */
    @Override
    public void AjouterLivre(String isbn,String titre,String auteur,String annee,String editeur) throws RemoteException, SQLException{
         try {
                MyConnection cnx = new MyConnection();    
                Connection con = cnx.getConnection();
                pst = con.prepareStatement("INSERT INTO books_details(ISBN,Title,Author,YearOfPublication,Publisher) VALUES(?,?,?,?,?)");
                pst.setString(1,isbn);
                pst.setString(2,titre);
                pst.setString(3,auteur);
                pst.setString(4,annee);  
                pst.setString(5,editeur);
                pst.executeUpdate();
                } catch(SQLException ex) {
                Logger.getLogger(LibrarianImplements.class.getName()).log(Level.SEVERE, null, ex);
                }
         }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public String getURL() throws RemoteException{
        return this.img_URL;
    }
    @Override
    public void SupprimerLivre(String isbn) throws RemoteException{
         try {
                MyConnection cnx = new MyConnection();    
                Connection con = cnx.getConnection();
                pst = con.prepareStatement("DELETE FROM books_details where ISBN = ?");
                pst.setString(1,isbn);
                pst.executeUpdate();
         }catch(SQLException ex) {
                Logger.getLogger(LibrarianImplements.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    @Override
    public HashMap<Integer,Livre> ConsulterLivres() throws RemoteException{
            try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("SELECT ISBN,Title,Author,YearOfPublication,Publisher,Borrower FROM books_details;");
            //int i =0;
            while (rsGet.next()) {
             Livres.put(Integer.SIZE, new Livre(rsGet.getString(1),rsGet.getString(2),rsGet.getString(3),rsGet.getString(4),rsGet.getString(5),rsGet.getString(6)));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(LibrarianImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Livres;
    }
    
    
    @Override
    public  HashMap<Integer,LivreDetails> ConsulterLivreTitre(String titre) throws RemoteException{
        try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("SELECT * FROM books_details WHERE Title ='"+titre+"';");
            while (rsGet.next()) {
             BookDetailed.put(0, new LivreDetails(rsGet.getString(1),rsGet.getString(2),rsGet.getString(3),rsGet.getString(4),rsGet.getString(5),rsGet.getString(6),rsGet.getString(7)));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(LibrarianImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return BookDetailed;
    }
    @Override
    public HashMap<Integer,LivreDetails> ConsulterLivreISBN(String isbn) throws RemoteException{  
        try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("SELECT * FROM books_details WHERE ISBN ='"+isbn+"';");
            while (rsGet.next()) {
             BookDetailed.put(0, new LivreDetails(rsGet.getString(1),rsGet.getString(2),rsGet.getString(3),rsGet.getString(4),rsGet.getString(5),rsGet.getString(6),rsGet.getString(7)));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(LibrarianImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return BookDetailed;
    }
    @Override
    public void AjouterUsager(String username, String password,String address, String category) throws RemoteException{
         try {
                MyConnection cnx = new MyConnection();    
                Connection con = cnx.getConnection();
                pst = con.prepareStatement("INSERT INTO users(Username,MotDePasse,Address,Category) VALUES(?,?,?,?)");
                pst.setString(1,username);
                pst.setString(2,password);
                pst.setString(3,address);
                pst.setString(4,category);  
                pst.executeUpdate();
                } catch(SQLException ex) {
                Logger.getLogger(LibrarianImplements.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    @Override
    public void SupprimerUsager(int id) throws RemoteException{
         try {
                MyConnection cnx = new MyConnection();    
                Connection con = cnx.getConnection();
                pst = con.prepareStatement("DELETE FROM users WHERE PersonID = ?");
                pst.setInt(1,id);
                pst.executeUpdate();
         }catch(SQLException ex) {
                Logger.getLogger(LibrarianImplements.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
    @Override
    public HashMap<Integer,Usager> ConsulterUsagers() throws RemoteException{
            try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("SELECT PersonID,Username,Address,Category FROM users;");
            while (rsGet.next()) {
             Usagers.put(Integer.SIZE, new Usager(rsGet.getInt(1),rsGet.getString(2),rsGet.getString(3),rsGet.getString(4)));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(LibrarianImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Usagers;
    }
    @Override
    public HashMap<Integer,UsagerDetails> ConsulterUsagerInfo(String Nom) throws RemoteException{
        try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("SELECT * FROM users WHERE Username ='"+Nom+"';");
            while (rsGet.next()) {
             UsagerInfo.put(0, new UsagerDetails(rsGet.getInt(1),rsGet.getString(2),rsGet.getString(3),rsGet.getString(4),rsGet.getString(5),rsGet.getInt(6),rsGet.getInt(7)));
            }
            ResultSet rsGetBookImg;
            rsGetBookImg = con.createStatement().executeQuery("SELECT `Image-URL-L` FROM `books_details`,`users` WHERE `Borrower`= `Username` AND `Username` = "+Nom+" LIMIT 1");
            while(rsGetBookImg.next()){
              this.img_URL=rsGetBookImg.getString(1);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(LibrarianImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return UsagerInfo;
    }


}

