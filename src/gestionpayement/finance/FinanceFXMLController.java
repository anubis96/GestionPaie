/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionpayement.finance;

import gestionpayement.databases.DatabaseHandler;
import gestionpayement.drh.DrhFXMLController;
import gestionpayement.drh.Utilisateurs;
import gestionpayement.loadAlert.LoadAlert;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Olivier Escobar
 */
public class FinanceFXMLController implements Initializable {

    @FXML
    private TableView<Utilisateurs> table_perso;
    @FXML
    private TableColumn<Utilisateurs, String> col_matricule;
    @FXML
    private TableColumn<Utilisateurs, String> col_nom;
    @FXML
    private TableColumn<Utilisateurs, String> col_prenom;
    @FXML
    private TableColumn<Utilisateurs, String> col_fonction;
    @FXML
    private TableColumn<Utilisateurs, Double> col_salaire;
    @FXML
    private TextField txt_mat;
    @FXML
    private TextField txt_nom;
    @FXML
    private TextField txt_prenom;
    @FXML
    private TextField txt_fonction;
    @FXML
    private TextField txt_salaire;
    @FXML
    private TextField txt_heuresup;
    @FXML
    private TextField ttxt_bonus;
    @FXML
    private TextField txt_total;
    @FXML
    private DatePicker dateOne;
    @FXML
    private DatePicker dateTwo;
    @FXML
    private TextField txt_dateOne;
    @FXML
    private TextField txt_dateTwo;
    @FXML
    private DatePicker date_Picker;
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
    private TableColumn<Payements, String> colH_du;
    @FXML
    private TableColumn<Payements, String> colH_au;
    @FXML
    private TableColumn<Payements, String> colH_datepay;
    
