package io.se7en.apigwtest;

import java.time.Instant;
import java.util.Optional;

public class Ping {
  private final String challenge;
  private final Instant timestamp;
  private final int nonce;

  public Ping() {
    this(null, null, 0);
  }

  public Ping(String challenge, Instant timestamp, int nonce) {
    this.challenge = Optional.ofNullable(challenge).orElse("<no challenge>");
    this.timestamp = Optional.ofNullable(timestamp).orElse(Instant.EPOCH);
    this.nonce = nonce;
  }

  public String getChallenge() {
    return challenge;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public int getNonce() {
    return nonce;
  }
}
