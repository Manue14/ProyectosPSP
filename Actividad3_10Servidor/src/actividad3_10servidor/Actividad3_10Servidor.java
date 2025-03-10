/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_10servidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//hilo primario del proceso servidor
// en cada instante del proceso, será un árbol n_ario de hilos
// siendo n el número de jugadores conectados jugando

public class Actividad3_10Servidor {

    
    public static void main(String[] args )throws IOException  {
        ServerSocket servidor = new ServerSocket(6001);
	System.out.println("Servidor iniciado...");		
	
	// Numero a adivinar entre 1 y 25
	int numero = (int) (1 + 25*Math.random()); 
	System.out.println("NUMERO A ADIVINAR=> " +numero);

	ObjetoCompartido objeto = new ObjetoCompartido(numero);
	int id = 0;
	while (true) {	
		Socket cliente = new Socket();//nuevo Socket para cada cliente
		cliente = servidor.accept(); //esperando cliente	
		id++;			
		HiloServidorAdivina hilo = new 
                           HiloServidorAdivina(cliente, objeto, id);
	    //nuevo hilo para cada cliente, comparten todos ellos objeto					   
		hilo.start();		//arranco el hilo y sigo escuchando..
	}
      
    }
    
}
