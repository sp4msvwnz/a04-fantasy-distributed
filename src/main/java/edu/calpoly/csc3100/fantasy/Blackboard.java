package edu.calpoly.csc3100.fantasy;

import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Player state and change notifications. The application confines access to the Swing EDT.
 * @author CSC 3100 Instructional Team
 * @version 1.0
 */
public final class Blackboard extends PropertyChangeSupport {
  public static final String PLAYERS = "players";
  public static final int COLUMNS = 16;
  public static final int ROWS = 11;
  private final Map<String,PlayerState> players = new LinkedHashMap<>();
  private final GamePublisher publisher;
  private String localUsername;

  public Blackboard(GamePublisher publisher) {
    super(Blackboard.class);
    this.publisher = Objects.requireNonNull(publisher);
  }

  private void store(PlayerState state) {
    PlayerState previous = players.put(state.player(), state);
    firePropertyChange(new java.beans.PropertyChangeEvent(this, PLAYERS, previous, state));
  }
  public PlayerState getPlayer(String username) { return players.get(username); }
  public Collection<PlayerState> players() { return List.copyOf(players.values()); }
  public void addLocalPlayer(PlayerState state) {
    localUsername = state.player();
    store(state);
    publisher.publish(state);
  }
  private static boolean supportedEquipment(String item) {
    return "sword".equals(item) || "shield".equals(item) || "shoes".equals(item);
  }
  public void moveLocal(String username, int dx, int dy) {
    // TODO: move only the local player one cardinal tile within the board
    if (localUsername == null || !localUsername.equals(username)) return;

    boolean horizontal = (dx == 1 || dx == -1) && dy == 0;

    boolean vertical = (dy == 1 || dy == -1) && dx == 0;

    if (!horizontal && !vertical) return;
    //therefore, 
    PlayerState current = players.get(username);
    if (current == null) return;

    //New coordinates
    int newX = current.x() + dx;
    int newY = current.y() + dy;
    if (newX < 0 || newX >= COLUMNS || newY < 0 || newY >= ROWS) return;


    //Lets create the snapshot
    PlayerState updated = new PlayerState(current.player(), current.type(), newX, newY, current.equipment());
    // Store the changed snapshot and publish it once.
    // lets notfiy
    store(updated);
    publisher.publish(updated);
  }
  public void applyRemote(PlayerState state) {
    if (!state.player().equals(localUsername)) store(state);
  }
  public void equipLocal(String username, String item) {
    // TODO: append supported equipment to the local snapshot if absent.
    // Store the changed snapshot and publish it once.
    if (!username.equals(localUsername)) return;
    if (!supportedEquipment(item)) return;
    PlayerState current = players.get(username);
    if (current == null || current.equipment().contains(item)) return;

    List<String> equipment = new ArrayList<>(current.equipment());
    equipment.add(item);

    PlayerState updated = new PlayerState(current.player(), current.type(), current.x(), current.y(), equipment);
    store(updated);
    publisher.publish(updated);
  }
  public void unequipLocal(String username, String item) {
    // TODO: remove the requested item from the local snapshot if present.
    // Preserve the order of the other items, store the snapshot, and publish it once.
    if (!username.equals(localUsername)) return;
    if (!supportedEquipment(item)) return;

    PlayerState current = players.get(username);
    if (current == null || !current.equipment().contains(item)) return;
    List<String> equipment = new ArrayList<>(current.equipment());
    equipment.remove(item);

    PlayerState updated = new PlayerState(current.player(), current.type(), current.x(), current.y(), equipment);
    store(updated);
    publisher.publish(updated);
  }
}
