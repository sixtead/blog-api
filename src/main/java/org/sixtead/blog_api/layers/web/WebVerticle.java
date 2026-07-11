package org.sixtead.blog_api.layers.web;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.validation.builder.Bodies;
import io.vertx.ext.web.validation.builder.ValidationHandlerBuilder;
import io.vertx.json.schema.Draft;
import io.vertx.json.schema.JsonSchemaOptions;
import io.vertx.json.schema.SchemaRepository;
import io.vertx.serviceproxy.ServiceProxyBuilder;
import org.sixtead.blog_api.layers.domain.models.CreatePostPayload;
import org.sixtead.blog_api.layers.domain.services.PostsService;
import org.sixtead.blog_api.layers.web.handlers.PostPostsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebVerticle extends VerticleBase {
  private final Logger logger = LoggerFactory.getLogger(WebVerticle.class);

  @Override
  public Future<?> start() throws Exception {
    var httpServer = vertx.createHttpServer();
    var router = Router.router(vertx);
    var port = this.config().getInteger("http.port");
    var schemaRepository =
        SchemaRepository.create(
            new JsonSchemaOptions()
                .setBaseUri("http://localhost:" + port + "/")
                .setDraft(Draft.DRAFT202012));

    var postsService =
        new ServiceProxyBuilder(vertx).setAddress(PostsService.ADDRESS).build(PostsService.class);

    router.route().handler(LoggerHandler.create());

    router.get("/health").respond(_ -> Future.succeededFuture(JsonObject.of("status", "UP")));

    router
        .post("/posts")
        .consumes("application/json")
        .handler(BodyHandler.create())
        .handler(
            ValidationHandlerBuilder.create(schemaRepository)
                .body(Bodies.json(CreatePostPayload.jsonSchema()))
                .build())
        .handler(new PostPostsHandler(postsService));

    return httpServer
        .requestHandler(router)
        .listen(port)
        .onSuccess(http -> logger.info("HTTP server started on port {}", http.actualPort()));
  }
}
