/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_5cliente;
import java.io.Serializable;

@SuppressWarnings("serial")
/**
 *
 * @author user
 */
public class Numeros implements Serializable {
        int numero;
	long cuadrado;
	long cubo;
	
	
	public Numeros() {
		super();
		
	}
	public Numeros(int numero, long cuadrado, long cubo) {
		super();
		this.numero = numero;
		this.cuadrado = cuadrado;
		this.cubo = cubo;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public long getCuadrado() {
		return cuadrado;
	}
	public void setCuadrado(long cuadrado) {
		this.cuadrado = cuadrado;
	}
	public long getCubo() {
		return cubo;
	}
	public void setCubo(long cubo) {
		this.cubo = cubo;
	}
	
	
	

}
