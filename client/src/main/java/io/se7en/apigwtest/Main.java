package io.se7en.apigwtest;

import javax.net.ssl.SSLException;
import javax.ws.rs.ProcessingException;

public class Main {
  public static void main(String[] arguments) throws Throwable {
    MainConfiguration config = new MainConfiguration(arguments);

    doHttpTest(config);
    System.out.println("====");
    doHttpsTest(config);

    System.out.println("Done.");
  }

  private static void doHttpTest(MainConfiguration config) throws Throwable {
    try (PingTest test = new PingTest()) {
      test.execute(config.httpBasePathGenerator());
    }
  }

  private static void doHttpsTest(MainConfiguration config) throws Throwable {
    try (PingTest test = new PingTest()) {
      test.execute(config.httpsBasePathGenerator());
    } catch (ProcessingException e) {
      Throwable cause = e.getCause();

      if (cause instanceof SSLException) {
        System.out.println("Unable to establish HTTPS conneciton; the server might not support it.");
      } else {
        throw cause;
      }
    }
  }
}
