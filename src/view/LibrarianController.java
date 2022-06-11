/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class LibrarianController implements Initializable {
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    public boolean RMIstate;
    @FXML
    private AnchorPane parentLib;
    @FXML
    private AnchorPane ManiplibPane;

    @FXML
    public Label Username;

    @FXML
    void CloseApp(MouseEvent event) {
        System.exit(0);
    }
    
    @FXML
    void AjoutLivre(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddBook.fxml"));
        Parent parentAjout= loader.load();	
        Scene mainAjout = new Scene(parentAjout);
        AddBookController abc = loader.getController();
        abc.RMIstate = this.RMIstate;
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainAjout);
        mainstage.show();
    }
    @FXML
    void SupprimerLivre(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DeleteBook.fxml"));
        Parent parentSupp= loader.load();	
        Scene mainSupp = new Scene(parentSupp);
        DeleteBookController dbc = loader.getController();
        dbc.RMIstate = this.RMIstate;
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainSupp);
        mainstage.show();
    }
    
    
    @FXML
    void ConsulterLivres(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ConsultBooks.fxml"));
        Parent parentConsult= loader.load();	
        Scene mainConsult = new Scene(parentConsult);
        ConsultBooksController cbc = loader.getController();
        cbc.RMIstate = this.RMIstate;
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainConsult);
        mainstage.show();
    }
    @FXML
    void ConsulterDetsLivre(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChooseForDetail.fxml"));
        Parent parentDets= loader.load();	
        Scene mainDets = new Scene(parentDets);
        ChooseForDetailController cfdc = loader.getController();
        cfdc.RMIstate = this.RMIstate;
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainDets);
        mainstage.show();
    }
   
    @FXML
    void AjouterUsager(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddUser.fxml"));
        Parent parentUsr= loader.load();	
        Scene mainUsr = new Scene(parentUsr);
        AddUserController auc = loader.getController();
        auc.RMIstate = this.RMIstate;
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainUsr);
        mainstage.show();
    }
    @FXML
    void SupprimerUsager(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DeleteUser.fxml"));
        Parent parentUsr= loader.load();	
        Scene mainUsr = new Scene(parentUsr);
        DeleteUserController duc = loader.getController();
        duc.RMIstate = this.RMIstate;
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainUsr);
        mainstage.show();
    }
    @FXML
    void ConsulterUsager(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ConsultUsers.fxml"));
        Parent parentUsr= loader.load();	
        Scene mainUsr = new Scene(parentUsr);
        ConsultUsersController cuc = loader.getController();
        cuc.RMIstate = this.RMIstate;
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainUsr);
        mainstage.show();
    }
    
    @FXML
    void ConsulterDetsUsager(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DetailedUser.fxml"));
        Parent parentDUsr= loader.load();	
        Scene mainDUsr = new Scene(parentDUsr);
        DetailedUserController duc = loader.getController();
        duc.RMIstate = this.RMIstate;
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainDUsr);
        mainstage.show();
    }
/*
    @FXML
    void GestionUsager(ActionEvent event) {

    }
*/
    
    @FXML
    void MinApp(MouseEvent event) {
        stage = (Stage)parentLib.getScene().getWindow();
        stage.setIconified(true);
    }
        private void makeStageDragable() {
		parentLib.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		parentLib.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		parentLib.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		parentLib.setOnMouseReleased((event)->{
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
