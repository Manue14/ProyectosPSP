package actividad3_10servidor;

import java.io.*;
import java.net.*;

//toda la funcionalidad del servidor est� aqu� encapsulada
//hay un servidor,(un hilo) atendiendo a cada cliente, y ejecut�ndose paralelamente,
//es decir simult�neamente, a la vez, tenemos tantos hilos corriendo a la vez como clientes jugando
//implementamos el hiloservidor de la primera forma que hemos aprendido:
// cada hilo ser� un objeto de una clase que extiende la clase Thread
// hereda todos los m�todos y propiedades de esta clase, entre ellos
// el m�todo run() donde tenemos que dise�ar y codificar la tarea que realiza el hilo 


public class HiloServidorAdivina extends Thread {
	ObjectInputStream fentrada;
	ObjectOutputStream fsalida;
 //los hilos servidor van a leer y escribir objetos a traves del socket 
 //conectado a su cliente, necesitar� un objeto de cada una de estas clases

	Socket socket = null;//para recibir el socket

	ObjetoCompartido objeto;//para recibir el objeto (mismo para todos los hilos del lado servidor )
	int identificador; //para recibir el identificador del cliente
	int intentos = 0; //contador de intentos consumidos
      //constructor,  1 parametro: recibe un socket ya conectado a su cliente, es un socket stream TCP
      // porque hemos recibido un objeto de la clase Socket, de modo que tenemos un canal de comunicaci�n
      // permanente con un determinado proceso cliente	
     //	 2 parametro, recibe un objeto, que es el mismo para todos los hilos vivos en cada instante
     //  del proceso-->ESTO IMPLICA QUE tenemos que sincronizar los hilos, los hilos  que se est�n ejecutando paralelamente,
	 //tendr�n que ejecutarse sincr�nicamente, es decir, sincronizar los accesos a objeto 	 


	 public HiloServidorAdivina(Socket s, ObjetoCompartido objeto, int id) {
		this.socket = s;
		this.objeto = objeto;
		this.identificador = id;
		//obtengo los stream de escritura  y de lectura de objetos
		try {
			 fsalida = new ObjectOutputStream(socket.getOutputStream());
			 fentrada = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("ERROR DE E/S en HiloServidor");
			e.printStackTrace();
		}
	}// ..fin constructor

	// ----------------------------------------------------------------
	//------------------- tarea ------------------
	// nunca se llama o implementa directamente este método para un hilo
	// el método start() llama al método run() del hilo
	public void run() {
		//en salida del servidor		
		System.out.println("=>Cliente conectado: " + identificador);
		int intentos=0; 

		//PREPARAR PRIMER ENVIO AL CLIENTE	
		//objeto datos es el que intercambio entre servidor y cliente
		// es un nuevo objeto distinto para cada cliente,
		//lo uso para "hablar, escribir" con mi cliente; mi cliente tiene uno igual 
		Datos datos = new Datos("Adivina un NUMERO ENTRE 1 Y 25", intentos, identificador);
		
		if (objeto.seAcabo()) {
			datos.setCadena("LO SENTIMOS, EL JUEGO HA TERMINADO, YA HAN ADIVINADO EL NUMERO");
			datos.setJuega(false); // ya nopuede  JUGAR nuestro cliente se lo decimos en juega a falso
		}
        // ESCRIBO AL CLIENTE
		try {
			fsalida.reset();
			fsalida.writeObject(datos);// envio objeto a trav�s del socket
		} catch (IOException e1) {
			System.out.println("Error en el Hilo al realizar " + 
		                  "el primer envio al jugador: " + identificador);
			return;
		}
       //jugamos..
		while (!objeto.seAcabo() && !(datos.getIntentos() == 5)) {
			// mientras no se haya acabado el juego y  t� no lleves 5 intentos 
			int numecli = 0; //para recibir nueva apuesta del cliente
			//lectura desde el socket, escuchamos al cliente
                        
			try {
				Datos d = (Datos) fentrada.readObject(); // leer numero del cliente
				numecli = Integer.parseInt(d.getCadena());
			} catch (IOException e) {
				System.out.println("Error en el Hilo al leer del jugador: " + identificador);
				break;
			} catch (NumberFormatException n) {
				System.out.println("El jugador: " + identificador + " se ha desconectado");
				break;
			} catch (ClassNotFoundException e) {				
				e.printStackTrace();
				break;
			}
			// jugar el numero
			//AQU� LLAMADA A UN M�TODO SINCRONIZADO
			//EL HILO TOMA EL MONITOR DEL OBJETO objeto y ejecuta el m�todo (est� �l jugando) 
			// O EL HILO SE BLOQUEA ESPERANDO POR EL MONITOR DEL OBJETO 
			String cad = objeto.nuevaJugada(identificador, numecli);
			intentos++;
			datos = new Datos(cad, intentos, identificador);
            
			if (objeto.seAcabo()) {
				datos.setJuega(false); // ya no tiene que seguir jugando
				if (identificador == objeto.getGanador())
					datos.setGana(true);				
			}

			try {
				fsalida.reset();
				fsalida.writeObject(datos);				
			} catch (IOException n1) {
				System.out.println(
						"Error escribiendo en flujo de salida del jugador: " + identificador + " * " + n1.getMessage());
				break;
			} catch (NullPointerException n) {// se produce cuando el cliente				
				System.out.println("El jugador  " + identificador + " ha desconectado ");
				break;
			}

		} // fin while

		if (objeto.seAcabo()) {
			System.out.println("EL JUEGO SE HA ACABADO.....");
			System.out.println("\t==>Desconecta: " + identificador);
		}
		try {
			fsalida.close();
			fentrada.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Error en Hilo al cerrar cliente: " + identificador);
			e.printStackTrace();
		}

	}// run

}// ..