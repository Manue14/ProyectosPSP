package service;

import entities.Pregunta;
import entities.Respuesta;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

public class DatabaseManager {
    private Connection connection;
    
    public DatabaseManager(String propertiesFile) throws Exception {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(propertiesFile);

        if (input == null) {
            throw new FileNotFoundException("Error, archivo de configuración no encontrado");
        }

        Properties properties = new Properties();
        properties.load(input);
        
        this.connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        
        System.out.println("Conexión realizada con éxito");
    }

    /*public DatabaseManager(String propertiesFile) throws Exception {
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
    }*/

    public void closeConnection() throws Exception{
        this.connection.close();
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

    public Optional<Respuesta> getRespuestaById(Integer id) {
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
        return Optional.ofNullable(respuesta);
    }
    
    public Optional<Pregunta> getPreguntaById(int id) {
        Pregunta pregunta = null;
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM preguntas WHERE id = ?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                pregunta = new Pregunta(result.getInt("id"), result.getString("cadena"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la respuesta: " + e.getMessage());
        }
        return Optional.ofNullable(pregunta);
    }

    public Optional<Respuesta> getRespuestaOfPregunta(Pregunta pregunta) {
        Respuesta respuesta = null;
        try {
            CallableStatement statement = this.connection.prepareCall(
                    "{? = call get_respuesta_from_pregunta(?)}"
            );
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, pregunta.getCadena());
            
            statement.execute();

            return getRespuestaById(statement.getInt(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(respuesta);
    }
}