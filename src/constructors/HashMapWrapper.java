/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constructors;
import java.util.HashMap;

/**
 *
 * @author Kazwell
 */
public final class HashMapWrapper {
    private HashMap<Integer, LivreDetails> LivreDetsMap;
    private HashMap<Integer, Livre> LivreMap;
    private HashMap<Integer,UsagerDetails> UsagerDetsMap;
    private HashMap<Integer,Usager> UsagerMap;
    
   /* public HashMapWrapper(HashMap<Integer, LivreDetails> ldm, HashMap<Integer, Livre> lm,HashMap<Integer,UsagerDetails> udm, HashMap<Integer,Usager> um) {
        this.setLivreDetsMap(ldm);
        this.setLivreMap(lm);
        this.setUsagerDetsMap(udm);
        this.setUsagerMap(um);
    }
    public HashMapWrapper(){
        this.setLivreDetsMap(new HashMap<>());
        this.setLivreMap(new HashMap<>());
        this.setUsagerDetsMap(new HashMap<>());
        this.setUsagerMap(new HashMap<>());
    }*/
    /**
     * @return the LivreDetsMap
     */
    public HashMap<Integer, LivreDetails> getLivreDetsMap() {
        return LivreDetsMap;
    }

    /**
     * @param LivreDetsMap the LivreDetsMap to set
     */
    public void setLivreDetsMap(HashMap<Integer, LivreDetails> LivreDetsMap) {
        this.LivreDetsMap = LivreDetsMap;
    }

    /**
     * @return the LivreMap
     */
    public HashMap<Integer, Livre> getLivreMap() {
        return LivreMap;
    }

    /**
     * @param LivreMap the LivreMap to set
     */
    public void setLivreMap(HashMap<Integer, Livre> LivreMap) {
        this.LivreMap = LivreMap;
    }

    /**
     * @return the UsagerDetsMap
     */
    public HashMap<Integer,UsagerDetails> getUsagerDetsMap() {
        return UsagerDetsMap;
    }

    /**
     * @param UsagerDetsMap the UsagerDetsMap to set
     */
    public void setUsagerDetsMap(HashMap<Integer,UsagerDetails> UsagerDetsMap) {
        this.UsagerDetsMap = UsagerDetsMap;
    }

    /**
     * @return the UsagerMap
     */
    public HashMap<Integer,Usager> getUsagerMap() {
        return UsagerMap;
    }

    /**
     * @param UsagerMap the UsagerMap to set
     */
    public void setUsagerMap(HashMap<Integer,Usager> UsagerMap) {
        this.UsagerMap = UsagerMap;
    }
}
