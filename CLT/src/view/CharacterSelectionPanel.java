package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class CharacterSelectionPanel extends JPanel {
    
    private JLabel labelTitle;
    private JLabel labelSubtitle;
    private JLabel labelVersus;    
    
    private Image backgroundImage;
    private JButton buttonPerson1;
    private JButton buttonPerson2;
    private JButton buttonPerson3;
    private JButton buttonPerson4;
    private JButton buttonPerson5;
    private JButton buttonStart;
    
    private JPanel panelPlayer1;
    private JPanel panelPlayer2;
    private String namePanel;
    
    public CharacterSelectionPanel() {
        setSize(1000, 700);
        setLayout(null);
        ImageIcon bgIcon = new ImageIcon("resources/backgrounds/backgroundSelection.png"); 
        backgroundImage = bgIcon.getImage();
        
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
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Desenha a imagem de fundo esticada para ocupar todo o painel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    public JLabel getLabelTitle() {
        if (labelTitle == null) {
            labelTitle = new JLabel("Selection");
            labelTitle.setBounds(395, 60, 210, 62);
            labelTitle.setFont(new Font("Tahoma", Font.PLAIN, 51));
        }
        return labelTitle;
    }

    public JLabel getLabelSubtitle() {
        if (labelSubtitle == null) {
            labelSubtitle = new JLabel("Choose the character for Player 1");
            labelSubtitle.setBounds(315, 130, 370, 29);
            labelSubtitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
        }
        return labelSubtitle;
    }

    public JButton getButtonPerson1() {
        if (buttonPerson1 == null) {
        	ImageIcon nita = new ImageIcon("resources/buttons/nita.png");
            buttonPerson1 = new JButton(nita);
            buttonPerson1.setBounds(253, 171, 140, 140);
            namePanel = "Nita";
        }
        return buttonPerson1;
    }
    

    public JButton getButtonPerson2() {
        if (buttonPerson2 == null) {
        	ImageIcon isagram = new ImageIcon("resources/buttons/isagram.png");
            buttonPerson2 = new JButton(isagram);
            buttonPerson2.setBounds(425, 171, 140, 140);
        }
        return buttonPerson2;
    }

    public JButton getButtonPerson3() {
        if (buttonPerson3 == null) {
        	ImageIcon murissoca = new ImageIcon("resources/buttons/murissoca.png");
            buttonPerson3 = new JButton(murissoca);
            buttonPerson3.setBounds(80, 171, 140, 140);
        }
        return buttonPerson3;
    }

    public JButton getButtonPerson4() {
        if (buttonPerson4 == null) {
        	ImageIcon teletony = new ImageIcon("resources/buttons/teletony.png");
            buttonPerson4 = new JButton(teletony);
            buttonPerson4.setBounds(595, 171, 140, 140);
        }
        return buttonPerson4;
    }
    
    public JButton getButtonPerson5() {
        if (buttonPerson5 == null) {
        	ImageIcon lule = new ImageIcon("resources/buttons/lule.png");
        	buttonPerson5 = new JButton(lule);
        	buttonPerson5.setBounds(766, 171, 140, 140);
        }
        return buttonPerson5;
    }

    public JButton getButtonStart() {
        if (buttonStart == null) {
            buttonStart = new JButton("Select");
            buttonStart.setBounds(434, 526, 150, 50);
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
        
        else {
        	ImageIcon bgIcon1 = new ImageIcon("resources/buttons/.png");
            Image profileImage1 = bgIcon1.getImage();
            //panelPlayer1 = new imagepa(profileImage1);
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