import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private static final String EXIT_STRING = "SALIR";
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            clientSocket.connect(addr);

            InputStream is = clientSocket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            OutputStream os = clientSocket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            Scanner sc = new Scanner(System.in);
            String pregunta = "";
            String respuesta = "";

            do {
                System.out.println("Pregúntale algo al servidor:");
                pregunta = sc.nextLine();
                pw.println(pregunta);
                respuesta = br.readLine();
                if (respuesta == null) {
                    throw new ConnectException();
                }
                System.out.println(respuesta);
            } while (pregunta.compareTo(EXIT_STRING) != 0);

            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Servidor desconectado: " + e.getMessage());
        }

    }
}
