/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionpayement.drh.lesfonctions;

import gestionpayement.databases.DatabaseHandler;
import gestionpayement.loadAlert.LoadAlert;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/*
 * FXML Controller class
 * @author Olivier Escobar
 */
public class FonctionsFXMLController implements Initializable {

    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_designation;
    @FXML
    private TextField txt_salaireb;
    @FXML
    private TextField txt_chercher;
    @FXML
    private Button btn_enregistrer;
    @FXML
    private TableView<LesFonctions> table_fonction;
    @FXML
    private TableColumn<LesFonctions, Integer> col_id;
    @FXML
    private TableColumn<LesFonctions, String> col_designation;
    @FXML
    private TableColumn<LesFonctions, Double> col_salaireb;
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    DatabaseHandler handler;
    LoadAlert alert;
    
    Parent parent;
    Stage stage;
    Scene scene;

    /*
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = new DatabaseHandler();
        alert = new LoadAlert();
        showTable();
    }    

    @FXML
    private void btn_enregistrer(ActionEvent event) {
        String designation = txt_designation.getText();
        double salaire = Double.valueOf(txt_salaireb.getText());
        if(designation.isEmpty()||txt_salaireb.getText().isEmpty()){
            alert.loadAlert("Erreur", "Veuillez remplir tout les champs", Alert.AlertType.ERROR);
        }else{
            handler.addFonction(designation, salaire);
            showTable();
        }
    }

    @FXML
    private void btn_modifier(ActionEvent event) {
        int id = Integer.parseInt(txt_id.getText());
        String designation = txt_designation.getText();
        double salaire = Double.valueOf(txt_salaireb.getText());
        if(designation.isEmpty()||txt_salaireb.getText().isEmpty()||txt_id.getText().isEmpty()){
            alert.loadAlert("Erreur", "Veuillez remplir tout les champs", Alert.AlertType.ERROR);
        }else{
            handler.updateFonction(id, designation, salaire);
            showTable();
        }        
    }

    @FXML
    private void btn_supprimer(ActionEvent event) {
        int id = Integer.parseInt(txt_id.getText());
        if(txt_id.getText().isEmpty()){
            alert.loadAlert("Erreur", "Veuillez remplir tout les champs", Alert.AlertType.CONFIRMATION);
        }else{
            handler.deleteFonctions(id);
            showTable();
        }
    }
    
    public ObservableList<LesFonctions> getFonctions(){
        ObservableList<LesFonctions> list = FXCollections.observableArrayList();
        conn = handler.connection;
        String sql = "SELECT * FROM lesfonctions";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            LesFonctions fonctions;
            while(rs.next()){
                fonctions = new LesFonctions(rs.getInt("id"), rs.getString("designation"), rs.getDouble("salaire"));
                list.add(fonctions);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public void showTable() { 
        ObservableList<LesFonctions> list = getFonctions();
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_designation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        col_salaireb.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        table_fonction.setItems(list);
    }

    @FXML
    private void load_TableInfo(MouseEvent event) {
        LesFonctions fonctions = table_fonction.getSelectionModel().getSelectedItem();
        txt_id.setText(String.valueOf(fonctions.getId()));
        txt_designation.setText(fonctions.getDesignation());
        txt_salaireb.setText(String.valueOf(fonctions.getSalaire()));
    }

    @FXML
    private void btn_Retour(ActionEvent event) throws IOException {
            parent = FXMLLoader.load(getClass().getResource("/gestionpayement/drh/DrhFXML.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Gestion des Personnels");
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
    }
    
    
}
