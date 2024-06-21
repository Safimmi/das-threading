package edu.unisabana.model;

import java.util.UUID;

public class Post {
  String id;
  String userId;
  String message;

  public Post() {
  }

  public Post(String userId, String message) {
    this.id = Long.toString(UUID.randomUUID().getMostSignificantBits());
    this.userId = userId;
    this.message = message;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
