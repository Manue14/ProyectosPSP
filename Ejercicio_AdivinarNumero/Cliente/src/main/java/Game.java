import java.io.Serializable;

public class Game implements Serializable {
    private long n;
    private boolean isOver;

    public Game() {
        this.n = Math.round(Math.random() * 100); //[0-100]
        this.isOver = false;
        System.out.println(n);
    }

    public synchronized boolean isOver() {
        return isOver;
    }

    public synchronized boolean checkInput(long userInput) {
        if (userInput == n) {
            isOver = true;
        }
        return isOver();
    }
}
