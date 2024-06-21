package edu.unisabana;

import edu.unisabana.db.MockDatabase;

public class Main {
  public static void main(String[] args) {
    System.err.println("---- POSTING API ----");
    System.out.println("\t [T1] Doing things on the main thread...");

    // * Set up data base ~ Mock
    MockDatabase.getInstance("src\\main\\resources\\db.json");

    // * Process CSV posts
    CsvProcessor.ProcessCsvFile("src\\main\\resources\\posts-extract.csv");

    // * Do other actions
    System.out.println("\t [T1] Doing other things on the main thread...");
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
