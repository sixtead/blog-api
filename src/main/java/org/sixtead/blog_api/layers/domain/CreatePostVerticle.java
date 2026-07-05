package org.sixtead.blog_api.layers.domain;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.JsonObject;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreatePostVerticle extends VerticleBase {
  private final Logger logger = LoggerFactory.getLogger(CreatePostVerticle.class);

  @Override
  public Future<?> start() throws Exception {
    var eventBus = vertx.eventBus();

    return eventBus
        .consumer("create-post")
        .handler(
            message -> {
              logger.info("Received message: {}", message.body());
              var post = (JsonObject) message.body();
              var id = UUID.randomUUID();

              eventBus
                  .request("insert-post", post.copy().put("id", id.toString()))
                  .onSuccess(answer -> message.reply(JsonObject.of("id", id.toString())));
            })
        .completion();
  }
}
