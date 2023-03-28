/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionpayement.finance;

/**
 *
 * @author Olivier Escobar
 */
public class Payements {
    
    public int id;
    public String matricule;
    public String nom;
    public String prenom;
    public String fonction;
    public double salaireBase;
    public int heureSup;
    public double bonus;
    public double total;
    public String paieDu;
    public String paieAu;
    public String datePayemen;

    public Payements(int id, String matricule, String nom, String prenom, String fonction, double salaireBase, int heureSup, double bonus, double total, String paieDu, String paieAu, String datePayemen) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.salaireBase = salaireBase;
        this.heureSup = heureSup;
        this.bonus = bonus;
        this.total = total;
        this.paieDu = paieDu;
        this.paieAu = paieAu;
        this.datePayemen = datePayemen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public double getSalaireBase() {
        return salaireBase;
    }

    public void setSalaireBase(double salaireBase) {
        this.salaireBase = salaireBase;
    }

    public int getHeureSup() {
        return heureSup;
    }

    public void setHeureSup(int heureSup) {
        this.heureSup = heureSup;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPaieDu() {
        return paieDu;
    }

    public void setPaieDu(String paieDu) {
        this.paieDu = paieDu;
    }

    public String getPaieAu() {
        return paieAu;
    }

    public void setPaieAu(String paieAu) {
        this.paieAu = paieAu;
    }

    public String getDatePayemen() {
        return datePayemen;
    }

    public void setDatePayemen(String datePayemen) {
        this.datePayemen = datePayemen;
    }
    
        
    
}
