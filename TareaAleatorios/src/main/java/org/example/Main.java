package org.example;


import java.io.*;

public class Main {
    public static void main(String[] args) {
        //if (args.length < 3) {
            //System.err.println("\nSe necesitan dos programas a ejecutar y un fichero a crear");
            //System.exit(-1);
        //}
        String nombreHijo1 = "aleatorioshijo.exe";
        String nombreHijo2 = "mayusculashijo.exe";

        //Instanciamos un objeto File a partir de la ruta que nos paso el usuario por argumento
        File fichero = new File("prueba.txt");

        try {
            FileWriter fileWriter = new FileWriter(fichero);    //Obtenemos un canal de escritura al fichero que vamos a guardar

            Process hijo1 = new ProcessBuilder(nombreHijo1).start();   //Inicializamos el proceso hijo1

            //Construimos un buffer de lectura a partir de nuestra entrada estándar
            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

            //Construimos un flujo de escritura a partir del OutputStream de hijo1
            PrintStream printer = new PrintStream(hijo1.getOutputStream());

            //Construimos un buffer de lectura a partir del InputStream de hijo1
            BufferedReader reader = new BufferedReader(new InputStreamReader(hijo1.getInputStream()));

            String cadena = scanner.readLine();

            while (cadena.compareTo("fin") != 0) {
                printer.println("Genérame un int aleatorio");  //Le mandamos una cadena de texto arbitraria a hijo1
                printer.flush(); //Una vez enviados los datos vaciamos nuestro flujo de esritura

                int n = Integer.parseInt(reader.readLine());    //Leemos el int que nos devuelve hijo 1

                Process hijo2 = new ProcessBuilder(nombreHijo2).start();   //Instanciamos hijo2

                //Construimos un flujo de escritura a partir del OutputStream de hijo2
                PrintStream printer2 = new PrintStream(hijo2.getOutputStream());

                //Construimos un buffer de lectura a partir del InputStream de hijo2
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(hijo2.getInputStream()));
                
                System.out.println("Vas a tener que intrducir " + n + " cadenas. Si prefieres introducir otra cantidad"
                        + " introduzca el número ahora, sino introduzca cualquier otro valor que no sea un entero: ");
                String helper = scanner.readLine(); //Le pedimos al usuario otro número de cadenas a introducir si lo prefiere
                
                if (checkIfInteger(helper)) {
                    n = Integer.parseInt(helper);   //Si el usuario introdujo un número válido sustituimos el valor de n por el valor introducido
                }
                
                System.out.println("Introduce " + n + " cadenas (si quieres acabar antes introduce ff):");

                int i = 0;
                String cadena2 = scanner.readLine();
                while (i < n && cadena2.compareTo("ff") != 0) {
                    printer2.println(cadena2);  //Le mandamos la cadena de texto que metemos por teclado a hijo2
                    printer2.flush();  //Una vez enviados los datos vaciamos nuestro flujo de esritura
                    fileWriter.write(reader2.readLine() + "\n");   //Escribimos en nuestro fichero el resultado de hijo2

                    i++;

                    if (i != n) {
                        cadena2 = scanner.readLine();
                    }
                }

                //Cerramos hijo2 y sus canales de entrada/salida
                hijo2.destroy();
                printer2.close();
                reader2.close();

                System.out.println("Vuelve a introducir una cadena para generar un número (fin para salir):");
                cadena = scanner.readLine();    //Volvemos a pedirle al usuario entrada por teclado

            }

            //Cerramos hijo1 y sus canales de entrada/salida
            hijo1.destroy();
            printer.close();
            reader.close();

            //Cerramos nuestro buffer de lectura conectado a nuestra entrada estándar
            scanner.close();

            //Cerramos nuestro FileWriter
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static boolean checkIfInteger(String n) {
        try {
            Integer.parseInt(n);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}