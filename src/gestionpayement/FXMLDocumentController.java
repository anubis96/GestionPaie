
package gestionpayement;

import gestionpayement.databases.DatabaseHandler;
import gestionpayement.loadAlert.LoadAlert;
import gestionpayement.users.UsersFXMLController;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Olivier Escobar
 */
public class FXMLDocumentController implements Initializable {
    Parent parent;
    Stage stage;
    Scene scene;
    
    DatabaseHandler handler;
    PreparedStatement pst;
    ResultSet rs;
    Connection conn;
    
    @FXML
    private TextField txt_user;
    @FXML
    private PasswordField txt_mdp;
    
    public String matricule;
    public String mat_admin;
    public String mdp_admin;
    public String nom;
    private String poste;
    private String motdepasse;
    
    UsersFXMLController fxController = new UsersFXMLController();
    
    LoadAlert alert;
    
    @FXML
    private TextField admin_mat;
    @FXML
    private PasswordField admin_mdp;
    @FXML
    private ComboBox<String> admin_fonction;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = new DatabaseHandler();
        alert = new LoadAlert();
        getFonctions();
    }    

    @FXML
    private void btn_Connection(ActionEvent event) throws IOException {
        matricule = txt_user.getText();
        motdepasse = txt_mdp.getText();
        String sql = "SELECT * FROM utilisateurs where matricule ='"+matricule+"' AND mdp='"+motdepasse+"'";
        //String sql1 = "SELECT * FROM utilisateurs where matricule ='"+matricule+"' AND mdp='"+motdepasse+"' AND fonction='"+poste+"'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                
                if(matricule.equals(rs.getString("matricule"))&&motdepasse.equals(rs.getString("mdp"))){
                    parent = FXMLLoader.load(getClass().getResource("/gestionpayement/users/usersFXML.fxml"));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionpayement/users/usersFXML.fxml"));
                    
                    parent = loader.load();
                    fxController = loader.getController();
                    fxController.getMatricule(matricule);
                    fxController.getMat(matricule);
                    //fxController.getMatricule(matricule);
                    
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("Gérer les personels");
                    stage.centerOnScreen();
                    stage.setResizable(false);
                    stage.show();                        
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    public void getFonctions(){
        conn = handler.connection;
        String sql = "SELECT designation FROM lesfonctions";
        
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                admin_fonction.getItems().addAll(rs.getString("designation"));
            }
    }catch(SQLException ex){
        ex.getLocalizedMessage();
    }
    }

    @FXML
    private void btn_Admin(ActionEvent event) throws IOException {
        mat_admin = admin_mat.getText();
        mdp_admin = admin_mdp.getText();
        poste = admin_fonction.getSelectionModel().getSelectedItem();
//        String sql = "SELECT * FROM utilisateurs where matricule ='"+mat_admin+"' AND mdp='"+mdp_admin+"'";
//        try {
//            pst = conn.prepareStatement(sql);
//            rs = pst.executeQuery();
//            while(rs.next()){
            try{
                if(poste.equals(("DRH"))){
                    parent = FXMLLoader.load(getClass().getResource("/gestionpayement/drh/DrhFXML.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("Gérer les ventes");
                    stage.centerOnScreen();
                    stage.setResizable(false);
                    stage.show();
                } else if(poste.equals("DAF")){ //if(mat_admin.equals(rs.getString("matricule"))&&mdp_admin.equals(rs.getString("mdp"))&&poste.equals("DAF")){
                    parent = FXMLLoader.load(getClass().getResource("/gestionpayement/finance/financeFXML.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("Gérer les payements");
                    stage.centerOnScreen();
                    stage.setResizable(false);
                    stage.show();
                }
               }catch(java.lang.NullPointerException ex){
                   ex.getMessage();
                   alert.loadAlert("Erreur", "Remplissez le champ Utilisateur", Alert.AlertType.ERROR);
               }
                  
               }
        

    
}