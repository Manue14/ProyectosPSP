package actividad3_8servidor;
import java.io.*;
import java.net.*;

public class HiloServidor extends Thread {
	DataInputStream fentrada;	
	Socket socket = null; //para recibir el socket que me pasa el hilo primario servidor
	DataOutputStream fsalida=null;
        // constructor 
	public HiloServidor(Socket s) {
		socket = s;//recibo socket de cliente del hilo primario servidor
		try {
			// CREO FLUJO DE entrada para leer del  proceso cliente 
                        // CREO FLUJO DE salida para escribir al proceso cliente 
			fentrada = new DataInputStream(socket.getInputStream());		
			fsalida = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("ERROR DE E/S");
			e.printStackTrace();
		}
	}// ..
           //metodo run() del hilo, tarea que realiza el hilo que atiende a cada cliente
	public void run() {
		// uso objeto InetAddress,obtememos IP del cliente al que estamos conectados
                // puerto remoto alque estamos conectados,puerto por el que está el cliente uso getPort()
		InetAddress direccion = socket.getInetAddress();
		System.out.println("=>Conecta IP " + direccion + 
				", Puerto remoto: " + socket.getPort() + socket.toString());
		
		// tarea con el cliente
		while (true) {
			String cadena = "";
			try {
				cadena = fentrada.readUTF();
				if (cadena.trim().equals("*")) 
                                {// CUANDO UN CLIENTE SE DESCONECTA RECIBO *						
					break; 
				}				
				String mayuscula=cadena.toUpperCase();//paso a mayuscula
				fsalida.writeUTF(mayuscula);//escribo en fsalida al cliente
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
			
			
			
		}// fin while el cliente ha enviado "*" para desconectarse y finalizar
		
		try {
			socket.close();
			System.out.println("\t=>Desconecta IP " + direccion + 
					", Puerto remoto: " + socket.getPort());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//run	
}// ..HiloServidor