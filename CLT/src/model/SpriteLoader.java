package model;

import javax.swing.ImageIcon;
import java.io.File;

public class SpriteLoader {
    public static ImageIcon loadImage(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                return icon;
            } else {
                System.err.println("Arquivo não encontrado: " + file.getAbsolutePath());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem: " + e.getMessage());
            return null;
        }
    }
}