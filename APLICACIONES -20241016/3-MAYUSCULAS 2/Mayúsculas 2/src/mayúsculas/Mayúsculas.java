package mayúsculas;

import java.io.*;

/**
 *
 * @author a12cristiance
 */
public class Mayúsculas {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("\nSe necesita un programa a ejecutar y un fichero a crear");
            System.exit(-1);
        }
       // el proceso ha recibido dos argumentos en su llamada: el ejecutable a instanciar como proceso hijo y un 
       // segundo argumento con la especificación en disco del fichero  de salida a crear.
        File fichero = new File(args[1]); //utilizamos aquí segundo argumento recibido
        FileWriter fw = null;

        try {
            Process hijo = new ProcessBuilder(args[0]).start(); //aquí utilizamos primer argumento recibido
            fw = new FileWriter(fichero);
            // definimos los streams para comunicarnos con el proceso hijo
            BufferedReader br = new BufferedReader(new InputStreamReader(hijo.getInputStream()));
            PrintStream ps = new PrintStream(hijo.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            String texto;

            do {
                texto = in.readLine();
                ps.println(texto); // se lo escribo al hijo en su entrada estándar, para que lo procese.....
      z          ps.flush();

                String line = br.readLine(); // intento leer del proceso hijo de su salida estándar, dato ya procesado...

                if (line.compareTo("kk") != 0) {
                    fw.write(line + "\r\n");  // la cadena procesada la escribo en el fichero
                }
            } while (texto.compareTo("ff") != 0);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (fw != null) {
                    fw.close();  // cierro fichero 
                }
            } catch (IOException ex) {
            }
        }
    }
}