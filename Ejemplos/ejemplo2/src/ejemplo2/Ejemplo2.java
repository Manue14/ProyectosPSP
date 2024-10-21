/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplo2;
import java.io.*;
/**
 *
 * @author mercedes
 */
public class Ejemplo2 {
public static void main(String[] args) throws IOException {

		Process p = new ProcessBuilder("CMD", "/C", "DIR").start();
		try {

			InputStream is = p.getInputStream();

			// leemos salida del proceso p caracter a caracter y la mostramos en pantalla 
			 int c;
			 while ((c = is.read()) != -1)  //-1 caracter  EOF
				System.out.print((char) c);
			 is.close();

		
		} catch (Exception e) {
			e.printStackTrace();
		}

		// COMPROBACION DE ERROR - 0 VALOR TERMINACIÓN CORRECTA DELPROCESO
		int exitVal;
		try {
			exitVal = p.waitFor();  
			System.out.println("Valor de Salida: " + exitVal);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    
    
}
