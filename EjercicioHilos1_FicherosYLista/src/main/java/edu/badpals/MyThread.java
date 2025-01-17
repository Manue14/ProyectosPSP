package edu.badpals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class MyThread extends Thread {
    private String path;

    public MyThread(String path) {
        this.path = path;
    }

    private void run(LinkedList myLinkedList) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            br.lines().forEach(line -> {
                String str = line;
                String initials = getInitials(line);
                int length = str.length();
                myLinkedList.insertar(str, initials, length);
            });
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void start(LinkedList myLinkedList) {
        run(myLinkedList);
    }

    private String getInitials(String line) {
        StringBuilder initials = new StringBuilder();
        String[] words = line.split(" ");
        for (String word : words) {
            initials.append(word.substring(0, 1));
        }
        return initials.toString();
    }
}
