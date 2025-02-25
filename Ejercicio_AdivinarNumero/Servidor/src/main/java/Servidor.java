import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            Game game = new Game();

            ServerSocket socketServidor = new ServerSocket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            socketServidor.bind(addr);

            int contador = 1;
            while (true) {
                Socket newSocket = socketServidor.accept();
                PlayerThread player = null;

                try {
                    player = new PlayerThread(newSocket, game, contador);
                } catch (IOException e) {
                    System.out.println("Error al establecer conexión con el cliente");
                }

                if (player != null) {
                    player.start();
                    contador++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al inicializar el servidor o al conectarse con el cliente : " + e.getMessage());
        }
    }
}
