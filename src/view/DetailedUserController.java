/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import clientRMI.LibrarianFunctionsInterface;
import com.jfoenix.controls.JFXComboBox;
import constructors.MyConnection;
import constructors.UsagerDetails;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class DetailedUserController implements Initializable {
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    public boolean RMIstate;
    private HashMap<Integer,UsagerDetails> UsagerInfo = new HashMap<>();
    
    @FXML
    private AnchorPane parentUserInfo;

    @FXML
    private ImageView image;

    @FXML
    private Label ID;

    @FXML
    private Label Username;

    @FXML
    private Label Password;

    @FXML
    private Label Address;

    @FXML
    private Label Category;

    @FXML
    private Label NbJours;

    @FXML
    private Label NbLivres;

    @FXML
    private JFXComboBox<String> JComboNom;
    


    @FXML
    void Search(ActionEvent event) {
        if(RMIstate){
            try {
                LibrarianFunctionsInterface lfi = (LibrarianFunctionsInterface) Naming.lookup("rmi://localhost:2003/CurrentLibrarian");
                UsagerInfo = lfi.ConsulterUsagerInfo(JComboNom.getValue());
                this.SetLabels(UsagerInfo.get(0).getPersonID(), UsagerInfo.get(0).getNom(), UsagerInfo.get(0).getMotdepasse(), UsagerInfo.get(0).getAddresse(), UsagerInfo.get(0).getCategorie(), UsagerInfo.get(0).getTps_emprunt(), UsagerInfo.get(0).getNb_emprunt());
                if(UsagerInfo.get(0).getNb_emprunt()>0)
                    this.setImage(lfi.getURL());
                else
                   this.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYbHcN6FURzXWGPBP8dCpb4E4EHc6Xa9ZD0A");           
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(DetailedUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
    
    @FXML
    void MinApp(MouseEvent event) {
        stage = (Stage)parentUserInfo.getScene().getWindow();
        stage.setIconified(true);
    }
    private void makeStageDragable() {
		parentUserInfo.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		parentUserInfo.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		parentUserInfo.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		parentUserInfo.setOnMouseReleased((event) -> {
                    stage.setOpacity(1.0f);
                });
	}
    public void SetLabels(int id,String nom,String mdp,String adr,String cat,int tps,int nb){
        ID.setText(Integer.toString(id));
        Username.setText(nom);
        Password.setText(mdp);
        Address.setText(adr);
        Category.setText(cat);
        NbJours.setText(Integer.toString(tps));
        NbLivres.setText(Integer.toString(nb));
    }
    public void setImage(String URL_img){      
        Image imgSource = new Image(URL_img);
        this.image.setImage(imgSource);
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();
        try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("SELECT Username FROM users;");
            while (rsGet.next()) {
                JComboNom.getItems().add(rsGet.getString(1));           
            }
        }catch (SQLException ex) {
            Logger.getLogger(DetailedUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
