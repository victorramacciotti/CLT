package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import model.Player;
import model.SpriteLoader;

public class GamePanel extends JPanel {
    private Player player1;
    private Player player2;

    private JLabel nameCharLabel1;
    private JLabel nameCharLabel2;
    private JProgressBar lifeBar1;
    private JProgressBar lifeBar2;
    private JProgressBar specialBar1;
    private JProgressBar specialBar2;
    private JLabel timerLabel;
    private JLabel spriteLabel1;
    private JLabel spriteLabel2;
    private JLabel gameOverLabel;

    private int timeRemaining = 300; // 5 minutos (300 segundos)
    private Timer gameTimer;
    private boolean gameOver = false;

    private Runnable onGameEnd;

    public GamePanel(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        setSize(800, 700);
        setPreferredSize(new java.awt.Dimension(800, 700));
        setLayout(null);
        setBackground(Color.BLACK);
        setFocusable(true);

        JPanel topPanel = createTopPanel();
        JPanel bottomPanel = createBottomPanel();

        spriteLabel1 = createSpriteLabel(player1, player1.getPositionX(), player1.getPositionY());
        spriteLabel2 = createSpriteLabel(player2, player2.getPositionX(), player2.getPositionY());

        add(topPanel);
        add(bottomPanel);
        add(spriteLabel1);
        add(spriteLabel2);

        // Temporizador atualizado
        gameTimer = new Timer(1000, e -> {
            if (!gameOver && timeRemaining > 0) {
                timeRemaining--;

                // Atualiza o texto do temporizador no formato mm:ss
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;
                timerLabel.setText(String.format("%02d:%02d", minutes, seconds));

                // Muda para vermelho nos últimos 10 segundos
                if (timeRemaining <= 10) {
                    timerLabel.setForeground(Color.RED);
                }

                if (timeRemaining <= 0) {
                    endGame();
                }
            }
        });
        gameTimer.start();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 800, 98);
        topPanel.setLayout(null);
        topPanel.setOpaque(false);

        nameCharLabel1 = createCharacterLabel(player1, 32, 11, SwingConstants.LEFT);
        lifeBar1 = createLifeBar(player1.getCharacter().getLife(), 32, 47);
        specialBar1 = createSpecialBar(32, 66);

        nameCharLabel2 = createCharacterLabel(player2, 500, 11, SwingConstants.RIGHT);
        lifeBar2 = createLifeBar(player2.getCharacter().getLife(), 500, 47);
        specialBar2 = createSpecialBar(600, 66);

        topPanel.add(nameCharLabel1);
        topPanel.add(lifeBar1);
        topPanel.add(specialBar1);
        topPanel.add(nameCharLabel2);
        topPanel.add(lifeBar2);
        topPanel.add(specialBar2);

        timerLabel = new JLabel(String.format("%02d:%02d", timeRemaining / 60, timeRemaining % 60));
        timerLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBounds(369, 30, 100, 37);
        topPanel.add(timerLabel);

        return topPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(364, 600, 72, 24);
        bottomPanel.setLayout(null);
        bottomPanel.setOpaque(false);
        return bottomPanel;
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

    private JProgressBar createSpecialBar(int x, int y) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(100);
        bar.setBounds(x, y, 150, 14);
        bar.setForeground(Color.BLUE);
        return bar;
    }

    private JLabel createSpriteLabel(Player player, int x, int y) {
        ImageIcon spriteIcon = SpriteLoader.loadImage(player.getSpritePath());
        JLabel spriteLabel;
        if (spriteIcon != null) {
            spriteLabel = new JLabel(spriteIcon);
            spriteLabel.setBounds(x, y, spriteIcon.getIconWidth(), spriteIcon.getIconHeight());
        } else {
            spriteLabel = new JLabel("Sprite não carregado: " + player.getSpritePath());
            spriteLabel.setForeground(Color.RED);
            spriteLabel.setBounds(x, y, 200, 50);
        }
        return spriteLabel;
    }

    private void updateLifeBarColor(JProgressBar lifeBar, double life) {
        double percentage = (life / lifeBar.getMaximum()) * 100;
        if (percentage >= 75) lifeBar.setForeground(Color.GREEN);
        else if (percentage >= 50) lifeBar.setForeground(Color.YELLOW);
        else if (percentage >= 25) lifeBar.setForeground(Color.ORANGE);
        else lifeBar.setForeground(Color.RED);
    }

    public synchronized void updateGame() {
        if (!gameOver) {
            lifeBar1.setValue((int) player1.getCharacter().getLife());
            lifeBar2.setValue((int) player2.getCharacter().getLife());

            updateLifeBarColor(lifeBar1, player1.getCharacter().getLife());
            updateLifeBarColor(lifeBar2, player2.getCharacter().getLife());

            spriteLabel1.setLocation(player1.getPositionX(), player1.getPositionY());
            spriteLabel2.setLocation(player2.getPositionX(), player2.getPositionY());

            System.out.println("Sprite 1 - UI updated: (" + spriteLabel1.getX() + ", " + spriteLabel1.getY() + ")");
            System.out.println("Sprite 2 - UI updated: (" + spriteLabel2.getX() + ", " + spriteLabel2.getY() + ")");
        }
    }

    private void endGame() {
        gameOver = true;
        gameTimer.stop();

        String winner;
        if (player1.getCharacter().getLife() > player2.getCharacter().getLife()) {
            winner = player1.getCharacter().getName();
        } else if (player2.getCharacter().getLife() > player1.getCharacter().getLife()) {
            winner = player2.getCharacter().getName();
        } else {
            winner = "Empate";
        }

        if (gameOverLabel != null) remove(gameOverLabel);
        gameOverLabel = new JLabel("Fim de Jogo! Vencedor: " + winner);
        gameOverLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        gameOverLabel.setForeground(Color.YELLOW);
        gameOverLabel.setBounds(200, 300, 400, 50);
        add(gameOverLabel);
        revalidate();
        repaint();

        if (onGameEnd != null) {
            onGameEnd.run();
        }
    }

    public boolean isGameOver() { return gameOver; }
    public JLabel getSpriteLabel1() { return spriteLabel1; }
    public JLabel getSpriteLabel2() { return spriteLabel2; }

    public void setOnGameEnd(Runnable onGameEnd) {
        this.onGameEnd = onGameEnd;
    }
}
