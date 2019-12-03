package io.se7en.apigwtest;

import java.time.Instant;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apigwtest")
public class EndpointController {
  @PostMapping(path = "/ping", consumes = "application/json", produces = "application/json")
  public Pong ping(@RequestBody Ping ping) {
    return pong(ping);
  }

  private Pong pong(Ping ping) {
    return new Pong(
      respondToChallenge(ping.getChallenge()),
      buildPongMessage(ping.getTimestamp()),
      complementNonce(ping.getNonce())
    );
  }

  private String respondToChallenge(String challenge) {
    if (challenge.equalsIgnoreCase("hello"))
      return "hi";
    if (challenge.equalsIgnoreCase("hi"))
      return "hello";

    return null;
  }

  private String buildPongMessage(Instant pingTimestamp) {
    String pingMessage = "ping(" + pingTimestamp.toString() + ")";
    String pongMessage = "pong(" + Instant.now().toString() + ")";
    return pingMessage + "-->" + pongMessage;
  }

  private int complementNonce(int pingNonce) {
    return pingNonce < 0 ? Integer.MIN_VALUE + pingNonce : Integer.MAX_VALUE - pingNonce;
  }
}
