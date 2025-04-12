package controll;

import model.Player;
import view.GamePanel;
import javax.swing.SwingUtilities;

public class PlayerThread extends Thread {
    private Player player;
    private GamePanel gamePanel;
    private volatile boolean running;
    private static final int MOVE_SPEED = 5;
    private static final int GAME_TICK = 16;

    private volatile boolean upPressed;
    private volatile boolean downPressed;
    private volatile boolean leftPressed;
    private volatile boolean rightPressed;

    public PlayerThread(Player player, GamePanel gamePanel) {
        this.player = player;
        this.gamePanel = gamePanel;
        this.running = true;
    }

    @Override
    public void run() {
        while (running && !gamePanel.isGameOver()) {
            updatePlayer();
            SwingUtilities.invokeLater(() -> {
                synchronized (gamePanel) {
                    gamePanel.updateGame();
                }
            });

            try {
                Thread.sleep(GAME_TICK);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void updatePlayer() {
        int dx = 0;
        int dy = 0;

        if (upPressed) dy -= MOVE_SPEED;
        if (downPressed) dy += MOVE_SPEED;
        if (leftPressed) dx -= MOVE_SPEED;
        if (rightPressed) dx += MOVE_SPEED;

        player.move(dx, dy);
        checkBoundaries();
    }

    private void checkBoundaries() {
        int panelWidth = gamePanel.getWidth();
        int panelHeight = gamePanel.getHeight();

        if (player.getPositionX() < 0) player.setPositionX(0);
        if (player.getPositionX() > panelWidth - player.getWidth())
            player.setPositionX(panelWidth - player.getWidth());
        if (player.getPositionY() < 0) player.setPositionY(0);
        if (player.getPositionY() > panelHeight - player.getHeight())
            player.setPositionY(panelHeight - player.getHeight());
    }

    public void setUpPressed(boolean upPressed) { this.upPressed = upPressed; }
    public void setDownPressed(boolean downPressed) { this.downPressed = downPressed; }
    public void setLeftPressed(boolean leftPressed) { this.leftPressed = leftPressed; }
    public void setRightPressed(boolean rightPressed) { this.rightPressed = rightPressed; }

    public void stopThread() { running = false; }
}