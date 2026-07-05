package org.sixtead.blog_api.layers.web;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.serviceproxy.ServiceProxyBuilder;
import org.sixtead.blog_api.layers.domain.models.CreatePostPayload;
import org.sixtead.blog_api.layers.domain.services.PostsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebVerticle extends VerticleBase {
  private final Logger logger = LoggerFactory.getLogger(WebVerticle.class);

  @Override
  public Future<?> start() throws Exception {
    var httpServer = vertx.createHttpServer();
    var router = Router.router(vertx);
    var port = this.config().getInteger("http.port");

    var postsService =
        new ServiceProxyBuilder(vertx).setAddress(PostsService.ADDRESS).build(PostsService.class);

    var eventBus = vertx.eventBus();

    router.route().handler(LoggerHandler.create());

    router.get("/health").respond(ctx -> Future.succeededFuture(JsonObject.of("status", "UP")));

    router
        .post("/posts")
        .consumes("application/json")
        .handler(BodyHandler.create())
        .handler(
            ctx ->
                postsService
                    .createPost(new CreatePostPayload(ctx.body().asJsonObject()))
                    .onSuccess(
                        post ->
                            ctx.response()
                                .setStatusCode(201)
                                .putHeader(
                                    "location",
                                    ctx.request().absoluteURI() + "/" + post.getId().toString())
                                .end()));
    return httpServer
        .requestHandler(router)
        .listen(port)
        .onSuccess(http -> logger.info("HTTP server started on port {}", http.actualPort()));
  }
}
