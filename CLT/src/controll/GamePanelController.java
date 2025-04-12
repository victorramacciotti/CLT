package controll;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.Player;
import view.GamePanel;
import view.GameWindow;

public class GamePanelController implements KeyListener {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Player player1;
    private Player player2;
    private PlayerThread player1Thread;
    private PlayerThread player2Thread;

    public GamePanelController(GameWindow gameWindow, GamePanel gamePanel, Player player1, Player player2) {
        this.gameWindow = gameWindow;
        this.gamePanel = gamePanel;
        this.player1 = player1;
        this.player2 = player2;

        player1Thread = new PlayerThread(player1, gamePanel);
        player2Thread = new PlayerThread(player2, gamePanel);

        gamePanel.addKeyListener(this);
        gamePanel.requestFocusInWindow();

        player1Thread.start();
        player2Thread.start();
    }

    public void setWPressed(boolean pressed) { player1Thread.setUpPressed(pressed); }
    public void setSPressed(boolean pressed) { player1Thread.setDownPressed(pressed); }
    public void setAPressed(boolean pressed) { player1Thread.setLeftPressed(pressed); }
    public void setDPressed(boolean pressed) { player1Thread.setRightPressed(pressed); }

    public void setUpPressed(boolean pressed) { player2Thread.setUpPressed(pressed); }
    public void setDownPressed(boolean pressed) { player2Thread.setDownPressed(pressed); }
    public void setLeftPressed(boolean pressed) { player2Thread.setLeftPressed(pressed); }
    public void setRightPressed(boolean pressed) { player2Thread.setRightPressed(pressed); }

    public void stopGame() {
        player1Thread.stopThread();
        player2Thread.stopThread();
        try {
            player1Thread.join();
            player2Thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isInRange(Player attacker, Player opponent) {
        int dx = Math.abs(attacker.getPositionX() - opponent.getPositionX());
        int dy = Math.abs(attacker.getPositionY() - opponent.getPositionY());
        return dx < 100 && dy < 50; // Distância arbitrária para ataque
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gamePanel.isGameOver()) return;

        switch (e.getKeyCode()) {
            // Movimento Player 1
            case KeyEvent.VK_W: setWPressed(true); break;
            case KeyEvent.VK_A: setAPressed(true); break;
            case KeyEvent.VK_S: setSPressed(true); break;
            case KeyEvent.VK_D: setDPressed(true); break;
            // Ataques Player 1
            case KeyEvent.VK_Q: 
                if (isInRange(player1, player2)) player1.attack(player2, "weak", false); 
                break;
            case KeyEvent.VK_E: 
                if (isInRange(player1, player2)) player1.attack(player2, "strong", false); 
                break;
            case KeyEvent.VK_R: 
                if (isInRange(player1, player2)) player1.attack(player2, "special", false); 
                break;

            // Movimento Player 2
            case KeyEvent.VK_UP: setUpPressed(true); break;
            case KeyEvent.VK_DOWN: setDownPressed(true); break;
            case KeyEvent.VK_LEFT: setLeftPressed(true); break;
            case KeyEvent.VK_RIGHT: setRightPressed(true); break;
            // Ataques Player 2
            case KeyEvent.VK_I: 
                if (isInRange(player2, player1)) player2.attack(player1, "weak", false); 
                break;
            case KeyEvent.VK_O: 
                if (isInRange(player2, player1)) player2.attack(player1, "strong", false); 
                break;
            case KeyEvent.VK_P: 
                if (isInRange(player2, player1)) player2.attack(player1, "special", false); 
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gamePanel.isGameOver()) return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: setWPressed(false); break;
            case KeyEvent.VK_A: setAPressed(false); break;
            case KeyEvent.VK_S: setSPressed(false); break;
            case KeyEvent.VK_D: setDPressed(false); break;
            case KeyEvent.VK_UP: setUpPressed(false); break;
            case KeyEvent.VK_DOWN: setDownPressed(false); break;
            case KeyEvent.VK_LEFT: setLeftPressed(false); break;
            case KeyEvent.VK_RIGHT: setRightPressed(false); break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}