/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_8servidor;
import java.io.*;
import java.net.*;
/**
 *
 * @author user
 */
public class Actividad3_8Servidor {
        static ServerSocket servidor;
	static final int PUERTO = 44444;// puerto por el que escucho
    
    public static void main(String[] args)throws IOException {
        servidor = new ServerSocket(PUERTO);
	System.out.println("Servidor iniciado...");
        while (true) {
			Socket s = new Socket();
			try {
				s = servidor.accept();// esperando cliente
			} catch (SocketException ns) {				
				 //sale por aqui si pulsamos boton salir y no se ejecuta todo el bucle			 
				break;
			}			
			//lanzamos un hilo para cada cliente con su socket 	
			HiloServidor hilo = new HiloServidor(s);
			hilo.start();
		}
        System.out.println("Servidor finalizado..."); 
    }
    
}
