/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio2_servidor;

/**
 *
 * @author user
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class EJERCICIO2_SERVIDOR {

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
              //flujos de entrada y salida, esta vez el servidor envía y lee datos en el socket
              InputStream is = newSocket.getInputStream();
              OutputStream os = newSocket.getOutputStream();
              BufferedReader br = new BufferedReader(new InputStreamReader(is));
              PrintWriter pw = new PrintWriter(os, true);
              // proceso
              String mensaje = "";
              char[] buffer = new char[1];
              int tam = 0;
              //ciclo de lectura de preguntas el cliente y escritura de respuestas
              while ((tam != -1) && (buffer[0] != '?')) {
                      tam = br.read(buffer);
                      if (tam != -1)
                      mensaje = mensaje + buffer[0];//construye mensaje byte a byte

              }
              String respuesta = null;
              if (mensaje.equals("¿Cómo te llamas?"))
                   respuesta = "Me llamo Ejercicio 2";
              else if (mensaje.equals("¿Cuántas líneas de código tienes?"))
                      respuesta = "Tengo 74 líneas de código";
                   else
                      respuesta = "No he entendido la pregunta";
              pw.println(respuesta);
              // Cerrando el nuevo socket
              newSocket.close();
              //Cerrando el socket servidor
             socketservidor.close();
        } catch (IOException e) {
                   e.printStackTrace();
            }     
              
              
    }
    
}
