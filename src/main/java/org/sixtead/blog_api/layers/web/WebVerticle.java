package org.sixtead.blog_api.layers.web;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.LoggerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebVerticle extends VerticleBase {
  private final Logger LOGGER = LoggerFactory.getLogger(WebVerticle.class);

  @Override
  public Future<?> start() throws Exception {
    var httpServer = vertx.createHttpServer();
    var router = Router.router(vertx);
    var port = this.config().getInteger("http.port");

    router.route().handler(LoggerHandler.create());

    router.get("/health").respond(ctx -> Future.succeededFuture(JsonObject.of("status", "UP")));

    return httpServer
        .requestHandler(router)
        .listen(port)
        .onSuccess(http -> LOGGER.info("HTTP server started on port {}", http.actualPort()));
  }
}
