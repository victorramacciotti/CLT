package model;

import javax.swing.ImageIcon;
import java.io.File;

public class SpriteLoader {

    public static ImageIcon loadImage(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return new ImageIcon(file.getAbsolutePath());
            } else {
                System.err.println("File not found: " + file.getAbsolutePath());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
}