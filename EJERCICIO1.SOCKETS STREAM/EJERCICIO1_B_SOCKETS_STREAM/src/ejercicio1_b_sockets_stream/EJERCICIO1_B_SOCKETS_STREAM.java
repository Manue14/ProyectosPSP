/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio1_b_sockets_stream;

/**
 *
 * @author user
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EJERCICIO1_B_SOCKETS_STREAM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
              // Creando socket servidor
              ServerSocket socketservidor = new ServerSocket();
              // Realizando el bind
              InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
              socketservidor.bind(addr);
              // Aceptando conexiones
              Socket newSocket = socketservidor.accept();
              //flujo de entrada, para leer del socket
              InputStream is = newSocket.getInputStream();
              BufferedReader br = new BufferedReader(new InputStreamReader(is));
              //ciclo de lectura desde el socket
              String line = br.readLine();
              while (line != null) {
                        System.out.println(line);
                        line = br.readLine();
                }
              // Cerrando el nuevo socket
              newSocket.close();
              //elproceso servidor continua a la escucha desde socket servidor
              //Cerrando el socket servidor
              socketservidor.close();
       } catch (IOException e) {
                         e.printStackTrace();
          }                    
                  
    }
    
}
