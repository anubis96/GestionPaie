/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionpayement.users;

/**
 *
 * @author Olivier Escobar
 */
public class Reclamations {
    int id;
    int idSalaire;
    String matriculePerso;
    String fonctionPerso;
    String reclamation;
    boolean etat;

    public Reclamations(int id, int idSalaire, String matriculePerso, String fonctionPerso, String reclamation, boolean etat) {
        this.id = id;
        this.idSalaire = idSalaire;
        this.matriculePerso = matriculePerso;
        this.fonctionPerso = fonctionPerso;
        this.reclamation = reclamation;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSalaire() {
        return idSalaire;
    }

    public void setIdSalaire(int idSalaire) {
        this.idSalaire = idSalaire;
    }

    public String getMatriculePerso() {
        return matriculePerso;
    }

    public void setMatriculePerso(String matriculePerso) {
        this.matriculePerso = matriculePerso;
    }

    public String getFonctionPerso() {
        return fonctionPerso;
    }

    public void setFonctionPerso(String fonctionPerso) {
        this.fonctionPerso = fonctionPerso;
    }

    public String getReclamation() {
        return reclamation;
    }

    public void setReclamation(String reclamation) {
        this.reclamation = reclamation;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
    
    
    
}
