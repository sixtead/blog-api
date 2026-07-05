package org.sixtead.blog_api.layers.persistence;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertPostVerticle extends VerticleBase {
  private final Logger logger = LoggerFactory.getLogger(InsertPostVerticle.class);
  private final Map<UUID, JsonObject> map = new HashMap<>();

  @Override
  public Future<?> start() throws Exception {
    var eventBus = vertx.eventBus();

    eventBus
        .consumer("insert-post")
        .handler(
            message -> {
              var post = (JsonObject) message.body();
              logger.info("Received message: {}", post);
              var id = UUID.fromString(post.getString("id"));

              map.put(id, post);

              message.reply(post);
            });

    return super.start();
  }
}
