package org.sixtead.blog_api.layers.domain.models;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.json.schema.common.dsl.Keywords;
import io.vertx.json.schema.common.dsl.ObjectSchemaBuilder;
import io.vertx.json.schema.common.dsl.Schemas;

@DataObject
public class CreatePostPayload {
  public static ObjectSchemaBuilder jsonSchema() {
    return Schemas.objectSchema()
        .requiredProperty("title", Schemas.stringSchema().with(Keywords.minLength(1)))
        .requiredProperty("content", Schemas.stringSchema().with(Keywords.minLength(1)))
        .allowAdditionalProperties(false);
  }

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
