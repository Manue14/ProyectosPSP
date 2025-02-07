/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio2_cliente;

/**
 *
 * @author user
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
public class EJERCICIO2_CLIENTE {

    
    public static void main(String[] args) {
        try {
               // Creando socket cliente
              Socket clientSocket = new Socket();
              // Estableciendo la conexión
              InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
              clientSocket.connect(addr);
              //BUFFER PARA LEER DESDE EL SOCKET
              InputStream is = clientSocket.getInputStream();
              BufferedReader br = new BufferedReader(new InputStreamReader(is));
              //BUFFER PARA ESCRIBIR EN EL SOCKET
              OutputStream os = clientSocket.getOutputStream();             
              PrintWriter pw = new PrintWriter(os, true);
              
              String pregunta = "¿Cómo te llamas?";
              System.out.println("Pregunta: "+pregunta);
              pw.println(pregunta);
              
              // Leyendo la respuesta DEL SERVIDOR
              String respuesta = br.readLine();
              System.out.println("Respuesta: "+respuesta);
              
              // Cerrando el socket cliente
              clientSocket.close();
           } catch (IOException e) {
                  e.printStackTrace();
              }   
              
    }
    
}
