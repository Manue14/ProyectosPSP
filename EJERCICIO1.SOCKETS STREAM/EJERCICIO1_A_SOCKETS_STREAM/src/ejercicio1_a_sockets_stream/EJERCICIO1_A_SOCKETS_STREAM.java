/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio1_a_sockets_stream;

/**
 *
 * @author user
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/*PROCESO CLIENTE.LEE DE UN FICHERO TEXTO Y LO ENVÍA AL PROCESO SERVIDOR, PROCESO B**/

public class EJERCICIO1_A_SOCKETS_STREAM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      try {
            // Creando socket cliente
            Socket clientSocket = new Socket();
            // Estableciendo la conexión
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            clientSocket.connect(addr); 
            //FLUJO DE ESCRITURA SOBRE EL SOCKET
            //ESTE CLIENTA SOLO VA A ESCRIBIR EN EL SOCKET, LE ENVIA UN FICHERO
            
            OutputStream os = clientSocket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            //PROCESO DE LECTURA SOBRE EL FICHERO DE TEXTO
            // Abriendo el fichero
            BufferedReader br = new BufferedReader(new FileReader("Fichero_Texto.txt"));
            //CICLO DE LECTURA SECUENCIAL DEL FICHERO TEXTO Y ENVÍO A PROCESO SERVIDOR 
                 while (br.ready())
                 {
                   String line = br.readLine();
                   pw.println(line);// ESCRIBO EN EL SOCKET
                 }  
            // Cerrando el socket cliente
             clientSocket.close(); 
      }   catch (IOException e) {
                                 e.printStackTrace();
                                 }
    }//FIN MAIN
    
}
