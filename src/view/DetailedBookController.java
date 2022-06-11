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
public class DetailedBookController implements Initializable {
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    public boolean RMIstate;
    @FXML
    private AnchorPane parentLibAdd;

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
    private Label Borrower;

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
    public void SetLabels(String isbn,String title,String author,String year,String publisher,String borrower){
        ISBN.setText(isbn);
        Title.setText(title);
        Author.setText(author);
        Publisher.setText(publisher);
        Year.setText(year);
        if (borrower.equals(""))
            Borrower.setText("Personne");
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
