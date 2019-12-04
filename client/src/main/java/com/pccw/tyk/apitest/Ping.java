package com.pccw.tyk.apitest;

import java.time.Instant;

public class Ping {
  private String challenge;
  private Instant timestamp;
  private int nonce;

  public Ping() {
    this("<no challenge>", Instant.EPOCH, 0);
  }

  public Ping(String challenge, Instant timestamp, int nonce) {
    this.challenge = challenge;
    this.timestamp = timestamp;
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

  public void setChallenge(String challenge) {
    this.challenge = challenge;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public void setNonce(int nonce) {
    this.nonce = nonce;
  }

  @Override
  public String toString() {
    return new StringBuilder()
      .append("[")
      .append("Ping")
      .append(" ")
      .append("challenge")
      .append("=")
      .append(challenge)
      .append(" ")
      .append("timestamp")
      .append("=")
      .append(timestamp.toString())
      .append(" ")
      .append("nonce")
      .append("=")
      .append(nonce)
      .append("]")
      .toString();
  }
}
