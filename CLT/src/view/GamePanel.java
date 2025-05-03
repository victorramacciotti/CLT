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
    private static final int PANEL_WIDTH = 1000;
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
    private JLabel timerLabel;

    public GamePanel(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        
        
        setupPanel();
        buildTopPanel();
        buildPlayerSprites();
        buildBackground();
    }
    
    private void setupPanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    
        setLayout(null);
        setFocusable(true);
    }

    private void buildTopPanel() {
        topPanel = new JPanel(null);
        topPanel.setOpaque(false);
        topPanel.setBounds(0, 0, PANEL_WIDTH, TOP_PANEL_HEIGHT);
        
        nameCharLabel1 = createCharacterLabel(player1, 32, 11, SwingConstants.LEFT);
        lifeBar1 = createLifeBar(player1.getCharacter().getLife(), 32, 47);
        
        nameCharLabel2 = createCharacterLabel(player2, 700, 11, SwingConstants.RIGHT);
        lifeBar2 = createLifeBar(player2.getCharacter().getLife(), 700, 47);
        
        timerLabel = createTimerLabel();
        
        topPanel.add(nameCharLabel1);
        topPanel.add(lifeBar1);
        topPanel.add(nameCharLabel2);
        topPanel.add(lifeBar2);
        topPanel.add(timerLabel);
        
        add(topPanel);
        
    }

    private JLabel createTimerLabel() {
        JLabel label = new JLabel("120");
        label.setFont(new Font("Tahoma", Font.BOLD, 40));
        label.setForeground(Color.WHITE);
        label.setBounds(450, 10, 80, 40);
        return label;
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
    
    private JLabel buildBackground() {
    	ImageIcon backgroundImage = new ImageIcon("resources/backgrounds/provisorio.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setSize(1000, 700);
        add(backgroundLabel);
        return backgroundLabel;
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
        return bar;
    }

    private JLabel createSpriteLabel(Player player) {       
        ImageIcon spriteIcon = SpriteLoader.loadImage(player.getCurrentGif());
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

    // Getters and setters
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
    
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    public JLabel getTimerLabel() {
        return timerLabel;
    }
    
    public JProgressBar getLifeBar1() {
        return lifeBar1;
    }
    
    public JProgressBar getLifeBar2() {
        return lifeBar2;
    }
    
    public int getPanelWidth() {
    	return PANEL_WIDTH;
    }
    
    public int getPanelHeight() {
    	return PANEL_HEIGHT;
    }
    
    public void setGameOverLabel(String gameOverString) {
        JLabel gameOverLabel = new JLabel(gameOverString);
        gameOverLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setBounds(300, 300, 500, 50);
        add(gameOverLabel);
        setComponentZOrder(gameOverLabel, 0);
        revalidate();
        repaint();
    }
}