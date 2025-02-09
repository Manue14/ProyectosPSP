package entities;

public class Pregunta {
    private int id;
    private String cadena;

    public Pregunta(String cadena) {
        this.cadena = cadena;
    }

    public Pregunta(int id, String cadena) {
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