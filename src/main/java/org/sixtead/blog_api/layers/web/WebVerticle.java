package org.sixtead.blog_api.layers.web;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class WebVerticle extends VerticleBase {
  @Override
  public Future<?> start() throws Exception {
    return ConfigRetriever.create(vertx)
      .getConfig()
      .compose(config -> {
          var httpServer = vertx.createHttpServer();
          var router = Router.router(vertx);
          var port = config.getInteger("http.port");

          router
            .get("/health")
            .respond(ctx -> Future.succeededFuture(JsonObject.of("status", "UP")));

          return httpServer
            .requestHandler(router)
            .listen(port)
            .onSuccess(http -> {
              System.out.println("HTTP server started on port" + port);
            });
        }
      );
  }
}
