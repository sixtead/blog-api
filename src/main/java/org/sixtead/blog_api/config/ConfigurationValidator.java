package org.sixtead.blog_api.config;

import io.vertx.core.json.JsonObject;
import java.math.BigInteger;
import java.util.function.Function;
import org.apache.commons.lang3.Validate;

public class ConfigurationValidator implements Function<JsonObject, JsonObject> {
  @Override
  public JsonObject apply(JsonObject config) {
    try {
      Validate.isTrue(config.containsKey("http.port"), "Missing http.port");
      Validate.isInstanceOf(
          Number.class, config.getValue("http.port"), "http.port must be a number");
      Validate.isInstanceOf(
          BigInteger.class, config.getValue("http.port"), "http.port must be an integer");
    } catch (IllegalArgumentException e) {
      throw new ConfigurationValidationException(e.getMessage());
    }

    return config;
  }
}
