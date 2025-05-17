package view;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

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
    private JButton buttonConfirm;
    
    private JLabel panelPlayer1;
    private JLabel panelPlayer2;
    private JTextArea description;
    private String profilePath1, profilePath2;
    
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
        add(getButtonPerson4());
        add(getButtonPerson5());
        add(getButtonStart());
        add(getButtonConfirm());
        
        add(getPanelPlayer1());
        add(getPanelPlayer2());
        add(getAreaDescription());
        
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
            labelTitle = new JLabel("Seleção");
            labelTitle.setBounds(395, 60, 210, 62);
            labelTitle.setFont(new Font("Tahoma", Font.BOLD, 51));
            labelTitle.setForeground(new Color(108, 60, 2));
        }
        return labelTitle;
    }

    public JLabel getLabelSubtitle() {
        if (labelSubtitle == null) {
            labelSubtitle = new JLabel("Jogador 1, escolha seu personagem.");
            labelSubtitle.setBounds(80, 130, 826, 29);
            labelSubtitle.setFont(new Font("Tahoma", Font.BOLD, 24));
            labelSubtitle.setForeground(new Color(108, 60, 2));
        }
        return labelSubtitle;
    }

    public JButton getButtonPerson1() {
        if (buttonPerson1 == null) {
        	ImageIcon nita = new ImageIcon("resources/buttons/nita.png");
            buttonPerson1 = new JButton(nita);
            buttonPerson1.setBounds(253, 171, 140, 140);
            buttonPerson1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        return buttonPerson1;
    }
    

    public JButton getButtonPerson2() {
        if (buttonPerson2 == null) {
        	ImageIcon isagram = new ImageIcon("resources/buttons/isagram.png");
            buttonPerson2 = new JButton(isagram);
            buttonPerson2.setBounds(425, 171, 140, 140);
            buttonPerson2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        return buttonPerson2;
    }

    public JButton getButtonPerson3() {
        if (buttonPerson3 == null) {
        	ImageIcon murissoca = new ImageIcon("resources/buttons/murissoca.png");
            buttonPerson3 = new JButton(murissoca);
            buttonPerson3.setBounds(80, 171, 140, 140);
            buttonPerson3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        return buttonPerson3;
    }

    public JButton getButtonPerson4() {
        if (buttonPerson4 == null) {
        	ImageIcon teletony = new ImageIcon("resources/buttons/teletony.png");
            buttonPerson4 = new JButton(teletony);
            buttonPerson4.setBounds(595, 171, 140, 140);
            buttonPerson4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        return buttonPerson4;
    }
    
    public JButton getButtonPerson5() {
        if (buttonPerson5 == null) {
        	ImageIcon lule = new ImageIcon("resources/buttons/lule.png");
        	buttonPerson5 = new JButton(lule);
        	buttonPerson5.setBounds(766, 171, 140, 140);
        	buttonPerson5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        return buttonPerson5;
    }

    public JButton getButtonStart() {
        if (buttonStart == null) {
            buttonStart = new JButton("Começar");
            buttonStart.setBounds(425, 500, 150, 50);
            buttonStart.setEnabled(false);
            buttonStart.setFont(new Font("Tahoma", Font.BOLD, 18));
            buttonStart.setForeground(new Color(238, 238, 238));
            buttonStart.setBackground(new Color(108, 60, 2));
            buttonStart.setBorderPainted(false);
            buttonStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            buttonStart.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                	buttonStart.setBackground(new Color(80, 0, 0)); // hover
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                	buttonStart.setBackground(new Color(108, 60, 2)); // original
                }
            });
        }
        return buttonStart;
    }
    
    public JButton getButtonConfirm() {
        if (buttonConfirm == null) {
        	buttonConfirm = new JButton("Selecionar");
        	buttonConfirm.setBounds(425, 440, 150, 50);
        	buttonConfirm.setEnabled(false);
        	buttonConfirm.setFont(new Font("Tahoma", Font.BOLD, 18));
        	buttonConfirm.setForeground(new Color(238, 238, 238));
        	buttonConfirm.setBackground(new Color(108, 60, 2));
        	buttonConfirm.setBorderPainted(false);
        	buttonConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
        	buttonConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                	buttonConfirm.setBackground(new Color(80, 0, 0)); // hover
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                	buttonConfirm.setBackground(new Color(108, 60, 2)); // original
                }
            });
        }
        return buttonConfirm;
    }

    public JLabel getLabelVersus() {
        if (labelVersus == null) {
            labelVersus = new JLabel("vs");
            labelVersus.setFont(new Font("Tahoma", Font.BOLD, 41));
            labelVersus.setBounds(471, 370, 58, 50);
            labelVersus.setForeground(new Color(108, 60, 2));
            labelVersus.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return labelVersus;
    }

    public JLabel getPanelPlayer1() {
        if (panelPlayer1 == null) {
            panelPlayer1 = new JLabel();
            panelPlayer1.setBackground(new Color(192, 192, 192));
            panelPlayer1.setBounds(130, 330, 220, 220);
        }
        return panelPlayer1;
    }

    public JLabel getPanelPlayer2() {
        if (panelPlayer2 == null) {
            panelPlayer2 = new JLabel();
            panelPlayer2.setBackground(new Color(192, 192, 192));
            panelPlayer2.setBounds(651, 330, 220, 220);
        }
        return panelPlayer2;
    }
    
    public void setProfilePathP1(String path) {
        this.profilePath1 = path;
        // Atualiza o ícone do JLabel do Player 1
        ImageIcon icon = new ImageIcon(path);
        panelPlayer1.setIcon(icon);
        panelPlayer1.revalidate(); // Revalida o painel
        panelPlayer1.repaint(); // Repaint para garantir que a nova imagem apareça
    }
    
    public void setProfilePathP2(String path) {
        this.profilePath2 = path;
        // Atualiza o ícone do JLabel do Player 2
        ImageIcon icon = new ImageIcon(path);
        panelPlayer2.setIcon(icon);
        panelPlayer2.revalidate(); // Revalida o painel
        panelPlayer2.repaint(); // Repaint para garantir que a nova imagem apareça
    }
    
    public JTextArea getAreaDescription() {
    	if (description == null) {
    		description = new JTextArea();
    		description.setBackground(new Color(238, 238, 238));
    		description.setBounds(50, 560, 900, 100);
    		description.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
    		description.setFont(new Font("Tahoma", Font.PLAIN, 20));
    		description.setLineWrap(true);     
    		description.setWrapStyleWord(true);
    		description.setMargin(new Insets(10, 10, 10, 10));
        }
        return description;
    }
}