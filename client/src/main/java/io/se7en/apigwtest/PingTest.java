package io.se7en.apigwtest;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class PingTest implements AutoCloseable {
  private final Client client;
  private final Supplier<WebTarget> basePathGenerator;

  public PingTest(Function<Client, WebTarget> basePathGenerator) {
    this.client = buildClient();
    this.basePathGenerator = () -> basePathGenerator.apply(client);
  }

  private Client buildClient() {
    return ClientBuilder
      .newBuilder()
      .register(new PingMessageBodyWorker())
      .register(new PongMessageBodyWorker())
      .build();
  }

  @Override
  public void close() throws Exception {
    client.close();
  }

  public void execute() {
    Ping ping = PingFactory.newPing();
    WebTarget target = basePathGenerator.get().path("ping");

    reportRequest(ping, target);

    try (Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(ping))) {
      reportResponse(response);
    }
  }

  private void reportResponse(Response response) {
    System.out.println("Response status: " + response.getStatus() + " " + response.getStatusInfo().getReasonPhrase());
    if (response.getStatus() == Status.OK.getStatusCode()) {
      if (response.hasEntity()) {
        Pong pong = response.readEntity(Pong.class);
        System.out.println("Response Pong Payload: " + pong.toString());
      }
    } else {
      if (response.hasEntity()) {
        String payload = response.readEntity(String.class);
        System.out.println("Response Payload: " + payload);
      }
    }
  }

  private void reportRequest(Ping ping, WebTarget target) {
    System.out.println("Testing POST request on " + target.getUri().toString() + " ...");
    System.out.println("Request Ping Payload: " + ping.toString());
    System.out.println("---------");
  }
}
