package edu.unisabana;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.unisabana.db.MockDatabase;
import edu.unisabana.model.Post;

public class CsvProcessor {

  // * Mock db
  public static MockDatabase db;

  public static void ProcessCsvFile(String csvFilePath) {

    db = MockDatabase.getInstance("src\\main\\resources\\db.json");
    ExecutorService executor = Executors.newSingleThreadExecutor();

    CompletableFuture
        .supplyAsync(() -> readPostsCsv(csvFilePath, ";"), executor)
        .thenApply(posts -> {
          System.out.println("\t\t [T2] Querying the DB on a separate thread...");
          db.batchAddPost(posts);
          return posts;
        })
        .thenAccept(json -> {
          System.out.println("\t\t [T2] Finish Task ~ Shutting down T2...");
          // System.out.println(json);
          executor.shutdown();
        });

  }

  public static List<Post> readPostsCsv(String path, String separator) {
    System.out.println("\t\t [T2] Reading CSV file on a separate thread...");
    List<Post> posts = new ArrayList<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
      String line;
      String[] headers = null;

      // * Read and define headers that will be used as keys
      if ((line = bufferedReader.readLine()) != null) {
        headers = line.split(separator);
      }

      // * Read data
      while ((line = bufferedReader.readLine()) != null) {
        // * Save data (post) on a HashMap
        String[] data = line.split(";");
        Map<String, String> post = new HashMap<>();
        if (headers != null) {
          for (int i = 0; i < headers.length; i++) {
            post.put(headers[i], data[i]);
          }
        }

        // * Validate if the author (user) exists and add post to the List
        // TODO: Message validation
        if (db.getUserById(post.get("user_id")) != null) {
          posts.add(new Post(post.get("user_id"), post.get("text")));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return posts;
  }
}
