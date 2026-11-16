package edu.calpoly.csc3100.fantasy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * undefined
 * @author CSC 3100 Instructional Team
 * @version 1.0
 */
public final class PlayerCodec {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private PlayerCodec() { }
  public static String encode(PlayerState state) {
    try { return MAPPER.writeValueAsString(state); }
    catch (Exception e) { throw new IllegalArgumentException("Cannot encode player", e); }
  }
  public static PlayerState decode(String json) {
    try {
      JsonNode root = MAPPER.readTree(json);
      // TODO: decode JSON and validate the required fields, type, board coordinates,
      if (root == null || !root.isObject()) {
        throw new IllegalArgumentException("Invalid JSON Object"); }
      // and equipment before returning a snapshot. See the README and acceptance tests.
    

    JsonNode playerNode = root.get("player");
    JsonNode typeNode = root.get("type");
    JsonNode xNode = root.get("x");
    JsonNode yNode = root.get("y");
    JsonNode equipmentNode = root.get("equipment");

    if (playerNode == null || !playerNode.isTextual() || playerNode.asText().isBlank()) {
      throw new IllegalArgumentException("Invalid player name");
    }

    String player = playerNode.asText();


    if (typeNode == null || !typeNode.isTextual() ||
        !"warrior".equalsIgnoreCase(typeNode.asText())){
          throw new IllegalArgumentException("Invalid player type");
        }

    String type = typeNode.asText();

    if (xNode ==  null || yNode == null ||
        !xNode.isIntegralNumber() ||
        !yNode.isIntegralNumber() ||
        !xNode.canConvertToInt() ||
        !yNode.canConvertToInt()) {
        throw new IllegalArgumentException("Invalid board coordinates");
        }
    
    int x = xNode.intValue();
    int y = yNode.intValue();

    if (x<0 || y >= Blackboard.COLUMNS ||
        y < 0 || y >= Blackboard.ROWS) {
          throw new IllegalArgumentException("Board coordinates out of bounds");
        }
    
    if (equipmentNode == null || !equipmentNode.isArray()) {
      throw new IllegalArgumentException("Invalid equipment");
    }

    List<String> equipment = new ArrayList<>();
    Set<String> equipmentSet = new HashSet<>();
    for (JsonNode itemNode : equipmentNode) {
      if (!itemNode.isTextual()) {
        throw new IllegalArgumentException("Invalid equipment item");
      }
      String item = itemNode.asText();
      if (!item.equals("sword") &&
          !item.equals("shield") &&
          !item.equals("shoes")) {
        throw new IllegalArgumentException("Unsupported equipment item: ");
          }
      if (!equipmentSet.add(item)) {
        throw new IllegalArgumentException("Duplicate equipment item: ");
      }
      equipment.add(item);

    }

    return new PlayerState(player, type, x, y, equipment);
    
  }
    catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Cannot decode player", e);
    }
  }
}
