package org.sixtead.blog_api;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
@Timeout(value = 2, unit = TimeUnit.SECONDS)
public class BaseMainVerticleTest {
  protected WebClient client;

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx
        .deployVerticle(new MainVerticle())
        .onComplete(testContext.succeeding(id -> testContext.completeNow()));
  }
}
