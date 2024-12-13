/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test_sincronizacion_wait_.notify;

/**
 *
 * @author user
 */
public class Consumidor  extends Thread        
{
    //clase de los hilo Consumidor
   private CMensaje mensaje;
   // en el constructor está previsot que el hilo recibe obteto de la clase CMensaje
   // todos los hilos que reciban el mismo objeto se tendrán que sincronizar para ejecutar obtener()
   //si reciben distintos objetos  de tipo CMensaje en exclusiva  siempre tendrán el monitor del objeto
   public Consumidor(CMensaje c) //constructor recibe objeto compartido por los distintos hilos
   {
       mensaje=c;
   }
   public void run() //tarea de los hilos Consumidor
   {
       String msj;
       while(true)
       {
           msj=mensaje.obtener();
           //intenta obtener el monitor del objeto para poder ejecutar su sección crítica
           // definida en método obtener--> DOS POSIBILIDADES: LO EJECUTA/SE BLOQUEA
           //al finalizar ejecución del método libera el monitor delobjeto y desbloquea a todos los hilos
           // esperando por el monitor del objeto, a todos!! hilos productores e hilos consumidores !!
           System.out.println("Consumidor  "+ getName()+" obtuvo mensaje número: "+ msj);
           
       }// fin while(true)
   }
    
    
}// fin clase Consumidor

/**los hilos consumidores están todo el tiempo   while(true) intentando
 * acceder al objeto mensaje , ejecutando su método obtener()
 * este método está definido como una sección crítica, de modo
 * que, para ejecutarlo el hilo tiene que adquirir el monitor del objeto,
 * con esto impedimos que varios hilos consumidores lean al mismo tiempo
 * y que cualquier hilo productor genere mensaje al mismo tiempo
 */
