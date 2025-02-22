/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entities.Pregunta;
import entities.Respuesta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import utils.Utils;

/**
 *
 * @author manu
 */
public class ServerThread extends Thread{
    private static final String PREGUNTA_NOT_EXISTS_MSG = "Lo siento, esa pregunta no existe en la base de datos";
    private static final String RESPUESTA_ERROR_MSG = "Parece que esta pregunta no tienes respuestas o hubo algún error a la hora de encontrarlas";
    private static final String EXIT_STRING = "SALIR";
    private static final String READ_ERROR_MSG = "Parece que hubo un problema al leer tu mensaje, por favor intentalo de nuevo";
    
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private BufferedReader br;
    private PrintWriter pw;
    private DatabaseManager dbManager;
    private int i;
    
    public ServerThread(ServerSocket serverSocket, String propertiesFile, int i) throws Exception{
        this.socket = serverSocket.accept();
        this.is = socket.getInputStream();
        this.os = socket.getOutputStream();
        this.br = new BufferedReader(new InputStreamReader(this.is));
        this.pw = new PrintWriter(os, true);
        this.dbManager = new DatabaseManager(propertiesFile);
        this.i = i;
    }
    
    public void close() throws Exception{
        this.br.close();
        this.pw.close();
        this.is.close();
        this.os.close();
        this.socket.close();
        this.dbManager.closeConnection();
    }
    
    public void run() {
        String userMsg = "";
        Optional<String> optString;
        System.out.println("Cliente " + i + " conectado");

        try {
            while (userMsg.compareTo(EXIT_STRING) != 0) {
                optString = Utils.readMessage(br);

                if (optString.isEmpty()) {
                    pw.println(READ_ERROR_MSG);
                    continue;
                }

                userMsg = optString.get();

                if (userMsg.compareTo(EXIT_STRING) == 0) {
                    pw.println("Bye-bye");
                    break;
                }

                Optional<Pregunta> optPregunta = dbManager.getPregunta(userMsg);

                managePregunta(optPregunta);
            }
        } catch (DisconnectedClientException e) {
            System.out.println("Cliente desconectado, cerrando conexión...");
        }

        try {
            close();
            System.out.println("Cliente " + i + " desconectado");
        } catch (Exception e) {
            System.out.println("Error al cerrar la conexión del cliente");
        }
    }
    
    private void managePregunta(Optional<Pregunta> optPregunta) {
        optPregunta.ifPresentOrElse(
            pregunta -> {
                Optional<Respuesta> optRespuesta = dbManager.getRespuestaOfPregunta(pregunta);
                manageRespuesta(optRespuesta);
            },    
            () -> {
                pw.println(PREGUNTA_NOT_EXISTS_MSG);
            }
        );
    }
    
    private void manageRespuesta(Optional<Respuesta> optRespuesta) {
        optRespuesta.ifPresentOrElse(
            respuesta -> {
                pw.println(respuesta.getCadena());
            },             
            () -> {
                pw.println(RESPUESTA_ERROR_MSG);
            }
        );
    }
}
