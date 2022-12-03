package model;

import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.scene.Scene;
import network.Requests;

public class OnlineCourt {

	// instance parameters
    private final RacketController playerA;
    public final double width, height; // m
    
    public final double scale;
    public final double scaleX, scaleY;
    public final double racketSpeed; // m/s
    public final double ballRadius; // m
    // instance state
    private double racketA; // m
    private double racketB; // m
    
    private double ballX, ballY; // m
    
    private int scoreA = 0;
    private int scoreB = 0;
    
    private int idPlayer;

    private double racketSize; // m

    private Scene lostScene;
    
    private boolean finished = false;
    
    private boolean lost = false;
    
    private boolean scored = false;
    
    private Requests r;
    
    public OnlineCourt(RacketController playerA,
    		double width, double height, Requests r) {
    	
        this.playerA = playerA;
        
        this.width = width;
        this.height = height;
        
        this.scale = (width * height) / (1360 * 768);
        this.scaleX = width / 1360;
        this.scaleY = height / 768;
        
        this.racketA = (height / 2) * scale;
        this.racketB = (height / 2) * scale;
        
        this.racketSize = 150 * scale;
        this.racketSpeed = 350.0 * scale;
        this.ballRadius = 15.0 * scale;
        
        Random rd = new Random();
        idPlayer = 0 - rd.nextInt() * 1000;
        
        this.r = r;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getRacketSize() {
        return racketSize;
    }
    
    public void setRacketSize(double x) {
        racketSize = x;
    }

    public double getRacketA() {
        return racketA;
    }

    public double getRacketB() {
        return racketB;
    }

    public double getBallX() {
        return ballX;
    }

    public double getBallY() {
        return ballY;
    }
    
    public void setScoreA(int s) {
        scoreA=s;
        scored = true;
    }
    
    public void setScoreB(int s) {
    	sound("LoseSound.wav");
        scoreB=s;
        scored = true;
    }
    
    public int getScoreA() {
        return scoreA;
    }
    
    public int getScoreB() {
        return scoreB;
    }

    public void update(double deltaT) {
        switch (playerA.getState()) {
            case GOING_UP:
                racketA -= racketSpeed * deltaT;
                if (racketA < 0.0) racketA = 0.0;
                
                // A MODIFIER
                else r.sendMessage(idPlayer, "RACKET_UPDATE", racketA * scale, null, false);
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketA += racketSpeed * deltaT;
                if (racketA + racketSize > height) racketA = height - racketSize;
                
                // A MODIFIER
                else r.sendMessage(idPlayer, "RACKET_UPDATE", racketA * scale, null, false);
                break;
		default:
			break;
        }
    }
    
    // VERIFIER VARIABLES / MESURES
    public void updateBall(double x, double y) {
    	
    	/* A MODIFIER
    	if ( (ballX <= 10 && ballY >= racketA && ballY <= racketA + racketSize) // la balle touche la raquette gauche
				|| ballX >= width - 10 && ballY >= racketB && ballY <= racketB + racketSize) { // la balle touche la raquette droite
    		sound("RacketSound.wav");
		}
    	// vérification> la balle touche un mur
		else if ( (ballX >= 10 && ballX <= width - 10 && ballY <= 5) // la balle touche le mur du haut
				|| (ballX >= 10) && ballX <= width - 10 && ballY >= height - 5) { // la balle touche le mur du bas
			sound("WallSound.wav");
		}	
    	*/
    	ballX = x * scale;
    	ballY = y * scale;    	
    }

    public void sound(String s) {
        // On joue le son
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("src/main/resources/"+s)));
            clip.start();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
    
    
    public void playerLost() {
    	sound("lost.wav");
        // On affiche un écran comme quoi on a perdu
    }
    
    public boolean getLost() {
        return lost;
    }
    
    public void setLost(boolean b) {
        lost = b;
    }
        
    public double getBallRadius() {
        return ballRadius;
    }
    
	public boolean getFinished() {
		// TODO Auto-generated method stub
		return finished;
	}
	
	public void finish() {
		finished = true;
	}

	public void setId(int i) {
		// TODO Auto-generated method stub
		idPlayer = i;
	}

	public void setRacketB(double y) {
		// TODO Auto-generated method stub
		racketB = y;
	}

	public int getIdPlayer() {
		// TODO Auto-generated method stub
		return idPlayer;
	}
	
	public boolean getScored() {
		return scored;
	}
	
	public void resetScored() {
		scored = false;
	}
}
