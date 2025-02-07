/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_6servidor;
import java.io.*;
import java.net.*;

/**
 *
 * @author user
 */
public class HiloServidor extends Thread {
    BufferedReader fentrada;
    PrintWriter fsalida;
    Socket socket=null;
                   //constructor del hilo, recibe el socket cliente desde hilo primario servidor
                   // recibe el socket y  el hilo se encarga ahora de crear flujos de entrada y salida con el cliente 
    
    public HiloServidor(Socket s) throws IOException {// CONSTRUCTOR
		socket = s;
		//el hilo recibe el socket conectado al cliente
		// el hilo se encarga de crear flujos de entrada y salida para el socket
		fsalida = new PrintWriter(socket.getOutputStream(), true);
		fentrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
    
    public void run() {// tarea a realizar con el cliente, tarea del hilo
		String cadena = null;

		System.out.println("COMUNICO CON: " + socket.toString());
        try {
		cadena = fentrada.readLine();
	} catch (IOException e) {
				// TODO Auto-generated catch block
	   e.printStackTrace();
	} // obtener  1º cadena del cliente
	while (!cadena.trim().equals("*")) {

	 fsalida.println(cadena.trim().toUpperCase());// enviar mayúscula
	try {
		cadena = fentrada.readLine();
	 } catch (IOException e) {
		// TODO Auto-generated catch block
	  e.printStackTrace();
	  } // obtener  siguiente cadena del cliente
	} // fin while

	System.out.println("FIN CON: " + socket.toString());
       
		 
		
		 
		try {
                        fsalida.close();
			fentrada.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
}
