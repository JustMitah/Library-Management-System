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
public class UsagerDetails implements Serializable {
    private int personID;
    private String nom;
    private String motdepasse;
    private String addresse;
    private String categorie;
    private int tps_emprunt;
    private int nb_emprunt;

    public UsagerDetails(int id, String name,String password,String address,String category,int tps,int nb){
        this.personID = id;
        this.nom = name;
        this.motdepasse = password;
        this.addresse = address;
        this.categorie = category;
        this.tps_emprunt = tps;
        this.nb_emprunt = nb;
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
     * @return the motdepasse
     */
    public String getMotdepasse() {
        return motdepasse;
    }

    /**
     * @param motdepasse the motdepasse to set
     */
    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
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
     * @return the tps_emprunt
     */
    public int getTps_emprunt() {
        return tps_emprunt;
    }

    /**
     * @param tps_emprunt the tps_emprunt to set
     */
    public void setTps_emprunt(int tps_emprunt) {
        this.tps_emprunt = tps_emprunt;
    }

    /**
     * @return the nb_emprunt
     */
    public int getNb_emprunt() {
        return nb_emprunt;
    }

    /**
     * @param nb_emprunt the nb_emprunt to set
     */
    public void setNb_emprunt(int nb_emprunt) {
        this.nb_emprunt = nb_emprunt;
    }
}
