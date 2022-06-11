/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import clientRMI.LibrarianFunctionsInterface;
import com.jfoenix.controls.JFXTextField;
import constructors.MyConnection;
import constructors.ShortBookModelTable;
import constructors.ShortUserModelTable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class DeleteUserController implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    public boolean RMIstate;
    private int chosenID=0;
    @FXML
    private AnchorPane parentUserRemove;
    @FXML
    private TableView<ShortUserModelTable> Table_user;

    @FXML
    private TableColumn<ShortUserModelTable, Integer> col_ID;

    @FXML
    private TableColumn<ShortUserModelTable, String> col_Nom;
    
    /**
     * Initializes the controller class.
     */
    ObservableList <ShortUserModelTable> oblistd = FXCollections.observableArrayList();

    private void lecture(){
            try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("SELECT PersonID,Username FROM users;");
            while (rsGet.next()) {
                oblistd.add(new ShortUserModelTable(rsGet.getInt(1),rsGet.getString(2)));
            }}
         catch (SQLException ex) {
            Logger.getLogger(DeleteUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    @FXML
    void MinApp(MouseEvent event) {
        stage = (Stage)parentUserRemove.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    void CloseApp(MouseEvent event) {
        System.exit(0);
    }
    private void makeStageDragable() {
		parentUserRemove.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		parentUserRemove.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		parentUserRemove.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		parentUserRemove.setOnMouseReleased((event) -> {
                    stage.setOpacity(1.0f);
                });
	}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lecture();
        col_ID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        col_Nom.setCellValueFactory(new PropertyValueFactory<>("username"));
        Table_user.setItems(oblistd);  
        Table_user.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                onEdit();
            }
        });
        makeStageDragable();
    }    
    public void onEdit() {
    if (Table_user.getSelectionModel().getSelectedItem() != null) {
        ShortUserModelTable selecteduser = Table_user.getSelectionModel().getSelectedItem();
        this.chosenID = selecteduser.getUserID();
    }
}
    @FXML
    void Delete(ActionEvent event) throws SQLException {
        if (this.chosenID !=0){
        if(RMIstate){
            try {
                LibrarianFunctionsInterface lfi = (LibrarianFunctionsInterface) Naming.lookup("rmi://localhost:2003/CurrentLibrarian");
                lfi.SupprimerUsager(chosenID);
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }} else {
            JOptionPane.showMessageDialog(null,"Clique sur une ligne!");
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
    
}
