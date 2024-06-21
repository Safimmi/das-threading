package edu.unisabana._examples;

public class ThreadExample {
  public static void main(String[] args) {
    Thread thread = new Thread(() -> {
      for (int i = 0; i < 5; i++) {
        System.out.println("Thread: " + i);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    thread.start();
    System.out.println("---- Java Threads! ----");
  }
}
