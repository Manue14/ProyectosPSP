/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_4servidor;
import java.io.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author user
 */
public class Actividad3_4Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws IOException {
                int numeroPuerto = 6000;// Puerto por el que escuchará el servidor
		ServerSocket servidor = null;
                try{
		servidor = new ServerSocket(numeroPuerto);// creamos  objeto ServerSocket
                }catch (IOException i){
                    i.printStackTrace();
                }

		Socket clienteConectado = null;
		System.out.println("Esperando al cliente.....");
                try{
		clienteConectado = servidor.accept();
                }catch(IOException i){
                    i.printStackTrace();
                }
                    

		// CREO FLUJO DE SALIDA AL CLIENTE
		OutputStream salida = null;
		salida = clienteConectado.getOutputStream();
		DataOutputStream flujoSalida = new DataOutputStream(salida);

		// CREO FLUJO DE ENTRADA DE CLIENTE
		InputStream entrada = null;
		entrada = clienteConectado.getInputStream();
		DataInputStream flujoEntrada = new DataInputStream(entrada);

		// EL CLIENTE ME ENVIA UN MENSAJE
                // LEO DEL SOCKET CLIENTE
		
		Integer numero = flujoEntrada.readInt();
		System.out.print("Número enviado por el CLIENTE: "+ numero);
		
		numero = numero * numero;
                //ENVIO AL CLIENTE EL DATO OBTENIDO
		flujoSalida.writeInt(numero);

		// CERRAR STREAMS Y SOCKETS
		try{
                entrada.close();
		flujoEntrada.close();
		salida.close();
		flujoSalida.close();
		clienteConectado.close();
		servidor.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
    }
    
}
