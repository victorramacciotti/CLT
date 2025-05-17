package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class MainMenuPanel extends JPanel {
    private JButton startButton;
    private JButton exitButton;
    private JLabel titleLabel;
    private Image backgroundImage;
    
 // Variáveis para animação do título
    private float scale = 1.0f;
    private boolean scalingUp = true;
    private Timer animationTimer;

    public MainMenuPanel() {
        setLayout(null);
        setBackground(Color.BLACK);
        
     // Carrega a imagem de fundo
        ImageIcon bgIcon = new ImageIcon("resources/backgrounds/background.png"); 
        backgroundImage = bgIcon.getImage();
        
     // Configura o título com imagem
        ImageIcon titleIcon = new ImageIcon("resources/buttons/title.png");
        titleLabel = new JLabel(titleIcon);
        titleLabel.setBounds(368, 30, titleIcon.getIconWidth(), titleIcon.getIconHeight());
        add(titleLabel);

     // Cria botões com imagens
        ImageIcon startIcon = new ImageIcon("resources/buttons/startButton.png"); 
        ImageIcon startHoverIcon = new ImageIcon("resources/buttons/startButtonHover.png");

        
        startButton = new JButton(startIcon);
        startButton.setRolloverIcon(startHoverIcon);
        configButton(startButton, 380, 340, startIcon);

        ImageIcon exitIcon = new ImageIcon("resources/buttons/exitButton.png");
        ImageIcon exitHoverIcon = new ImageIcon("resources/buttons/exitButtonHover.png");
        
        exitButton = new JButton(exitIcon);
        exitButton.setRolloverIcon(exitHoverIcon);
        configButton(exitButton, 475, 450, exitIcon);

        add(startButton);
        add(exitButton);
        
     // Iniciar a animação do título
        startTitleAnimation();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Desenha a imagem de fundo esticada para ocupar todo o painel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    private void configButton(JButton button, int x, int y, ImageIcon icon) {
        button.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
    }
    
    private void startTitleAnimation() {
        animationTimer = new Timer(30, e -> {
            // Atualiza escala para pulse entre 1.0 e 1.1
            if (scalingUp) {
                scale += 0.005f;
                if (scale >= 1.1f) {
                    scale = 1.1f;
                    scalingUp = false;
                }
            } else {
                scale -= 0.005f;
                if (scale <= 1.0f) {
                    scale = 1.0f;
                    scalingUp = true;
                }
            }
            titleLabel.setSize((int) (titleLabel.getIcon().getIconWidth() * scale), 
                               (int) (titleLabel.getIcon().getIconHeight() * scale));
            titleLabel.repaint();
        });
        animationTimer.start();
    }

    // Getters para os botões e título
    public JButton getStartButton() {
        return startButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}