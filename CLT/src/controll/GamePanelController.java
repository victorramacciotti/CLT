package controll;

import model.Player;
import view.GamePanel;
import view.GameWindow;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanelController implements KeyListener {
    
    // Dependencies
    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    private final Player player1;
    private final Player player2;
    
    // Game state
    private final PlayerThread player1Thread;
    private final PlayerThread player2Thread;
    private final GameTimer gameTimer;
    private final JLabel timerLabel;
    
    // Constants
    private static final int MAX_GAME_TIME_SECONDS = 120; // 2 minutes

    public GamePanelController(GameWindow gameWindow, GamePanel gamePanel, Player player1, Player player2) {
        this.gameWindow = gameWindow;
        this.gamePanel = gamePanel;
        this.player1 = player1;
        this.player2 = player2;
        
        this.player1Thread = new PlayerThread(player1, gamePanel);
        this.player2Thread = new PlayerThread(player2, gamePanel);
        
        this.timerLabel = setupTimerLabel();
        this.gameTimer = new GameTimer(MAX_GAME_TIME_SECONDS, timerLabel);
        
        setupInput();
        startGameMonitoring();
        gameTimer.setOnTimeout(this::endGameByTime);
        gameTimer.start();
        
        player1Thread.start();
        player2Thread.start();
    }

    private JLabel setupTimerLabel() {
        JLabel label = new JLabel(String.format("%03d", MAX_GAME_TIME_SECONDS));
        label.setFont(new Font("Tahoma", Font.BOLD, 40));
        label.setForeground(Color.WHITE);
        label.setBounds(335, 10, 80, 30);
        gamePanel.getTopPanel().add(label);
        gamePanel.revalidate();
        gamePanel.repaint();
        return label;
    }

    private void setupInput() {
        gamePanel.addKeyListener(this);
        gamePanel.requestFocusInWindow();
    }

    private void startGameMonitoring() {
        new Thread(() -> {
            while (!gamePanel.isGameOver()) {
                checkGameEnd();
                try {
                    Thread.sleep(100); // Check every 100ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void checkGameEnd() {
        String winner = null;
        if (player1.getCharacter().getLife() <= 0) {
            winner = player2.getCharacter().getName();
        } else if (player2.getCharacter().getLife() <= 0) {
            winner = player1.getCharacter().getName();
        }
        
        if (winner != null) {
            endGame(winner);
        }
    }

    private void endGameByTime() {
        String winner;
        if (player1.getCharacter().getLife() > player2.getCharacter().getLife()) {
            winner = player1.getCharacter().getName();
        } else if (player2.getCharacter().getLife() > player1.getCharacter().getLife()) {
            winner = player2.getCharacter().getName();
        } else {
            winner = "Draw";
        }
        endGame(winner);
    }

    private void endGame(String winner) {
        gamePanel.showGameOver(winner);
        stopGame();
        gameTimer.stop();
    }

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
        return dx < 100 && dy < 50;
    }
    
    // Player 1 input setters
    public void setWPressed(boolean pressed) {
        player1Thread.setUpPressed(pressed);
    }
    
    public void setSPressed(boolean pressed) {
        player1Thread.setDownPressed(pressed);
    }
    
    public void setAPressed(boolean pressed) {
        player1Thread.setLeftPressed(pressed);
    }
    
    public void setDPressed(boolean pressed) {
        player1Thread.setRightPressed(pressed);
    }
    
    // Player 2 input setters
    public void setUpPressed(boolean pressed) {
        player2Thread.setUpPressed(pressed);
    }
    
    public void setDownPressed(boolean pressed) {
        player2Thread.setDownPressed(pressed);
    }
    
    public void setLeftPressed(boolean pressed) {
        player2Thread.setLeftPressed(pressed);
    }
    
    public void setRightPressed(boolean pressed) {
        player2Thread.setRightPressed(pressed);
    }
    
    // KeyListener implementation
    @Override
    public void keyPressed(KeyEvent e) {
        if (gamePanel.isGameOver()) {
            return;
        }
        
        switch (e.getKeyCode()) {
            // Player 1 movement
            case KeyEvent.VK_W:
                setWPressed(true);
                break;
            case KeyEvent.VK_A:
                setAPressed(true);
                break;
            case KeyEvent.VK_S:
                setSPressed(true);
                break;
            case KeyEvent.VK_D:
                setDPressed(true);
                break;
                
            // Player 1 attacks
            case KeyEvent.VK_Q:
                if (isInRange(player1, player2)) {
                    player1.attack(player2, "weak", false);
                }
                break;
            case KeyEvent.VK_E:
                if (isInRange(player1, player2)) {
                    player1.attack(player2, "strong", false);
                }
                break;
            case KeyEvent.VK_R:
                if (isInRange(player1, player2)) {
                    player1.attack(player2, "special", false);
                }
                break;
                
            // Player 2 movement
            case KeyEvent.VK_UP:
                setUpPressed(true);
                break;
            case KeyEvent.VK_DOWN:
                setDownPressed(true);
                break;
            case KeyEvent.VK_LEFT:
                setLeftPressed(true);
                break;
            case KeyEvent.VK_RIGHT:
                setRightPressed(true);
                break;
                
            // Player 2 attacks
            case KeyEvent.VK_I:
                if (isInRange(player2, player1)) {
                    player2.attack(player1, "weak", false);
                }
                break;
            case KeyEvent.VK_O:
                if (isInRange(player2, player1)) {
                    player2.attack(player1, "strong", false);
                }
                break;
            case KeyEvent.VK_P:
                if (isInRange(player2, player1)) {
                    player2.attack(player1, "special", false);
                }
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (gamePanel.isGameOver()) {
            return;
        }
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                setWPressed(false);
                break;
            case KeyEvent.VK_A:
                setAPressed(false);
                break;
            case KeyEvent.VK_S:
                setSPressed(false);
                break;
            case KeyEvent.VK_D:
                setDPressed(false);
                break;
            case KeyEvent.VK_UP:
                setUpPressed(false);
                break;
            case KeyEvent.VK_DOWN:
                setDownPressed(false);
                break;
            case KeyEvent.VK_LEFT:
                setLeftPressed(false);
                break;
            case KeyEvent.VK_RIGHT:
                setRightPressed(false);
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
}