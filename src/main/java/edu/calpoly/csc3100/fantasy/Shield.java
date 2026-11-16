package edu.calpoly.csc3100.fantasy;

/**
 * undefined
 * @author CSC 3100 Instructional Team
 * @version 1.0
 */
public final class Shield implements Character {
  private final Character wrapped;
  public Shield(Character wrapped) { this.wrapped = java.util.Objects.requireNonNull(wrapped); }
  @Override public void draw(java.awt.Graphics2D g, int x, int y) {
    wrapped.draw(g, x, y);
    g.setColor(java.awt.Color.BLUE);
    g.fillOval(x + 4, y + 16, 10, 14);

    g.setColor(java.awt.Color.LIGHT_GRAY);
    g.fillOval(x + 8, y + 21, 3, 4);


  }

  public int attack() { return wrapped.attack(); }
  @Override
  public int defense() { 
    return wrapped.defense() + 5; }
  public String type() { return wrapped.type(); }
}
