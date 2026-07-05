package org.sixtead.blog_api.layers.persistence;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.serviceproxy.ServiceBinder;
import org.sixtead.blog_api.layers.persistence.daos.PostsDao;
import org.sixtead.blog_api.layers.persistence.daos.PostsDaoImpl;

public class PostsDaoVerticle extends VerticleBase {
  @Override
  public Future<?> start() throws Exception {
    return new ServiceBinder(vertx)
        .setAddress(PostsDao.ADDRESS)
        .register(PostsDao.class, new PostsDaoImpl(vertx))
        .completion();
  }
}
