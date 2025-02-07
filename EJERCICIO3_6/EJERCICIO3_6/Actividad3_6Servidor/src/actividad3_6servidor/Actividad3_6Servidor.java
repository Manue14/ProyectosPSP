/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_6servidor;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author user
 */
public class Actividad3_6Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws IOException {
       ServerSocket servidor=null;
       servidor=new ServerSocket(6000); //socket servidor implementado, escuchando por el puerto 6000
       System.out.println("Servidor iniciado.....");
       //Ciclo con la tarea del hilo primario servidor, atendiendo peticiones de conexión de los clientes
       // en un ciclo sin fin
       while(true){
           Socket cliente = new Socket();          
           cliente=servidor.accept();
           HiloServidor hilo=new HiloServidor(cliente);
           hilo.start();
       }
       
    }//main
    
}
