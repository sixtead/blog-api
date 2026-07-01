package org.sixtead.blog_api;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.JsonObject;
import org.sixtead.blog_api.layers.web.WebVerticle;

public class MainVerticle extends VerticleBase {
  @Override
  public Future<?> start() {
    var configRetriever =
        ConfigRetriever.create(
            vertx,
            new ConfigRetrieverOptions()
                .addStore(
                    new ConfigStoreOptions()
                        .setType("file")
                        .setFormat("properties")
                        .setConfig(new JsonObject().put("path", "application.properties"))));

    return configRetriever
        .getConfig()
        .onComplete(
            config -> {
              vertx.deployVerticle(
                  WebVerticle::new, new DeploymentOptions().setConfig(config.result()));
            });
  }
}
