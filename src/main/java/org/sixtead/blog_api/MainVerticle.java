package org.sixtead.blog_api;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends VerticleBase {

  @Override
  public Future<?> start() {
    var httpServer = vertx.createHttpServer();
    var router = Router.router(vertx);

    router
      .get("/health")
      .respond(ctx -> Future.succeededFuture(JsonObject.of("status", "UP")));

    return httpServer
      .requestHandler(router)
      .listen(8888)
      .onSuccess(http -> {
        System.out.println("HTTP server started on port 8888");
      });
  }
}
