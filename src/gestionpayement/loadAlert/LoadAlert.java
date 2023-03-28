/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionpayement.loadAlert;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/*
 * @author Olivier Escobar
 */
public class LoadAlert {
    
    public void loadAlert(String titre, String contenu, AlertType type){
        Alert alerte = new Alert(type);
        alerte.setTitle(titre);
        alerte.setHeaderText(null);
        alerte.setContentText(contenu);
        alerte.showAndWait();
    }
    
    public ButtonType loadAlertType(String title, String contenu, Optional<ButtonType> reponse){
        ButtonType ok = ButtonType.OK;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(contenu);
        reponse = alert.showAndWait();
        
        return ok;
    }
}
