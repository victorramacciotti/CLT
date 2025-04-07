package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

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
    private JLabel spriteLabel1; // JLabel para o sprite do jogador 1
    private JLabel spriteLabel2; // JLabel para o sprite do jogador 2

    private int timeRemaining = 300; // 5 minutos (300 segundos)

    public GamePanel(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        setSize(800, 700);
        setLayout(null);

        // Torna o painel focável para receber eventos de teclado
        setFocusable(true);
        requestFocusInWindow();

        JPanel topPanel = createTopPanel();
        JPanel bottomPanel = createBottomPanel();

        // Adicionar os sprites na área do jogo
        spriteLabel1 = createSpriteLabel(player1, player1.getPositionX(), player1.getPositionY());
        spriteLabel2 = createSpriteLabel(player2, player2.getPositionX(), player2.getPositionY());

        add(topPanel);
        add(bottomPanel);
        add(spriteLabel1);
        add(spriteLabel2);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 800, 98);
        topPanel.setLayout(null);

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

        timerLabel = new JLabel(timeRemaining + "s");
        timerLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        timerLabel.setBounds(369, 30, 61, 37);
        topPanel.add(timerLabel);

        return topPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(364, 34, 72, 24);
        bottomPanel.setLayout(null);
        return bottomPanel;
    }

    private JLabel createCharacterLabel(Player player, int x, int y, int alignment) {
        JLabel label = new JLabel(player.getCharacter().getName());
        label.setHorizontalAlignment(alignment);
        label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        label.setBounds(x, y, 250, 25);
        return label;
    }

    private JProgressBar createLifeBar(double life, int x, int y) {
        JProgressBar bar = new JProgressBar(0, (int) life);
        bar.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bar.setBounds(x, y, 250, 14);
        return bar;
    }

    private JProgressBar createSpecialBar(int x, int y) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setBounds(x, y, 150, 14);
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
            spriteLabel.setBounds(x, y, 200, 50);
        }
        return spriteLabel;
    }

    private void updateLifeBarColor(JProgressBar lifeBar, double life) {
        if (life >= 75) {
            lifeBar.setForeground(Color.GREEN);
        } else if (life >= 50) {
            lifeBar.setForeground(Color.YELLOW);
        } else if (life >= 25) {
            lifeBar.setForeground(Color.ORANGE);
        } else {
            lifeBar.setForeground(Color.RED);
        }
    }

    public void updateGame() {
        lifeBar1.setValue((int) player1.getCharacter().getLife());
        lifeBar2.setValue((int) player2.getCharacter().getLife());

        updateLifeBarColor(lifeBar1, player1.getCharacter().getLife());
        updateLifeBarColor(lifeBar2, player2.getCharacter().getLife());

        // Atualizar as posições dos sprites
        spriteLabel1.setLocation(player1.getPositionX(), player1.getPositionY());
        spriteLabel2.setLocation(player2.getPositionX(), player2.getPositionY());

        // Log para depuração
        System.out.println("Sprite 1 - Posição atualizada: (" + spriteLabel1.getX() + ", " + spriteLabel1.getY() + ")");
        System.out.println("Sprite 2 - Posição atualizada: (" + spriteLabel2.getX() + ", " + spriteLabel2.getY() + ")");
    }

    // Getters para os JLabels dos sprites (usados pelo controlador)
    public JLabel getSpriteLabel1() {
        return spriteLabel1;
    }

    public JLabel getSpriteLabel2() {
        return spriteLabel2;
    }
}