package edu.calpoly.csc3100.fantasy;

/**
 * undefined
 * @author CSC 3100 Instructional Team
 * @version 1.0
 */
public final class Sword implements Character {
  private final Character wrapped;
  public Sword(Character wrapped) { this.wrapped = java.util.Objects.requireNonNull(wrapped); }
  @Override public void draw(java.awt.Graphics2D g, int x, int y) {
    wrapped.draw(g, x, y);
    g.setColor(java.awt.Color.LIGHT_GRAY);
    g.fillRect(x + 32, y +9, 3, 17);
    g.setColor(java.awt.Color.DARK_GRAY);
    g.fillRect(x + 29, y + 25, 9, 3);
    g.fillRect(x + 32, y + 28, 3, 5);
  }
  @Override 
  public int attack() { 
    return wrapped.attack() + 5; }

  public int defense() { return wrapped.defense(); }
  public String type() { return wrapped.type(); }
}
