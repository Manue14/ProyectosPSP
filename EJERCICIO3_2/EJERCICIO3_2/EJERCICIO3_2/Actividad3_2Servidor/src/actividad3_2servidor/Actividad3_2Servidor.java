/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_2servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author user
 */
public class Actividad3_2Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
                int Puerto = 6000;// Puerto 
                ServerSocket servidor =null;
                try{
		       servidor = new ServerSocket(Puerto);
                }catch (IOException io) {
                    io.printStackTrace();
                }
                //Servidor es un ServerSocket conectado al puerto 6000,por este puerto el proceso servidor
                // escuchará peticiones de conexión de los procesos cliente 
		System.out.println(" Servidor escuchando por el puerto " + servidor.getLocalPort());	
		
		System.out.println("Esperando primer cliente..........");
                Socket cliente1=null;
                try{
		       cliente1= servidor.accept();//esperando a un cliente 
                }catch (IOException io) {
                    io.printStackTrace();
                }
		
                //el método accept escucha una solicitud de conexión,una vez establecida la conexión
                // devuelve un objeto de tipo Socket, por el Socket cliente1 el proceso servidor leerá del proceso cliente 
                //y escribirá al proceso cliente
		System.out.println("Primer cliente atendido........");
		System.out.println("Puerto local  en el proceso servidor para el cliente 1 getLocalPort():  " +cliente1.getLocalPort());
		System.out.println("Puerto remoto del cliente getPort():  " +cliente1.getPort() );

		// aquí  se realizarían  operaciones con cliente1
		
		System.out.println("Esperando segundo cliente..........");
                Socket cliente2=null;
                try{
		       cliente2= servidor.accept();//esperando al segundo cliente 
                }catch (IOException io) {
                    io.printStackTrace();
                }
		
		System.out.println("Segundo cliente atendido...........");
		System.out.println("Puerto local  en el proceso servidor para el cliente 2 getLocalPort(): " +cliente2.getLocalPort());
		System.out.println("Puerto remoto del cliente 2 getPort(): " +cliente2.getPort() );

		
		// aquí realizar acciones con cliente2
		servidor.close();	//cierro socket servidor
	}

   
    
}
