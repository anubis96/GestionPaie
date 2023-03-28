/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionpayement.drh.lesfonctions;

/**
 *
 * @author Olivier Escobar
 */
public class LesFonctions {
    int id;
    String designation;
    double salaire;

    public LesFonctions(int id, String designation, double salaire) {
        this.id = id;
        this.designation = designation;
        this.salaire = salaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }
    
    
}
