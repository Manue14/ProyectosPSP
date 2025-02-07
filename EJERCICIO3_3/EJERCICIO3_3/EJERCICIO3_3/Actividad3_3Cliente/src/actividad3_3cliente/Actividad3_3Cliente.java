/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_3cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author user
 */
public class Actividad3_3Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String Host = "localhost";//HOST DEL SERVIDOR
	int Puerto = 6000;//puerto remoto, puerto por donde escucha el servidor	
	
	System.out.println("PROGRAMA CLIENTE INICIADO....");
        Socket cliente=null;
        try{
	     cliente = new Socket(Host, Puerto);
        }catch (IOException i){
              i.printStackTrace();
        }

        //USO DE LAS CLASES DataOutputStream y DataInputStream, para escribir y leer datos primitivos java 
	// CREO FLUJO DE SALIDA AL SERVIDOR, EN ESTE FLUJO ESCRIBO PARA ENVIAR AL SERVIDOR..	
	DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());	

	// CREO FLUJO DE ENTRADA AL SERVIDOR, PARA LEER DEL SERVIDOR	
	DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());

	// EL servidor ME ENVIA UN MENSAJE...
	String cadena= flujoEntrada.readUTF();
	System.out.println("Recibiendo mensaje del SERVIDOR: \n\t" + cadena);

	// LE ENVIO DE NUEVO EL MENSAJE AL SERVIDOR en minúsculas
	flujoSalida.writeUTF(cadena.toLowerCase());
	
	// CERRAR STREAMS Y SOCKETS	
	flujoEntrada.close();	
	flujoSalida.close();	
	cliente.close();
	
    }
    
}
