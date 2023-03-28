/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionpayement.drh;

import gestionpayement.databases.DatabaseHandler;
import gestionpayement.loadAlert.LoadAlert;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Olivier Escobar
 */
public class DrhFXMLController implements Initializable {
    Parent parent;
    Stage stage;
    Scene scene;
    
    DatabaseHandler handler;
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    LoadAlert alert;
    
    @FXML
    private TextField txt_nom;
    @FXML
    private TextField txt_prenom;
    @FXML
    private TextField txt_matricule;
    @FXML
    private TextField txt_mdp;
    @FXML
    private TextField txt_adresse;
    @FXML
    private TextField txt_phone;
    @FXML
    private TextField txt_mail;
    @FXML
    private ComboBox<String> txt_choix;
    @FXML
    private DatePicker date_picker;
    @FXML
    private TextField txt_salairebase;
    @FXML
    private TableView<Utilisateurs> table_users;
    @FXML
    private TableColumn<Utilisateurs, String> col_nom;
    @FXML
    private TableColumn<Utilisateurs, String> col_prenom;
    @FXML
    private TableColumn<Utilisateurs, String> col_matricule;
    @FXML
    private TableColumn<Utilisateurs, String> col_fonction;
    @FXML
    private TableColumn<Utilisateurs, String> col_phone;
    @FXML
    private TableColumn<Utilisateurs, String> col_date;
    @FXML
    private TableColumn<Utilisateurs, Double> col_salaire;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = new DatabaseHandler();
        alert = new LoadAlert();
        getFonctions();
        showTable();
    }    

    @FXML
    private void btn_lesFonctions(ActionEvent event) throws IOException {
            parent = FXMLLoader.load(getClass().getResource("/gestionpayement/drh/lesfonctions/fonctionsFXML.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Les Fonctions");
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
    }

    @FXML
    private void btn_Effacer(ActionEvent event) {
        txt_nom.clear();
        txt_prenom.clear();
        txt_matricule.clear();
        txt_mdp.clear();
        txt_adresse.clear();
        txt_phone.clear();
        txt_mail.clear();
        txt_choix.getSelectionModel().clearSelection();
        date_picker.getEditor().clear();
        txt_salairebase.clear();
    }
    
    @FXML
    private void btn_Add(ActionEvent event) {
        String nom = txt_nom.getText();
        String prenom = txt_prenom.getText();
        String matricule = txt_matricule.getText();
        String choix = txt_choix.getSelectionModel().getSelectedItem();
        String mdp = txt_mdp.getText();
        String adresse = txt_adresse.getText();
        String phone = txt_phone.getText();
        String mail = txt_mail.getText();
        String datePicker = date_picker.getEditor().getText();
        if(nom.isEmpty()||prenom.isEmpty()||matricule.isEmpty()||mdp.isEmpty()||adresse.isEmpty()||phone.isEmpty()||mail.isEmpty()||choix.isEmpty()||datePicker.isEmpty()||txt_salairebase.getText().isEmpty()){
            alert.loadAlert("Erreur","Remplit tout les champs avant de continuer", Alert.AlertType.ERROR);
        }
        double salaire = Double.valueOf(txt_salairebase.getText());
        handler.addUsers(nom, prenom, matricule, choix, mdp, adresse, phone, mail, datePicker, salaire);
        showTable();
    }
    
    @FXML
    private void btn_Modifier(ActionEvent event) {
        String nom = txt_nom.getText();
        String prenom = txt_prenom.getText();
        String matricule = txt_matricule.getText();
        String mdp = txt_mdp.getText();
        String adresse = txt_adresse.getText();
        String phone = txt_phone.getText();
        String mail = txt_mail.getText();
        String choix = txt_choix.getSelectionModel().getSelectedItem();
        String datePicker = date_picker.getEditor().getText();
        if(nom.isEmpty()||prenom.isEmpty()||matricule.isEmpty()||mdp.isEmpty()||adresse.isEmpty()||phone.isEmpty()||mail.isEmpty()||choix.isEmpty()||datePicker.isEmpty()||txt_salairebase.getText().isEmpty()){
            alert.loadAlert("Erreur","Remplit tout les champs avant de continuer", Alert.AlertType.ERROR);
        }else{
            double salaire = Double.valueOf(txt_salairebase.getText());
            handler.updateUsers(matricule, nom, prenom, mdp, adresse, phone, mail, choix, datePicker, salaire);
            showTable();
        } 
    }

    @FXML
    private void btn_supprimer(ActionEvent event) {
        String matricule = txt_matricule.getText();
        if(matricule.isEmpty()){
            alert.loadAlert("Erreur", "Veuillez Selectionner l'utilisateur avant de supprimer", Alert.AlertType.ERROR);
        }else{
            handler.deleteUsers(matricule);
            showTable();
        }
    }

    @FXML
    private void btn_chercher(ActionEvent event) {
    }

    @FXML
    private void btn_voirProfile(ActionEvent event) {
    }
    
    @FXML
    private void btn_Deconnection(ActionEvent event) {
    }
    
    public void getFonctions(){
        conn = handler.connection;
        String sql = "SELECT designation FROM lesfonctions";
        
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                txt_choix.getItems().addAll(rs.getString("designation"));
            }
    }catch(Exception ex){
        ex.getLocalizedMessage();
    }
    }

    @FXML
    private void loadSalaire(ActionEvent event) {
        String designation = txt_choix.getSelectionModel().getSelectedItem();
        conn = handler.connection;
        String sql = "SELECT salaire FROM lesfonctions WHERE designation='"+designation+"'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                txt_salairebase.setText(rs.getString("salaire"));
            }
    }catch(Exception ex){
        ex.getLocalizedMessage();
    }
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
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        col_fonction.setCellValueFactory(new PropertyValueFactory<>("fonction"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("dateEnreg"));
        col_salaire.setCellValueFactory(new PropertyValueFactory<>("salaireB"));
        table_users.setItems(list);
    }

    @FXML
    private void loadTableInfo(MouseEvent event) {
        Utilisateurs utilisateurs = table_users.getSelectionModel().getSelectedItem();
        txt_nom.setText(utilisateurs.getNom());
        txt_prenom.setText(utilisateurs.getPrenom());
        txt_matricule.setText(utilisateurs.getMatricule());
        txt_choix.getSelectionModel().select(utilisateurs.getFonction());
        txt_mdp.setText(utilisateurs.getMdp());
        txt_adresse.setText(utilisateurs.getAdresse());
        txt_phone.setText(utilisateurs.getPhone());
        txt_mail.setText(utilisateurs.getMail());
        date_picker.getEditor().setText(utilisateurs.getDateEnreg());
        txt_salairebase.setText(String.valueOf(utilisateurs.getSalaireB()));
    }
    
    public void generer(){
        String nom = txt_nom.getText();
        String prenom = txt_prenom.getText();
        String Snom = nom.substring(0,1).toUpperCase();
        String Sprenom = prenom.substring(0, 1).toUpperCase();
        Random random = new Random();
        int hazard = random.nextInt(1000);
        //boolean desisEspace = desis.contains(" ");
        txt_matricule.setText(Snom+Sprenom+hazard);
        System.out.println(Snom+Sprenom+hazard);
    }

    @FXML
    private void loadMatricule(ActionEvent event) {
        generer();
    }
}
