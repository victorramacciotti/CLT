package controll;

import model.Player;
import view.GamePanel;
import view.GameWindow;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

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
    private Timer releaseDelayTimer;
    
    // Constants
    private static final int MAX_GAME_TIME_SECONDS = 120; // 2 minutes

    public GamePanelController(GameWindow gameWindow, GamePanel gamePanel, Player player1, Player player2) {
        this.gameWindow = gameWindow;
        this.gamePanel = gamePanel;
        this.player1 = player1;
        this.player2 = player2;
        
        this.player1Thread = new PlayerThread(player1, gamePanel, this);
        this.player2Thread = new PlayerThread(player2, gamePanel, this);
        
        this.gameTimer = new GameTimer(MAX_GAME_TIME_SECONDS, gamePanel.getTimerLabel());
        
        setupInput();
        startGameMonitoring();
        gameTimer.setOnTimeout(this::endGameByTime);
        gameTimer.start();
        
        player1Thread.start();
        player2Thread.start();
    }

    private void setupInput() {
        gamePanel.addKeyListener(this);
        gamePanel.requestFocusInWindow();
    }

    private void startGameMonitoring() {
        new Thread(() -> {
            while (!gamePanel.isGameOver()) {
                checkGameEnd();
                updateGame();
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
        // Calcula a porcentagem de vida restante para cada personagem
        double player1LifePercentage = (double) player1.getCharacter().getLife() / player1.getCharacter().getMaxLife();
        double player2LifePercentage = (double) player2.getCharacter().getLife() / player2.getCharacter().getMaxLife();

        if (player1LifePercentage > player2LifePercentage) {
            winner = player1.getCharacter().getName();
        } else if (player2LifePercentage > player1LifePercentage) {
            winner = player2.getCharacter().getName();
        } else {
            winner = "Draw";
        }
        endGame(winner);
    }

    private void endGame(String winner) {
        showGameOver(winner);
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

    private void updateLifeBarColor(JProgressBar lifeBar, double life) {
        double percentage = (life / lifeBar.getMaximum()) * 100;
        if (percentage >= 75) {
            lifeBar.setForeground(Color.GREEN);
        } else if (percentage >= 50) {
            lifeBar.setForeground(Color.YELLOW);
        } else if (percentage >= 25) {
            lifeBar.setForeground(Color.ORANGE);
        } else {
            lifeBar.setForeground(Color.RED);
        }
    }

    public void updateGame() {
        if (gamePanel.isGameOver()) {
            return;
        }
        
        gamePanel.getSpriteLabel1().setLocation(player1.getPositionX(), player1.getPositionY());
        gamePanel.getSpriteLabel2().setLocation(player2.getPositionX(), player2.getPositionY());
        
        double life1 = player1.getCharacter().getLife();
        double life2 = player2.getCharacter().getLife();
        
        JProgressBar lifeBar1 = gamePanel.getLifeBar1();
        lifeBar1.setMaximum((int) player1.getCharacter().getMaxLife());
        lifeBar1.setValue((int) life1);
        updateLifeBarColor(lifeBar1, life1);
        
        JProgressBar lifeBar2 = gamePanel.getLifeBar2();
        lifeBar2.setMaximum((int) player2.getCharacter().getMaxLife());
        lifeBar2.setValue((int) life2);
        updateLifeBarColor(lifeBar2, life2);
        
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    public void showGameOver(String winner) {
        gamePanel.setGameOver(true);
        
        // Personaliza o texto do label com base no valor de winner
        String labelText = winner.equals("Draw") ? "Game Over! It's a Draw!" : "Game Over! Winner: " + winner;
        JLabel gameOverLabel = new JLabel(labelText);
        gameOverLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        gameOverLabel.setForeground(Color.YELLOW);
        gameOverLabel.setBounds(300, 300, 500, 50);
        gamePanel.setGameOverLabel(gameOverLabel);
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
                player1.setState("jump");
                gamePanel.getSpriteLabel1().setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel1().revalidate();
                gamePanel.getSpriteLabel1().repaint();
                
                break;
            case KeyEvent.VK_A:
                setAPressed(true);
                player1.setState("backwards");
                gamePanel.getSpriteLabel1().setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel1().revalidate();
                gamePanel.getSpriteLabel1().repaint();
                break;
            case KeyEvent.VK_S:
                setSPressed(true);
                break;
            case KeyEvent.VK_D:
                setDPressed(true);
                player1.setState("walk");
                gamePanel.getSpriteLabel1().setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel1().revalidate();
                gamePanel.getSpriteLabel1().repaint();
                break;
                
            // Player 1 attacks
            case KeyEvent.VK_Q:
            	player1.setState("punch");
                gamePanel.getSpriteLabel1().setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel1().revalidate();
                gamePanel.getSpriteLabel1().repaint();
                if (isInRange(player1, player2)) {
                    player1.attack(player2, "weak", false);
                }
                break;
            case KeyEvent.VK_E:
            	player1.setState("kick");
                gamePanel.getSpriteLabel1().setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel1().revalidate();
                gamePanel.getSpriteLabel1().repaint();
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
                player2.setState("jump");
                gamePanel.getSpriteLabel2().setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel2().revalidate();
                gamePanel.getSpriteLabel2().repaint();
                break;
            case KeyEvent.VK_DOWN:
                setDownPressed(true);
                break;
            case KeyEvent.VK_LEFT:
                setLeftPressed(true);
                player2.setState("walk");
                gamePanel.getSpriteLabel2().setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel2().revalidate();
                gamePanel.getSpriteLabel2().repaint();
                break;
            case KeyEvent.VK_RIGHT:
                setRightPressed(true);
                player2.setState("backwards");
                gamePanel.getSpriteLabel2().setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel2().revalidate();
                gamePanel.getSpriteLabel2().repaint();
                break;
                
            // Player 2 attacks
            case KeyEvent.VK_I:
            	player2.setState("punch");
                gamePanel.getSpriteLabel2().setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel2().revalidate();
                gamePanel.getSpriteLabel2().repaint();
                if (isInRange(player2, player1)) {
                    player2.attack(player1, "weak", false);
                }
                break;
            case KeyEvent.VK_O:
            	player2.setState("kick");
                gamePanel.getSpriteLabel2().setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel2().revalidate();
                gamePanel.getSpriteLabel2().repaint();
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
                player1.setState("idle");
                gamePanel.getSpriteLabel1().setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel1().revalidate();
                gamePanel.getSpriteLabel1().repaint();
                break;
            case KeyEvent.VK_A:
                setAPressed(false);
                player1.setState("idle");
                gamePanel.getSpriteLabel1().setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel1().revalidate();
                gamePanel.getSpriteLabel1().repaint();
                break;
            case KeyEvent.VK_S:
                setSPressed(false);
                break;
            case KeyEvent.VK_D:
                setDPressed(false);
                player1.setState("idle");
                gamePanel.getSpriteLabel1().setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel1().revalidate();
                gamePanel.getSpriteLabel1().repaint();
                break;
            case KeyEvent.VK_UP:
                setUpPressed(false);
                player2.setState("idle");
                gamePanel.getSpriteLabel2().setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel2().revalidate();
                gamePanel.getSpriteLabel2().repaint();
                break;
            case KeyEvent.VK_DOWN:
                setDownPressed(false);
                player2.setState("idle");
                gamePanel.getSpriteLabel2().setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel2().revalidate();
                gamePanel.getSpriteLabel2().repaint();
                break;
            case KeyEvent.VK_LEFT:
                setLeftPressed(false);
                player2.setState("idle");
                gamePanel.getSpriteLabel2().setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel2().revalidate();
                gamePanel.getSpriteLabel2().repaint();
                break;
            case KeyEvent.VK_RIGHT:
                setRightPressed(false);
                player2.setState("idle");
                gamePanel.getSpriteLabel2().setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel2().revalidate();
                gamePanel.getSpriteLabel2().repaint();
                break;
            case KeyEvent.VK_E:
            	// Cancela qualquer timer anterior
                if (releaseDelayTimer != null && releaseDelayTimer.isRunning()) {
                    releaseDelayTimer.stop();
                }
                
                // Cria um novo timer que só roda depois de 500ms
                releaseDelayTimer = new Timer(500, evt -> {
                	player1.setState("idle");
                    gamePanel.getSpriteLabel1().setIcon(new ImageIcon(player1.getCurrentGif()));
                    gamePanel.getSpriteLabel1().revalidate();
                    gamePanel.getSpriteLabel1().repaint();
                });

                releaseDelayTimer.setRepeats(false); // Executa só uma vez
                releaseDelayTimer.start();
                
                break;
            case KeyEvent.VK_Q:
            	if (releaseDelayTimer != null && releaseDelayTimer.isRunning()) {
                    releaseDelayTimer.stop();
                }
                
                releaseDelayTimer = new Timer(200, evt -> {
                	player1.setState("idle");
                    gamePanel.getSpriteLabel1().setIcon(new ImageIcon(player1.getCurrentGif()));
                    gamePanel.getSpriteLabel1().revalidate();
                    gamePanel.getSpriteLabel1().repaint();
                });

                releaseDelayTimer.setRepeats(false); 
                releaseDelayTimer.start();
                break;
            case KeyEvent.VK_I:
            	if (releaseDelayTimer != null && releaseDelayTimer.isRunning()) {
                    releaseDelayTimer.stop();
                }
                
                releaseDelayTimer = new Timer(200, evt -> {
                	player2.setState("idle");
                    gamePanel.getSpriteLabel2().setIcon(new ImageIcon(player2.getCurrentGif()));
                    gamePanel.getSpriteLabel2().revalidate();
                    gamePanel.getSpriteLabel2().repaint();
                });

                releaseDelayTimer.setRepeats(false); 
                releaseDelayTimer.start();
                break;
            case KeyEvent.VK_O:
            	if (releaseDelayTimer != null && releaseDelayTimer.isRunning()) {
                    releaseDelayTimer.stop();
                }
                
                releaseDelayTimer = new Timer(500, evt -> {
                	player2.setState("idle");
                    gamePanel.getSpriteLabel2().setIcon(new ImageIcon(player2.getCurrentGif()));
                    gamePanel.getSpriteLabel2().revalidate();
                    gamePanel.getSpriteLabel2().repaint();
                });

                releaseDelayTimer.setRepeats(false); 
                releaseDelayTimer.start();
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
}