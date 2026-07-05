package org.sixtead.blog_api.layers.web;

import static org.assertj.core.api.Assertions.assertThat;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxTestContext;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.sixtead.blog_api.BaseMainVerticleTest;

public class WebVerticleTest extends BaseMainVerticleTest {

  @Test
  void health_endpoint(Vertx vertx, VertxTestContext testContext) throws Throwable {
    client = WebClient.create(vertx);

    client
        .request(HttpMethod.GET, 8888, "localhost", "/health")
        .send()
        .onComplete(
            testContext.succeeding(
                res ->
                    testContext.verify(
                        () -> {
                          assertThat(res.statusCode()).isEqualTo(200);
                          assertThat(res.bodyAsJsonObject())
                              .isEqualTo(JsonObject.of("status", "UP"));
                          testContext.completeNow();
                        })));
  }

  @Test
  void post_posts_endpoint(Vertx vertx, VertxTestContext testContext) throws Throwable {
    client = WebClient.create(vertx);

    client
        .request(HttpMethod.POST, 8888, "localhost", "/posts")
        .sendJsonObject(new JsonObject().put("title", "My article").put("content", "My content"))
        .onComplete(
            testContext.succeeding(
                response ->
                    testContext.verify(
                        () -> {
                          assertThat(response.statusCode()).isEqualTo(201);
                          assertThat(response.getHeader("location"))
                              .matches(
                                  Pattern.compile(
                                      "http://localhost:8888/posts/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"));

                          testContext.completeNow();
                        })));
  }
}
