/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test2_sincronizacion_wait_.notify;

/**
 *
 * @author user
 */
// CMensaje clase a la que pernetecerá objeto a compartir por los hilos cooperantes

public class CMensaje 
{
   private String textoMensaje;
   private int numeroMensaje;
   private boolean disponible_para_mostrar=false;      
        // valor false cuando no hay ningún mensaje que mostrar
       // el mensaje ya ha sido mostrado, sólo puede entrar un productor
   
   public synchronized void almacenar(int nmsj)
   {
        //definimos aquí una sección crítica, una operación atómica...
       // el hilo productor al ejecutar almacenar, lo primero que hace es verificar
       // valor de disponible
       // ya tengo el monitor del objeto ahora segunda comprobación con segunda "puerta", disponible_para_mostar
       while (disponible_para_mostrar==true)
       {
           //no puede entrar un productor, que es elque intenta ejecutar almacenar 
           // el último mensaje producido aún no ha sido mostrado, el productor tiene que esperar
           // hay un mensaje para mostrar, valor true. No se puede generar nuevo mensaje
           // sin mostrar anterior generado
           try
               {
                   wait();//el hilo se pone en estado de esperar y CEDE EL MONITOR DEL OBJETO
                          // SIN TERMINAR DE EJECUTAR LA SECCIÓN CRÍTICA
                          //una diferencia importante entre sleep() y wait() es que wait() cede el monitor
                          // del objeto y sleep no
                  //   !!!!!!!LOS MÉTODOS wait(), notify() y notifyAll() 
                  // !!!!!!SON INVOCADOS DESDE DENTRODE UNA SECCIÓN CRÍTICA!!!!!
               }catch (InterruptedException e){} 
       }                         /* aquí retorna proceso cuando el hilo se bloqueó con wait(), por eso no vale if tiene
                                       que ser WHILE!!!!!!!!!!! IMPORTANTE
                                    pero también ha tenido que adquirir monitor del objeto
                                    FIJAROS EL HILO ADQUIERE MONITOR DEL OBJETO Y VERIFICA VALOR DE disponible_para_mostrar
                                   */
       // si el hilo llega aquí es que tiene el monitor del objeto y disponible_para_mostrar es falso
       numeroMensaje=nmsj; // recibimos número de mensaje
       // suponer aquí operaciones en donde
       // buscamos el mensaje dentro de una tabla de mensajes.
       textoMensaje="mensaje prueba "; // genera nuevo mensaje
       disponible_para_mostrar=true; //ya tenemos nuevo mensaje producido que mostrar
       notifyAll();//el hilo despierta a todos los hilos que están esperando
                   //para adquirir elmonitor del objeto.
                   // fijarse otra vez desde dentro de sección crítca. invocamos notifyAll() siempre desde dentro de sección crítica 
                   // al terminar  el método almacenar cede  el monitor del objeto
   } //fin almacenar, fin método en donde se define sección crítica.
   
   public synchronized String obtener()
   {
         // segunda sección crítica definida en esta clase.
       //le da un determinado formato al mensaje que tenemos en textoMensaje,
       // el hilo consumidor lo primero
       // que hace es verificar valor de disponible, si es false no hay mensaje que mostrar
       while(disponible_para_mostrar==false)
       {
           //valor false, no hay ningún mensaje nuevo que mostar, el hilo consumidor
           //que tiene el control del monitor del objeto, no puede ejecutar la sección crítica , se pone en estado de espera y cede el control
           // del monitor del objeto
           try
               {
                   wait();
               }catch (InterruptedException e){} 
       }
       
       //!!! importante while , no sirve if!!!! 
       
       String mensaje;
       mensaje= textoMensaje +"###"+numeroMensaje; 
       disponible_para_mostrar=false;
       notifyAll();
        
       return mensaje;
       
   }
   
   
   }
//los métodos almacenar y obtener son operaciones atómicas y secciones críticas
//para cualquier hilo que las ejecute adquiere el monitor del objeto , no se pueden 
// interrumpir y no será ejecutado por otro hilo hasta que se libere el cerrojo
// del objeto.
//pero hemos implementado además una secuenciación de las tareas: PCPCPC
