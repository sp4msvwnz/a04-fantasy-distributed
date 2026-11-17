package edu.calpoly.csc3100.fantasy;

import org.junit.jupiter.api.Test;

import javax.swing.SwingUtilities;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;


class StudentAcceptanceTest {
    

    @Test

    void equipmentChangesAndRestoresPlayerRendering() throws Exception {
        AtomicReference<int[]> before = new AtomicReference<>();
        AtomicReference<int[]> equipped = new AtomicReference<>();
        AtomicReference<int[]> after = new AtomicReference<>();

        SwingUtilities.invokeAndWait(() -> {
            Blackboard board = new Blackboard(state -> {});

            board.addLocalPlayer(new PlayerState("alice", "warrior", 2 ,2, List.of()));
            GamePanel panel = new GamePanel(board, "alice");
            panel.setSize(panel.getPreferredSize());

            before.set(renderPixels(panel));

            board.equipLocal("alice", "sword");
            equipped.set(renderPixels(panel));

            board.unequipLocal("alice", "sword");
            after.set(renderPixels(panel));

        });

        // Equipping a sword should change the rendering of the player
        assertFalse(Arrays.equals(before.get(), equipped.get()));

        // Removing the sword should restore the original appareance

        assertArrayEquals(before.get(), after.get());
}

private static int[] renderPixels(GamePanel panel) {
    int width = panel.getWidth();
    int height = panel.getHeight();

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();
    try {
        panel.paint(g);
    } finally {
        g.dispose();
    }

    return image.getRGB(0, 0, width, height, null, 0, width);
}
}