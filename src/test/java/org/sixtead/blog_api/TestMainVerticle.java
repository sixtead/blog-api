package org.sixtead.blog_api;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

  private HttpClient client;

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx
        .deployVerticle(new MainVerticle())
        .onComplete(testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
    client = vertx.createHttpClient();

    client
        .request(HttpMethod.GET, 8888, "localhost", "/health")
        .compose(req -> req.send().compose(HttpClientResponse::body))
        .onComplete(
            testContext.succeeding(
                body ->
                    testContext.verify(
                        () -> {
                          Assertions.assertEquals(
                              JsonObject.of("status", "UP"), body.toJsonObject());
                          testContext.completeNow();
                        })));
  }
}
