package io.se7en.apigwtest;

import java.util.Optional;

public class Pong {
  private final String response;
  private final String message;
  private final int nonce;

  public Pong() {
    this(null, null, 0);
  }

  public Pong(String response, String message, int nonce) {
    this.response = Optional.ofNullable(response).orElse("<no response>");
    this.message = Optional.ofNullable(message).orElse("<no message>");
    this.nonce = nonce;
  }

  public String getResponse() {
    return response;
  }

  public String getMessage() {
    return message;
  }

  public int getNonce() {
    return nonce;
  }
}
