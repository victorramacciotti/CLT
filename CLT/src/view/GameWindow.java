package view;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
	
	
	public GameWindow(CharacterSelectionPanel panelCharacterSelection) {
		initialize(panelCharacterSelection);
		revalidate();
        repaint();
	}
	
	private void initialize(CharacterSelectionPanel panelCharacterSelection) {
		setSize(800, 700);
		setTitle("CLT: Caos, Luta e Treta");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		if(panelCharacterSelection != null){
			setContentPane(panelCharacterSelection);
		} else {
			System.out.println("Erro: Painel de seleção de personagens não foi inicializado.");
		}
	}
}
