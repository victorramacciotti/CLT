package view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

public class CharacterSelectionPanel extends JPanel {
    
    private JLabel labelTitle;
    private JLabel labelSubtitle;
    private JLabel labelVersus;    
    
    private JButton buttonPerson1;
    private JButton buttonPerson2;
    private JButton buttonPerson3;
    private JButton buttonPerson4;
    private JButton buttonPerson5;
    private JButton buttonStart;
    
    private JPanel panelPlayer1;
    private JPanel panelPlayer2;
    
    public CharacterSelectionPanel() {
        setSize(1000, 700);
        setLayout(null);
        
        add(getLabelTitle());
        add(getLabelSubtitle());
        add(getLabelVersus());

        add(getButtonPerson1());
        add(getButtonPerson2());
        add(getButtonPerson3());
        add(getButtonPerson5());
        add(getButtonStart());
        
        add(getPanelPlayer1());
        add(getPanelPlayer2());
        add(getButtonPerson4());
    }
    
    public JLabel getLabelTitle() {
        if (labelTitle == null) {
            labelTitle = new JLabel("Selection");
            labelTitle.setBounds(395, 40, 210, 62);
            labelTitle.setFont(new Font("Tahoma", Font.PLAIN, 51));
        }
        return labelTitle;
    }

    public JLabel getLabelSubtitle() {
        if (labelSubtitle == null) {
            labelSubtitle = new JLabel("Choose the character for Player 1");
            labelSubtitle.setBounds(315, 113, 370, 29);
            labelSubtitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
        }
        return labelSubtitle;
    }

    public JButton getButtonPerson1() {
        if (buttonPerson1 == null) {
            buttonPerson1 = new JButton("Nita");
            buttonPerson1.setBounds(41, 171, 150, 150);
        }
        return buttonPerson1;
    }

    public JButton getButtonPerson2() {
        if (buttonPerson2 == null) {
            buttonPerson2 = new JButton("IsaGram");
            buttonPerson2.setBounds(232, 171, 150, 150);
        }
        return buttonPerson2;
    }

    public JButton getButtonPerson3() {
        if (buttonPerson3 == null) {
            buttonPerson3 = new JButton("Murissoca");
            buttonPerson3.setBounds(423, 171, 150, 150);
        }
        return buttonPerson3;
    }

    public JButton getButtonPerson4() {
        if (buttonPerson4 == null) {
            buttonPerson4 = new JButton("TeleTony");
            buttonPerson4.setBounds(614, 171, 150, 150);
        }
        return buttonPerson4;
    }
    
    public JButton getButtonPerson5() {
        if (buttonPerson5 == null) {
        	buttonPerson5 = new JButton("Lule");
        	buttonPerson5.setBounds(805, 171, 150, 150);
        }
        return buttonPerson5;
    }

    public JButton getButtonStart() {
        if (buttonStart == null) {
            buttonStart = new JButton("Select");
            buttonStart.setBounds(434, 566, 150, 50);
            buttonStart.setEnabled(false);
        }
        return buttonStart;
    }

    public JLabel getLabelVersus() {
        if (labelVersus == null) {
            labelVersus = new JLabel("Vs");
            labelVersus.setFont(new Font("Tahoma", Font.PLAIN, 41));
            labelVersus.setBounds(479, 394, 42, 50);
        }
        return labelVersus;
    }

    public JPanel getPanelPlayer1() {
        if (panelPlayer1 == null) {
            panelPlayer1 = new JPanel();
            panelPlayer1.setBackground(new Color(192, 192, 192));
            panelPlayer1.setBounds(130, 356, 219, 175);
        }
        return panelPlayer1;
    }

    public JPanel getPanelPlayer2() {
        if (panelPlayer2 == null) {
            panelPlayer2 = new JPanel();
            panelPlayer2.setBackground(new Color(192, 192, 192));
            panelPlayer2.setBounds(651, 356, 219, 175);
        }
        return panelPlayer2;
    }
}