package utils;

import service.DisconnectedClientException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class Utils {
    public static Optional<String> readMessage(BufferedReader br) throws DisconnectedClientException {
        try {
            String mensaje = "";
            char[] buffer = new char[1];
            int charInteger = 0;

            while (charInteger != -1 && buffer[0] != '\n') {
                charInteger = br.read(buffer);
                if (buffer[0] == Character.MIN_VALUE) {
                    throw new DisconnectedClientException("Cliente desconectado");
                }
                if (charInteger != -1 && buffer[0] != '\n')
                    mensaje = mensaje + buffer[0];
            }

            return Optional.of(mensaje.trim());
        } catch(IOException e) {
            System.out.println("Error al leer el mensaje del cliente");
            return Optional.empty();
        }
    }
}