    DatabaseHandler handler;
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    LoadAlert alert;
    @FXML
    private Label txt_id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = new DatabaseHandler();
        alert = new LoadAlert();
        showTable();
    }    

    @FXML
    private void btn_Add(ActionEvent event) {
        String mat = txt_mat.getText();
        String nom = txt_nom.getText();
        String prenom = txt_prenom.getText();
        String fonction = txt_fonction.getText();
        Double salaire = Double.valueOf(txt_salaire.getText());
        int heure = Integer.parseInt(txt_heuresup.getText());
        Double bonus = Double.valueOf(ttxt_bonus.getText());
        Double total = Double.valueOf(txt_total.getText()); 
        String dates = txt_dateOne.getText();
        String datess = txt_dateTwo.getText();
        String picker = date_Picker.getEditor().getText(); 
        
        if(mat.isEmpty()||nom.isEmpty()||prenom.isEmpty()||fonction.isEmpty()||txt_salaire.getText().isEmpty()||txt_heuresup.getText().isEmpty()||ttxt_bonus.getText().isEmpty()||txt_total.getText().isEmpty()||dates.isEmpty()||datess.isEmpty()||picker.isEmpty()){
            alert.loadAlert("Erreur", "Tout les champs sont obligatoires", Alert.AlertType.ERROR);
        }else{
            handler.addSalaire(mat, nom, prenom, fonction, salaire, heure, bonus, total, dates, datess, picker);
            showTables();
        }
    }

    @FXML
    private void btn_Modifier(ActionEvent event) {
        String mat = txt_mat.getText();
        String nom = txt_nom.getText();
        String prenom = txt_prenom.getText();
        String fonction = txt_fonction.getText();
        Double salaire = Double.valueOf(txt_salaire.getText());
        int heure = Integer.parseInt(txt_heuresup.getText());
        Double bonus = Double.valueOf(ttxt_bonus.getText());
        Double total = Double.valueOf(txt_total.getText()); 
        String dates = txt_dateOne.getText();
        String datess = txt_dateTwo.getText();
        String picker = date_Picker.getEditor().getText(); 
        if(mat.isEmpty()||nom.isEmpty()||prenom.isEmpty()||fonction.isEmpty()||txt_salaire.getText().isEmpty()||txt_heuresup.getText().isEmpty()||ttxt_bonus.getText().isEmpty()||txt_total.getText().isEmpty()||dates.isEmpty()||datess.isEmpty()||picker.isEmpty()){
            alert.loadAlert("Erreur", "Tout les champs sont obligatoires", Alert.AlertType.ERROR);
        }else{
            int id = Integer.parseInt(txt_id.getText());
            handler.updateSalaire(id, heure, bonus, total, dates, datess, picker);
            showTables();
        }
    }

    @FXML
    private void btn_Print(ActionEvent event) {
    }

    @FXML
    private void btn_supprimer(ActionEvent event) {
        int id = Integer.parseInt(txt_id.getText());
        if(txt_id.getText().equals("ID")){
            alert.loadAlert("Erreur", "Veuillez Selectionner l'utilisateur avant de supprimer", Alert.AlertType.ERROR);
        }else{
            handler.deleteSalaire(id);
            showTables();
        }
    }

    @FXML
    private void btn_Effacer(ActionEvent event) {
    }

    @FXML
    private void btn_PrintHisto(ActionEvent event) {
    }
    
    public ObservableList<Utilisateurs> getUsers(){
        ObservableList<Utilisateurs> list = FXCollections.observableArrayList();
        conn = handler.connection;
        String sql = "SELECT * FROM utilisateurs";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            Utilisateurs utilisateurs;
            while(rs.next()){
                utilisateurs = new Utilisateurs(rs.getString("nom"), rs.getString("prenom"), rs.getString("matricule"),rs.getString("fonction"), rs.getString("mdp"), rs.getString("adresse"), rs.getString("phone"), rs.getString("mail"), rs.getString("dateEnreg"), rs.getDouble("salaireb"));
                list.add(utilisateurs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DrhFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;        
    }

    private void showTable() {
        ObservableList<Utilisateurs> list = getUsers();
        col_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_fonction.setCellValueFactory(new PropertyValueFactory<>("fonction"));
        col_salaire.setCellValueFactory(new PropertyValueFactory<>("salaireB"));
        table_perso.setItems(list);
    }    

    @FXML
    private void btn_loadPersoInfo(MouseEvent event) {
        Utilisateurs utilisateurs = table_perso.getSelectionModel().getSelectedItem();
        txt_nom.setText(utilisateurs.getNom());
        txt_prenom.setText(utilisateurs.getPrenom());
        txt_mat.setText(utilisateurs.getMatricule());
        txt_fonction.setText(utilisateurs.getFonction());
        txt_salaire.setText(String.valueOf(utilisateurs.getSalaireB()));
    }
 
    public ObservableList<Payements> getSalaires(){
        ObservableList<Payements> list = FXCollections.observableArrayList();
        conn = handler.connection;
        String sql = "SELECT * FROM salaires";
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
        
        return list;        
    }
   
    private void showTables() {
        ObservableList<Payements> list = getSalaires();
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
    private void loadDateOne(ActionEvent event) {
      LocalDate myDate = dateOne.getValue();
      txt_dateOne.setText(myDate.toString());
      System.out.println(myDate);
    }

    @FXML
    private void loadDateTwo(ActionEvent event) {
        LocalDate myDate = dateTwo.getValue();
        txt_dateTwo.setText(myDate.toString());
        System.out.println(myDate);
    }

    @FXML
    private void loadSalaireInfo(MouseEvent event) {
        Payements payements = table_historique.getSelectionModel().getSelectedItem();
        txt_id.setText(String.valueOf(payements.getId()));
        txt_mat.setText(payements.getMatricule());
        txt_nom.setText(payements.getNom());
        txt_prenom.setText(payements.getPrenom());
        txt_fonction.setText(payements.getFonction());
        txt_salaire.setText(String.valueOf(payements.getSalaireBase()));
        txt_heuresup.setText(String.valueOf(payements.getHeureSup()));
        ttxt_bonus.setText(String.valueOf(payements.getBonus()));
        txt_total.setText(String.valueOf(payements.getTotal()));
        txt_dateOne.setText(payements.getPaieDu());
        txt_dateTwo.setText(payements.getPaieAu());
        date_Picker.getEditor().setText(payements.getDatePayemen());
    }
}
