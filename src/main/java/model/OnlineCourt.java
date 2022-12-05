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
    
    public final double scale; // scale X*Y rapporté au client
    public final double serverScale; // scale X*Y rapporté au serveur
    
    public final double scaleX, scaleY; // scale X rapporté au client, scale Y rapporté au client
    public final double serverScaleX, serverScaleY; // scale X rapporté au serveur, scale Y rapporté au serveur
    
    public final double racketSpeed; // m/s
    public final double ballRadius; // m
    // instance state
    private double racketA; // m
    private double racketB; // m
    
    private double ballX, ballY; // m
    
    private int scoreA = 0;
    private int scoreB = 0;
    
    private int idPlayer;
    
    private String pseudo;
    private String pseudoAdv;
    
    private boolean advDef = false;

    private double racketSize; // m

    private Scene lostScene;
    
    private boolean finished = false;
    
    private boolean lost = false;
    
    private boolean scored = false;
    
    private Requests r;
    
    public OnlineCourt(RacketController playerA,
    		double width, double height, Requests r, String pseudo) {
    	
        this.playerA = playerA;
        
        this.width = width;
        this.height = height;
        
        this.scale = (width * height) / (1920 * 1080);
        this.serverScale = (1920 * 1080) / (width * height);
        
        this.scaleX = width / 1920;
        this.scaleY = height / 1080;
        this.serverScaleX = 1920 / width;
        this.serverScaleY = 1080 / height;
        
        this.racketA = height / 2;
        this.racketB = height / 2;
        
        this.racketSize = 200 * scaleY;
        this.racketSpeed = 350.0 * scaleY;
        this.ballRadius = 30.0 * scale;
        
        this.ballX = width / 2;
        this.ballY = height / 2;
        
        this.pseudo = pseudo;
        
        Random rd = new Random();
        idPlayer = -1 - rd.nextInt(1000);
        
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
    	if (s != scoreA) {
    		scoreA=s;
    		scored = true;
    	}
        
    }
    
    public void setScoreB(int s) {
    	if (s != scoreB) {
    		sound("LoseSound.wav");
        	scoreB=s;
        	scored = true;
    	}
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
                else r.sendMessage(idPlayer, "RACKET_UPDATE", 0.0, racketA * serverScaleY, "null", false);
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketA += racketSpeed * deltaT;
                if (racketA + racketSize > height) racketA = height - racketSize;
                
                // A MODIFIER
                else r.sendMessage(idPlayer, "RACKET_UPDATE", 0.0, racketA * serverScaleY, "null", false);
                break;
		default:
			break;
        }
    }
    
    // VERIFIER VARIABLES / MESURES
    public void updateBall(double x, double y) {
    	/*
    	if (ballX <= 50 * scaleX && ballY >= racketA && ballY <= racketA + racketSize ||
    			(ballX >= width - 50 * scaleX && ballY >= racketB && ballY <= racketB + racketSize)) { // la balle touche une raquette
    		sound("RacketSound.wav");
    	}
    	else if ((ballX >= 50 * scaleX && ballX <= width - 50 * scaleX && ballY <= 20 * scaleY) // la balle touche un mur
						|| (ballX >= 50 * scaleX && ballX <= width - 50 * scaleX && ballY >= height - 20 * scaleY)) {
    		sound("WallSound.wav");
    	}*/

    	ballX = x * scaleX;
    	ballY = y * scaleY;    	
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

	public void setRacketA(double y) {
		racketA = y * scaleY;
	}
	
	public void setRacketB(double y) {
		// TODO Auto-generated method stub
		racketB = y * scaleY;
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
	
	public String getPseudo() {
		return pseudo;
	}
	
	public String getPseudoAdv() {
		return pseudoAdv;
	}
	
	public void setPseudoAdv(String pseudoAdv) {
		this.pseudoAdv = pseudoAdv;
		if (pseudoAdv != null && !pseudoAdv.equals(""))	advDef = true;
	}
	
	public boolean pseudoAdvDef() {
		return advDef;
	}
}