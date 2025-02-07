/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_2cliente;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author user
 */
public class Actividad3_2Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws UnknownHostException, IOException {
                String Host = "localhost";
		int Puerto = 6000;//puerto remoto por el que escucha el servidor, tiene
                          // que ser conocido por el cliente		
			
		// ABRIR SOCKET 
                Socket cliente=null;
                try{
		    cliente = new Socket(Host, Puerto);
                       //crea un socket y lo conecta al número de puerto
                       // y al nombre de host especificado
                }catch (IOException io) {
                       io.printStackTrace();
                }

		InetAddress i= cliente.getInetAddress ();// objeto InetAddress dirección IP a la que el
                //socket referenciado (cliente) está conectado
                
		System.out.println ("Puerto local del cliente: "+ cliente.getLocalPort());
		System.out.println ("Puerto Remoto al que está conectado, puerto del servidor : "+ cliente.getPort());
		System.out.println ("Nombre Host/IP: "+ cliente.getInetAddress());
		System.out.println ("Host Remoto: "+ i.getHostName().toString());
		System.out.println ("IP Host Remoto: "+ i.getHostAddress().toString());
		 // Cierra el socket
		 try{
                     cliente.close();
                 }
                 catch (IOException e){
                    e.printStackTrace();

                }
    
}
}