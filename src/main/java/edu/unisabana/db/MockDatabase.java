package edu.unisabana.db;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

import edu.unisabana.model.Post;
import edu.unisabana.model.User;

public class MockDatabase {
  private static MockDatabase instance;
  private String jsonFilePath;
  private ObjectMapper objectMapper;

  private List<User> users;
  private List<Post> posts;
  private Map<String, User> userIndex;
  private Map<String, Post> postIndex;

  public MockDatabase(String jsonFilePath) {
    System.out.println("\t [T1] Initializing DB...");
    this.jsonFilePath = jsonFilePath;
    objectMapper = new ObjectMapper();

    try {
      readJsonFile(jsonFilePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static MockDatabase getInstance(String jsonFilePath) {
    // * Instantiate db as a singleton
    if (instance == null) {
      instance = new MockDatabase(jsonFilePath);
    }
    return instance;
  }

  public void readJsonFile(String jsonFilePath) throws IOException {
    // * Read JSON
    JsonNode rootNode = objectMapper.readTree(new File(jsonFilePath));
    JsonNode usersNode = rootNode.path("users");
    JsonNode postsNode = rootNode.path("posts");

    // * Deserialize data into a list
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    List<User> users = objectMapper.readValue(
        usersNode.toString(),
        typeFactory.constructCollectionType(List.class, User.class));
    List<Post> posts = objectMapper.readValue(
        postsNode.toString(),
        typeFactory.constructCollectionType(List.class, Post.class));

    // * Define lists and indexes (HashMap)
    this.users = users;
    userIndex = new HashMap<>();
    for (User user : users) {
      userIndex.put(user.getId(), user);
    }

    this.posts = posts;
    postIndex = new HashMap<>();
    for (Post post : posts) {
      postIndex.put(post.getId(), post);
    }
  }

  public User getUserById(String id) {
    // * Search user on index
    if (userIndex.containsKey(id)) {
      return userIndex.get(id);
    } else {
      return null;
    }
  }

  public void batchAddPost(List<Post> newPosts) {
    // * Add posts to the `List`
    posts.addAll(newPosts);

    // * Add posts to the index
    for (Post post : newPosts) {
      postIndex.put(post.getId(), post);
    }

    updateJsonWithLocalData();
  }

  public void updateJsonWithLocalData() {

    ObjectNode rootNode = objectMapper.createObjectNode();

    ArrayNode usersArray = objectMapper.createArrayNode();
    users.forEach(user -> usersArray.addPOJO(user));
    rootNode.set("users", usersArray);

    ArrayNode postsArray = objectMapper.createArrayNode();
    posts.forEach(post -> postsArray.addPOJO(post));
    rootNode.set("posts", postsArray);

    try {
      objectMapper.writeValue(new File(jsonFilePath), rootNode);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
