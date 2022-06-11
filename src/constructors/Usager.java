/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constructors;

import java.io.Serializable;

/**
 *
 * @author Kazwell
 */
public class Usager implements Serializable {
    private int personID;
    private String nom;
    private String categorie;
    private String addresse;
    public Usager(int id, String name,String address,String category){
        this.personID = id;
        this.nom = name;
        this.categorie = category;
        this.addresse = address;
    }
    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }


    /**
     * @return the addresse
     */
    public String getAddresse() {
        return addresse;
    }

    /**
     * @param addresse the addresse to set
     */
    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @param categorie the categorie to set
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * @return the personID
     */
    public int getPersonID() {
        return personID;
    }

    /**
     * @param personID the personID to set
     */
    public void setPersonID(int personID) {
        this.personID = personID;
    }
   
}
