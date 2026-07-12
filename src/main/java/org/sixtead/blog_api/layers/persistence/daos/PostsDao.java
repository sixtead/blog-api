package org.sixtead.blog_api.layers.persistence.daos;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.sixtead.blog_api.layers.domain.models.Post;

@ProxyGen
public interface PostsDao {
  String ADDRESS = "posts-dao";

  static PostsDao create() {
    return new PostsDaoImpl();
  }

  static PostsDao createProxy(Vertx vertx, String address) {
    return new PostsDaoVertxEBProxy(vertx, address);
  }

  Future<Post> createPost(Post post);
}
