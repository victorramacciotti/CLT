package controll;

import javax.swing.SwingUtilities;

import model.Player;
import view.GamePanel;

public class PlayerThread extends Thread {
    private Player player;
    private GamePanel gamePanel;
    private volatile boolean running;
    private static final int MOVE_SPEED = 5;
    private static final int GAME_TICK = 16;
    private static final int GAME_GROUND = 100;

    private static final int GRAVITY = 1;
    private static final int JUMP_STRENGTH = -15;
    private int velocityY = 0;
    private boolean onGround = true;

    private volatile boolean upPressed;
    private volatile boolean downPressed;
    private volatile boolean leftPressed;
    private volatile boolean rightPressed;
    boolean value = true;

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
    	
    	if(value) {
    		System.out.println(player.getPositionX());
    		System.out.println(player.getPositionY());
    		value = false;
    	}
    	
    	int dx = 0;

        if (leftPressed) dx -= MOVE_SPEED;
        if (rightPressed) dx += MOVE_SPEED;

        // Pulo: só pula se estiver no chão
        if (upPressed && onGround) {
            velocityY = JUMP_STRENGTH;
            onGround = false;
        }

        // Aplicar gravidade
        velocityY += GRAVITY;
        int dy = velocityY;

        player.move(dx, dy);
        checkBoundaries();
    }

    private void checkBoundaries() {
        int panelWidth = gamePanel.getWidth();
        int panelHeight = gamePanel.getHeight();

        // Limites horizontais
        if (player.getPositionX() < 0) player.setPositionX(0);
        if (player.getPositionX() > panelWidth - player.getWidth())
            player.setPositionX(panelWidth - player.getWidth());

        // Limites verticais
        if (player.getPositionY() < 0) {
            player.setPositionY(0);
            velocityY = 0;
        }

        // "Chão"
        if (player.getPositionY() > panelHeight - player.getHeight() - GAME_GROUND) {
            player.setPositionY(panelHeight - player.getHeight() - GAME_GROUND);
            velocityY = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }

    public void setUpPressed(boolean upPressed) { this.upPressed = upPressed; }
    public void setDownPressed(boolean downPressed) { this.downPressed = downPressed; }
    public void setLeftPressed(boolean leftPressed) { this.leftPressed = leftPressed; }
    public void setRightPressed(boolean rightPressed) { this.rightPressed = rightPressed; }

    public void stopThread() { running = false; }
}
