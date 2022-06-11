/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package constructors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Kazwell
 */
public class Test {
    private HashMap<Integer,Livre> LivresTest = new HashMap<>();
    public Test(){
        LivresTest.put(0, new Livre("A","A","A","A","A","A"));
        LivresTest.put(1, new Livre("B","B","B","B","B","B"));
        List<Livre> list = new ArrayList<>(LivresTest.values());
        list.forEach(s -> {
            System.out.println(s.getAnnee());
        });
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Test t = new Test();
    }
}
