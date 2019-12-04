package com.pccw.tyk.apitest;

import java.time.Instant;
import java.util.Random;

public class PingFactory {
  private static final Random RANDOMIZER = new Random();

  public static final Ping newPing() {
    return new Ping(generateChallenge(), generateTimestamp(), generateNonce());
  }

  private static String generateChallenge() {
    return RANDOMIZER.nextBoolean() ? "hi" : "hello";
  }

  private static Instant generateTimestamp() {
    return Instant.now();
  }

  private static int generateNonce() {
    return RANDOMIZER.nextInt();
  }
}
