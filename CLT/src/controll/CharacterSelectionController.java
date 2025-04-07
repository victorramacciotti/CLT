package controll;

import model.Character;
import model.Player;
import view.CharacterSelectionPanel;
import view.GamePanel;
import view.GameWindow;

public class CharacterSelectionController {
	
	private GameWindow gameWindow;
	private CharacterSelectionPanel characterSelectionPanel;
	private Player player1;
	private Player player2;
	private boolean isPlayer1Turn = true;
	
	public CharacterSelectionController() {
		
	}
	
	public CharacterSelectionController(GameWindow gameWindow, CharacterSelectionPanel characterSelectionPanel, Player player1, Player player2) {
		this.gameWindow = gameWindow;
		this.characterSelectionPanel = characterSelectionPanel;
		this.player1 = player1;
		this.player2 = player2;
		adicionarEventos();
	}
	
	private void adicionarEventos() {
		characterSelectionPanel.getButtonPerson1().addActionListener(e -> selecionarPersonagem(new Character("Akuma", 100, 20, 5)));
		characterSelectionPanel.getButtonPerson2().addActionListener(e -> selecionarPersonagem(new Character("Cable", 100, 18, 6)));
		characterSelectionPanel.getButtonPerson3().addActionListener(e -> selecionarPersonagem(new Character("Chunli", 95, 18, 7)));
		characterSelectionPanel.getButtonPerson4().addActionListener(e -> selecionarPersonagem(new Character("Doom", 110, 22, 4)));
		characterSelectionPanel.getButtonStart().addActionListener(e -> iniciarJogo());
    }
	
	private void selecionarPersonagem(Character personagem) {
        if (isPlayer1Turn) {
            player1.setCharacter(personagem);
            player1.setSpritePath("resources/sprites/" + personagem.getName() + "P1/" + personagem.getName() + "_pd.gif");
            characterSelectionPanel.getLabelSubtitle().setText("Escolha o personagem para o Player 2");
            isPlayer1Turn = false;
        } else {
            player2.setCharacter(personagem);
            player2.setSpritePath("resources/sprites/" + personagem.getName() + "P2/" + personagem.getName() + "_pd.gif");
            characterSelectionPanel.getLabelSubtitle().setText("Pronto! Clique em 'Selecionar' para iniciar.");
            characterSelectionPanel.getButtonStart().setEnabled(true);
        }
	}
	
	private void iniciarJogo() {
		if (player1.getCharacter() != null && player2.getCharacter() != null) {
	        
			gameWindow.remove(characterSelectionPanel);
			GamePanel gamePanel = new GamePanel(player1, player2);
			GamePanelController gamePanelController = new GamePanelController(gameWindow, gamePanel, player1, player2);
	        gameWindow.setContentPane(gamePanel);
	        gameWindow.revalidate();
	        gameWindow.repaint();
	    } 
    }
}
