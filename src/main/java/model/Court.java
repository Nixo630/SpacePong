package model;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import gui.App;
import gui.GameView;
import javafx.scene.Scene;

public class Court {
    // instance parameters
    private final RacketController playerA, playerB;
    private final double width, height; // m
    private final double racketSpeed = 300.0; // m/s
    private double racketSize = 100.0; // m
    private final double ballRadius = 10.0; // m
    // instance state
    private double racketA; // m
    private double racketB; // m
    private double ballX, ballY; // m
    private double ballSpeedX, ballSpeedY; // m 
    private int scoreA = 0;
    private int scoreB = 0;
    
    private Scene lostScene;
    private boolean ballTouched = false;
    private boolean scored = false;

    public Court(RacketController playerA, RacketController playerB,
    		double width, double height, Scene lostScene) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.width = width;
        this.height = height;
        this.lostScene = lostScene;
        reset();
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
    }
    
    public void setScoreB(int s) {
    	scoreB=s;
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
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketA += racketSpeed * deltaT;
                if (racketA + racketSize > height) racketA = height - racketSize;
                break;
        }
        switch (playerB.getState()) {
            case GOING_UP:
                racketB -= racketSpeed * deltaT;
                if (racketB < 0.0) racketB = 0.0;
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketB += racketSpeed * deltaT;
                if (racketB + racketSize > height) racketB = height - racketSize;
                break;
        }
        
        if (updateBall(deltaT)) reset();
    }
       
    /**
     * @return true if a player lost
     */
    private boolean updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;
        // next, see if the ball would meet some obstacle
        if (nextBallY < 10 || nextBallY > height - 10) { // 10 correspond à la taille des murs
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY;
            Sound("WallSound.wav");
        }
        if ((nextBallX < 0 && nextBallY > racketA && nextBallY < racketA + racketSize)
                || (nextBallX > width && nextBallY > racketB && nextBallY < racketB + racketSize)) {
            if (ballSpeedX > 0) {
            	ballSpeedX = -(ballSpeedX + 25);
            	Sound("RacketSound.wav");
            } // MAJ vitesse de la balle après avoir touché la raquette
            else {
            	ballSpeedX = -(ballSpeedX - 25);
            	Sound("RacketSound.wav");
            } // MAJ gauche> droite quand la vitesse est dans le négatif
            if (ballSpeedY > 0) {
		// ballY - ((racketsize/2)+ballX) //rapport entre le milieu de la raquette et la position de la balle
                if (nextBallX < 0) {
                    ballSpeedY = Math.abs(ballSpeedX) + (Math.abs(ballY - ((racketSize/2)+racketA))*7);
                }
                else {
                    ballSpeedY = Math.abs(ballSpeedX) + (Math.abs(ballY - ((racketSize/2)+racketB))*7);
                }
            }
            else {
                if (nextBallX < 0) {
                    ballSpeedY = -(Math.abs(ballSpeedX) + (Math.abs(ballY - ((racketSize/2)+racketA))*4));
                }
                else {
                    ballSpeedY = -(Math.abs(ballSpeedX) + (Math.abs(ballY - ((racketSize/2)+racketB)))*4);
                }
            }
            ballTouched = true;
            nextBallX = ballX + deltaT * ballSpeedX;
        } else if (nextBallX < 0) {
        	setScoreB(scoreB+1);
        	playerLost();
            return true;
        } else if (nextBallX > width) {
        	setScoreA(scoreA+1);
        	playerLost();
            return true;
        }
        ballX = nextBallX;
        ballY = nextBallY;
        return false;
    }
    
    public void Sound(String s) {
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
    	scored = true;
    	if (scoreA==5 || scoreB== 5) {
    		this.scoreA=0;
    		this.scoreB=0;
    	// On joue le son
    		Sound("lost.wav");
    	
    		GameView.stopAnimation();
    		reset();
    		App.getStage().setScene(lostScene);
    	}
    }
        
    public double getBallRadius() {
        return ballRadius;
    }
    
    public boolean getBallTouched() {
    	return ballTouched;
    }
    
    public void resetBallTouched() {
    	ballTouched = false;
    }
    
    public boolean scored() {
    	return scored;
    }
    
    public void resetScored() {
    	scored = false;
    }

    void reset() {
    	this.racketA = height / 2;
        this.racketB = height / 2;
        this.ballSpeedX = 200.0;
        this.ballSpeedY = 200.0;
        this.ballX = width / 2;
        this.ballY = height / 2;       
    }

}
