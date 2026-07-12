package org.sixtead.blog_api.layers.domain.models;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import java.util.UUID;

@DataObject
public class Post {
  private final String content;
  private final UUID id;
  private final String title;

  public Post(JsonObject json) {
    this.id = UUID.fromString(json.getString("id"));
    this.title = json.getString("title");
    this.content = json.getString("content");
  }

  public Post(UUID id, String title, String content) {
    this.id = id;
    this.title = title;
    this.content = content;
  }

  public UUID getId() {
    return id;
  }

  public JsonObject toJson() {
    return new JsonObject().put("id", id.toString()).put("title", title).put("content", content);
  }
}
