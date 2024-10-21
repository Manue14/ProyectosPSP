/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplo7;
import java.io.File;

 
//redireccionamiento de la salida estandar y  de la salida de error de un proceso hijo
public class Ejemplo7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("CMD","/C" ,"DIR");
    
    File fOut = new File("salida.txt");
    File fErr = new File("error.txt");
     // redefiniendo objeto pb , redirigimos su salida estándar y  su salida de error
	 // a dos ficheros 
    pb.redirectOutput(fOut);
    pb.redirectError(fErr); 
	// método start() retorna objeto de la clase Process
	// se encuentra en estado listo , el planificador de procesos lo pondrá en ejecución
   
        
    try {
			Process p = pb.start();// retorna objeto clase Process
			
			 
	} catch (Exception e) {
			e.printStackTrace();
		}    
        
        
        
        
        
  }
 }
    

