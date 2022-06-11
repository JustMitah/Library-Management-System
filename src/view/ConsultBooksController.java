/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import clientRMI.LibrarianFunctionsInterface;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import constructors.Livre;

import constructors.MyConnection;
import constructors.BookModelTable;
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
public class ConsultBooksController implements Initializable, Serializable {
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    public boolean RMIstate;
    @FXML
    private AnchorPane parentLibAdd;
    @FXML
    private JFXButton display;
    @FXML
    private TableView<BookModelTable> Table_Books;

    @FXML
    private TableColumn<BookModelTable, String> col_ISBN;

    @FXML
    private TableColumn<BookModelTable, String> col_Titre;
    @FXML
    private TableColumn<BookModelTable, String> col_Auteur;
    @FXML
    private TableColumn<BookModelTable, String> col_Annee;
    @FXML
    private TableColumn<BookModelTable, String> col_Editeur;
    @FXML
    private TableColumn<BookModelTable, String> col_Emprunteur;
    @FXML
    private JFXComboBox<String> JComboAuthor;
    @FXML
    private JFXComboBox<String> JComboPublisher;
    @FXML
    private JFXComboBox<String> JComboTitle;
    @FXML
    private JFXComboBox<String> JComboBorrower;
    private HashMap<Integer,Livre> Livres = new HashMap<>();
    
    
    ObservableList <BookModelTable> oblistd = FXCollections.observableArrayList();
    private void FillComboBox(){
        try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("SELECT ISBN,Title,Author,YearOfPublication,Publisher,Borrower FROM books_details;");
            while (rsGet.next()) {
                oblistd.add(new BookModelTable(rsGet.getString(1),rsGet.getString(2),rsGet.getString(3),rsGet.getString(4),rsGet.getString(5),rsGet.getString(6)));
                JComboTitle.getItems().add(rsGet.getString(2));           
                JComboAuthor.getItems().add(rsGet.getString(3));
                JComboPublisher.getItems().add(rsGet.getString(5));
                JComboBorrower.getItems().add(rsGet.getString(6));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ConsultBooksController.class.getName()).log(Level.SEVERE, null, ex);
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
        FillComboBox();
        col_ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        col_Titre.setCellValueFactory(new PropertyValueFactory<>("Titre"));
        col_Auteur.setCellValueFactory(new PropertyValueFactory<>("Auteur"));
        col_Editeur.setCellValueFactory(new PropertyValueFactory<>("Editeur"));
        col_Annee.setCellValueFactory(new PropertyValueFactory<>("Annee"));
        col_Emprunteur.setCellValueFactory(new PropertyValueFactory<>("Emprunteur")); 
        makeStageDragable();

        }
    @FXML
    void Afficher(ActionEvent event){
       display.setDisable(true);
       display.setVisible(false);
       if(RMIstate){
            try {
                LibrarianFunctionsInterface lfi = (LibrarianFunctionsInterface) Naming.lookup("rmi://localhost:2003/CurrentLibrarian");
                Livres = lfi.ConsulterLivres();
                Livres.entrySet().forEach(entry -> {
                    oblistd.add(new BookModelTable(entry.getValue().getISBN(),entry.getValue().getTitre(),entry.getValue().getAuteur(),entry.getValue().getAnnee(),entry.getValue().getEditeur(),entry.getValue().getEmprunteur()));
                }); 
                Table_Books.setItems(oblistd); 
                FilteredList<BookModelTable> filteredData = new FilteredList<>(oblistd, b -> true);
                // Wrap the ObservableList in a FilteredList (initially display all data).
		JComboTitle.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate((BookModelTable ModelTable) -> {
				// If filter text is empty, display all persons.		
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
                            return ModelTable.getTitre().toLowerCase().contains(lowerCaseFilter);
			});
		});
                JComboAuthor.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate((BookModelTable ModelTable) -> {
				// If filter text is empty, display all persons.		
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
                            return ModelTable.getAuteur().toLowerCase().contains(lowerCaseFilter);
			});
		});
                JComboPublisher.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate((BookModelTable ModelTable) -> {
				// If filter text is empty, display all persons.		
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
                            return ModelTable.getEditeur().toLowerCase().contains(lowerCaseFilter);
			});
		});
                JComboBorrower.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate((BookModelTable ModelTable) -> {
				// If filter text is empty, display all persons.		
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}				
				String lowerCaseFilter = newValue.toLowerCase();
				
                            return ModelTable.getEmprunteur().toLowerCase().contains(lowerCaseFilter);
			});
		});
                // 3. Wrap the FilteredList in a SortedList. 
		SortedList<BookModelTable> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(Table_Books.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		Table_Books.setItems(sortedData); 
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(ConsultBooksController.class.getName()).log(Level.SEVERE, null, ex);
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
