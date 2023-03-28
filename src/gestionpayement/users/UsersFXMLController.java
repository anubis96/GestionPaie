
package gestionpayement.users;

import gestionpayement.databases.DatabaseHandler;
import gestionpayement.drh.DrhFXMLController;
import gestionpayement.finance.Payements;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/*
 * FXML Controller class
 * @author Olivier Escobar
 */
public class UsersFXMLController implements Initializable {
    
    @FXML
    private Text txt_mat;
    @FXML
    private Text txt_nom;
    @FXML
    private Text txt_prenom;
    @FXML
    private Text txt_fonction;
    @FXML
    private Text txt_adresse;
    @FXML
    private Text txt_phone;
    @FXML
    private Text txt_mail;
    @FXML
    private TableView<Payements> table_historique;
    @FXML
    private TableColumn<Payements, String> colH_matricule;
    @FXML
    private TableColumn<Payements, String> colH_nom;
    @FXML
    private TableColumn<Payements, String> colH_prenom;
    @FXML
    private TableColumn<Payements, String> colH_fonction;
    @FXML
    private TableColumn<Payements, Double> colH_salaire;
    @FXML
    private TableColumn<Payements, Integer> colH_heureSup;
    @FXML
    private TableColumn<Payements, Double> colH_bonus;
    @FXML
    private TableColumn<Payements, Double> colH_total;
    @FXML
    private TableColumn<Payements, Double> colH_du;
    @FXML
    private TableColumn<Payements, Double> colH_au;
    @FXML
    private TableColumn<Payements, Double> colH_datepay;
    
    DatabaseHandler handler;
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    @FXML
    private Text txt_date;
    @FXML
    private Text txt_id;
    
    ObservableList<Payements> list = FXCollections.observableArrayList();
    @FXML
    private TextField tx_salaire;
    @FXML
    private TextField tx_matricule;
    @FXML
    private TextField tx_fontion;
    @FXML
    private TextArea txt_area;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = new DatabaseHandler();
        txt_area.setWrapText(true);
    }

    @FXML
    private void btn_Add(ActionEvent event) {
    }
    
    public void getMatricule(String matricule){
        txt_mat.setText(matricule);
        matricule = txt_mat.getText();
        conn = handler.connection;
        String sql = "SELECT * FROM utilisateurs WHERE matricule='"+matricule+"'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                txt_nom.setText(rs.getString("nom"));
                txt_prenom.setText(rs.getString("prenom"));
                txt_fonction.setText(rs.getString("fonction"));
                txt_date.setText(rs.getString("dateEnreg"));
                txt_adresse.setText(rs.getString("adresse"));
                txt_phone.setText(rs.getString("phone"));
                txt_mail.setText(rs.getString("mail"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getMat(String matricule){
        txt_mat.setText(matricule);
        String mot = txt_mat.getText();
        
        conn = handler.connection;
        String sql = "SELECT * FROM salaires WHERE matricule='"+mot+"'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            Payements payements;
            while(rs.next()){
                payements = new Payements(rs.getInt("id"), rs.getString("matricule"), rs.getString("nom"), rs.getString("prenom"),rs.getString("fonction"), rs.getDouble("salaire"), rs.getInt("heuresup"), rs.getDouble("bonus"), rs.getDouble("total"), rs.getString("paiedu"), rs.getString("paieau"), rs.getString("datepay"));
                list.add(payements);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DrhFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        showTables(list);
    }

    private void showTables(ObservableList<Payements> list) {
        colH_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        colH_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colH_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colH_fonction.setCellValueFactory(new PropertyValueFactory<>("fonction"));
        colH_salaire.setCellValueFactory(new PropertyValueFactory<>("salaireBase"));
        colH_heureSup.setCellValueFactory(new PropertyValueFactory<>("heureSup"));
        colH_bonus.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        colH_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        colH_du.setCellValueFactory(new PropertyValueFactory<>("paieDu"));
        colH_au.setCellValueFactory(new PropertyValueFactory<>("paieAu"));
        colH_datepay.setCellValueFactory(new PropertyValueFactory<>("datePayemen"));
        table_historique.setItems(list);
    }
    
    @FXML
    private void loadSalaireInfo(MouseEvent event) {
        Payements payements = table_historique.getSelectionModel().getSelectedItem();
        txt_id.setText(String.valueOf(payements.getId()));
        tx_salaire.setText(String.valueOf(payements.getId()));
        tx_matricule.setText(payements.getMatricule());
        tx_fontion.setText(payements.getFonction());
    }
}