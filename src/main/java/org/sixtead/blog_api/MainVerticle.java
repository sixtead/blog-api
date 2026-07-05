package org.sixtead.blog_api;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.JsonObject;
import org.sixtead.blog_api.config.ConfigurationValidator;
import org.sixtead.blog_api.layers.domain.PostServiceVerticle;
import org.sixtead.blog_api.layers.persistence.PostsDaoVerticle;
import org.sixtead.blog_api.layers.web.WebVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends VerticleBase {
  private final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

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
                            .setConfig(new JsonObject().put("path", "application.properties"))))
            .setConfigurationProcessor(new ConfigurationValidator());

    return configRetriever
        .getConfig()
        .onComplete(
            config -> {
              Future.all(
                      vertx.deployVerticle(
                          WebVerticle::new, new DeploymentOptions().setConfig(config.result())),
                      vertx.deployVerticle(new PostServiceVerticle()),
                      vertx.deployVerticle(new PostsDaoVerticle()))
                  .onSuccess(_ -> logger.info("Application started"))
                  .onFailure(err -> logger.error("Application failed to start", err));
            });
  }
}
