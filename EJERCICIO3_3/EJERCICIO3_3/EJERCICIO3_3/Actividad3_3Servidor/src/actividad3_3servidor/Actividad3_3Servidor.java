/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_3servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 Uso de la clase DataOutputStream.

Uso de la clase DataInputStream.

 */
public class Actividad3_3Servidor {

    
    public static void main(String[] arg) throws IOException {
    int numeroPuerto = 6000;// Puerto POR EL QUE ESCUCHARÁ EL SERVIDOR
    ServerSocket servidor = null;
                // creamos un objeto de tipo ServerSocket,por el que el servidor escuchará
                // peticiones de conexión de los clientes,escucha por el puerto numeroPuerto,
                // se considera el tratamiento de excepciones        
                try{
		servidor = new ServerSocket(numeroPuerto);
                } catch(IOException io){
                    io.printStackTrace();
                }
               
		Socket clienteConectado = null;
		System.out.println("Esperando al cliente.....");
                // creamos un objeto de tipo Socket,se usa método accept que retorna
                // objeto de tipo Socket al establecer la conexión con el cliente
                try{
		clienteConectado = servidor.accept();
                }catch(IOException io){
                   io.printStackTrace(); 
                }
		// CREO DESDE SERVIDOR FLUJO DE SALIDA AL CLIENTE
		OutputStream salida = null;
                try{
		salida = clienteConectado.getOutputStream();//método de la clase Socket
                }catch (IOException e1){
                    e1.printStackTrace();
                }
		DataOutputStream flujoSalida = new DataOutputStream(salida);
                // La clase DataOutputStream, dispone de métodos para escribir tipos primitivos Java
                //writeInt(),writeDouble(),writeBytes(),writeUTF(), etc.
                // ENVIO UN SALUDO AL CLIENTE
		flujoSalida.writeUTF("SALUDOS AL CLIENTE DESDE EL SERVIDOR......  ");

                // CREO DESDE EL SERVIDOR FLUJO DE ENTRADA DEL CLIENTE
		InputStream entrada = null;
                try{
		entrada = clienteConectado.getInputStream();//método de la clase Socket
                }catch(IOException e){
                    e.printStackTrace();
                }
		DataInputStream flujoEntrada = new DataInputStream(entrada);
                // la clase DataInputStream permite la lectura de líneas de texto y tipos primitivos Java
                //readInt(),readDouble(),readLine(),readUTF(), etc.
                
	// EL CLIENTE ME ENVIA UN MENSAJE
		System.out.println("Recibiendo mensaje del CLIENTE: \n\t" + 
		     flujoEntrada.readUTF());

		// CERRAR STREAMS Y SOCKETS
                // el orden de cierre es relevante, primero se han de cerrar los streams relacionados
                // con un Socket antes que el propio Socket
                try {
		entrada.close();
		flujoEntrada.close();
		salida.close();
		flujoSalida.close();
		clienteConectado.close();//CIERRO SOCKET CLIENTE
		servidor.close();//CIERRO SOCKET SERVIDOR

               } catch (IOException i){
                    i.printStackTrace();
               }
    
}//main
    
}
