/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test_sincronizacion_wait_.notify;

/**
 *
 * @author user
 */
// CMensaje clase a la que pertenecerá el/los objeto a compartir por los hilos cooperantes

public class CMensaje 
{
   private String textoMensaje;
   private int numeroMensaje;
   
   public synchronized void almacenar(int nmsj)
   {
        //definimos aquí una sección crítica, que se ejecutará con exclusividad por sólo UN hilo en cada instante, es una operación atómica...
       numeroMensaje=nmsj; // recibimos número de mensaje
       //  y ahora suponer aquí operaciones en donde
       
       // buscamos el mensaje dentro de una tabla de mensajes, fichero, base de datos,etc ..., el mensaje que le corresponde al número recibido.
       textoMensaje="mensajetexto prueba ";
   } //fin almacenar, fin método en donde se define sección crítica.
   
   public synchronized String obtener()
   {
         // segunda sección crítica definida en esta clase.
       //le da un determinado formato al mensaje que tenemos en textoMensaje y luego lo envía o consume
       String mensaje;
       mensaje= textoMensaje +"###"+numeroMensaje;
       return mensaje;
       
   }
   
   
   }
//los métodos almacenar y obtener son operaciones atómicas y secciones críticas
//para cualquier hilo que las ejecute , el hilo primero debe adquir el monitor del objeto, sólo uno , por tanto
// todos los hilos que intenten ejecutar una sección crítica(cualquiera) para el mismo objeto quedarán bloqueados, esperando
// por el monitor del objeto, durante la ejecución de una sección crítica el hilo no se puede  
// interrumpir y cualquier sección crítica del objeto no será ejecutado por otro hilo hasta que se libere el cerrojo
// del objeto.(SÓLO UN CERROJO DEL OBJETO O MONITOR DEL OBJETO)
