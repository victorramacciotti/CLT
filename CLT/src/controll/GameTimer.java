package controll;

import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Color;

public class GameTimer {
    
    private final Timer timer;
    private final JLabel timerLabel;
    private final int maxSeconds;
    private int currentSeconds;
    private boolean isRunning;
    private Runnable onTimeout;

    public GameTimer(int maxSeconds, JLabel timerLabel) {
        this.maxSeconds = maxSeconds;
        this.currentSeconds = maxSeconds;
        this.timerLabel = timerLabel;
        this.isRunning = false;
        this.timer = new Timer(1000, e -> tick());
    }

    public void setOnTimeout(Runnable onTimeout) {
        this.onTimeout = onTimeout;
    }

    public void start() {
        if (!isRunning) {
            currentSeconds = maxSeconds;
            updateLabel();
            timer.start();
            isRunning = true;
        }
    }
    
    public void restart() {
    	currentSeconds = maxSeconds;
        updateLabel();
        timer.start();
        isRunning = true;
    }

    public void stop() {
        if (isRunning) {
            timer.stop();
            isRunning = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void tick() {
        if (currentSeconds <= 0) {
            stop();
            if (onTimeout != null) {
                onTimeout.run();
            }
            return;
        }
        
        currentSeconds--;
        updateLabel();
        
        if (currentSeconds <= 15) {
            timerLabel.setForeground(Color.RED);
        }
    }

    private void updateLabel() {
        timerLabel.setText(String.format("%03d", currentSeconds));
    }
}