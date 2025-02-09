package entities;

public class Respuesta {
    private int id;
    private String cadena;

    public Respuesta(String cadena) {
        this.cadena = cadena;
    }

    public Respuesta(int id, String cadena) {
        this.id = id;
        this.cadena = cadena;
    }

    public int getId() {
        return id;
    }

    public String getCadena() {
        return cadena;
    }
}
