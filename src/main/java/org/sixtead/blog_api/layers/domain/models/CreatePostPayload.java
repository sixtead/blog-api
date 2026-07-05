package org.sixtead.blog_api.layers.domain.models;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject
public class CreatePostPayload {
  private final String content;
  private final String title;

  public CreatePostPayload(JsonObject json) {
    this.title = json.getString("title");
    this.content = json.getString("content");
  }

  public String getContent() {
    return content;
  }

  public String getTitle() {
    return title;
  }

  public JsonObject toJson() {
    return new JsonObject().put("title", title).put("content", content);
  }
}
