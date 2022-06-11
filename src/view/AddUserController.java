/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import clientRMI.LibrarianFunctionsInterface;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
public class AddUserController implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    public boolean RMIstate;
    @FXML
    private AnchorPane parentUserAdd;

    @FXML
    private JFXTextField Username;

    @FXML
    private JFXTextField Password;

    @FXML
    private JFXTextField Address;

    @FXML
    private JFXComboBox<String> JComboCategory;

    @FXML
    void CloseApp(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void Confirm(ActionEvent event) {
        if(RMIstate){
            try {
                LibrarianFunctionsInterface lfi = (LibrarianFunctionsInterface) Naming.lookup("rmi://localhost:2003/CurrentLibrarian");
                lfi.AjouterUsager(Username.getText(), Password.getText(), Address.getText(), JComboCategory.getValue());
                Username.setText("");
                Password.setText("");
                Address.setText(""); 
                JComboCategory.setPromptText("");
                Username.requestFocus();
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void MinApp(MouseEvent event) {
        stage = (Stage)parentUserAdd.getScene().getWindow();
        stage.setIconified(true);
    }

   @FXML
    void goBack(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Librarian.fxml"));	
	Scene mainpo = new Scene(parent);
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainpo);
	mainstage.show();
    }
    private void makeStageDragable() {
		parentUserAdd.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		parentUserAdd.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		parentUserAdd.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		parentUserAdd.setOnMouseReleased((event) -> {
                    stage.setOpacity(1.0f);
                });
	}
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();
        JComboCategory.getItems().add("Scolaire");
        JComboCategory.getItems().add("Etudiant");
        JComboCategory.getItems().add("Senior");
        JComboCategory.getItems().add("Travailleur");
        JComboCategory.getItems().add("Chômeur");
    }    
    
}
