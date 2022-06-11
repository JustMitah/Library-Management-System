/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import clientRMI.LibrarianFunctionsInterface;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import constructors.MyConnection;
import constructors.Usager;
import constructors.UserModelTable;
import java.io.IOException;
import java.io.Serializable;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
public class ConsultUsersController implements Initializable, Serializable {
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    public boolean RMIstate;
    @FXML
    private AnchorPane parentUserConsult;
   
    @FXML
    private JFXButton display;

    @FXML
    private TableView<UserModelTable> Table_User;

    @FXML
    private TableColumn<UserModelTable,Integer> col_ID;

    @FXML
    private TableColumn<UserModelTable, String> col_name;

    @FXML
    private TableColumn<UserModelTable, String> col_address;

    @FXML
    private TableColumn<UserModelTable, String> col_category;

    @FXML
    private JFXComboBox<String> JComboNom;

    @FXML
    private JFXComboBox<String> JComboCategory;

    @FXML
    private JFXComboBox<String> JComboAddress;

    private HashMap<Integer,Usager> Usagers = new HashMap<>();
    
    
    ObservableList <UserModelTable> oblistd = FXCollections.observableArrayList();
    private void FillComboBox(){
        try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("SELECT PersonID,Username,Address,Category FROM users;");
            while (rsGet.next()) {
                oblistd.add(new UserModelTable(rsGet.getInt(1),rsGet.getString(2),rsGet.getString(3),rsGet.getString(4)));         
                JComboNom.getItems().add(rsGet.getString(2));
                JComboAddress.getItems().add(rsGet.getString(3));
                JComboCategory.getItems().add(rsGet.getString(4));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ConsultBooksController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    @FXML
    void MinApp(MouseEvent event) {
        stage = (Stage)parentUserConsult.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    void CloseApp(MouseEvent event) {
        System.exit(0);
    }
    private void makeStageDragable() {
		parentUserConsult.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		parentUserConsult.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		parentUserConsult.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		parentUserConsult.setOnMouseReleased((event) -> {
                    stage.setOpacity(1.0f);
                });
	}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FillComboBox();
        col_ID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        makeStageDragable();

        }
    @FXML
    void Afficher(ActionEvent event){
       display.setDisable(true);
       display.setVisible(false);
       if(RMIstate){
            try {
                LibrarianFunctionsInterface lfi = (LibrarianFunctionsInterface) Naming.lookup("rmi://localhost:2003/CurrentLibrarian");
                Usagers = lfi.ConsulterUsagers();
                Usagers.entrySet().forEach(entry -> {
                    oblistd.add(new UserModelTable(entry.getValue().getPersonID(),entry.getValue().getNom(),entry.getValue().getAddresse(),entry.getValue().getCategorie()));
                }); 
                Table_User.setItems(oblistd); 
                FilteredList<UserModelTable> filteredData = new FilteredList<>(oblistd, b -> true);
                // Wrap the ObservableList in a FilteredList (initially display all data).
		JComboNom.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate((UserModelTable ModelTable) -> {
				// If filter text is empty, display all persons.		
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
                            return ModelTable.getUsername().toLowerCase().contains(lowerCaseFilter);
			});
		});
                JComboAddress.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate((UserModelTable ModelTable) -> {
				// If filter text is empty, display all persons.		
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
                            return ModelTable.getAdresse().toLowerCase().contains(lowerCaseFilter);
			});
		});
                JComboCategory.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate((UserModelTable ModelTable) -> {
				// If filter text is empty, display all persons.		
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
                            return ModelTable.getCategorie().toLowerCase().contains(lowerCaseFilter);
			});
		});

		SortedList<UserModelTable> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(Table_User.comparatorProperty());
		Table_User.setItems(sortedData); 
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(ConsultUsersController.class.getName()).log(Level.SEVERE, null, ex);
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
    