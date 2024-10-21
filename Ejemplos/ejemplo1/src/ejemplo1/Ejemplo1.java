/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplo1;
import java.io.IOException;
/**
 *
 * @author mercedes
 */
public class Ejemplo1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {	   
	   ProcessBuilder pb = new ProcessBuilder("NOTEPAD");
	   Process p = pb.start();

   }
    
}
