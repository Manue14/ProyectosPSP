
package ejercicio3_cliente;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EJERCICIO3_CLIENTE {

    
    public static void main(String[] args) {
        try {
            // Creando socket cliente
            Socket clientSocket = new Socket();
           // Estableciendo la conexión
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            clientSocket.connect(addr);
            // Cerrando el socket cliente
            clientSocket.close();
           // Terminado
           } catch (IOException e) {
                    e.printStackTrace();
                 }
    }
    
}
