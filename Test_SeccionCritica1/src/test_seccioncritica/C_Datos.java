/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test_seccioncritica;

/**
 *@author mercedes
 * 
 */

/****primera forma de definir una sección crítica,hemos agrupado el código
 * definido como crítico en un método declarado como synchronized
 * 
 */
public class C_Datos  {
    private double[] datos;
    public int ind=0;
    public int tamano; /* fijarse ind y tamano son public*/
    
    public C_Datos(int n){
        if(n<1){
            n=10;
        }
        this.tamano=n;
        datos=new double[n];
    }
    
    public double obtener(int i){
        return this.datos[i];
    }
    /*establecemos, definimos secciones críticas  método asignar y método calculos**/
    /* AQUÍ RECUERDA BIEN QUÉ ES UNA SECCIÓN CRÍTICA**/
    public synchronized void asignar(double x,int i){
        datos[i]=x;
    }
    public synchronized int calculos(String hilo){
        if(ind>=tamano){
            return tamano;
        }
        double x=Math.random();
        System.out.println(hilo+" almacena una muestra en posición  "+ind);
        asignar(x,ind); /**atención llamada a un método sincronizado, dentro de una sección crítica **/
        ind++;
        return ind;
    }
}

/***** EL MÉTODO CALCULOS TAMBIÉN LO DEFINIMOS COMO SINCRONIZADO, POR QUÉ???
 *     MANEJO DEL ÍNDICE,ind++, FIJARSE ATRIBUTO ind de tipo public
 * 
 *     ADEMÁS NECESARIO MONITOR REENTRANTE !!!!  
       LANZAMOS N HILOS QUE RECIBEN MISMO OBJETO DE ESTA CLASE
       EN CADA INSTANTE SOLO UN HILO PUEDE EJECUTAR SOBRE EL OBJETO UN MÉTODO
       SINCRONIZADO, TODOS LOS DEMÁS HILOS QUE INTENTEN EJECUTAR asignar
        o claculos QUEDARÁN BLOQUEDOS ESPERANDO ADQUIR EL MONITOR DEL OBJETO 
*/