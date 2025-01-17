package edu.badpals;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Es necesario como mínimo pasar un fichero por argumento");
            return;
        }

        LinkedList list = new LinkedList();
        MyThread[] threads = new MyThread[args.length];
        int i = 0;
        for (String path : args) {
            threads[i] = new MyThread(path);
            i += 1;
        }

        for (MyThread thread : threads) {
            thread.start(list);
        }

        list.travel();
    }
}