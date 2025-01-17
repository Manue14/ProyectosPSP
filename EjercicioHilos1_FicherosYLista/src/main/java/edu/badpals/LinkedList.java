package edu.badpals;

public class LinkedList {
    private Node head;

    public LinkedList() {}

    public synchronized void insertar(String str, String initials, int length) {
        Node node = new Node(length, str, initials);

        if (head == null) {
            head = node;
        } else {
            Node current = head;
            Node previous = null;
            while (str.compareTo(current.str) >= 0 && current.next != null) {
                previous = current;
                current = current.next;
            }

            if (current == head) {
                node.next = head;
                head = node;
            } else {
                previous.next = node;
                node.next = current;
            }
        }
    }

    public void travel() {
        Node current = head;
        while (current != null) {
            System.out.println(current.str);
            current = current.next;
        }
    }

    private static class Node {
        private int length;
        private String initials;
        private String str;
        private Node next;

        public Node(int longitud, String cadena, String iniciales) {
            this.length = longitud;
            this.initials = iniciales;
            this.str = cadena;
            this.next = null;
        }
    }
}
