package org.sixtead.blog_api.layers.web.handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;
import org.sixtead.blog_api.layers.domain.models.CreatePostPayload;
import org.sixtead.blog_api.layers.domain.services.PostsService;

public class PostPostsHandler implements Handler<RoutingContext> {
  private final PostsService postsService;

  public PostPostsHandler(PostsService postsService) {
    this.postsService = postsService;
  }

  @Override
  public void handle(RoutingContext event) {
    var requestParameters = (RequestParameters) event.get(ValidationHandler.REQUEST_CONTEXT_KEY);
    var createPostPayload = new CreatePostPayload(requestParameters.body().getJsonObject());

    postsService
        .createPost(createPostPayload)
        .onSuccess(
            post ->
                event
                    .response()
                    .setStatusCode(201)
                    .putHeader(
                        HttpHeaders.LOCATION,
                        event.request().absoluteURI() + "/" + post.getId().toString())
                    .end(post.toJson().toBuffer()))
        .onFailure(event::fail);
  }
}
