package org.sixtead.blog_api.config;

public class ConfigurationValidationException extends RuntimeException {
  public ConfigurationValidationException(String message) {
    super(message);
  }
}
