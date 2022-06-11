/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operationWS;


import constructors.Livre;
import constructors.LivreDetails;
import constructors.MyConnection;
import constructors.Usager;
import constructors.UsagerDetails;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Kazwell
 */
@WebService(name="Bibliothecaire")
public class Librarian implements Serializable{

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

    public Librarian()
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
     * @throws SQLException
     */
    @WebMethod(operationName = "AjoutLivre")
    public void AjouterLivre(@WebParam(name="isbn")String isbn,@WebParam(name="titre")String titre,@WebParam(name="auteur")String auteur,@WebParam(name="annee")String annee,@WebParam(name="editeur")String editeur) throws SQLException{
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
                Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
                }
         }

    /**
     *
     * @return
     */
    @WebMethod(operationName = "imgURL")
    public String getURL(){
        return this.img_URL;
    }
    @WebMethod(operationName = "SupprimerLivre")
    public void SupprimerLivre(@WebParam(name="isbn")String isbn){
         try {
                MyConnection cnx = new MyConnection();    
                Connection con = cnx.getConnection();
                pst = con.prepareStatement("DELETE FROM books_details where ISBN = ?");
                pst.setString(1,isbn);
                pst.executeUpdate();
         }catch(SQLException ex) {
                Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    @WebMethod(operationName = "ConsulterLivres")
    public List<Livre> ConsulterLivres() {
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
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Livre> list = new ArrayList<>(Livres.values());
        return list;
    }
    
    
    @WebMethod(operationName = "ConsulterParTire")
    public  List<LivreDetails> ConsulterLivreTitre(@WebParam(name="titre")String titre){
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
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<LivreDetails> list = new ArrayList<>(BookDetailed.values());
        return list;
    }
    @WebMethod(operationName = "ConsulterParISBN")
    public List<LivreDetails> ConsulterLivreISBN(@WebParam(name="isbn")String isbn){  
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
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<LivreDetails> list = new ArrayList<>(BookDetailed.values());
        return list;
    }
    @WebMethod(operationName = "AjouterUsager")
    public void AjouterUsager(@WebParam(name="username")String username,@WebParam(name="password") String password,@WebParam(name="address")String address,@WebParam(name="category")String category){
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
                Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    @WebMethod(operationName = "SupprimerUsager")
    public void SupprimerUsager(@WebParam(name="id")int id) {
         try {
                MyConnection cnx = new MyConnection();    
                Connection con = cnx.getConnection();
                pst = con.prepareStatement("DELETE FROM users WHERE PersonID = ?");
                pst.setInt(1,id);
                pst.executeUpdate();
         }catch(SQLException ex) {
                Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
    @WebMethod(operationName = "ConsulterUsagers")
    public List<Usager> ConsulterUsagers(){
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
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Usager> list = new ArrayList<>(Usagers.values());
        return list;
    }
    @WebMethod(operationName = "ConsulterUsagerEnDetails")
    public List<UsagerDetails> ConsulterUsagerInfo(@WebParam(name="Nom")String Nom){
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
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<UsagerDetails> list = new ArrayList<>(UsagerInfo.values());
        return list;
    } 
}
