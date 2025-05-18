package controll;

import view.GameWindow;
import view.MainMenuPanel;
import audio.Sound;
import view.CharacterSelectionPanel;


public class MainMenuController {
    
    private GameWindow gameWindow;
    private MainMenuPanel mainMenuPanel;
    private Sound sound = new Sound();
    
    public MainMenuController(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        this.mainMenuPanel = new MainMenuPanel();
        addEvents();
        showMainMenu();
        sound.playMusic(0);
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
        CharacterSelectionController characterSelectionController = new CharacterSelectionController(gameWindow, characterSelectionPanel, sound);
        
        sound.setVolume(-30.0f);
        gameWindow.setContentPane(characterSelectionPanel);
        gameWindow.revalidate();
        gameWindow.repaint();
    }
   
}
