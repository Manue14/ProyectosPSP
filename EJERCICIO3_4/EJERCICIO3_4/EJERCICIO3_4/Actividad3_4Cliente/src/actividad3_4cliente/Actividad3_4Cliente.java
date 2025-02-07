/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_4cliente;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.net.Socket;
import java.util.Scanner;
/**
 *
 * @author user
 */
public class Actividad3_4Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args  )throws IOException {
                String Host = "localhost";//Host del servidor
		int Puerto = 6000;// puerto remoto por el que escucha el servidor
		Socket Cliente = new Socket(Host, Puerto); //creación del socket cliente y petición de conexión

		// CREO FLUJO DE SALIDA AL SERVIDOR, una vez conectado el socket
		OutputStream salida = null;
		salida = Cliente.getOutputStream();//método de la clase Socket 
		DataOutputStream flujoSalida = new DataOutputStream(salida);

		// CREO FLUJO DE ENTRADA AL SERVIDOR, una vez conectado el socket 
		InputStream fentrada = null;
		fentrada = Cliente.getInputStream();//método de la clase Socket
		DataInputStream flujoEntrada = new DataInputStream(fentrada);

		// ENTRADA ESTANDAR DE ESTE PROCESO
		Scanner sc = new Scanner(System.in);

		System.out.print("Introduce un número: ");
		String cadena = sc.nextLine();
		Integer n;
		try {
			n = Integer.parseInt(cadena);
		} catch (NumberFormatException e) {
			System.out.print("Número introducido incorrecto.");
			Cliente.close();
			return;
		}

		flujoSalida.writeInt(n);//enviamos número al servidor
		
		int cuadrado = flujoEntrada.readInt();//leemos ENTERO :número al cuadrado DEL SERVIDOR
		
		System.out.println("EL CUADRADO ES => " + cuadrado);

		flujoSalida.close();
		flujoEntrada.close();
		System.out.println("Fin de proceso... ");

		Cliente.close();
    }
    
}
