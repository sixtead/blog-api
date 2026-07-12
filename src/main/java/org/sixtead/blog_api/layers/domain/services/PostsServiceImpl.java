package org.sixtead.blog_api.layers.domain.services;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ServiceProxyBuilder;
import java.util.UUID;
import org.sixtead.blog_api.layers.domain.models.CreatePostPayload;
import org.sixtead.blog_api.layers.domain.models.Post;
import org.sixtead.blog_api.layers.persistence.daos.PostsDao;

public class PostsServiceImpl implements PostsService {
  private final PostsDao dao;

  public PostsServiceImpl(Vertx vertx) {
    this.dao = new ServiceProxyBuilder(vertx).setAddress(PostsDao.ADDRESS).build(PostsDao.class);
  }

  @Override
  public Future<Post> createPost(CreatePostPayload payload) {
    var post = new Post(UUID.randomUUID(), payload.getTitle(), payload.getContent());

    return dao.createPost(post);
  }
}
