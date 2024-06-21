package edu.unisabana._examples;

public class RunnableExample {
  public static void main(String[] args) {
    Runnable task = () -> {
      for (int i = 0; i < 5; i++) {
        System.out.println("Runnable: " + i);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    Thread thread = new Thread(task);
    thread.start();
    System.out.println("---- Java Threads! ----");
  }
}
