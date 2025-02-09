package service;

import entities.Pregunta;
import entities.Respuesta;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(String propertiesFile) {
        try(InputStream input = this.getClass().getClassLoader().getResourceAsStream(propertiesFile);) {
            if (input == null) {
                throw new FileNotFoundException("Error, archivo de configuración no encontrado");
            }

            Properties properties = new Properties();
            properties.load(input);
            this.connection = DriverManager.getConnection(properties.getProperty("url"), properties);
            System.out.println("Conexión realizada con éxito");
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println("Error al cargar el archivo de configuración");
        } catch(SQLException e) {
            System.out.println("Error al conectarse a la base de datos: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión con la base de datos: " + e.getMessage());
        }
    }

    public Optional<Pregunta> getPregunta(String cadena) {
        Pregunta pregunta = null;
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM preguntas WHERE cadena = ?");
            statement.setString(1, cadena);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                pregunta = new Pregunta(result.getInt("id"), result.getString("cadena"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la pregunta: " + e.getMessage());
        }
        return Optional.ofNullable(pregunta);
    }

    public Optional<Respuesta> getRespuesta(String cadena) {
        Respuesta respuesta = null;
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM respuestas WHERE cadena = ?");
            statement.setString(1, cadena);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                respuesta = new Respuesta(result.getInt("id"), result.getString("cadena"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la respuesta: " + e.getMessage());
        }
        return Optional.ofNullable(respuesta);
    }

    public Respuesta getRespuestaById(int id) {
        Respuesta respuesta = null;
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM respuestas WHERE id = ?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                respuesta = new Respuesta(result.getInt("id"), result.getString("cadena"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la respuesta: " + e.getMessage());
        }
        return respuesta;
    }

    public List<Respuesta> getRespuestasOfPregunta(Pregunta pregunta) {
        List<Respuesta> respuestas = new ArrayList<>();
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT id_respuesta FROM preguntas_respuestas WHERE id_pregunta = ?");
            statement.setInt(1, pregunta.getId());
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                int id_repuestas = result.getInt("id_respuesta");
                Respuesta respuesta = getRespuestaById(id_repuestas);
                if (respuesta != null) {
                    respuestas.add(respuesta);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return respuestas;
    }
}