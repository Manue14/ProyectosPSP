/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplo2error;
import java.io.*;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 *
 * @author mercedes
 */
public class Ejemplo2error {
public static void main(String[] args) throws IOException {

	Process p = new ProcessBuilder("CMD", "/C", "DIRR").start();
        //forzamos error en argumentos del objeto ProcessBuilder , comando erróneo
        try {

	    InputStream is = p.getInputStream();
        //ACCEDMOS A LA SALIDA ESTANDAR DEL PROCESO HIJO REFERENCIADO DESDE p
	//para leerla caracter a caracter y mostrarla en nuestra salida estándar   
	    int c;
	    while ((c = is.read()) != -1)
		System.out.print((char) c);
	    is.close();

				
	} catch (Exception e) {
					e.printStackTrace();
	      		       }

	// COMPROBACION DE  valor de salida de proceso 
	int exitVal;
	try {
		exitVal = p.waitFor(); //el proceso se bloquea en espera de la terminación del hijo
		System.out.println("Valor de Salida: " + exitVal);
	   } catch (InterruptedException e) {
	    e.printStackTrace();
	    }
	//ACCEDEMOS AL BUFFER DE ERRORES DEL PROCESO HIJO 			
	try {
		InputStream er = p.getErrorStream();					
		BufferedReader brer = new BufferedReader(new InputStreamReader(er));
		String liner = null;
		while ((liner = brer.readLine()) != null)
				System.out.println("ERROR >" + liner);
	} catch (IOException ioe) {
			ioe.printStackTrace();
	}

   }
    
}//fin ejemplo2error
