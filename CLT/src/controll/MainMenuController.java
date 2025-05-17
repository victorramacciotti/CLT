package controll;

import view.GamePanel;
import view.GameWindow;
import view.MainMenuPanel;
import view.CharacterSelectionPanel;
import model.Character;
import model.Player;

public class MainMenuController {
    
    private GameWindow gameWindow;
    private MainMenuPanel mainMenuPanel;
    
    public MainMenuController(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        this.mainMenuPanel = new MainMenuPanel();
        addEvents();
        showMainMenu();
    }

    private void addEvents() {
        mainMenuPanel.getStartButton().addActionListener(e -> showCharacterSelection());
        mainMenuPanel.getExitButton().addActionListener(e -> System.exit(0));
    }

    private void showMainMenu() {
        gameWindow.setContentPane(mainMenuPanel);
        gameWindow.revalidate();
        gameWindow.repaint();
    }

    private void showCharacterSelection() {
        CharacterSelectionPanel characterSelectionPanel = new CharacterSelectionPanel();
        CharacterSelectionController characterSelectionController = new CharacterSelectionController(gameWindow, characterSelectionPanel);
        
        gameWindow.setContentPane(characterSelectionPanel);
        gameWindow.revalidate();
        gameWindow.repaint();
    }
}
