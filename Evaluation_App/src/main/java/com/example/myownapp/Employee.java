package com.example.myownapp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {

    private StringProperty emp_id;
    private StringProperty nom;
    private StringProperty prenom;

    private StringProperty email;


    private StringProperty password;

    private StringProperty sexe;


    private StringProperty datenaissance;
    //private StringProperty adresse;

    private StringProperty qualification;
    private StringProperty poste;

    private StringProperty service;
    private StringProperty numtel;

    private StringProperty daterecrutement;

    private StringProperty promotion;

    private StringProperty demission;







    public void setEmp_id(String value) { emp_idProperty().set(value); }
    public String getEmp_id() { return emp_idProperty().get(); }
    public StringProperty emp_idProperty() {
        if (emp_id == null) emp_id = new SimpleStringProperty(this, "emp_id");
        return emp_id;
    }



    public void setNom(String value) { nomProperty().set(value); }
    public String getNom() { return nomProperty().get(); }
    public StringProperty nomProperty() {
        if (nom == null) nom = new SimpleStringProperty(this, "nom");
        return nom;
    }


    public void setPrenom(String value) { prenomProperty().set(value); }
    public String getPrenom() { return prenomProperty().get(); }
    public StringProperty prenomProperty() {
        if (prenom == null) prenom = new SimpleStringProperty(this, "prenom");
        return prenom;
    }


    public void setEmail(String value) { emailProperty().set(value); }
    public String getEmail() { return emailProperty().get(); }
    public StringProperty emailProperty() {
        if (email == null) email = new SimpleStringProperty(this, "email");
        return email;
    }



    public void setPassword(String value) { passwordProperty().set(value); }
    public String getPassword() { return passwordProperty().get(); }
    public StringProperty passwordProperty() {
        if (password == null) password = new SimpleStringProperty(this, "password");
        return password;
    }




    public void setSexe(String value) { sexeProperty().set(value); }
    public String getSexe() { return sexeProperty().get(); }
    public StringProperty sexeProperty() {
        if (sexe == null) sexe = new SimpleStringProperty(this, "sexe");
        return sexe;
    }


    public void setDatenaissance(String value) { datenaissanceProperty().set(value); }
    public String getDatenaissance() { return datenaissanceProperty().get(); }
    public StringProperty datenaissanceProperty() {
        if (datenaissance == null) datenaissance = new SimpleStringProperty(this, "datenaissance");
        return datenaissance;
    }


    /*public void setAdresse(String value) { adresseProperty().set(value); }
    public String getAdresse() { return adresseProperty().get(); }
    public StringProperty adresseProperty() {
        if (adresse == null) adresse = new SimpleStringProperty(this, "adresse");
        return adresse;
    }*/


    public void setQualification(String value) { qualificationProperty().set(value); }
    public String getQualification() { return qualificationProperty().get(); }
    public StringProperty qualificationProperty() {
        if (qualification == null) qualification = new SimpleStringProperty(this, "qualification");
        return qualification;
    }




    public void setPoste(String value) { posteProperty().set(value); }
    public String getPoste() { return posteProperty().get(); }
    public StringProperty posteProperty() {
        if (poste == null) poste = new SimpleStringProperty(this, "poste");
        return poste;
    }


    public void setService(String value) { serviceProperty().set(value); }
    public String getService() { return serviceProperty().get(); }
    public StringProperty serviceProperty() {
        if (service == null) service = new SimpleStringProperty(this, "service");
        return service;
    }



    public void setNumtel(String value) { numtelProperty().set(value); }
    public String getNumtel() { return numtelProperty().get(); }
    public StringProperty numtelProperty() {
        if (numtel == null) numtel = new SimpleStringProperty(this, "numtel");
        return numtel;
    }


    public void setDaterecrutement(String value) { daterecrutementProperty().set(value); }
    public String getDaterecrutement() { return daterecrutementProperty().get(); }
    public StringProperty daterecrutementProperty() {
        if (daterecrutement == null) daterecrutement = new SimpleStringProperty(this, "datrecrutement");
        return daterecrutement;
    }



    public void setPromotion(String value) { promotionProperty().set(value); }
    public String getPromotion() { return promotion.get(); }
    public StringProperty promotionProperty() {
        if (promotion == null) promotion = new SimpleStringProperty(this, "1");
        return promotion;
    }


    public void setDemission(String value) { demissionProperty().set(value); }
    public String getDemission() { return demission.get(); }
    public StringProperty demissionProperty() {
        if (demission == null) demission = new SimpleStringProperty(this, "1");
        return demission;
    }








    public Employee(StringProperty emp_id, StringProperty nom, StringProperty prenom, StringProperty sexe, StringProperty datenaissance,  StringProperty qualification, StringProperty poste,StringProperty service,  StringProperty numtel, StringProperty daterecrutement) {
        this.emp_id = emp_id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.datenaissance = datenaissance;
       // this.adresse = adresse;
        this.qualification = qualification;
        this.poste = poste;
        this.service = service;
        this.numtel = numtel;
        this.daterecrutement = daterecrutement;
        this.promotion = promotion;
        this.demission = demission;

    }


    public Employee() {

    }


}



