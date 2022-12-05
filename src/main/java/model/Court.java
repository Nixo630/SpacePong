package model;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Court {
    // instance parameters
    private final RacketController playerA, playerB; //, playerC, playerD,playerE;
    private final double width, height; // m
    private final double racketSpeed = 350.0; // m/s
    private final double ballRadius = 15.0; // m
    // instance state
    private double racketA; // m
    private double racketB; // m
    private double racketC; // m
    //private double racketE;
    private double racketD; // m
    private double ballX, ballY; // m
    private double ballSpeedX, ballSpeedY; // m 
    private int scoreA = 0;
    private int scoreB = 0;
    private int scoreFinal = 5;
    private double racketSize; // m
    private RacketController.State botDirection;//direction du bot
    private double directionPoint;//coordonee en y ou la balle se dirige
    private boolean isBot;//pour savoir si on est en solo et qu'on a besoin d'un bot
    
   // private Scene lostScene;
    private boolean ballTouched = false;
    private boolean scored = false;
    
    private boolean partiEnCours = false;
    private int difficulty;//chiffre entre 1 et 4 pour les 4 niveaux de difficultés
    
    private boolean lost=false;
    
    // Idée : pour la mise en pause
    //Créer un boolean partieEnCours
    // TU fais tout les vérifications dans la fonction, tu dois juste verifier si partieEnCours est égale à True
    //Si c'est le cas tu affiches les bouton résume et exit
    //Sinon tu fais rien
    // Bien oublie pas quand tu appuis sur la touche p ou le bouton de Adem la fonction partieEnCours est égale à false
    // Comme ca y'a pas l'erreur d'apuiiyer plusieurs fois sur pause.

    public Court(RacketController playerA, RacketController playerB,
            double width, double height) {
        this.playerA = playerA;
        this.playerB = playerB;
        /*this.playerC = playerC;
        this.playerD = playerD;
        this.playerE = null;*/
        this.width = width;
        this.height = height;
        
        reset();
    }
    
    public void setScoreFinal(int scoreFinal) {
        this.scoreFinal = scoreFinal;
    }

    public void setIsBot(boolean b){
        isBot = b;
    }
    
    public boolean getPartiEnCours() {
        return partiEnCours;
    }
    
    public void setPartiEnCours(boolean b) {
        partiEnCours = b;
    }
    
    public void setDifficulty(int n) {
        difficulty=n;
    }

    public boolean getIsBot(){
        return isBot;
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

    public double getRacketC() {
        return racketC;
    }

    public double getRacketD() {
        return racketD;
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

    private void direction() {
        if ((racketB + racketSize/2) > directionPoint) {
            botDirection = RacketController.State.GOING_UP;
        }
        else if ((racketB + racketSize/2) < directionPoint) {
            botDirection = RacketController.State.GOING_DOWN;
        }
        else {
            botDirection = RacketController.State.IDLE;
        }
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
		default:
			break;
        }
        if(!isBot){
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
			default:
				break;
            }
            if (updateBall(deltaT)) reset();
            return;
        }
        //La suite est pour le fonctionnement du bot
        if ((ballX < (width/4)*(4-difficulty) || (ballX > (width/4)*(4-difficulty) && ballSpeedX < 0)) && ballY < racketB + racketSize/2) {//si la balle est dans la premiere moitie du terrain et que les coordonées de la balle sont en dessous du milieu de la raquette alors on monte pour suivre la balle
            //mais il faut aussi que la balle aille dans la direction du bot
//            System.out.println((width/4)*difficulty);
            racketB -= racketSpeed * deltaT;
            if (racketB < 0.0) racketB = 0.0;
        }
        else if ((ballX < (width/4)*(4-difficulty) || (ballX > (width/4)*(4-difficulty) && ballSpeedX < 0)) && ballY >= racketB + racketSize/2) {//donc si les coordonnées sont au dessus alors on descend pour suivre la balle
//          System.out.println((width/4)*difficulty);
            racketB += racketSpeed * deltaT;
            if (racketB + racketSize > height) racketB = height - racketSize;
        }
        else {//et si la balle est dans la deuxieme moitie du terrain on predict la trajectoire
            double tmp = (width - ballX)/ballSpeedX;//rapport de la distance restante sur la vitesse
            if (ballSpeedY > 0) {//si la balle va de haut en bas
                if (((height-ballY)/(width-ballX)) < (ballSpeedY/ballSpeedX)) {//si la balle va toucher le mur du bas avant le but
                    directionPoint = Math.abs(ballY + ballSpeedY*tmp) % height;
                    directionPoint = height-directionPoint;
                }
                else {//si la balle va de haut en bas directement dans le but
                    directionPoint = Math.abs(ballY + ballSpeedY*tmp) % height;
                }
            }
            else {//si la balle va de bas en haut
                if ((ballY/(width-ballX)) < (Math.abs(ballSpeedY)/ballSpeedX)) {//si la balle va toucher le mur du haut avant le but
                    directionPoint = Math.abs(ballY + ballSpeedY*tmp) % height;
                    //directionPoint = height-directionPoint;
                }
                else {//si la balle va de bas en haut directement dans le but
                    directionPoint = Math.abs(ballY + ballSpeedY*tmp) % height;
                }
            }
            direction();
            if (Math.abs((racketB + racketSize/2) - directionPoint) < 5) {//on teste pour savoir quand s'arreter de deplacer le bot avec une approximation de 5
                botDirection = RacketController.State.IDLE;
            }
            switch (botDirection) {
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
			default:
				break;
            }
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
            sound("WallSound.wav");
        }
        if ((nextBallX < 0 && nextBallY > racketA && nextBallY < racketA + racketSize)
                || (nextBallX > width && nextBallY > racketB && nextBallY < racketB + racketSize)) {
            if (ballSpeedX > 0) {
                botDirection = RacketController.State.IDLE;
                ballSpeedX = -(ballSpeedX + 25);
                sound("RacketSound.wav");
            } // MAJ vitesse de la balle après avoir touché la raquette
            else {
                ballSpeedX = -(ballSpeedX - 25);
                sound("RacketSound.wav");
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
            sound("LoseSound.wav");
            return true;
        } else if (nextBallX > width) {
            setScoreA(scoreA+1);
            playerLost();
            sound("LoseSound.wav");
            return true;
        }
        ballX = nextBallX;
        ballY = nextBallY;
        return false;
    }
    


    public void update2(double deltaT) {
        switch (playerA.getState()) { // on récupère le déplacement du joueur A pour déplacer la hitbox de sa racket
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
		default:
			break;
        }
        switch (playerB.getState()) {  // on récupère le déplacement du joueur B pour déplacer la hitbox de sa racket
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
		default:
			break;
        }

        if (updateBall2(deltaT)) reset(); // on actualise l'état de la balle

        // on déplace les bots en fonction de la position de la balle, les deux resteront aux même coordonées, pas besoin de comparer avec les deux 
        // on décide de déplacer les bots en fonction du milieu de la racket et pas des bords pour qu'ils soient plus réactifs 

        if (ballX < (racketC*2) ){
            botDirection = RacketController.State.GOING_LEFT;
        }   
        else {
            if (ballX > (racketC)*2 +racketSize/2) {

                botDirection = RacketController.State.GOING_RIGHT;
        }
        else {
            botDirection = RacketController.State.IDLE;
        }

        }
        switch (botDirection) { // déplacement du bot
            case GOING_LEFT:
                racketC -= racketSpeed * deltaT  ;
                if (racketC < 0.0) racketC = 0.0;

                racketD -= racketSpeed * deltaT  ;
                if (racketD < 0.0) racketD = 0.0;
                break;
            case IDLE:
                break;
            case GOING_RIGHT:
                racketC += racketSpeed * deltaT ;
                if (racketC + racketSize > width) racketC = width - racketSize;

                racketD += racketSpeed * deltaT ;
                if (racketD + racketSize > width) racketD = width - racketSize;
                break;
		default:
			break;
        }
    
}
       
    /**
     * @return true if a player lost
     */
     private boolean updateBall2(double deltaT) {

         // prochaine position de la balle 
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;

        // les lignes suivantes sont le cas où la balle rencontre un obstacle

        if ((nextBallY < 50 && nextBallX > (racketC*2)-racketSize&& nextBallX < (racketC*2)+racketSize  )|| 
            (nextBallY > height - 50 && nextBallX > (racketD*2)-racketSize && nextBallX < (racketD *2) + racketSize )) {
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY;}
            else {

            if (nextBallY < 50) {
            setScoreA(scoreA+1);
            playerLost();
            return true;
            
            } else if (nextBallY > height-50) {
            setScoreA(scoreA+1);
            playerLost();
            return true;
            }
            }
        
            if ((nextBallX < 10 && nextBallY > racketA && nextBallY < racketA + racketSize)
                || (nextBallX > width +10 && nextBallY > racketB && nextBallY < racketB + racketSize)) {
            if (ballSpeedX > 0){ballSpeedX = -(ballSpeedX + 25);} // MAJ vitesse de la balle après avoir touché la raquette
            else {ballSpeedX = -(ballSpeedX - 25);} // MAJ gauche> droite quand la vitesse est dans le négatif
            if (ballSpeedY > 0) {ballSpeedY += 25;}
            else {ballSpeedY -= 25;}
            nextBallX = ballX + deltaT * ballSpeedX;
        } else if (nextBallX < 10) {
            setScoreB(scoreB+1);
            playerLost();
            return true;
            
        } else if (nextBallX > width+10) {
            setScoreB(scoreB+1);
            playerLost();
            return true; 
        }
        
        ballX = nextBallX;
        ballY = nextBallY;
        return false;
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
        scored = true;
        if (scoreA==scoreFinal || scoreB== scoreFinal) {
        // On joue le son
            partiEnCours = false;
            sound("lost.wav");
            
            lost = true;
        }
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

    public void reset_score() {
        this.scoreA=0;
        this.scoreB=0;
    }
    
    public void reset() {
        this.racketA = height / 2;
        this.racketB = height / 2;
        this.racketC = height / 2.5;
        //this.racketE = height / 2.5;
        this.racketD = height / 2.5; 
        
        double nb;
        nb = Math.random()*10;
    
        if (nb<2.5) {
            this.ballSpeedX = 450.0;
            this.ballSpeedY = 450.0;
            }
        else if(nb>=2.5 && nb<5) {
            this.ballSpeedX = 450.0;
            this.ballSpeedY = -450.0;
        }
        else if(nb>=5 && nb<7.5) {
            this.ballSpeedX = -450.0;
            this.ballSpeedY = 450.0;
        }
        else{
            this.ballSpeedX = -450.0;
            this.ballSpeedY = -450.0;
        }
        
        this.ballX = width / 2;
        this.ballY = height / 2;
        this.racketSize = 150.0;
        botDirection = RacketController.State.IDLE;//reset de la direction du bot pour recalculer la trajectoire
    }

}
