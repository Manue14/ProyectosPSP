/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplo6;
import java.io.*;
import java.util.*;
/**
 *
 * @author user
 */
public class Ejemplo6 {

    
    public static void main(String[] args) {
        // verificamos funcionamiento método command() de la clase ProcessBuiler
		ProcessBuilder test=new ProcessBuilder();
		//con esta llamada al método modificamos los argumentos del objeto ProcessBuilder 
		
		test = test.command("CMD", "/C", "DIR");
		
		// con esta llamada  el método devuelve el nombre del proceso y sus argumentos
		List l = test.command();
		Iterator iter = l.iterator();
		System.out.println("\nArgumentos del comando:");
		while (iter.hasNext())
			System.out.println(iter.next());

		// ejecutamos el comando DIR
		
		try {
			Process p = test.start();// retorna objeto clase Process
			InputStream is = p.getInputStream();
		    //leeremos en is lo que el proceso hijo referenciado desde p
			// escribe en su salida standar
			
			System.out.println();
			// mostramos en pantalla caracter a caracter
			 int c;
			 //lectura secuencial de la información obtenida por comando DIR "hijo"
			 while ((c = is.read()) != -1)
				System.out.print((char) c);
			 is.close();
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
