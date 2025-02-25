import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try(Socket clientSocket = new Socket();) {
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            clientSocket.connect(addr);

            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            Scanner sc = new Scanner(System.in);

            Game game;
            boolean gameOver;
            int turnsLeft;
            do {
                System.out.println("holaaaaaaaaaaaaaaaa");
                game = (Game) in.readObject();
                gameOver = game.isOver();
                if (gameOver) {
                    System.out.println("El juego ya ha acabado");
                    break;
                }
                turnsLeft = in.readInt();
                System.out.println("adiossssssssssssssss");
                System.out.println("Tienes " + turnsLeft + " turnos restantes");

                System.out.println("Introduzca su número");
                System.out.print("> ");
                String input = sc.nextLine();

                try {
                    int n = Integer.parseInt(input);
                    out.writeInt(n);
                } catch (NumberFormatException e) {
                    out.writeInt(-1);
                }

            } while (turnsLeft > 1);
        } catch(IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
