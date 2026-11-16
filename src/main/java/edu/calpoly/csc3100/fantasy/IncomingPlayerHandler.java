package edu.calpoly.csc3100.fantasy;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Processes received MQTT player-state messages independently of the broker.
 * Students complete the topic, decoding, and self-message handling.
 *
 * @author CSC 3100 Instructional Team
 * @version 1.0
 */
public final class IncomingPlayerHandler {
  private final String topicPrefix;
  private final String localUsername;
  private final Consumer<PlayerState> receiver;

  public IncomingPlayerHandler(String team, String localUsername,
                               Consumer<PlayerState> receiver) {
    this.topicPrefix = Subscriber.topicPrefix(team);
    this.localUsername = Objects.requireNonNull(localUsername);
    this.receiver = Objects.requireNonNull(receiver);
  }

  public void accept(String topic, byte[] payload) {
    if (!topic.startsWith(topicPrefix)) return;
    String player = topic.substring(topicPrefix.length());
    if (player.isEmpty() || player.contains("/") || player.equals(localUsername)) return;
    // TODO: complete the snapshot processing below.
    
    // TODO: decode UTF-8 JSON using PlayerCodec.
    try {
      String json = new String(payload, StandardCharsets.UTF_8);
      PlayerState state = PlayerCodec.decode(json);

      if (!player.equals(state.player())) return;

      receiver.accept(
        state
      );
    } catch (IllegalArgumentException e) {
      // Ignore malformed JSON or invalid player state.
      return;
    }
    
  }
}
