/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;


import clientRMI.UserFunctionsInterface;
import com.jfoenix.controls.JFXComboBox;
import constructors.LivreDetailsbis;
import constructors.MyConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class UserConsultBooksController implements Initializable {
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    public boolean RMIstate;
    private String disponibilite = "";
    private String username="";
    private int nb_books = 0;
    @FXML
    private AnchorPane parentUsrConsult;

    @FXML
    private ImageView image;

    @FXML
    private Label ISBN;

    @FXML
    private Label Title;

    @FXML
    private Label Author;

    @FXML
    private Label Publisher;

    @FXML
    private Label Year;

    @FXML
    private Label Availability;

    @FXML
    private Label Aisle;

    @FXML
    private JFXComboBox<String> JComboType;

    @FXML
    private JFXComboBox<String> JComboData;
    private HashMap<Integer,LivreDetailsbis> UsagerBook = new HashMap<>();
    public void setUser(String name){
        this.username=name;
    }
    public void setNbBooks(int nb){
        this.nb_books = nb;
    }
    @FXML
    void Emprunter(ActionEvent event) throws SQLException {
        if(RMIstate){
            try {     
                UserFunctionsInterface ufi = (UserFunctionsInterface) Naming.lookup("rmi://localhost:2003/CurrentUsager");
                if (disponibilite.equals("OUI")){
                    this.nb_books = ufi.EmprunterLivre(this.username,this.ISBN.getText());
                    this.ChangeDisponibility("NON");
                }
                else
                    JOptionPane.showMessageDialog(null,"Livre Indisponible");
            }catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(UserConsultBooksController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @FXML
    void Afficher(ActionEvent event) {
            if(RMIstate){
            try {     
               UserFunctionsInterface ufi = (UserFunctionsInterface) Naming.lookup("rmi://localhost:2003/CurrentUsager");
                this.UsagerBook = ufi.ConsulterLivreCritere(JComboType.getValue(),JComboData.getValue());
                if (UsagerBook.get(0).getEmprunteur().equals(""))
                    this.disponibilite = "OUI";
                else
                    this.disponibilite = "NON";
                this.SetLabels(UsagerBook.get(0).getISBN(), UsagerBook.get(0).getTitre(),UsagerBook.get(0).getAuteur(), UsagerBook.get(0).getAnnee(), UsagerBook.get(0).getEditeur(), UsagerBook.get(0).getRayon(), disponibilite);
                this.setImage(UsagerBook.get(0).getImageURL());
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(UserConsultBooksController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

 @FXML
    void MinApp(MouseEvent event) {
        stage = (Stage)parentUsrConsult.getScene().getWindow();
        stage.setIconified(true);
    }
    private void makeStageDragable() {
		parentUsrConsult.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		parentUsrConsult.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		parentUsrConsult.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		parentUsrConsult.setOnMouseReleased((event) -> {
                    stage.setOpacity(1.0f);
                });
	}
        @FXML
    void goBack(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("SIB.fxml"));	
	Scene mainpo = new Scene(parent);
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainpo);
	mainstage.show();
    }
    @FXML
    void CloseApp(MouseEvent event) {
        System.exit(0);
    }
    public void ChangeDisponibility(String available){
        Availability.setText(available);
    }
    public void SetLabels(String isbn,String title,String author,String year,String publisher,String rayon,String availability){
        ISBN.setText(isbn);
        Title.setText(title);
        Author.setText(author);
        Year.setText(year);
        Publisher.setText(publisher);
        Aisle.setText(rayon);
        Availability.setText(availability);
    }
    public void setImage(String URL_img){      
        Image imgSource = new Image(URL_img);
        this.image.setImage(imgSource);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();
        MyConnection cnx = new MyConnection();
        Connection con = cnx.getConnection();
        JComboType.getItems().add("ISBN");
        JComboType.getItems().add("Title");
        JComboType.getItems().add("Author");
        JComboType.getItems().add("Publisher");
        JComboType.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                JComboData.getItems().clear();
                JComboData.setDisable(true);
            } else{
            String item = JComboType.getValue();
            JComboData.setDisable(false);
            switch (item) {
                case "ISBN":
                    JComboData.getItems().clear();
                    try {
                    ResultSet rsGet;
                    rsGet = con.createStatement().executeQuery("SELECT ISBN FROM books_details;");
                    while (rsGet.next()) {
                        JComboData.getItems().add(rsGet.getString(1));       
                    }
                    }catch (SQLException ex) {
                        Logger.getLogger(UserConsultBooksController.class.getName()).log(Level.SEVERE, null, ex);
                    }   
                    break;
                case "Title":
                    JComboData.getItems().clear();
                    try {
                    ResultSet rsGet;
                    rsGet = con.createStatement().executeQuery("SELECT Title FROM books_details;");
                    while (rsGet.next()) {
                        JComboData.getItems().add(rsGet.getString(1));       
                    }
                    }catch (SQLException ex) {
                        Logger.getLogger(UserConsultBooksController.class.getName()).log(Level.SEVERE, null, ex);
                    }  
                    break;
                case "Author":
                    JComboData.getItems().clear();
                    try {
                    ResultSet rsGet;
                    rsGet = con.createStatement().executeQuery("SELECT Author FROM books_details;");
                    while (rsGet.next()) {
                        JComboData.getItems().add(rsGet.getString(1));       
                    }
                    }catch (SQLException ex) {
                        Logger.getLogger(UserConsultBooksController.class.getName()).log(Level.SEVERE, null, ex);
                    }  
                    break;
                case "Publisher":
                    JComboData.getItems().clear();
                    try {
                    ResultSet rsGet;
                    rsGet = con.createStatement().executeQuery("SELECT Publisher FROM books_details;");
                    while (rsGet.next()) {
                        JComboData.getItems().add(rsGet.getString(1));       
                    }
                    }catch (SQLException ex) {
                        Logger.getLogger(UserConsultBooksController.class.getName()).log(Level.SEVERE, null, ex);
                    }  
                    break;
                }
            }
    });
    }
}    
    
