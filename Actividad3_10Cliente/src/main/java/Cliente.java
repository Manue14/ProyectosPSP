import actividad3_10servidor.Datos;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private static final String EXIT_STRING = "SALIR";
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 6001);
            clientSocket.connect(addr);

            ObjectOutputStream salida = new ObjectOutputStream(clientSocket.getOutputStream());;
            ObjectInputStream entrada = new ObjectInputStream(clientSocket.getInputStream());
            Scanner sc = new Scanner(System.in);
            Datos datos;
            String input;

            do {
                datos = (Datos) entrada.readObject();
                System.out.println(datos.getCadena());

                if (datos.isGana()) {
                    System.out.println("Felicidades has ganado");
                    break;
                }
                if (datos.getIntentos() >= 5) {
                    System.out.println("Mala suerte, te has quedado si intentos");
                    break;
                }
                if (!datos.isJuega()) {
                    System.out.println("El juego ya ha acabado");
                    break;
                }

                System.out.print(">");
                input = sc.nextLine();
                datos.setCadena(input);
                salida.writeObject(datos);
            } while (true);

            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Servidor desconectado: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada: " + e.getMessage());
        }

    }
}
