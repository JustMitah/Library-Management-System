/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constructors;

/**
 *
 * @author Kazwell
 */
public class ShortBookModelTable {
    private String ISBN;
    private String Titre;
    
    public ShortBookModelTable(String isbn,String title){
        this.ISBN = isbn;
        this.Titre = title;
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
    
}
