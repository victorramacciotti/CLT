package view;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public GameWindow(CharacterSelectionPanel characterSelectionPanel) {
        initialize(characterSelectionPanel);
        revalidate();
        repaint();
    }
    
    private void initialize(CharacterSelectionPanel characterSelectionPanel) {
        setSize(1000, 700);
        setTitle("CLT: Caos, Luta e Treta");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
        if (characterSelectionPanel != null) {
            setContentPane(characterSelectionPanel);
        } else {
            System.err.println("Error: Character selection panel is not initialized.");
        }
    }
}