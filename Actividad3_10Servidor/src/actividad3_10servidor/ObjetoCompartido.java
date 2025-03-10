package actividad3_10servidor;
// objeto del lado servidor, en el �rbol de hilos del servidor 
// cada hilo servidor recibe este mismo objeto, con n�mero a adivinar
// este objeto es compartido por todos los hilos del lado servidor
// a un proceso cliente, (conectado a trav�s de un socket)no le paso "mi tesoro" que
// es el objeto ServerSocket y tampoco este objeto

public class ObjetoCompartido {
	private int numero; // n�mero a adivinar
	private boolean acabo; // true cuando se haya terminado el juego
	private int ganador=0; // jugador ganador, se inicializa a falso

	public ObjetoCompartido(int numero) {
		this.numero = numero;// numero A ADIVINAR
		this.acabo = false;
	}//.. constructor

	public synchronized boolean seAcabo() {
		return acabo;
	}

	public synchronized int getGanador() {
		 return ganador;
	}
     // atenci�n secci�n cr�tica, se ejecuta en exclusividad, s�lo un hilo cada vez
	 // juegan de uno en uno 
	 // manipula al objeto,ya que modifica las propiedades ganador y acabo
	public synchronized String nuevaJugada(int jugador, int suNumero) {
		String cad = "";

		if (!seAcabo()) { // si el juego ha terminado no se comprueba nada

			if (suNumero > numero) { // demasiado grande
				cad = "Numero que has dado es demasiado grande";
			} else
			   if (suNumero < numero) { // demasiado peque
				cad = "Numero que has dado es demasiado peque�o";
			    } else 

			        if (suNumero == numero) { // ha acertado
				    cad = "Jugador " + jugador + " gana, adivin� el n�mero: " + numero;
				    acabo = true;
				    ganador = jugador;
			     }

		} else
			cad = " EL Jugador " + ganador + " adivin� el n�mero: " + numero;
		
		return cad;
		
	}// nuevaJugada

}// ObjetoCompartido..
