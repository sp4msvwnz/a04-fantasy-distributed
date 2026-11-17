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
    // TODO: draw a visible sword to the right of the body, within the tile.

    java.awt.Color previousColor = g.getColor();

    try{
        g.setColor(new java.awt.Color(190, 190, 200));
        g.fillRect(x + 31, y + 8, 3, 19);

        g.setColor(new java.awt.Color(120, 75, 30));
        g.fillRect(x + 28, y + 25, 9, 3);
        g.fillRect(x + 31, y + 28, 3, 5);

    } finally{
        g.setColor(previousColor);
    }

  }

  public int attack() { return wrapped.attack() + 5;}
  public int defense() { return wrapped.defense(); }
  public String type() { return wrapped.type(); }
}
