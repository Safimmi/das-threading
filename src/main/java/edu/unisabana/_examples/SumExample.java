package edu.unisabana._examples;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SumExample {
  public static void main(String[] args) {
    List<Callable<Integer>> tasks = Arrays.asList(
        () -> {
          Thread.sleep(1000);
          return 10;
        },
        () -> {
          Thread.sleep(1000);
          return 20;
        },
        () -> {
          Thread.sleep(1000);
          return 30;
        });

    ExecutorService executor = Executors.newFixedThreadPool(3);

    try {
      List<Future<Integer>> results = executor.invokeAll(tasks);
      int sum = 0;
      for (Future<Integer> result : results) {
        sum += result.get();
      }
      System.out.println("Sum: " + sum);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    } finally {
      executor.shutdown();
    }
  }
}
