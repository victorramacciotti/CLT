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
    
    // Armazena seleção temporária antes da confirmação
    private Character tempSelectedCharacter = null;

    public CharacterSelectionController() {}

    public CharacterSelectionController(GameWindow gameWindow, CharacterSelectionPanel characterSelectionPanel) {
        this.gameWindow = gameWindow;
        this.characterSelectionPanel = characterSelectionPanel;
        addEvents();
        characterSelectionPanel.getButtonConfirm().setEnabled(false);
        characterSelectionPanel.getButtonStart().setEnabled(false);
    }

    private void addEvents() {
        characterSelectionPanel.getButtonPerson1().addActionListener(e -> selectTemporaryCharacter(new Character("Nita", 120, 20, 5, 204, 239)));
        characterSelectionPanel.getButtonPerson2().addActionListener(e -> selectTemporaryCharacter(new Character("Isagram", 1000, 18, 6, 239, 239)));
        characterSelectionPanel.getButtonPerson3().addActionListener(e -> selectTemporaryCharacter(new Character("Murissoca", 80, 18, 7, 270, 239)));
        characterSelectionPanel.getButtonPerson4().addActionListener(e -> selectTemporaryCharacter(new Character("Teletony", 110, 22, 4, 239, 239)));
        characterSelectionPanel.getButtonPerson5().addActionListener(e -> selectTemporaryCharacter(new Character("Lule", 90, 22, 4, 239, 239)));
        
        // Botão confirmar seleção do personagem atual
        characterSelectionPanel.getButtonConfirm().addActionListener(e -> confirmSelection());
        
        // Botão para iniciar jogo
        characterSelectionPanel.getButtonStart().addActionListener(e -> startGame());
    }
    
    
    private void selectTemporaryCharacter(Character character) {
        tempSelectedCharacter = character;
        characterSelectionPanel.getLabelSubtitle().setText(
            (isPlayer1Turn ? "Jogador 1" : "Jogador 2") + " selecionou: " + character.getName() + ". Clique 'Selecionar' para confirmar."
        );
        // Atualiza o JLabel com a imagem do personagem selecionado
        if (isPlayer1Turn) {
            characterSelectionPanel.setProfilePathP1("resources/buttons/profile" + tempSelectedCharacter.getName() + ".png");
        } else {
            characterSelectionPanel.setProfilePathP2("resources/buttons/profile" + tempSelectedCharacter.getName() + ".png");
        }
        
        // Atualiza descrição
        characterSelectionPanel.getAreaDescription().setText(getCharacterDescription(character.getName()));
        
        characterSelectionPanel.getButtonConfirm().setEnabled(true);
    }
    
    private void confirmSelection() {
        if (tempSelectedCharacter == null) {
            return;
        }
        if (isPlayer1Turn) {
            player1 = new Player(10, 630, tempSelectedCharacter, 
                "resources/sprites/" + tempSelectedCharacter.getName() + "/idle" + tempSelectedCharacter.getName() + ".gif");
            isPlayer1Turn = false;
            tempSelectedCharacter = null;
            characterSelectionPanel.getButtonConfirm().setEnabled(false);
            characterSelectionPanel.getLabelSubtitle().setText("Jogador 1 confirmado. Jogador 2, escolha seu personagem.");
        } else {
            player2 = new Player(690, 630, tempSelectedCharacter, 
                "resources/sprites/" + tempSelectedCharacter.getName() + "/idle" + tempSelectedCharacter.getName() + "Flipped.gif");
            tempSelectedCharacter = null;
            characterSelectionPanel.getButtonConfirm().setEnabled(false);
            characterSelectionPanel.getLabelSubtitle().setText("Jogador 2 confirmado. Clique Começar para jogar!");
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
    
    private String getCharacterDescription(String characterName) {
        switch(characterName) {
            case "Nita": return "A NITA, Nova Influência do Tesão e da Audácia, chegou pra mudar o jogo. No ringue ou no palco, ela domina com ritmo, charme e poder de nocaute.\r\n"
            		+ "";
            case "Isagram": return "Isa Gram, a Diva Digital do Instagram.\r\n"
            		+ "Rainha dos filtros e dos flertes forçados, ela luta com egocentrismo, spray de cabelo e hashtags letais. Ignora o conflito ao redor — a não ser que tenha câmera e boa luz. Toda pose é golpe.\r\n"
            		+ "\r\n";
            case "Murissoca": return "Murissoca, o  Prof. P*tasso."
            		+ " Armado de giz, memes, polêmicas e revolta, ele leciona soco com viés de direita. com QI acima do brasileiro médio — que, segundo ele, é menor que de um chimpanzé. \r\nSua didática? Uma voadora pedagógica!";
            case "Teletony": return "TeleTony, o Guerreiro do SAC e do Sofrimento.\r\n"
            		+ "Última linha de defesa entre o cliente e o colapso. Cansado da vida, do script e da musiquinha de espera, ele luta sem vontade, mas com profundo rancor. Cada golpe é um ticket encerrado.";
            case "Lule": return "Metade carisma, metade caos, Lule Moronaro é 100% polarização. Ele distribui promessas, tapa na mesa e golpes de oportunismo com a mesma fluidez. Seu lema?"
            		+ " “E daí? Não sou coveiro.”\r\n"
            		+ "Ninguém entende de que lado ele tá — mas o ringue todo gira em torno dele.";
            default: return "";
        }
    }
}