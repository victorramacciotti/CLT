package view;

import model.Player;
import model.SpriteLoader;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class GamePanel extends JPanel {
    
    // Panel dimensions and constants
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 700;
    private static final int TOP_PANEL_HEIGHT = 100;
    
    // Game state
    private final Player player1;
    private final Player player2;
    private boolean gameOver = false;
    
    // UI components
    private JPanel topPanel;
    private JLabel nameCharLabel1;
    private JLabel nameCharLabel2;
    private JProgressBar lifeBar1;
    private JProgressBar lifeBar2;
    private JLabel spriteLabel1;
    private JLabel spriteLabel2;
    private JLabel gameOverLabel;

    public GamePanel(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        
        setupPanel();
        buildTopPanel();
        buildPlayerSprites();
    }
    
    private void setupPanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setLayout(null);
        setFocusable(true);
    }

    private void buildTopPanel() {
        topPanel = new JPanel(null);
        topPanel.setOpaque(false);
        topPanel.setBounds(0, 0, PANEL_WIDTH, TOP_PANEL_HEIGHT);
        
        nameCharLabel1 = createCharacterLabel(player1, 32, 11, SwingConstants.LEFT);
        lifeBar1 = createLifeBar(player1.getCharacter().getLife(), 32, 47);
        
        nameCharLabel2 = createCharacterLabel(player2, 500, 11, SwingConstants.RIGHT);
        lifeBar2 = createLifeBar(player2.getCharacter().getLife(), 500, 47);
        
        topPanel.add(nameCharLabel1);
        topPanel.add(lifeBar1);
        topPanel.add(nameCharLabel2);
        topPanel.add(lifeBar2);
        
        add(topPanel);
    }

    private void buildPlayerSprites() {
        spriteLabel1 = createSpriteLabel(player1);
        spriteLabel2 = createSpriteLabel(player2);
        
        add(spriteLabel1);
        add(spriteLabel2);
        
        // Force initial sprite position update
        spriteLabel1.setLocation(player1.getPositionX(), player1.getPositionY());
        spriteLabel2.setLocation(player2.getPositionX(), player2.getPositionY());
        revalidate();
        repaint();
    }

    private JLabel createCharacterLabel(Player player, int x, int y, int alignment) {
        JLabel label = new JLabel(player.getCharacter().getName());
        label.setHorizontalAlignment(alignment);
        label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, 250, 25);
        return label;
    }

    private JProgressBar createLifeBar(double life, int x, int y) {
        JProgressBar bar = new JProgressBar(0, (int) life);
        bar.setValue((int) life);
        bar.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bar.setBounds(x, y, 250, 14);
        updateLifeBarColor(bar, life);
        return bar;
    }

    private JLabel createSpriteLabel(Player player) {       
        ImageIcon spriteIcon = SpriteLoader.loadImage(player.getSpritePath());
        JLabel spriteLabel;
        
        if (spriteIcon != null) {
            spriteLabel = new JLabel(spriteIcon);
            spriteLabel.setBounds(player.getPositionX(), player.getPositionY(),
                    spriteIcon.getIconWidth(), spriteIcon.getIconHeight());
        } else {
            spriteLabel = new JLabel("Sprite not loaded");
            spriteLabel.setForeground(Color.RED);
            spriteLabel.setBounds(player.getPositionX(), player.getPositionY(), 200, 50);
        }
        
        return spriteLabel;
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
        if (gameOver) {
            return;
        }
        
        spriteLabel1.setLocation(player1.getPositionX(), player1.getPositionY());
        spriteLabel2.setLocation(player2.getPositionX(), player2.getPositionY());
        
        double life1 = player1.getCharacter().getLife();
        double life2 = player2.getCharacter().getLife();
        
        lifeBar1.setMaximum((int) player1.getCharacter().getMaxLife());
        lifeBar1.setValue((int) life1);
        updateLifeBarColor(lifeBar1, life1);
        
        lifeBar2.setMaximum((int) player2.getCharacter().getMaxLife());
        lifeBar2.setValue((int) life2);
        updateLifeBarColor(lifeBar2, life2);
        
        revalidate();
        repaint();
    }

    public void showGameOver(String winner) {
        gameOver = true;
        
        gameOverLabel = new JLabel("Game Over! Winner: " + winner);
        gameOverLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        gameOverLabel.setForeground(Color.YELLOW);
        gameOverLabel.setBounds(150, 300, 500, 50);
        add(gameOverLabel);
        
        revalidate();
        repaint();
    }
    
    // Getters
    public JLabel getSpriteLabel1() {
        return spriteLabel1;
    }
    
    public JLabel getSpriteLabel2() {
        return spriteLabel2;
    }
    
    public JPanel getTopPanel() {
        return topPanel;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
}