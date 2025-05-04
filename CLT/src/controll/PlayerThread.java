package controll;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import model.Player;
import view.GamePanel;

public class PlayerThread extends Thread {
    private Player player;
    private GamePanel gamePanel;
    private GamePanelController controller;
    private volatile boolean running;
    private static final int MOVE_SPEED = 5;
    private static final int GAME_TICK = 16;
    private static final int GAME_GROUND = 200;
    
    private static final int COMBO_DURATION = 500; // 500ms de duração do golpe especial
    private boolean executingCombo = false;

    private static final int GRAVITY = 1;
    private static final int JUMP_STRENGTH = -15;
    private int velocityY = 0;
    private boolean onGround = true;

    private volatile boolean upPressed;
    private volatile boolean downPressed;
    private volatile boolean leftPressed;
    private volatile boolean rightPressed;

    public PlayerThread(Player player, GamePanel gamePanel, GamePanelController controller) {
        this.player = player;
        this.gamePanel = gamePanel;
        this.controller = controller;
        this.running = true;
    }

    @Override
    public void run() {
        while (running && !gamePanel.isGameOver()) {
            updatePlayer();
            gamePanel.revalidate();
            gamePanel.repaint();
            SwingUtilities.invokeLater(() -> {
                controller.updateGame();
            });

            try {
                Thread.sleep(GAME_TICK);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void updatePlayer() {
        int dx = 0;

        if (leftPressed) { 
        	dx -= MOVE_SPEED;
        	player.setSpritePath("resources/sprites/" + player.getCharacter().getName() + "/walking" + player.getCharacter().getName() + ".gif");
        }
        if (rightPressed) dx += MOVE_SPEED;

        // Pulo: só pula se estiver no chão
        if (upPressed && onGround) {
            velocityY = JUMP_STRENGTH;
            onGround = false;
        }

        // Aplicar gravidade
        velocityY += GRAVITY;
        int dy = velocityY;

        player.move(dx, dy);
        checkBoundaries();
 
    }

    private void checkBoundaries() {
    	
    	int panelWidth = gamePanel.getPanelWidth();
        int panelHeight = gamePanel.getPanelHeight();
        
        int playerWidth = (int) player.getCharacter().getWIDTH();
        int playerHeight = (int) player.getCharacter().getHEIGHT();

        // Limites horizontais
        if (player.getPositionX() < -50) {
            player.setPositionX(-50);
        }
        if (player.getPositionX() + playerWidth > panelWidth ) {
            player.setPositionX(panelWidth - playerWidth);
        }

        // Limites verticais (teto)
        if (player.getPositionY() < 0) {
            player.setPositionY(0);
            velocityY = 0;
        }

        // Chão
        int groundLevel = panelHeight - playerHeight - GAME_GROUND;
        if (player.getCharacter().getName().equals("Murissoca")) groundLevel -= 30;
        if (player.getPositionY() > groundLevel) {
            player.setPositionY(groundLevel);
            velocityY = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }
    
    public void executeCombo() {
        if (executingCombo) return;

        executingCombo = true;
        updateSprite("Combo");

        // Cria um agendador de tarefas
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Agenda a tarefa para resetar após COMBO_DURATION milissegundos
        scheduler.schedule(() -> {
            executingCombo = false;
            SwingUtilities.invokeLater(() -> {
                updateSprite("idle");
            });
            scheduler.shutdown(); // Encerra o scheduler
        }, COMBO_DURATION, TimeUnit.MILLISECONDS);
    }
    
    private void updateSprite(String state) {
        String path = "resources/sprites/" + 
                     player.getCharacter().getName() + "/" + 
                     state + 
                     player.getCharacter().getName() + ".gif";
        player.setSpritePath(path);
    }

    public void setUpPressed(boolean upPressed) { this.upPressed = upPressed; }
    public void setDownPressed(boolean downPressed) { this.downPressed = downPressed; }
    public void setLeftPressed(boolean leftPressed) { this.leftPressed = leftPressed; }
    public void setRightPressed(boolean rightPressed) { this.rightPressed = rightPressed; }

    public void stopThread() { running = false; }
}