package org.sixtead.blog_api.layers.domain;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.serviceproxy.ServiceBinder;
import org.sixtead.blog_api.layers.domain.services.PostsService;
import org.sixtead.blog_api.layers.domain.services.PostsServiceImpl;

public class PostServiceVerticle extends VerticleBase {
  @Override
  public Future<?> start() throws Exception {
    return new ServiceBinder(vertx)
        .setAddress(PostsService.ADDRESS)
        .register(PostsService.class, new PostsServiceImpl(vertx))
        .completion();
  }
}
