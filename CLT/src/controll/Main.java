package controll;

import view.CharacterSelectionPanel;
import view.GameWindow;
import view.MainMenuPanel;

public class Main {

	public static void main(String[] args) {
		CharacterSelectionPanel characterSelectionPanel = new CharacterSelectionPanel();
		MainMenuPanel mainMenuPanel = new MainMenuPanel();
		GameWindow gameWindow = new GameWindow(mainMenuPanel);
		MainMenuController mainMenuController = new MainMenuController(gameWindow); 
	}
}
