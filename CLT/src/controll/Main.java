package controll;

import model.Player;
import view.CharacterSelectionPanel;
import view.GameWindow;

public class Main {

	public static void main(String[] args) {
		
		Player player1 = new Player(1, 100, 200);
		Player player2 = new Player(2, 600, 200);

		CharacterSelectionPanel characterSelectionPanel = new CharacterSelectionPanel();
		GameWindow gameWindow = new GameWindow(characterSelectionPanel);
		CharacterSelectionController characterSelectionController = new CharacterSelectionController(gameWindow, characterSelectionPanel, player1, player2); 
	}
}
