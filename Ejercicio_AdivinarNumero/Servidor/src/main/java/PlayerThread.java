import java.io.*;
import java.net.Socket;

public class PlayerThread extends Thread {
    private static final int MAX_TURNS = 5;

    private Socket socket;
    private Game game;
    private String name;
    private int turnsLeft;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public PlayerThread(Socket socket, Game game, int i) throws IOException {
        this.socket = socket;
        this.game = game;
        this.name = "Jugador " + i;
        this.turnsLeft = MAX_TURNS;

        this.out = new ObjectOutputStream(this.socket.getOutputStream());
        this.in = new ObjectInputStream(this.socket.getInputStream());

        System.out.println(this.name + " conectado");
    }

    public void close() throws IOException {
        this.in.close();
        this.out.close();
        this.socket.close();

        System.out.println(this.name + " desconectado");
    }

    public void run() {
        do {
            try {
                out.writeObject(game);
                out.flush();
                out.writeInt(turnsLeft);
                out.flush();
                if (game.checkInput(in.readInt())) {
                    System.out.println("Has ganado");
                }

                turnsLeft--;
            } catch(IOException e) {
                System.out.println(this.name + " ha abandonado la partida");
                break;
            }
        } while (turnsLeft > 0);

        try {
            close();
        } catch (IOException e) {
            System.out.println("Error al desconectar al " + name);
        }
    }
}
