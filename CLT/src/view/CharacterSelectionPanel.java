package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

public class CharacterSelectionPanel extends JPanel {
	private JLabel labelTitle;
	private JLabel labelSubtitle;
	private JButton buttonPerson1;
	private JButton buttonPerson2;
	private JButton buttonPerson3;
	private JButton buttonPerson4;
	private JButton buttonStart;
	private JLabel labelVersus;
	private JPanel panelPlayer1;
	private JPanel panelPlayer2;
	
	public CharacterSelectionPanel() {
		setSize(800, 700);
		setLayout(null);
		add(getLabelTitle());
		add(getLabelSubtitle());
		add(getButtonPerson1());
		add(getButtonPerson2());
		add(getButtonPerson3());
		add(getButtonPerson4());
		add(getButtonStart());
		add(getLabelVersus());
		add(getPanelPlayer1());
		add(getPanelPlayer2());
	}
	public JLabel getLabelTitle() {
		if (labelTitle == null) {
			labelTitle = new JLabel("Selection");
			labelTitle.setBounds(298, 40, 203, 62);
			labelTitle.setFont(new Font("Tahoma", Font.PLAIN, 51));
		}
		return labelTitle;
	}
	public JLabel getLabelSubtitle() {
		if (labelSubtitle == null) {
			labelSubtitle = new JLabel("Escolha o personagem para o player 1");
			labelSubtitle.setBounds(195, 113, 410, 29);
			labelSubtitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
		}
		return labelSubtitle;
	}
	public JButton getButtonPerson1() {
		if (buttonPerson1 == null) {
			buttonPerson1 = new JButton("Personagem 1");
			buttonPerson1.setBounds(82, 171, 150, 150);
		}
		return buttonPerson1;
	}
	public JButton getButtonPerson2() {
		if (buttonPerson2 == null) {
			buttonPerson2 = new JButton("Personagem 2");
			buttonPerson2.setBounds(242, 171, 150, 150);
		}
		return buttonPerson2;
	}
	public JButton getButtonPerson3() {
		if (buttonPerson3 == null) {
			buttonPerson3 = new JButton("Personagem 3");
			buttonPerson3.setBounds(402, 171, 150, 150);
		}
		return buttonPerson3;
	}
	public JButton getButtonPerson4() {
		if (buttonPerson4 == null) {
			buttonPerson4 = new JButton("Personagem 4");
			buttonPerson4.setBounds(562, 171, 150, 150);
		}
		return buttonPerson4;
	}
	public JButton getButtonStart() {
		if (buttonStart == null) {
			buttonStart = new JButton("Selecionar");
			buttonStart.setBounds(325, 566, 150, 50);
			buttonStart.setEnabled(false);
		}
		return buttonStart;
	}
	public JLabel getLabelVersus() {
		if (labelVersus == null) {
			labelVersus = new JLabel("Vs");
			labelVersus.setFont(new Font("Tahoma", Font.PLAIN, 41));
			labelVersus.setBounds(379, 394, 42, 50);
		}
		return labelVersus;
	}
	public JPanel getPanelPlayer1() {
		if (panelPlayer1 == null) {
			panelPlayer1 = new JPanel();
			panelPlayer1.setBackground(new Color(192, 192, 192));
			panelPlayer1.setBounds(116, 356, 219, 175);
		}
		return panelPlayer1;
	}
	public JPanel getPanelPlayer2() {
		if (panelPlayer2 == null) {
			panelPlayer2 = new JPanel();
			panelPlayer2.setBackground(new Color(192, 192, 192));
			panelPlayer2.setBounds(454, 356, 219, 175);
		}
		return panelPlayer2;
	}
}
