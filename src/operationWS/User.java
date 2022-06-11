/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operationWS;

import constructors.LivreDetailsbis;
import constructors.MyConnection;
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
@WebService(name="User")
public class User implements Serializable {
    PreparedStatement pst,ps;
    ResultSet rs;
    private HashMap<Integer,LivreDetailsbis> UsagerBook = new HashMap<>();
    public User()
    { 
        super();
    }
    @WebMethod(operationName = "EmpruntLivre")
    public int EmprunterLivre(@WebParam(name="user")String user,@WebParam(name="isbn")String isbn) throws SQLException{
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
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
          }
        return count_book;
    }
    @WebMethod(operationName = "ConsulterLiveSelonCritere")
    public List<LivreDetailsbis> ConsulterLivreCritere(@WebParam(name="type")String type,@WebParam(name="data")String data){
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
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<LivreDetailsbis> list = new ArrayList<>(UsagerBook.values());
        return list;
    }
    
}
