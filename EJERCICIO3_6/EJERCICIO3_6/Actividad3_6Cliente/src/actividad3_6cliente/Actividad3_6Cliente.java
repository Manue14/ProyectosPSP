/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_6cliente;
import java.io.*;
import java.net.*;
/**
 *
 * @author user
 */
public class Actividad3_6Cliente {


   public static void main(String[] args) throws IOException {
	String Host = "localhost";// HOST DEL SERVIDOR
	int Puerto = 6000;// puerto remoto por el que escucha el servidor
	Socket Cliente = new Socket(Host, Puerto);
		
	// CREO FLUJO DE SALIDA AL SERVIDOR	
	PrintWriter fsalida = new PrintWriter (Cliente.getOutputStream(), true);
	// CREO FLUJO DE ENTRADA AL SERVIDOR	
	BufferedReader fentrada =  new BufferedReader
	     (new InputStreamReader(Cliente.getInputStream()));
		 
	// FLUJO PARA  MI ENTRADA ESTANDAR
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	String cadena, eco="";
	
	do{ 
		System.out.print("Introduce una cadena,para finalizar: * ");
		cadena = in.readLine();
		fsalida.println(cadena);// se la envío al servidor
		eco=fentrada.readLine(); // leo del servidor			
		System.out.println("  =>ECO: "+eco);	
	} while(!cadena.trim().equals("*"));
		
	fsalida.close();
	fentrada.close();
	System.out.println("Fin del proceso con el servidor... ");
	in.close();
	Cliente.close();
	}//
    
}
