package view;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import constructors.MyConnection;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import serverRMI.ServeurRMI;

public class SIBController implements Initializable {
    PreparedStatement pst;
    ResultSet rs;
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    private boolean bookie = false;
    public boolean RMIstate = true;
    @FXML
    private Hyperlink forgot;
    @FXML
    private AnchorPane parent;
    @FXML
    private ImageView ImgB;
    @FXML
    private AnchorPane LoginPane;

    @FXML
    private JFXTextField Username;
    
    @FXML
    private Label pick;

    @FXML
    private JFXPasswordField Password;
   
    @FXML
    private JFXRadioButton CheckWS;

    @FXML
    private JFXRadioButton CheckRMI;
    @FXML
    private JFXButton loginButton;

    @FXML
    private ImageView Img;

    @FXML
    private Label question;

    @FXML
    void CloseApp(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void MinApp(MouseEvent event) {
        stage = (Stage)parent.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void changeLogintoLibrarian(MouseEvent event) {
        this.bookie=true;
        LoginPane.setStyle("-fx-background-color:  #d00721; -fx-background-radius : 30 ");
        loginButton.setStyle("-fx-background-color:  #d00721;-fx-background-radius : 30; -fx-text-fill: white");
        forgot.setStyle("-fx-background-color:  #d00721");
        question.setStyle("-fx-text-fill: white");
        pick.setStyle("-fx-text-fill: white");
        Username.setStyle("-jfx-focus-color: white;-jfx-unfocus-color: white;-fx-prompt-text-fill : white; -fx-text-fill : white");
        Password.setStyle("-jfx-focus-color: white;-jfx-unfocus-color: white;-fx-prompt-text-fill : white; -fx-text-fill : white");
    }
    @FXML
    void changeLogintoUser(MouseEvent event) {
        this.bookie=false;
        LoginPane.setStyle("-fx-background-color: #fbbf17; -fx-background-radius : 30 ");
        loginButton.setStyle("-fx-background-color: #d4a419;-fx-background-radius : 30;-fx-text-fill: black");
        forgot.setStyle("-fx-background-color: #fbbf17");
        question.setStyle("-fx-text-fill: black");
        pick.setStyle("-fx-text-fill: black");
        Username.setStyle("-jfx-focus-color: black;-jfx-unfocus-color: black;");
        Password.setStyle("-jfx-focus-color: black;-jfx-unfocus-color: black;");
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        if(CheckWS.isSelected()) {
           this.RMIstate = false;
           /*if (this.bookie==true){
              ServeurWS.main("Librarian");
            } else {
              ServeurWS.main("User");  
            }*/
        } else{  
            if (this.bookie==true){
              ServeurRMI.main("Librarian");
            } else {
              ServeurRMI.main("User");  
            }
        String username = Username.getText();
        String password = Password.getText();
        if (this.bookie==true){

            if (username.equals("") || password.equals(""))
            {
                JOptionPane.showMessageDialog(null,"Username or Password needed");
                Username.setText("");
                Password.setText(""); 
                Username.requestFocus();    
            }
            else
            {
                try {
                    MyConnection cnx = new MyConnection();    
                    Connection con = cnx.getConnection();
                    pst = con.prepareStatement("select * from `administrator` where username=? and password=?");
                    pst.setString(1,username);
                    pst.setString(2,password);
                    rs = pst.executeQuery();
                    if (rs.next()){
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("Librarian.fxml"));
                        Parent parentLIB = loader.load();	
                        Scene mainLIB = new Scene(parentLIB);
                        LibrarianController libc = loader.getController();
                        libc.Username.setText(username);
                        libc.RMIstate = this.RMIstate;
                        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
                        mainstage.setScene(mainLIB);
                        mainstage.show();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Username or Password invalid");
                        Username.setText("");
                        Password.setText(""); 
                        Username.requestFocus();
                    }
                } 
                catch (SQLException ex) {
                    Logger.getLogger(SIBController.class.getName()).log(Level.SEVERE, null, ex);
                }       
            }   
        } 
        else {
            if (username.equals("") || password.equals(""))
            {
                JOptionPane.showMessageDialog(null,"Username or Password needed");
                Username.setText("");
                Password.setText(""); 
                Username.requestFocus();
            }
            else
            {           
                try{
                    MyConnection cnx = new MyConnection();    
                    Connection con = cnx.getConnection();  
                    pst = con.prepareStatement("select * from `users` where Username=? and MotDePasse=?");
                    pst.setString(1,username);
                    pst.setString(2,password);
                    rs = pst.executeQuery();
                    if (rs.next()){
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("UserConsultBooks.fxml"));
                        Parent parentUSR = loader.load();	
                        Scene mainUSR = new Scene(parentUSR);
                        UserConsultBooksController ucbc = loader.getController();
                        ucbc.setUser(username);
                        ucbc.setNbBooks(rs.getInt(7));
                        ucbc.RMIstate = this.RMIstate;
                        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
                        mainstage.setScene(mainUSR);
                        mainstage.show();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Username or Password invalid");
                        Username.setText("");
                        Password.setText(""); 
                        Username.requestFocus();
                    }
                } 
                catch (SQLException ex) {
                    Logger.getLogger(SIBController.class.getName()).log(Level.SEVERE, null, ex);
                }       
            }   
        }
    }}

    
    private void makeStageDragable() {
		parent.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		parent.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		parent.setOnDragDone((event)-> {
                    stage.setOpacity(1.0f);
		});
		parent.setOnMouseReleased((event) -> {
                    stage.setOpacity(1.0f);
                });
	}

    @FXML
    void redirectToAdmin(ActionEvent event) {
        JOptionPane.showMessageDialog(null,"Tu veux ouvrir un compte?\nTu as oublié ton mot de passe?\nVoici nos mails : \nhlakchili@gmail.com\njeghal12@gmail.com","CONTACT INFORMATION",JOptionPane.INFORMATION_MESSAGE);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		makeStageDragable();
}  

}
