/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_8hilocliente;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
/**
 *
 * @author user
 */
public class Actividad3_8Hilocliente extends JFrame implements ActionListener, Runnable  {
        private static final long serialVersionUID = 1L;
	Socket socket = null;
	// streams para leer/escribir al servidor
	DataInputStream fentrada;
	DataOutputStream fsalida;

	static JLabel Lmensaje = new JLabel("Escribe texto:");
	static JTextField mensaje = new JTextField();

	static JTextField salida = new JTextField("");

	JButton boton = new JButton("Enviar");
	JButton desconectar = new JButton("Salir");
	JButton limpiar = new JButton("Limpiar");
	boolean repetir = true;
        
        // constructor
	public Actividad3_8Hilocliente(Socket s) {
		super(" VENTANA DEL CLIENTE - EJERCICIO 3_8 ");
		setLayout(null);

		Lmensaje.setBounds(10, 10, 100, 30);
		add(Lmensaje);
		mensaje.setBounds(10, 50, 300, 30);
		add(mensaje);

		salida.setBounds(10, 90, 300, 30);
		add(salida);

		// botones
		boton.setBounds(370, 20, 100, 30);
		add(boton);
		desconectar.setBounds(370, 60, 100, 30);
		add(desconectar);
		limpiar.setBounds(370, 100, 100, 30);
		add(limpiar);

		salida.setEditable(false);

		boton.addActionListener(this);
		desconectar.addActionListener(this);
		limpiar.addActionListener(this);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		socket = s;

		try {   //creo flujos con servidor
			fentrada = new DataInputStream(socket.getInputStream());
			fsalida = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("ERROR DE E/S");
			e.printStackTrace();
			System.exit(0);
		}
	}// fin constructor
        //  definimos accion cuando pulsamos botones
    public void actionPerformed(ActionEvent e) {
		if (e.getSource() == boton) { // SE PULSA EL ENVIAR
			String texto = mensaje.getText();
			try {
				fsalida.writeUTF(texto);
				if (texto.equals("*"))
					fin();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == desconectar) { // SE PULSA DESCONECTAR
			try {
				fsalida.writeUTF("*");
				fin();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == limpiar) { // SE PULSA LIMPIAR
			mensaje.setText("");
			salida.setText("");
		}
	}//  
    
    // FINALIZA LA EJECUCIÓN
	void fin() {
		System.out.println("Cliente finalizado .....");
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}//..fin
    // ejecutar,  tarea del hilo
	public void run() {
		String texto = "";
		while (repetir) {
			try {
				texto = fentrada.readUTF();
				salida.setText(texto);

			} catch (IOException e) {
				// este error sale cuando el servidor se cierra
				JOptionPane
						.showMessageDialog(
								null,
								"IMPOSIBLE CONECTAR CON EL SERVIDOR\n"
										+ e.getMessage(),
								"<<MENSAJE DE ERROR:2>>",
								JOptionPane.ERROR_MESSAGE);			
				repetir = false;
			}
		}// while

		try {
			//sale por aqui cuando no se puede conectar con servidor
			//y sale del while
			socket.close();			
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//.. ejecutar    
    //MAIN
    public static void main(String[] args) {
                int puerto = 44444;// puerto por el que escucha servidor
		// si se conecta a maquina remota poner  su IP, por ej "192.168.0.194"		
		Socket s = null;
		try {
			s = new Socket("localhost", puerto);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(),
					"<<MENSAJE DE ERROR:1>>", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		Actividad3_8Hilocliente cliente = new Actividad3_8Hilocliente (s);
		cliente.setBounds(0, 0, 500, 190);
		cliente.setVisible(true);
		new Thread(cliente).start();

    }
    
}
