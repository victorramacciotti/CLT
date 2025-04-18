package controll;

import view.CharacterSelectionPanel;
import view.GameWindow;

public class Main {

	public static void main(String[] args) {
		CharacterSelectionPanel characterSelectionPanel = new CharacterSelectionPanel();
		GameWindow gameWindow = new GameWindow(characterSelectionPanel);
		CharacterSelectionController characterSelectionController = new CharacterSelectionController(gameWindow, characterSelectionPanel); 
	}
}
