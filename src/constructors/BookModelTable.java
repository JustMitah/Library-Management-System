/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constructors;

import java.io.Serializable;

/**
 *
 * @author Kazwell
 */
public class BookModelTable implements Serializable{
    private String ISBN;
    private String Titre;
    private String Auteur;
    private String Annee;
    private String Editeur;
    private String Emprunteur;
    
    public BookModelTable(String isbn,String title,String author,String year,String publisher,String borrower){
        this.ISBN = isbn;
        this.Titre = title;
        this.Auteur = author;
        this.Annee = year;
        this.Editeur = publisher;
        this.Emprunteur = borrower; 
    }

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
     * @return the Titre
     */
    public String getTitre() {
        return Titre;
    }

    /**
     * @param Titre the Titre to set
     */
    public void setTitre(String Titre) {
        this.Titre = Titre;
    }

    /**
     * @return the Auteur
     */
    public String getAuteur() {
        return Auteur;
    }

    /**
     * @param Auteur the Auteur to set
     */
    public void setAuteur(String Auteur) {
        this.Auteur = Auteur;
    }

    /**
     * @return the Annee
     */
    public String getAnnee() {
        return Annee;
    }

    /**
     * @param Annee the Annee to set
     */
    public void setAnnee(String Annee) {
        this.Annee = Annee;
    }

    /**
     * @return the editeur
     */
    public String getEditeur() {
        return Editeur;
    }

    /**
     * @param editeur the editeur to set
     */
    public void setEditeur(String editeur) {
        this.Editeur = editeur;
    }

    /**
     * @return the emprunteur
     */
    public String getEmprunteur() {
        return Emprunteur;
    }

    /**
     * @param emprunteur the emprunteur to set
     */
    public void setEmprunteur(String emprunteur) {
        this.Emprunteur = emprunteur;
    }
    
}
