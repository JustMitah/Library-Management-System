/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package serveurWS;

import javax.xml.ws.Endpoint;
import operationWS.Librarian;
import operationWS.User;

/**
 *
 * @author Kazwell
 */
public class ServeurWS {


    public static void main(String args[]) {
    /*if (who.equals("Librarian")){
        String url="http://localhost:6685/";
        Endpoint.publish(url, new Librarian());
	System.out.println(url);
    } else{
        String url="http://localhost:6685/";
        Endpoint.publish(url, new User());
	System.out.println(url);
    }*/
        String url="http://localhost:6685/";
        Endpoint.publish(url, new User());
	System.out.println(url);
    }
}
