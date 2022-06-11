/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import clientRMI.LibrarianFunctionsInterface;
import com.jfoenix.controls.JFXTextField;
import constructors.MyConnection;
import constructors.ShortBookModelTable;
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

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class DeleteBookController implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    public boolean RMIstate;
    @FXML
    private  JFXTextField bookID;
    @FXML
    private AnchorPane parentLibAdd;
    @FXML
    private TableView<ShortBookModelTable> Table_ISBN;

    @FXML
    private TableColumn<ShortBookModelTable, String> col_ISBN;

    @FXML
    private TableColumn<ShortBookModelTable, String> col_Titre;
    /**
     * Initializes the controller class.
     */
    ObservableList <ShortBookModelTable> oblistd = FXCollections.observableArrayList();

    private void lecture(){
             try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("SELECT ISBN,Title FROM books_details LIMIT 6;");
            while (rsGet.next()) {
                oblistd.add(new ShortBookModelTable(rsGet.getString(1),rsGet.getString(2)));
            }}
         catch (SQLException ex) {
            Logger.getLogger(DeleteBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    @FXML
    void MinApp(MouseEvent event) {
        stage = (Stage)parentLibAdd.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    void CloseApp(MouseEvent event) {
        System.exit(0);
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lecture();
        col_ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        col_Titre.setCellValueFactory(new PropertyValueFactory<>("Titre"));
        Table_ISBN.setItems(oblistd);  
        Table_ISBN.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                onEdit();
            }
        });
        makeStageDragable();
    }    
    public void onEdit() {
    if (Table_ISBN.getSelectionModel().getSelectedItem() != null) {
        ShortBookModelTable selectedBook = Table_ISBN.getSelectionModel().getSelectedItem();
        bookID.setText(selectedBook.getISBN());
    }
}
    @FXML
    void Delete(ActionEvent event) throws SQLException {
        if(RMIstate){
            try {
                LibrarianFunctionsInterface lfi = (LibrarianFunctionsInterface) Naming.lookup("rmi://localhost:2003/CurrentLibrarian");
                lfi.SupprimerLivre(bookID.getText());
                bookID.setText(""); 
                bookID.requestFocus();
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
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
    
}
