package controll;

import model.Player;
import view.GamePanel;
import view.GameWindow;

public class GamePanelController {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Player player1;
    private Player player2;

    private static final int MOVE_SPEED = 5; // Velocidade de movimento dos jogadores

    // Flags para rastrear teclas pressionadas
    private boolean wPressed, aPressed, sPressed, dPressed; // Player 1 (WASD)
    private boolean upPressed, downPressed, leftPressed, rightPressed; // Player 2 (Setas)

    public GamePanelController(GameWindow gameWindow, GamePanel gamePanel, Player player1, Player player2) {
        this.gameWindow = gameWindow;
        this.gamePanel = gamePanel;
        this.player1 = player1;
        this.player2 = player2;
    }
}