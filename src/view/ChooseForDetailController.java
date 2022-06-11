/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import clientRMI.LibrarianFunctionsInterface;
import com.jfoenix.controls.JFXComboBox;
import constructors.LivreDetails;
import constructors.BookModelTable;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class ChooseForDetailController implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    public boolean RMIstate;
    private HashMap<Integer,LivreDetails> BookD = new HashMap<>();
    @FXML
    private AnchorPane parentLibAdd;

    @FXML
    private JFXComboBox<String> JComboTitle;

    @FXML
    private JFXComboBox<String> JComboISBN;
    private void FillComboBox(){
        try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("SELECT ISBN,Title FROM books_details;");
            while (rsGet.next()) {
                JComboISBN.getItems().add(rsGet.getString(1));
                JComboTitle.getItems().add(rsGet.getString(2));           
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ConsultBooksController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    @FXML
    void AfficherSelonISBN(ActionEvent event) throws IOException {
        if(RMIstate){
            try {
            LibrarianFunctionsInterface lfi = (LibrarianFunctionsInterface) Naming.lookup("rmi://localhost:2003/CurrentLibrarian");
            this.BookD = lfi.ConsulterLivreISBN(JComboISBN.getValue());
            } 
            catch (NotBoundException | MalformedURLException | RemoteException ex){
            Logger.getLogger(ConsultBooksController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DetailedBook.fxml"));
        Parent parentISBN= loader.load();	
        Scene mainISBN = new Scene(parentISBN);
        DetailedBookController dbc = loader.getController();
        dbc.RMIstate = this.RMIstate;
        dbc.SetLabels(this.BookD.get(0).getISBN(), this.BookD.get(0).getTitre(),this.BookD.get(0).getAuteur(),this.BookD.get(0).getAnnee(), this.BookD.get(0).getEditeur(),this.BookD.get(0).getEmprunteur());
        dbc.setImage(this.BookD.get(0).getImageURL()+".png");
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainISBN);
        mainstage.show();
        }

    @FXML
    void AfficherSelonTitre(ActionEvent event) throws IOException {
        if(RMIstate){
            try {     
                LibrarianFunctionsInterface lfi = (LibrarianFunctionsInterface) Naming.lookup("rmi://localhost:2003/CurrentLibrarian");
                this.BookD = lfi.ConsulterLivreTitre(JComboTitle.getValue());
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(ConsultBooksController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DetailedBook.fxml"));
        Parent parentTitle = loader.load();	
        Scene mainTitle = new Scene(parentTitle);
        DetailedBookController dbc = loader.getController();
        dbc.RMIstate = this.RMIstate;
        dbc.SetLabels(this.BookD.get(0).getISBN(), this.BookD.get(0).getTitre(),this.BookD.get(0).getAuteur(),this.BookD.get(0).getAnnee(), this.BookD.get(0).getEditeur(),this.BookD.get(0).getEmprunteur());
        dbc.setImage(this.BookD.get(0).getImageURL()+".png");
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainTitle);
        mainstage.show();
    }
    @FXML
    void MinApp(MouseEvent event) {
        stage = (Stage)parentLibAdd.getScene().getWindow();
        stage.setIconified(true);
    }
    private void makeStageDragable() {
		parentLibAdd.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		parentLibAdd.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		parentLibAdd.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		parentLibAdd.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1.0f);
                });
	}
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FillComboBox();
        makeStageDragable();
    }    
    @FXML
    void goBack(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Librarian.fxml"));	
	Scene mainpo = new Scene(parent);
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainpo);
	mainstage.show();
    }
    @FXML
    void CloseApp(MouseEvent event) {
        System.exit(0);
    }
}
