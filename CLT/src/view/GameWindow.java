package view;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public GameWindow(MainMenuPanel mainMenuPanel) {
        initialize(mainMenuPanel);
        revalidate();
        repaint();
    }
    
    private void initialize(MainMenuPanel mainMenuPanel) {
        setSize(1000, 700);
        setTitle("CLT: Caos, Luta e Treta");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
        if (mainMenuPanel != null) {
            setContentPane(mainMenuPanel);
        } else {
            System.err.println("Error: Main menu panel is not initialized.");
        }
    }
}