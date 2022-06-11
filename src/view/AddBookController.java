/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import clientRMI.LibrarianFunctionsInterface;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.*;
import java.sql.SQLException;
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
public class AddBookController implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    public boolean RMIstate;
    @FXML
    private AnchorPane parentLibAdd;
    @FXML
    private JFXTextField author;
    @FXML
    private  JFXTextField bookID;
    @FXML
    private  JFXTextField title;
    @FXML
    private  JFXTextField year;
    @FXML
    private JFXTextField publisher;
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
    void Confirm(ActionEvent event) throws SQLException {
        if(RMIstate){
            try {
                LibrarianFunctionsInterface lfi = (LibrarianFunctionsInterface) Naming.lookup("rmi://localhost:2003/CurrentLibrarian");
                lfi.AjouterLivre(bookID.getText(), title.getText(), author.getText(), year.getText(),publisher.getText());
                bookID.setText("");
                title.setText("");
                author.setText(""); 
                year.setText(""); 
                publisher.setText(""); 
                bookID.requestFocus();
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            
        }
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
		parentLibAdd.setOnMouseReleased((event) -> {
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
    }    
    
}
