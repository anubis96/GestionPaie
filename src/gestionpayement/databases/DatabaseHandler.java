/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionpayement.databases;

import gestionpayement.loadAlert.LoadAlert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.swing.JOptionPane;

/**
 *
 * @author Olivier Escobar
 */
public class DatabaseHandler {
    DatabaseHandler handler=null;
    ///LoadAlert alert;
    
    public Connection connection;
    PreparedStatement pst = null;
    ResultSet rs = null;
    //Connection à la base de données
    String db = "gestionsalaire";
    String user = "root";
    String pwd = "Olivier25@";
    String url = "jdbc:mysql://localhost:3306/"+db;
    
    LoadAlert alert = new LoadAlert();;
    
    public DatabaseHandler(){
        try {
            connection = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connecté ...");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

public ResultSet execQuery(String query){
    ResultSet result;
        try {
            pst = connection.prepareStatement(query);
            result = pst.executeQuery();
        } catch (SQLException ex) {
            System.out.println("L'Exception est sur "+ex.getLocalizedMessage());
            return null;
        }finally{
        }
    
    return result;
}

public boolean execAction(String qu){
        try {
            pst = connection.prepareStatement(qu);
            pst.execute(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur: "+ex.getLocalizedMessage(), "Erreur est Survenue", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception sur "+ex.getLocalizedMessage());
            return false;
        }finally{
        }
}

public void addFonction(String designation, double salaireb){
        String sql = "insert into lesfonctions(designation, salaire) values(?,?)";
        try{
        pst = connection.prepareStatement(sql);
        pst.setString(1, designation);
        pst.setDouble(2, salaireb);
        pst.executeUpdate();
        alert.loadAlert("Insertion", "Insertion avec succès", Alert.AlertType.INFORMATION);
        }catch(SQLException ex){
        alert.loadAlert("Echec", "Echec d'Insertion", Alert.AlertType.ERROR);
        }    
}

public void updateFonction(int id, String designation, double salaire){
        Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
        aler.setHeaderText(null);
        aler.setTitle("Modification");
        aler.setContentText("Voulez vous modifier cette fonction ?");
        Optional<ButtonType> reponse = aler.showAndWait();
        if(reponse.get()==ButtonType.OK){
            String sql = "UPDATE lesfonctions SET designation='"+designation+"', salaire='"+salaire+"' WHERE id="+id+"";
            execAction(sql);
            alert.loadAlert("Modification", "Modification réussit!", Alert.AlertType.ERROR);
            }
        else{
            alert.loadAlert("Echec", "Opération annulé!", Alert.AlertType.ERROR);
        }
           
}

public void deleteFonctions(int id){
        Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
        aler.setHeaderText(null);
        aler.setTitle("Suppresion");
        aler.setContentText("Voulez vous supprimer cette fonction ?");
        Optional<ButtonType> reponse = aler.showAndWait();
        if(reponse.get()==ButtonType.OK){
            String sql = "DELETE from lesfonctions WHERE id='"+id+"'";
            execAction(sql);
            alert.loadAlert("Suppression", "Suppression réussit!", Alert.AlertType.ERROR);
            }
        else{
            alert.loadAlert("Echec", "Opération annulé!", Alert.AlertType.ERROR);
        }
    }

public void addUsers(String nom, String prenom, String matricule, String fonction, String mdp, String adresse, String phone, String mail, String dateEnreg, double salaireB){

        alert = new LoadAlert();
        String sql = "insert into utilisateurs(nom, prenom, matricule, fonction, mdp, adresse, phone, mail, dateEnreg, salaireb) values(?,?,?,?,?,?,?,?,?,?)";
        try{
        pst = connection.prepareStatement(sql);
        pst.setString(1, nom);
        pst.setString(2, prenom);
        pst.setString(3, matricule);
        pst.setString(4, fonction);
        pst.setString(5, mdp);
        pst.setString(6, adresse);
        pst.setString(7, phone);
        pst.setString(8, mail);
        pst.setString(9, dateEnreg);
        pst.setDouble(10, salaireB);
        pst.executeUpdate();
        alert.loadAlert("Insertion", "Insertion avec succès", Alert.AlertType.INFORMATION);
        
        }catch(SQLException ex){
        alert.loadAlert("Echec", "Echec d'Insertion", Alert.AlertType.ERROR);
        }    
}

public void updateUsers(String matricule, String nom, String prenom, String fonction, String mdp, String adresse, String phone, String mail, String dateEnreg, double salaireB){
        Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
        aler.setHeaderText(null);
        aler.setTitle("Modification");
        aler.setContentText("Voulez vous modifier cet utilisateur ?");
        Optional<ButtonType> reponse = aler.showAndWait();
        if(reponse.get()==ButtonType.OK){
            String sql = "UPDATE utilisateurs SET nom='"+nom+"', prenom='"+prenom+"', fonction='"+fonction+"', mdp='"+mdp+"', adresse='"+adresse+"', phone='"+phone+"', mail='"+mail+"', dateEnreg='"+dateEnreg+"', salaireb="+salaireB+" WHERE matricule='"+matricule+"'";
            execAction(sql);
            alert.loadAlert("Modification", "Modification réussit!", Alert.AlertType.INFORMATION);
            }
        else{
            alert.loadAlert("Echec", "Opération annulé!", Alert.AlertType.ERROR);
        }
           
}

public void deleteUsers(String matricule){
        Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
        aler.setHeaderText(null);
        aler.setTitle("Suppresion");
        aler.setContentText("Voulez vous supprimer cet utilisateur ?");
        Optional<ButtonType> reponse = aler.showAndWait();
        if(reponse.get()==ButtonType.OK){
            String sql = "DELETE from utilisateurs WHERE matricule='"+matricule+"'";
            execAction(sql);
            alert.loadAlert("Suppression", "Suppression réussit!", Alert.AlertType.ERROR);
            }
        else{
            alert.loadAlert("Echec", "Opération annulé!", Alert.AlertType.ERROR);
        }
    }

public void addSalaire(String matricule, String nom, String prenom, String fonction, double salaireB, int heureSup, double bonus, double total, String paieDu, String paieAu, String datePayemen){

        alert = new LoadAlert();
        String sql = "insert into salaires(matricule, nom, prenom, fonction, salaire, heuresup, bonus, total, paiedu, paieau, datepay) values(?,?,?,?,?,?,?,?,?,?,?)";
        try{
        pst = connection.prepareStatement(sql);
        pst.setString(1, matricule);
        pst.setString(2, nom);
        pst.setString(3, prenom);
        pst.setString(4, fonction);
        pst.setDouble(5, salaireB);
        pst.setInt(6, heureSup);
        pst.setDouble(7, bonus);
        pst.setDouble(8, total);
        pst.setString(9, paieDu);
        pst.setString(10, paieAu);
        pst.setString(11, datePayemen);
        pst.executeUpdate();
        alert.loadAlert("Insertion", "Insertion avec succès", Alert.AlertType.INFORMATION);
        
        }catch(SQLException ex){
        alert.loadAlert("Echec", "Echec d'Insertion", Alert.AlertType.ERROR);
        }    
}

public void updateSalaire(int id, int heureSup, double bonus, double total, String paieDu, String paieAu, String datePayemen){
        Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
        aler.setHeaderText(null);
        aler.setTitle("Modification");
        aler.setContentText("Voulez vous modifier cet utilisateur ?");
        Optional<ButtonType> reponse = aler.showAndWait();
        if(reponse.get()==ButtonType.OK){
            String sql = "UPDATE salaires SET heuresup='"+heureSup+"', bonus='"+bonus+"', total='"+total+"', paiedu='"+paieDu+"', paieau='"+paieAu+"', datepay="+datePayemen+" WHERE id='"+id+"'";
            execAction(sql);
            alert.loadAlert("Modification", "Modification réussit!", Alert.AlertType.INFORMATION);
            }
        else{
            alert.loadAlert("Echec", "Opération annulé!", Alert.AlertType.ERROR);
        }
           
}
public void deleteSalaire(int id){
        Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
        aler.setHeaderText(null);
        aler.setTitle("Suppresion");
        aler.setContentText("Voulez vous supprimer cet utilisateur ?");
        Optional<ButtonType> reponse = aler.showAndWait();
        if(reponse.get()==ButtonType.OK){
            String sql = "DELETE from salaires WHERE id="+id+"";
            execAction(sql);
            alert.loadAlert("Suppression", "Suppression réussit!", Alert.AlertType.ERROR);
            }
        else{
            alert.loadAlert("Echec", "Opération annulé!", Alert.AlertType.ERROR);
        }
    }

}
