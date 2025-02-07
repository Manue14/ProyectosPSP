/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_5cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class Actividad3_5Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String Host = "localhost";
	int Puerto = 6000;// puerto remoto

		Scanner sc = new Scanner(System.in);

		Socket cliente = null;
		try {
			cliente = new Socket(Host, Puerto);
			System.out.println("PROGRAMA CLIENTE INICIADO....");
		} catch (ConnectException ce) {
			System.out.println("ERROR AL ESTABLECER LA CONEXIÓN CON EL SERVIDOR....");
			System.exit(0);
		}
		// preparamos Flujo de entrada para leer objetos desde servidor
		ObjectInputStream Ent = new ObjectInputStream(cliente.getInputStream());

		// preapramos FLUJO DE salida para escribir objetos al servidor
		ObjectOutputStream Sal = new ObjectOutputStream(cliente.getOutputStream());

		int numero = 0;

		do {

			System.out.print("Introduce un número para fin 0 : ");

			try {
				numero = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException nn) {
				sc.nextLine();
				numero = 1;
				System.out.print("\tNúmero incorrecto...\n");
				continue;
			}
			Numeros n = new Numeros();
			n.setNumero(numero);

			// Se envía el objeto al servidor, si envió objeto con atributo numero 0 o menor que 0 servidor
                        //finaliza
			Sal.writeObject(n);

			
			if (numero > 0) {
                               // Se recibe un objeto desde el servidor
				Numeros dato = (Numeros) Ent.readObject();// recibo objeto desde servidor

				System.out.println("\tCuadrado : " + dato.getCuadrado() + ", Cubo: * " + dato.getCubo());
			}

		} while (numero > 0);

		System.out.println("CLIENTE FINALIZADO....");

		// CERRAR STREAMS Y SOCKETS
		Ent.close();
		Sal.close();
		cliente.close();
    }
    
}
