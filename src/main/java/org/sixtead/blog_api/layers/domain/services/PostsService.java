package org.sixtead.blog_api.layers.domain.services;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.sixtead.blog_api.layers.domain.models.CreatePostPayload;
import org.sixtead.blog_api.layers.domain.models.Post;

@ProxyGen
public interface PostsService {
  String ADDRESS = "posts-service";

  static PostsService create(Vertx vertx) {
    return new PostsServiceImpl(vertx);
  }

  static PostsService createProxy(Vertx vertx, String address) {
    return new PostsServiceVertxEBProxy(vertx, address);
  }

  Future<Post> createPost(CreatePostPayload payload);
}
