import entities.Pregunta;
import entities.Respuesta;
import service.DatabaseManager;
import utils.Utils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Servidor {
    private static final String PREGUNTA_NOT_EXISTS_MSG = "Lo siento, esa pregunta no existe en la base de datos";
    private static final String EXIT_STRING = "Adios";
    private static final String READ_ERROR_MSG = "Parece que hubo un problema al leer tu mensaje, por favor intentalo de nuevo";

    public static void main(String[] args) {
        try {
            DatabaseManager dbManager = new DatabaseManager("config.properties");

            ServerSocket socketServidor = new ServerSocket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            socketServidor.bind(addr);
            Socket newSocket = socketServidor.accept();

            InputStream is = newSocket.getInputStream();
            OutputStream os = newSocket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            PrintWriter pw = new PrintWriter(os, true);

            String userMsg = "";
            Optional<String> optString;

            while (userMsg.compareTo(EXIT_STRING) != 0) {
                optString = Utils.readMessage(br);
                if (optString.isEmpty()) {
                    pw.println(READ_ERROR_MSG);
                    continue;
                }
                userMsg = optString.get();

                if (userMsg.compareTo(EXIT_STRING) == 0) {
                    pw.println("Bye-bye");
                    continue;
                }

                Optional<Pregunta> optPregunta = dbManager.getPregunta(userMsg);

                optPregunta.ifPresentOrElse(
                        pregunta -> {
                            List<Respuesta> respuestas = dbManager.getRespuestasOfPregunta(pregunta);
                            if (!respuestas.isEmpty()) {
                                String completeRespuesta = "";
                                for (Respuesta respuesta : respuestas) {
                                    completeRespuesta += respuesta.getCadena() + "-[]-";
                                }
                                pw.println(completeRespuesta);
                            } else {
                                pw.println("Parece que esta pregunta no tienes respuestas o hubo algún error a la hora de encontrarlas");
                            }
                        },
                        () -> {
                            pw.println(PREGUNTA_NOT_EXISTS_MSG);
                        }
                );

            }

            newSocket.close();
            socketServidor.close();
            dbManager.closeConnection();
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
