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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GamePanelController implements KeyListener {
    
    // Dependencies
    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    private final Player player1;
    private final Player player2;
    private Set<Integer> pressedKeys = new HashSet<Integer>();
    
    // Game state
    private final PlayerThread player1Thread;
    private final PlayerThread player2Thread;
    private final GameTimer gameTimer;
    private Timer releaseDelayTimer;
    
    // Constants
    private static final int MAX_GAME_TIME_SECONDS = 120; // 2 minutes
    
 // Rastrear sequência de inputs do Player 1
    private List<Integer> player1InputSequence = new ArrayList<>();
    private long lastPlayer1InputTime = 0;
    private static final int COMBO_TIMEOUT = 1000; // 1 segundo para completar o combo
    private static final int[] BACK_FORWARD_KICK_COMBO_P1 = { 
        KeyEvent.VK_A, // Trás (A)
        KeyEvent.VK_D, // Frente (D)
        KeyEvent.VK_E  // Chute (E)
    };
    private static final int[] BACK_FORWARD_KICK_COMBO_P2 = { 
            KeyEvent.VK_RIGHT, // Trás (right)
            KeyEvent.VK_LEFT, // Frente (left)
            KeyEvent.VK_O  // Chute (O)
        };

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
        
        gamePanel.getSpriteLabel(player1).setLocation(player1.getPositionX(), player1.getPositionY());
        gamePanel.getSpriteLabel(player2).setLocation(player2.getPositionX(), player2.getPositionY());
        
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
        gamePanel.setGameOverLabel(labelText);
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
        
        int keyCode = e.getKeyCode();
        pressedKeys.add(e.getKeyCode());
        
        switch (e.getKeyCode()) {
            // Player 1 movement
            case KeyEvent.VK_W:
                setWPressed(true);
                player1.setState("jump");
                gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel(player1).revalidate();
                gamePanel.getSpriteLabel(player1).repaint();
                
                break;
            case KeyEvent.VK_A:
                setAPressed(true);
                player1.setState("backwards");
                gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel(player1).revalidate();
                gamePanel.getSpriteLabel(player1).repaint();
                addToComboSequence(keyCode);// Adiciona tecla à sequência
                break;
            case KeyEvent.VK_S:
                setSPressed(true);
                break;
            case KeyEvent.VK_D:
                setDPressed(true);
                player1.setState("walk");
                gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel(player1).revalidate();
                gamePanel.getSpriteLabel(player1).repaint();
                addToComboSequence(keyCode);// Adiciona tecla à sequência
                break;
                
            // Player 1 attacks
            case KeyEvent.VK_Q:
            	player1.setState("punch");
            	gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel(player1).revalidate();
                gamePanel.getSpriteLabel(player1).repaint();
                if (isInRange(player1, player2)) {
                    player1.attack(player2, "punch");
                }
                break;
            case KeyEvent.VK_E:
            	player1.setState("kick");
            	gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel(player1).revalidate();
                gamePanel.getSpriteLabel(player1).repaint();
                addToComboSequence(keyCode); // Adiciona tecla à sequência
                checkCombo(player1, player2); // Verifica o combo
                if (isInRange(player1, player2)) {
                    player1.attack(player2, "kick");
                }
                break;
            case KeyEvent.VK_R:
            	if(pressedKeys.contains(KeyEvent.VK_F)) {
                	player1.setState("comboAttack");
                	gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                    gamePanel.getSpriteLabel(player1).revalidate();
                    gamePanel.getSpriteLabel(player1).repaint();
                    
                    if (isInRange(player1, player2)) {
                        player1.attack(player2, "combo");
                    }
                }
            	break;
            case KeyEvent.VK_F:
            	if(pressedKeys.contains(KeyEvent.VK_R)) {
            		gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                    gamePanel.getSpriteLabel(player1).revalidate();
                    gamePanel.getSpriteLabel(player1).repaint();
                    
                    if (isInRange(player1, player2)) {
                        player1.attack(player2, "combo");
                    }
                }
            	break;
                
            // Player 2 movement
            case KeyEvent.VK_UP:
                setUpPressed(true);
                player2.setState("jump");
                gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel(player2).revalidate();
                gamePanel.getSpriteLabel(player2).repaint();
                break;
            case KeyEvent.VK_DOWN:
                setDownPressed(true);
                break;
            case KeyEvent.VK_LEFT:
                setLeftPressed(true);
                player2.setState("walk");
                gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel(player2).revalidate();
                gamePanel.getSpriteLabel(player2).repaint();
                break;
            case KeyEvent.VK_RIGHT:
                setRightPressed(true);
                player2.setState("backwards");
                gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel(player2).revalidate();
                gamePanel.getSpriteLabel(player2).repaint();
                break;
                
            // Player 2 attacks
            case KeyEvent.VK_I:
            	player2.setState("punch");
            	gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel(player2).revalidate();
                gamePanel.getSpriteLabel(player2).repaint();
                if (isInRange(player2, player1)) {
                    player2.attack(player1, "punch");
                }
                break;
            case KeyEvent.VK_O:
            	player2.setState("kick");
            	gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel(player2).revalidate();
                gamePanel.getSpriteLabel(player2).repaint();
                if (isInRange(player2, player1)) {
                    player2.attack(player1, "kick");
                }
                break;
            case KeyEvent.VK_K:
            	if(pressedKeys.contains(KeyEvent.VK_L)) {
                	player2.setState("comboAttack");
                	gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                    gamePanel.getSpriteLabel(player2).revalidate();
                    gamePanel.getSpriteLabel(player2).repaint();
                    
                    if (isInRange(player1, player2)) {
                        player2.attack(player1, "combo");
                    }
                }
            	break;
            case KeyEvent.VK_L:
            	if(pressedKeys.contains(KeyEvent.VK_K)) {
                	player2.setState("comboAttack");
                	gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                    gamePanel.getSpriteLabel(player2).revalidate();
                    gamePanel.getSpriteLabel(player2).repaint();
                    
                    if (isInRange(player1, player2)) {
                        player2.attack(player1, "combo");
                    }
                }
            	break;
        }
    }
    
 // Adiciona a tecla pressionada à sequência e atualiza o tempo
    private void addToComboSequence(int keyCode) {
        player1InputSequence.add(keyCode);
        lastPlayer1InputTime = System.currentTimeMillis();
    }

    // Verifica se a sequência atual corresponde ao combo desejado
    private void checkCombo(Player player, Player opponent) {
        if (isComboValid(BACK_FORWARD_KICK_COMBO_P1)) {
            executecombo(player, opponent);
            player1InputSequence.clear(); // Reseta após executar
        }
        else if (isComboValid(BACK_FORWARD_KICK_COMBO_P2)) {
            executecombo(player, opponent);
            player1InputSequence.clear(); // Reseta após executar
        }
    }

    // Valida se a sequência e o tempo estão corretos
    private boolean isComboValid(int[] combo) {
        if (player1InputSequence.size() < combo.length) return false;

        // Pega os últimos inputs do tamanho do combo
        List<Integer> recentInputs = player1InputSequence.subList(
            player1InputSequence.size() - combo.length, 
            player1InputSequence.size()
        );

        // Verifica sequência e tempo
        for (int i = 0; i < combo.length; i++) {
            if (recentInputs.get(i) != combo[i]) return false;
        }

        // Verifica se o combo foi feito dentro do tempo limite
        return (System.currentTimeMillis() - lastPlayer1InputTime) <= COMBO_TIMEOUT;
    }
    
    private void executecombo(Player player, Player opponent) {
        if (isInRange(player, opponent)) {
            player.attack(opponent, "combo"); // Dano maior
            player.setState("comboAttack");
            gamePanel.getSpriteLabel(player).setIcon(new ImageIcon(player.getCurrentGif()));
            gamePanel.getSpriteLabel(player).revalidate();
            gamePanel.getSpriteLabel(player).repaint();
            System.out.println(player.getCurrentGif());
            releaseDelayTimer = new Timer(1000, evt -> {
            	player.setState("idle");
                gamePanel.getSpriteLabel(player).setIcon(new ImageIcon(player.getCurrentGif()));
                gamePanel.getSpriteLabel(player).revalidate();
                gamePanel.getSpriteLabel(player).repaint();
            });

            releaseDelayTimer.setRepeats(false); 
            releaseDelayTimer.start();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (gamePanel.isGameOver()) {
            return;
        }
        
     // Reseta a sequência se o tempo exceder
        if (System.currentTimeMillis() - lastPlayer1InputTime > COMBO_TIMEOUT) {
            player1InputSequence.clear();
        }
        
        if (player1.getState().equals("comboAttack") || player2.getState().equals("comboAttack")) {
        	setWPressed(false);
        	setDPressed(false);
        	setLeftPressed(false);
        	setRightPressed(false);
            return;
        }
        
        // Remove a tecla liberada do conjunto
        pressedKeys.remove(e.getKeyCode());
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                setWPressed(false);
                player1.setState("idle");
                gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel(player1).revalidate();
                gamePanel.getSpriteLabel(player1).repaint();
                break;
            case KeyEvent.VK_A:
                setAPressed(false);
                player1.setState("idle");
                gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel(player1).revalidate();
                gamePanel.getSpriteLabel(player1).repaint();
                break;
            case KeyEvent.VK_S:
                setSPressed(false);
                break;
            case KeyEvent.VK_D:
                setDPressed(false);
                player1.setState("idle");
                gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                gamePanel.getSpriteLabel(player1).revalidate();
                gamePanel.getSpriteLabel(player1).repaint();
                break;
            case KeyEvent.VK_UP:
                setUpPressed(false);
                if (releaseDelayTimer != null && releaseDelayTimer.isRunning()) {
                    releaseDelayTimer.stop();
                }
                
                releaseDelayTimer = new Timer(200, evt -> {
                	player2.setState("idle");
                    gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                    gamePanel.getSpriteLabel(player2).revalidate();
                    gamePanel.getSpriteLabel(player2).repaint();
                });

                releaseDelayTimer.setRepeats(false); 
                releaseDelayTimer.start();
                break;
            case KeyEvent.VK_DOWN:
                setDownPressed(false);
                player2.setState("idle");
                gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel(player2).revalidate();
                gamePanel.getSpriteLabel(player2).repaint();
                break;
            case KeyEvent.VK_LEFT:
                setLeftPressed(false);
                player2.setState("idle");
                gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel(player2).revalidate();
                gamePanel.getSpriteLabel(player2).repaint();
                break;
            case KeyEvent.VK_RIGHT:
                setRightPressed(false);
                player2.setState("idle");
                gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                gamePanel.getSpriteLabel(player2).revalidate();
                gamePanel.getSpriteLabel(player2).repaint();
                break;
            case KeyEvent.VK_E:
            	// Cancela qualquer timer anterior
                if (releaseDelayTimer != null && releaseDelayTimer.isRunning()) {
                    releaseDelayTimer.stop();
                }
                
                // Cria um novo timer que só roda depois de 500ms
                releaseDelayTimer = new Timer(500, evt -> {
                	player1.setState("idle");
                	gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                    gamePanel.getSpriteLabel(player1).revalidate();
                    gamePanel.getSpriteLabel(player1).repaint();
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
                	gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                    gamePanel.getSpriteLabel(player1).revalidate();
                    gamePanel.getSpriteLabel(player1).repaint();
                });

                releaseDelayTimer.setRepeats(false); 
                releaseDelayTimer.start();
                break;
            case KeyEvent.VK_R:
            	if (releaseDelayTimer != null && releaseDelayTimer.isRunning()) {
                    releaseDelayTimer.stop();
                }
                
                releaseDelayTimer = new Timer(200, evt -> {
                	player1.setState("idle");
                	gamePanel.getSpriteLabel(player1).setIcon(new ImageIcon(player1.getCurrentGif()));
                    gamePanel.getSpriteLabel(player1).revalidate();
                    gamePanel.getSpriteLabel(player1).repaint();
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
                	gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                    gamePanel.getSpriteLabel(player2).revalidate();
                    gamePanel.getSpriteLabel(player2).repaint();
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
                	gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                    gamePanel.getSpriteLabel(player2).revalidate();
                    gamePanel.getSpriteLabel(player2).repaint();
                });

                releaseDelayTimer.setRepeats(false); 
                releaseDelayTimer.start();
                break;
            case KeyEvent.VK_K:
            	if (releaseDelayTimer != null && releaseDelayTimer.isRunning()) {
                    releaseDelayTimer.stop();
                }
                
                releaseDelayTimer = new Timer(500, evt -> {
                	player2.setState("idle");
                	gamePanel.getSpriteLabel(player2).setIcon(new ImageIcon(player2.getCurrentGif()));
                    gamePanel.getSpriteLabel(player2).revalidate();
                    gamePanel.getSpriteLabel(player2).repaint();;
                });

                releaseDelayTimer.setRepeats(false); 
                releaseDelayTimer.start();
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
}