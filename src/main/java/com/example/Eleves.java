package com.example;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Eleves {
    private final IntegerProperty id;
    private final StringProperty nom;
    private final StringProperty prenom;
    private final IntegerProperty age;
    private final IntegerProperty moyenne;

    public Eleves(int id, String nom, String prenom, int age) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.age = new SimpleIntegerProperty(age);
        this.moyenne = new SimpleIntegerProperty(0);
    }
    
    public Eleves(int id, String nom, String prenom, int age, int moyenne) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.age = new SimpleIntegerProperty(age);
        this.moyenne = new SimpleIntegerProperty(moyenne);
    }
    
    public IntegerProperty idProperty() { return id; }
    public StringProperty nomProperty() { return nom; }
    public StringProperty prenomProperty() { return prenom; }
    public IntegerProperty ageProperty() { return age; }
    public IntegerProperty moyenneProperty() { return moyenne; }

    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public String getPrenom() { return prenom.get(); }
    public int getAge() { return age.get(); }
    public int getMoyenne() { return moyenne.get(); }

    public void setId(int id) { this.id.set(id); }
    public void setNom(String nom) { this.nom.set(nom); }
    public void setPrenom(String prenom) { this.prenom.set(prenom); }
    public void setAge(int age) { this.age.set(age); }
    public void setMoyenne(int moyenne) { this.moyenne.set(moyenne); }
}