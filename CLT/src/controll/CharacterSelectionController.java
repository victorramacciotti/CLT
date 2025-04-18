package controll;

import model.Character;
import model.Player;
import view.CharacterSelectionPanel;
import view.GamePanel;
import view.GameWindow;

public class CharacterSelectionController {
    
    // Dependencies and state
    private GameWindow gameWindow;
    private CharacterSelectionPanel characterSelectionPanel;
    private Player player1;
    private Player player2;
    private boolean isPlayer1Turn = true;

    public CharacterSelectionController() {}

    public CharacterSelectionController(GameWindow gameWindow, CharacterSelectionPanel characterSelectionPanel) {
        this.gameWindow = gameWindow;
        this.characterSelectionPanel = characterSelectionPanel;
        addEvents();
    }

    private void addEvents() {
        characterSelectionPanel.getButtonPerson1().addActionListener(e -> selectCharacter(new Character("Akuma", 100, 20, 5)));
        characterSelectionPanel.getButtonPerson2().addActionListener(e -> selectCharacter(new Character("Cable", 100, 18, 6)));
        characterSelectionPanel.getButtonPerson3().addActionListener(e -> selectCharacter(new Character("Chunli", 95, 18, 7)));
        characterSelectionPanel.getButtonPerson4().addActionListener(e -> selectCharacter(new Character("Doom", 110, 22, 4)));
        characterSelectionPanel.getButtonStart().addActionListener(e -> startGame());
    }

    private void selectCharacter(Character character) {
        if (isPlayer1Turn) {
        	this.player1 = new Player(1, 200, 630, character, "resources/sprites/" + character.getName() + "P1/" + character.getName() + "_pd.gif");
            characterSelectionPanel.getLabelSubtitle().setText("Choose the character for Player 2");
            isPlayer1Turn = false;
        } else {
        	this.player2 = new Player(1, 500, 630, character, "resources/sprites/" + character.getName() + "P2/" + character.getName() + "_pd.gif");
            characterSelectionPanel.getLabelSubtitle().setText("Ready! Click 'Select' to start.");
            characterSelectionPanel.getButtonStart().setEnabled(true);
        }
    }

    private void startGame() {
        if (player1.getCharacter() != null && player2.getCharacter() != null) {
            GamePanel gamePanel = new GamePanel(player1, player2);
            GamePanelController gamePanelController = new GamePanelController(gameWindow, gamePanel, player1, player2);
            
            gameWindow.setContentPane(gamePanel);
            gameWindow.revalidate();
            gameWindow.repaint();
            
            gamePanel.requestFocusInWindow();
        }
    }
}