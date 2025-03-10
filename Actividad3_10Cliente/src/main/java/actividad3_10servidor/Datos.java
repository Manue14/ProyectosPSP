package actividad3_10servidor;

import java.io.Serializable;

@SuppressWarnings("serial")

//esta clase define el objeto que usamos para leer/escribir entre servidor y cliente
// es uno propio para cada hilo_servidor y uno propio para cada proceso cliente
// COMO SE VAN A ESCRIBIR/LEER A TRAVÉS DE LOS SOCKETS NECESITA IMPLEMENTAR 
// LA INTERFACE Serializable
public class Datos implements Serializable {
	String cadena; //cadena que se intercambia con el servidor
	int intentos;  //intentos que lleva el jugador
	int identificador; //id del jugador
	boolean gana; //true si el jugador adivina el numero
	boolean juega; //true si el jugador juega, false juego finalizado

	
	public Datos(String cadena, int intentos, int identificador) {		
		this.cadena = cadena;
		this.intentos = intentos;
		this.identificador=identificador;
		this.gana=false;
		this.juega=true;		
	}	
	
	public Datos() {
		super();		
	}

	public boolean isJuega() {
		return juega;
	}

	public void setJuega(boolean juega) {
		this.juega = juega;
	}

	public boolean isGana() {
		return gana;
	}

	public void setGana(boolean gana) {
		this.gana = gana;
	}


	public int getIdentificador() {
		return identificador;
	}


	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}


	public String getCadena() {
		return cadena;
	}
	public void setCadena(String cadena) {
		this.cadena = cadena;
	}
	public int getIntentos() {
		return intentos;
	}
	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}
	
	
	

}
