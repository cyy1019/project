package controller;

public class MyThread extends Thread {
    public void run() {
        for (int i = 1; i < 1000; i++) {
            i += 1;
        }
    }
}
