package audio;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	Clip clip;
	URL soundURL[] = new URL[15];
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/sounds/remixBR.wav");
		soundURL[1] = getClass().getResource("/sounds/barbieGirl.wav");
		soundURL[2] = getClass().getResource("/sounds/masterOfPuppets.wav");
		soundURL[3] = getClass().getResource("/sounds/takeOnMe.wav");
		soundURL[4] = getClass().getResource("/sounds/soundIsagram.wav");
		soundURL[5] = getClass().getResource("/sounds/soundLule.wav");
		soundURL[6] = getClass().getResource("/sounds/soundTeletony.wav");
		soundURL[7] = getClass().getResource("/sounds/soundNita.wav");
		soundURL[8] = getClass().getResource("/sounds/soundMurissoca.wav");
		soundURL[9] = getClass().getResource("/sounds/punch.wav");
		soundURL[10] = getClass().getResource("/sounds/jump.wav");
		soundURL[11] = getClass().getResource("/sounds/fight.wav");
		soundURL[12] = getClass().getResource("/sounds/punch2.wav");
	}
	
	public void setFile(int i) {
		try {
	        if (soundURL[i] == null) {
	            System.out.println("Sound file not found for index: " + i);
	            return; // Retorna se o arquivo não for encontrado
	        }
	        
	        AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
	        clip = AudioSystem.getClip();
	        clip.open(ais);
	    } catch (Exception e) {
	        e.printStackTrace(); // Imprime a exceção para depuração
	    }
	}
	
	public void play() {
		if (clip != null) {
	        clip.start();
	    } else {
	        System.out.println("Clip is not initialized. Please set the file first.");
	    }
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
	
	public void playMusic(int i) {
    	setFile(i);
    	play();
    	loop();
    }
    
    public void stopMusic() {
    	stop();
    }
    
    public void playSoundFX(int i) {
    	setFile(i);
    	play();
    }
    
    public void setVolume(float volume) {
        if (clip != null) {
            try {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume); 
            } catch (IllegalArgumentException e) {
                System.out.println("Volume control not supported for this clip.");
            }
        }
    }

}
