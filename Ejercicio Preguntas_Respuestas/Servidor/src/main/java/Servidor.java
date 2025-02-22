import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import service.ServerThread;

public class Servidor {
    private static final String PROPERTIES_FILE = "config.properties";

    public static void main(String[] args) {
        try {
            ServerSocket socketServidor = new ServerSocket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            socketServidor.bind(addr);

            int contador = 1;
            while (true) {
                ServerThread cliente = null;
                
                try {
                    cliente = new ServerThread(socketServidor, PROPERTIES_FILE, contador);
                } catch (Exception e) {
                    System.out.println("Error al conectarse con el cliente: " + e.getMessage());
                }
                
                if (cliente != null) {
                    cliente.start();
                    contador++;
                } 
            }
        } catch (IOException e) {
            System.out.println("Error al inicializar el servidor: " + e.getMessage());
        }
    }
}
