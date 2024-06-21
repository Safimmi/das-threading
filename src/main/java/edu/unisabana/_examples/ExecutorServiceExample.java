package edu.unisabana._examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample {
  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(2);

    Runnable task1 = () -> {
      for (int i = 0; i < 5; i++) {
        System.out.println("Task 1: " + i);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    Runnable task2 = () -> {
      for (int i = 0; i < 5; i++) {
        System.out.println("Task 2: " + i);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    executor.submit(task1);
    executor.submit(task2);

    executor.shutdown();
  }
}
