/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test_sincronizacion_wait_.notify;

/**
 *
 * @author user
 */
public class Productor  extends Thread
{   //clase que define a los hilos productores
    
    private CMensaje mensaje; //para recibir objeto 
    public Productor(CMensaje c) //constructor, recibe objeto compartido por otro/OTROS hilos (productores y consumidores)          
    {
      mensaje=c;  
    }
    public void run() //tarea de cualquier hilo Productor
    {
       int numeroMsj;
       while(true)
       {
           numeroMsj=(int)(Math.random()*100); //calcula número de mensaje
                                              // simulamos aquí un proceso de recibir un entero, cada entero recibido indentifica un texto 
                                              // o mensaje distinto que hay que registrar en el objeto de tipo CMensaje 
           // ahora, intenta obtener el monitor del objeto, intenta ejecutar una sección crítica 
           // que identifica y registra un determinado mensaje....
           // esto implica dos posibilidades: la ejecuta/se bloquea
           // cuando termine la ejecución del método sincronizado libera el monitor del objeto Y desbloquea a todos los hilos
           // esperando por elmonitor de ese objeto (a todos!! consumidores y productores)
           mensaje.almacenar(numeroMsj);
           
           System.out.println("Productor  "+getName()+" ha almacenado el mensaje número: "+numeroMsj);
           try
           {
                int msegs=(int)(Math.random()*100);
                //ponerse a dormir el hilo msegs , hasta producir siguiente mensaje
                sleep(msegs);
           }
           catch(InterruptedException e){}
       } //fin while (true)
    }// fin  run()

}
//los hilos Productor están intentando todo el tiempo, cada msegs,ejemplo de modificar ritmo, crear un mensaje accediendo
// al objeto de tipo CMensaje e intentando ejecutar su sección crítica almacenar