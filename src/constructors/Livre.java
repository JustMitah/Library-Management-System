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
public class Livre implements Serializable {
    private String ISBN;
    private String titre;
    private String auteur;
    private String annee;
    private String editeur;
    private String emprunteur;
    public Livre(String isbn,String title,String author,String year,String publisher,String borrower){
        this.ISBN = isbn;
        this.titre = title;
        this.auteur = author;
        this.annee = year;
        this.editeur = publisher;
        this.emprunteur = borrower;
    }
    /*
    public Livre(){
        this.ISBN = "";
        this.titre = "";
        this.auteur = "";
        this.annee = "";
        this.editeur = "";
        this.emprunteur = "";
    }*/
    /**
     * @return the ISBN
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * @param ISBN the ISBN to set
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return the auteur
     */
    public String getAuteur() {
        return auteur;
    }

    /**
     * @param auteur the auteur to set
     */
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    /**
     * @return the editeur
     */
    public String getEditeur() {
        return editeur;
    }

    /**
     * @param editeur the editeur to set
     */
    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    /**
     * @return the emprunteur
     */
    public String getEmprunteur() {
        return emprunteur;
    }

    /**
     * @param emprunteur the emprunteur to set
     */
    public void setEmprunteur(String emprunteur) {
        this.emprunteur = emprunteur;
    }

    /**
     * @return the annee
     */
    public String getAnnee() {
        return annee;
    }

    /**
     * @param annee the annee to set
     */
    public void setAnnee(String annee) {
        this.annee = annee;
    }
    
    
}
