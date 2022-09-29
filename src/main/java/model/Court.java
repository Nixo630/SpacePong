package model;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Court {
    // instance parameters
    private final RacketController playerA, playerB;
    private final double width, height; // m
    private final double racketSpeed = 300.0; // m/s
    private final double racketSize = 100.0; // m
    private final double ballRadius = 10.0; // m
    // instance state
    private double racketA; // m
    private double racketB; // m
    private double ballX, ballY; // m
    private double ballSpeedX, ballSpeedY; // m
    

    public Court(RacketController playerA, RacketController playerB, double width, double height) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.width = width;
        this.height = height;
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
        }
        if ((nextBallX < 0 && nextBallY > racketA && nextBallY < racketA + racketSize)
                || (nextBallX > width && nextBallY > racketB && nextBallY < racketB + racketSize)) {
            if (ballSpeedX > 0){ballSpeedX = -(ballSpeedX + 25);} // MAJ vitesse de la balle après avoir touché la raquette
            else {ballSpeedX = -(ballSpeedX - 25);} // MAJ gauche> droite quand la vitesse est dans le négatif
            if (ballSpeedY > 0) {ballSpeedY += 25;}
            else {ballSpeedY -= 25;}
            nextBallX = ballX + deltaT * ballSpeedX;
        } else if (nextBallX < 0) {
        	playerLost();
            return true;
            
        } else if (nextBallX > width) {
        	playerLost();
            return true;
        }
        ballX = nextBallX;
        ballY = nextBallY;
        return false;
    }
    
    /* Ajouté par Evan le 27/09/2022 : méthode permettant d'informer que le joueur
     * a perdu, par le biais d'un son et d'un message. */
    public void playerLost() {
    	
    	// On joue le son
    	try
        {
    		Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("src/main/resources/lost.wav")));
            clip.start();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    	
    	// On affiche une boîte de dialogue
    	int rep = JOptionPane.showConfirmDialog(null,
                "Vous avez perdu... voulez-vous rejouer ?", "Perdu !",
                JOptionPane.YES_NO_OPTION);
    	if (rep == JOptionPane.NO_OPTION) System.exit(0);	
    }
        
    public double getBallRadius() {
        return ballRadius;
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
