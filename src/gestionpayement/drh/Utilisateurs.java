/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionpayement.drh;

/**
 *
 * @author Olivier Escobar
 */
public class Utilisateurs {
    String nom;
    String prenom;
    String matricule;
    String fonction;
    String mdp;
    String adresse;
    String phone;
    String mail;
    String dateEnreg;
    double salaireB;

    public Utilisateurs(String nom, String prenom, String matricule, String fonction, String mdp, String adresse, String phone, String mail, String dateEnreg, double salaireB) {
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.fonction = fonction;
        this.mdp = mdp;
        this.adresse = adresse;
        this.phone = phone;
        this.mail = mail;
        this.dateEnreg = dateEnreg;
        this.salaireB = salaireB;
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

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDateEnreg() {
        return dateEnreg;
    }

    public void setDateEnreg(String dateEnreg) {
        this.dateEnreg = dateEnreg;
    }

    public double getSalaireB() {
        return salaireB;
    }

    public void setSalaireB(double salaireB) {
        this.salaireB = salaireB;
    }

    
}