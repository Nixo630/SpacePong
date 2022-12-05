package model;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import gui.App;
import gui.GameView;
import javafx.scene.Scene;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class Court {
    public long time;//secondes actuelle du jeu
    public double nowTimerA = 0;//pour prévoir la durée d'un pouvoir
    public double nowTimerB = 0;//idem mais pour le joueur B

    // instance parameters
    private final RacketController playerA, playerB, playerC, playerD,playerE;// player E deviendra un pbstacle dans une future MAJ
    private final double width, height; // m
    private final double racketSpeed = 500.0; // m/s//350
    private double ballRadius = 15.0; // m et est devenu non final pour pouvoir rendre la balle en invisible
    private final double ballPowerRadius = 75.0;//m
    // instance state
    private double racketA; // m
    private double racketB; // m
    private double racketC; // m
    private double racketE;
    private double racketD; // m
    private double ballX, ballY; // m
    private double ballSpeedX, ballSpeedY; // m
    private int scoreA = 0;
    private int scoreB = 0;
    private double racketSpeedA = 350.0; // m/s pour changer la vitesse d'une raquette pour les pouvoirs
    private double racketSpeedB = 350.0; // m/s pour changer la vitesse d'une raquette pour les pouvoirs
    private int scoreFinal = 5;
    private double racketSizeA; // m pour changer la taille d'une raquette pour les pouvoirs
    private double racketSizeB; //m pour changer la taille d'une raquette pour les pouvoirs
    private double racketSize; //m
    private RacketController.State botDirection;//direction du bot
    private double directionPoint;//coordonee en y ou la balle se dirige
    private boolean isBot;//pour savoir si on est en solo et qu'on a besoin d'un bot
    private boolean isFun = false;//pour savoir si on est dans le mode fun
    private double ballPowerX;//coordonnées du pouvoir en x
    private double ballPowerY;//coordonnées du pouvoir en y
    private RacketController.Power currentPower = RacketController.Power.IDLE;//pouvoir courant de la ballPower
    public boolean powerUsedA = false;//pour savoir quand les pouvoirs sont utilises
    public boolean powerUsedB = false;

    private Scene lostScene;
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

    public Court(RacketController playerA, RacketController playerB,RacketController playerC, RacketController playerD,
            double width, double height) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.playerC = playerC;
        this.playerD = playerD;
        this.playerE = null;
        this.width = width;
        this.height = height;
        
        reset();
    }

    public RacketController getPlayerA() {
        return playerA;
    }

    public RacketController getPlayerB() {
        return playerB;
    }

    public boolean getIsFun() {
        return isFun;
    }

    public void setIsFun(boolean b) {
        isFun = b;
    }

    public double getBallPowerRadius() {
        return ballPowerRadius;
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

    public double getRacketSizeA() {
        return racketSizeA;
    }

    public double getRacketSizeB() {
        return racketSizeB;
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

    public double getBallPowerX() {
        return ballPowerX;
    }

    public void setBallPowerX(double x) {
        ballPowerX = x;
    }

    public double getBallPowerY() {
        return ballPowerY;
    }

    public void setBallPowerY(double y) {
        ballPowerY = y;
    }

    public RacketController.Power getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(RacketController.Power p) {
        currentPower = p;
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

        private void direction2() { // pour le mode 2v2

         if (directionPoint < racketC*2 + (racketSize-20)/4){ // on enleve 20 car raquetsize est optimisée en fonction de la hauteur, 
                                                              //étant donné que la largeur est plus longue,
            botDirection = RacketController.State.GOING_LEFT; // celà crée un problème et une hitbox plus grande, 
                                                              //pour celà il faut donc baisser la hitbox d'une valeur brut

        }   
        else {
            if (directionPoint > racketC*2 + (racketSize-20)*3/4) {

                botDirection = RacketController.State.GOING_RIGHT;
        }
        else {
            botDirection = RacketController.State.IDLE;
            }
        }
    }

    public void update(double deltaT) {
        switch (playerA.getState()) {
            case GOING_UP:
                racketA -= racketSpeedA * deltaT;
                if (racketA < 0.0) racketA = 0.0;
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketA += racketSpeedA * deltaT;
                if (racketA + racketSizeA > height) racketA = height - racketSizeA;
                break;
            case PARALYSED:
                break;
        }
        if(!isBot){
            switch (playerB.getState()) {
            case GOING_UP:
                racketB -= racketSpeedB * deltaT;
                if (racketB < 0.0) racketB = 0.0;
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketB += racketSpeedB * deltaT;
                if (racketB + racketSizeB > height) racketB = height - racketSizeB;
                break;
            case PARALYSED:
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
                case PARALYSED://uniquement pour la gestion d'erreurs puisque il n'y a pas de pouvoirs dans ce mode
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
        if (isFun && Math.sqrt(Math.pow(ballX-ballPowerX,2)+Math.pow(ballY-ballPowerY,2)) <= ballPowerRadius+ballRadius) {//si on est dans le cercle de la ballePower
            if (ballPowerX != -1 && ballPowerY != -1) {
                //si la ballPower n'a pas déjà été prise
                if (ballSpeedX > 0) {//si la balle va de gauche à droite alors c le joueur A qui a le pouvoir
                    playerA.setPower(currentPower);
                    System.out.print("Player A got ");
                    System.out.println(currentPower);
                    switch (currentPower) {
                    case SMALLER: useCurrentPowerA();this.powerUsedA = true;break;
                    case SLOWER: useCurrentPowerA();this.powerUsedA = true;break;
                    default : break;
                    }
                }
                else {//idem si la balle va de droite à gauche
                    playerB.setPower(currentPower);
                    System.out.print("Player B got ");
                    System.out.println(currentPower);
                    switch (currentPower) {
                    case SMALLER: useCurrentPowerB();this.powerUsedB = true;break;
                    case SLOWER: useCurrentPowerB();this.powerUsedB = true;break;
                    default : break;
                    }
                }
                ballPowerY = -1;
                ballPowerX = -1;
                currentPower = null;
            }
        }
        if ((nextBallX < 0 && nextBallY > racketA && nextBallY < racketA + racketSizeA)
                || (nextBallX > width && nextBallY > racketB && nextBallY < racketB + racketSizeB)) {
            if (nextBallX > width) {
                if (playerB.getPower() == RacketController.Power.STRENGHTACTIVATED) {
                    ballSpeedX *= 2;
                }
                botDirection = RacketController.State.IDLE;
                ballSpeedX = -(ballSpeedX + 75); //25
                sound("RacketSound.wav");
            } // MAJ vitesse de la balle après avoir touché la raquette
            if (nextBallX < 0) {
                if (playerA.getPower() == RacketController.Power.STRENGHTACTIVATED) {
                    ballSpeedX *= 2;
                }
                ballSpeedX = -(ballSpeedX - 75);//25
                sound("RacketSound.wav");
            }
            if (nextBallX < 0 && playerA.getPower() == RacketController.Power.STRENGHTACTIVATED) {
                ballSpeedY = 0;
                playerA.setPower(RacketController.Power.IDLE);
            }
            else if (nextBallX > width && playerB.getPower() == RacketController.Power.STRENGHTACTIVATED) {
                ballSpeedY = 0;
                playerB.setPower(RacketController.Power.IDLE);
            }
            else if (ballSpeedY > 0) {
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
                    ballSpeedY = -(Math.abs(ballSpeedX) + (Math.abs(ballY - ((racketSize/2)+racketA))*7));
                }
                else {
                    ballSpeedY = -(Math.abs(ballSpeedX) + (Math.abs(ballY - ((racketSize/2)+racketB)))*7);
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
            case PARALYSED://uniquement pour la gestion d'erreurs puisque il n'y a pas de pouvoirs dans ce mode
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
            case PARALYSED://uniquement pour la gestion d'erreurs puisque il n'y a pas de pouvoirs dans ce mode
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
            case PARALYSED://uniquement pour la gestion d'erreurs puisque il n'y a pas de pouvoirs dans ce mode
                    break;
        }
    }
       
     //@return true if a player lost

     private boolean updateBall2(double deltaT) {

         // prochaine position de la balle 
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;

        // les lignes suivantes sont le cas où la balle rencontre un obstacle
        int x = new Random().nextInt(1,3);
        if (x == 2) x = -1;

        if ((nextBallY < 50 && nextBallX > racketC*2 -(racketSize-20) && nextBallX < racketC*2+(racketSize-20) ) ||
            (nextBallY > height - 50 && nextBallX > (racketD*2)-(racketSize-20)/2&& nextBallX < (racketD *2) + (racketSize-20) )){

           ballSpeedY = -ballSpeedY ;
            nextBallY = ballY + deltaT * ballSpeedY *1.5 ;
            ballSpeedX = - (ballSpeedX-25) * x;
        }

            else { // sinon on relance la partie et on augmente le score des joueur

                if (nextBallY < 50) { 
                    setScoreA(scoreA+1);
                    playerLost();
                    return true;
                } 
                else 
                    if (nextBallY > height-50) {
                    setScoreA(scoreA+1);
                    playerLost();
                    return true;
                    }
            }
        
            if ((nextBallX < 10 && nextBallY > racketA && nextBallY < racketA + racketSize) // si la balle touche le joueur A
                || (nextBallX > width +10 && nextBallY > racketB && nextBallY < racketB + racketSize)) { // si la balle touche le joueur B
                if (ballSpeedX > 0) {
                ballSpeedX = -(ballSpeedX + 25);
            } // MAJ vitesse de la balle après avoir touché la raquette
            else {
                ballSpeedX = -(ballSpeedX - 25);
            } // MAJ gauche> droite quand la vitesse est dans le négatif
                System.out.println("racket touched");
                if (ballSpeedY > 0) {
                    // ballY - ((racketsize/2)+ballX) //rapport entre le milieu de la raquette et la position de la balle
                if (nextBallX < 10) {
                    ballSpeedY = Math.abs(ballSpeedX) + (Math.abs(ballY - ((racketSize/2)+racketA))*7);
                }
                else {
                    ballSpeedY = Math.abs(ballSpeedX) + (Math.abs(ballY - ((racketSize/2)+racketB))*7);
                }
            }
            else {
                if (nextBallX < 10) {
                    ballSpeedY = -(Math.abs(ballSpeedX) + (Math.abs(ballY - ((racketSize/2)+racketA))*7));
                }
                else {
                    ballSpeedY = -(Math.abs(ballSpeedX) + (Math.abs(ballY - ((racketSize/2)+racketB)))*7);
                }
            }

            nextBallX = ballX + deltaT * ballSpeedX;
            }

        else 
            if (nextBallX < 10) {
                setScoreB(scoreB+1);
                playerLost();
                return true;  
            } 
        else
            if (nextBallX > width+10) {
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

    public void endPowerA() {
        switch (playerA.getPower()) {
        case STRENGHT : break;
        case STRENGHTACTIVATED : break;
        case ELECTRICAL : playerB.setState(RacketController.State.IDLE);break;
        case WIND : ballSpeedY = -ballSpeedY;break;
        case INVISIBLE : this.ballRadius = 15.0;break;
        case BIGGER : racketSizeA = racketSize;break;
        case SMALLER : racketSizeA = racketSize;break;
        case SLOWER : racketSpeedA = racketSpeed;break;
        case FASTER : racketSpeedA = racketSpeed;break;
        default : break;//pour la gestion d'erreurs uniquement
        }
        nowTimerA = 0;
        playerA.setPower(RacketController.Power.IDLE);
    }

    public void endPowerB() {
        switch (playerB.getPower()) {
        case STRENGHT : break;
        case STRENGHTACTIVATED : break;
        case ELECTRICAL : playerA.setState(RacketController.State.IDLE);break;
        case WIND : break;
        case INVISIBLE : this.ballRadius = 15.0;break;
        case BIGGER : racketSizeB = racketSize;break;
        case SMALLER : racketSizeB = racketSize;break;
        case SLOWER : racketSpeedB = racketSpeed;break;
        case FASTER : racketSpeedB = racketSpeed;break;
        default : break;//pour la gestion d'erreurs uniquement
        }
        nowTimerB = 0;
        playerB.setPower(RacketController.Power.IDLE);
    }

    public void useCurrentPowerA() {
        //le player A active son pouvoir
        switch (playerA.getPower()) {
        case STRENGHT : playerA.setPower(RacketController.Power.STRENGHTACTIVATED);break;
        case ELECTRICAL : playerB.setState(RacketController.State.PARALYSED);this.nowTimerA = this.time+1.5e+9;break;
        case WIND : ballSpeedY = -ballSpeedY;playerA.setPower(RacketController.Power.IDLE);break;
        case INVISIBLE : this.ballRadius = 0;this.nowTimerA = this.time+2.0e+9;break;
        case BIGGER : racketSizeA += 300;this.nowTimerA = this.time+2.0e+9;break;
        case SMALLER : racketSizeB /= 4;this.nowTimerA = this.time+3.0e+9;break;
        case SLOWER : racketSpeedB /= 4;this.nowTimerA = this.time+3.0e+9;break;
        case FASTER : racketSpeedA *= 6;this.nowTimerA = this.time+5.0e+9;break;
        default : break;//pour la gestion d'erreurs et pour le pouvoir STRENGHTACTIVATED
        }
    }

    public void useCurrentPowerB() {
        switch (playerB.getPower()) {
        case STRENGHT : playerB.setPower(RacketController.Power.STRENGHTACTIVATED);break;
        case ELECTRICAL : playerA.setState(RacketController.State.PARALYSED);this.nowTimerB = this.time+1.5e+9;break;
        case WIND : ballSpeedY = -ballSpeedY;playerB.setPower(RacketController.Power.IDLE);break;
        case INVISIBLE : this.ballRadius = 0;this.nowTimerB = this.time+2.0e+9;break;
        case BIGGER : racketSizeB += 300;this.nowTimerB = this.time+2.0e+9;break;
        case SMALLER : racketSizeA /= 4;this.nowTimerB = this.time+3.0e+9;break;
        case SLOWER : racketSpeedA /= 4;this.nowTimerB = this.time+3.0e+9;break;
        case FASTER : racketSpeedB *= 6;this.nowTimerB = this.time+5.0e+9;break;
        default : break;//pour la gestion d'erreurs et pour tous le pouvoir STRENGHTACTIVATED
        }
    }

    public void reset() {
        this.racketA = height / 2;
        this.racketB = height / 2;
        this.racketC = width / 2;
        this.racketE = width / 2;
        this.racketD = height / 2;
        endPowerA();
        endPowerB();
        nowTimerA = 0;
        nowTimerB = 0;

        playerA.setPower(RacketController.Power.IDLE);
        playerB.setPower(RacketController.Power.IDLE);
        currentPower = null;
        ballPowerY = -1;
        ballPowerX = -1;

        this.ballSpeedX = -550.0;
        this.ballSpeedY = 0;//rajouter un composant aléatoire pour la direction de l'engagement si on est en solo ou non

        this.ballX = width / 2;
        this.ballY = height / 2;
        this.racketSize = 150.0;
        this.racketSizeA = 150.0;
        this.racketSizeB = 150.0;
        botDirection = RacketController.State.IDLE;//reset de la direction du bot pour recalculer la trajectoire
    }
}