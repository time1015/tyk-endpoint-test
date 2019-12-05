package io.se7en.apigwtest;

public class Main {
  public static void main(String[] arguments) throws Throwable {
    MainConfiguration config = new MainConfiguration(arguments);

    doHttpTest(config);
    System.out.println("====");
    doHttpsTest(config);

    System.out.println("Done.");
  }

  private static void doHttpTest(MainConfiguration config) {
    try (PingTest test = new PingTest(ClientBuilderFactory.forHttp(), config.httpBasePathGenerator())) {
      test.execute();
    } catch (Throwable t) {
      System.out.println("An error occurred!");
      printStackTracesOfAllCauses(t);
      System.out.println("Cancelled.");
    }
  }

  private static void doHttpsTest(MainConfiguration config) throws Throwable {
    try (PingTest test = new PingTest(ClientBuilderFactory.forHttps(), config.httpsBasePathGenerator())) {
      test.execute();
    } catch (Throwable t) {
      System.out.println("An error occurred!");
      printStackTracesOfAllCauses(t);
      System.out.println("Cancelled.");
    }
  }

  private static void printStackTracesOfAllCauses(Throwable throwable) {
    System.out.println("===");
    for (Throwable t = throwable; t != null; t = t.getCause()) {
      t.printStackTrace(System.out);
      System.out.println("===");
    }
  }
}
