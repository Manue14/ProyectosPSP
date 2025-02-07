
package ejercicio3_servidor;

/**
 *
 * @author user
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EJERCICIO3_SERVIDOR {

    
    public static void main(String[] args) {
     try {  
        // Creando socket servidor
         ServerSocket serverSocket = new ServerSocket();
        // Realizando el bind
        InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
        serverSocket.bind(addr);
        // Aceptando conexiones
        int num_conexiones = 0;
        while (true) {
               Socket newSocket = serverSocket.accept();
               num_conexiones++;
               
               System.out.println("Conexión "+num_conexiones+" recibida");
               // Cerrando el nuevo socket
               newSocket.close();
        }
    } catch (IOException e) {
               e.printStackTrace();
       }
    
}
}






