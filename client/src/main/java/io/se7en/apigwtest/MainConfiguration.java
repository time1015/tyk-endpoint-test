package io.se7en.apigwtest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

public class MainConfiguration {
  private final String host;
  private final int port;
  private final String root;

  public MainConfiguration(String[] arguments) {
    List<String> list = Arrays.asList(arguments);

    this.host = getOrEmpty(list, 0).orElseGet(() -> {
      System.out.println("No host provided; using localhost instead...");
      return "localhost";
    });

    this.port = getOrEmpty(list, 1).map(a -> {
      try {
        return Integer.valueOf(a);
      } catch (NumberFormatException e) {
        System.out.println("Invalid port provided; using 80 instead...");
        return Integer.valueOf(80);
      }
    }).orElseGet(() -> {
      System.out.println("No port provided; using 80 instead...");
      return Integer.valueOf(80);
    }).intValue();

    this.root = getOrEmpty(list, 2).orElseGet(() -> {
      System.out.println("No root path provided; using apigwtest instead...");
      return "apigwtest";
    });
  }

  private static <T> Optional<T> getOrEmpty(List<T> list, int index) {
    if (list == null)
      return Optional.empty();
    if (list.isEmpty())
      return Optional.empty();
    if (list.size() <= index)
      return Optional.empty();

    return Optional.ofNullable(list.get(index));
  }

  public Function<Client, WebTarget> httpBasePathGenerator() {
    return client -> client.target("http://" + host + ":" + port).path(root);
  }

  public Function<Client, WebTarget> httpsBasePathGenerator() {
    return client -> client.target("https://" + host + ":" + port).path(root);
  }
}
