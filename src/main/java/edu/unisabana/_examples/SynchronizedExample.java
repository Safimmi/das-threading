package edu.unisabana._examples;

public class SynchronizedExample {
  private static int counter = 0;

  // * A synchronized method can be executed only by one thread at a time
  public static synchronized void increment() {
    counter++;
  }

  public static void main(String[] args) {
    Runnable task = () -> {
      for (int i = 0; i < 1000; i++) {
        increment();
      }
    };

    Thread thread1 = new Thread(task);
    Thread thread2 = new Thread(task);

    thread1.start();
    thread2.start();

    try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Counter: " + counter);
  }
}
