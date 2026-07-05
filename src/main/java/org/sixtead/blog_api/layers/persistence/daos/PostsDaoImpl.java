package org.sixtead.blog_api.layers.persistence.daos;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.sixtead.blog_api.layers.domain.models.Post;

public class PostsDaoImpl implements PostsDao {
  private final Map<UUID, Post> posts = new HashMap<>();
  private Vertx vertx;

  public PostsDaoImpl(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public Future<Post> createPost(Post post) {
    posts.put(post.getId(), post);

    return Future.succeededFuture(post);
  }
}
