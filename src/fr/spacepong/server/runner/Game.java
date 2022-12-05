package fr.spacepong.server.runner;

import java.util.HashMap;

import fr.spacepong.server.Position;
import fr.spacepong.server.Sens;
import fr.spacepong.server.State;
import fr.spacepong.server.network.Network;
import fr.spacepong.server.network.Requests;

public class Game {
	
	public static int NB_GAMES;
	private int game_id;
	private HashMap<String, Object> player1, player2; // informations des deux joueurs (adresse ip, port, identifiant, position raquette, score) 
	
	private Position ball; // position de la balle pour le serveur et vue du joueur 1
	
	private Position ballP2; // position de la balle vue du joueur 2 (miroir)
	
	private double ballSpeed; // vitesse de la balle
	private double ballRadius;
	private Sens sens = Sens.BAS_DROIT; // sens de la balle (par défaut, elle va vers le coin droit en bas = true)
	
	
	private Position upWall, downWall; // position des murs en haut et en bas
	private double racketSize; // taille raquette
	
	/* Ces valeurs correspondent à une résolution de 1360x768. 
	 * Pour que le jeu s'éxecute correctement chez le client,
	 * on multipliera width & height par un coefficient multiplicateur (scale)
	 * si la résolution est différente.
	 */
	public static final int width = 1920; // taille de la représentation du point de vue du serveur
	public static final int height = 1080; // taille de la représentation du point de vue du serveur
		
	private State gameState = State.WAITING; // statut de la partie
	
	private Requests r; // nous servira à émettre des requêtes
		
	public Game(HashMap<String, Object> p1, HashMap<String, Object> p2, Network n, Requests r) {
		game_id = NB_GAMES;
		NB_GAMES++;
		
		player1 = p1;
		player2 = p2;
						
		// On place la balle et la balle miroir au milieu
		ball = new Position (width /  2, height / 2);
		ballP2 = new Position (width /  2, height / 2);
		
		// Vitesse et rayon initiaux
		ballSpeed = 0.005;
		ballRadius = 15.0;
		
		// On place les murs du haut et du bas
		upWall = new Position(0, 20);
		downWall = new Position(0, height - 20);
		
		racketSize = 200; // on suppose qu'une raquette a une taille de départ de 150 px (on multipliera également par le coef. mult. chez le client).
		
		// On instancie les requêtes
		this.r = r;
		
		// On met à jour le statut au cas où la partie est prête à démarrer
		updateState();
		
		System.out.println("Partie créée [#" + game_id + "]");
	}
	
	// Fonction lancée au démarrage de la partie	
	public void gameStart() {
		
		// On met bien les raquettes au milieu
		updateRacketPos(1, Double.valueOf(height / 2));
		updateRacketPos(2, new Position(width - 40, height / 2));		
					
		System.out.println("Partie démarrée [#" + game_id + "]");
		gameState = State.PLAYING; // la partie a commencé, on change donc le statut
		
		while(gameState != State.FINISHED) { // tant que la partie n'est pas finie / interrompue			
			Position racketA = (Position) player1.get("racket");
			Position racketB = (Position) player2.get("racket");

			// On anime la balle selon son sens (et on fait une MAJ du miroir)
			if (sens == Sens.BAS_DROIT) {
				ball.setX(ball.getX() + ballSpeed);
				ball.setY(ball.getY() + ballSpeed);

				ballP2.setX(ballP2.getX() - ballSpeed);
				ballP2.setY(ballP2.getY() + ballSpeed);
			}
			else if (sens == Sens.HAUT_DROIT) {
				ball.setX(ball.getX() + ballSpeed);
				ball.setY(ball.getY() - ballSpeed);

				ballP2.setX(ballP2.getX() - ballSpeed);
				ballP2.setY(ballP2.getY() - ballSpeed);
			}
			else if (sens == Sens.BAS_GAUCHE) {
				ball.setX(ball.getX() - ballSpeed);
				ball.setY(ball.getY() + ballSpeed);

				ballP2.setX(ballP2.getX() + ballSpeed);
				ballP2.setY(ballP2.getY() + ballSpeed);
			}
			else if (sens == Sens.HAUT_GAUCHE) {
				ball.setX(ball.getX() - ballSpeed);
				ball.setY(ball.getY() - ballSpeed);

				ballP2.setX(ballP2.getX() + ballSpeed);
				ballP2.setY(ballP2.getY() - ballSpeed);
			}

			// on envoie le message réseau de la pos. de la balle actualisée
			r.sendMessage((String) player1.get("ip"), (int) player1.get("port"), (int) player1.get("id"), "BALL_UPDATE", ball.getX(), ball.getY(), "null", false);
			r.sendMessage((String) player2.get("ip"), (int) player2.get("port"), (int) player2.get("id"), "BALL_UPDATE", ballP2.getX(), ballP2.getY(), "null", false);

			// Si la balle touche une raquette on le signale aux joueurs et la balle accélère
			if ((ball.getX() - ballRadius <= racketA.getX() + 10 && ball.getY() >= racketA.getY() && ball.getY() <= racketA.getY() + racketSize) ) { // balle touche la raquette gauche
				if (sens == Sens.BAS_GAUCHE) sens = Sens.BAS_DROIT;
				else if (sens == Sens.HAUT_GAUCHE) sens = Sens.HAUT_DROIT;

				r.sendMessage((String) player1.get("ip"), (int) player1.get("port"), (int) player1.get("id"), "TOUCHED_RACKET", 0.0, 0.0, "null", false);
				r.sendMessage((String) player2.get("ip"), (int) player2.get("port"), (int) player2.get("id"), "TOUCHED_RACKET", 0.0, 0.0, "null", false);

				ballSpeed += 0.002;
			}
			else if ((ball.getX() + ballRadius >= racketB.getX() - 10 && ball.getY() >= racketB.getY() && ball.getY() <= racketB.getY() + racketSize)) { // balle touche raquette droite
				if (sens == Sens.BAS_DROIT) sens = Sens.BAS_GAUCHE;
				else if (sens == Sens.HAUT_DROIT) sens = Sens.HAUT_GAUCHE;

				r.sendMessage((String) player1.get("ip"), (int) player1.get("port"), (int) player1.get("id"), "TOUCHED_RACKET", 0.0, 0.0, "null", false);
				r.sendMessage((String) player2.get("ip"), (int) player2.get("port"), (int) player2.get("id"), "TOUCHED_RACKET", 0.0, 0.0, "null", false);

				ballSpeed += 0.002;
			} // Si la balle touche un mur on le signale aussi aux joueurs, mais sans accélération
			else if ( (ball.getX() >= racketA.getX() && ball.getX() <= racketB.getX() && ball.getY() <= upWall.getY()) // la balle touche le mur du haut
					|| (ball.getX() >= racketA.getX() && ball.getX() <= racketB.getX() && ball.getY() >= downWall.getY())) { // la balle touche le mur du bas
				if (sens == Sens.BAS_DROIT) sens = Sens.HAUT_DROIT;
				else if (sens == Sens.HAUT_DROIT) sens = Sens.BAS_DROIT;
				else if (sens == Sens.BAS_GAUCHE) sens = Sens.HAUT_GAUCHE;
				else if (sens == Sens.HAUT_GAUCHE) sens = Sens.BAS_GAUCHE;

				r.sendMessage((String) player1.get("ip"), (int) player1.get("port"), (int) player1.get("id"), "TOUCHED_WALL", 0.0, 0.0, "null", false);
				r.sendMessage((String) player2.get("ip"), (int) player2.get("port"), (int) player2.get("id"), "TOUCHED_WALL", 0.0, 0.0, "null", false);
			}

			// Si la balle sort du terrain, un point est marqué : on le signale au joueur
			else if ((ball.getX() < 0)
					|| ball.getX() > width) { // la balle sort du terrain
				sens = Sens.BAS_DROIT;
				if (ball.getX() <= 0) { // le joueur 2 marque un point	
					player2.put("score", (int) player2.get("score") + 1);						
					onPoint();						
					updateState();
					System.out.println("Joueur 2 | +1 point [#" + game_id + "]");
				}
				else if (ball.getX() >= width) { // le joueur 1 marque un point
					player1.put("score", (int) player1.get("score") + 1);
					onPoint();
					updateState();
					System.out.println("Joueur 1 | +1 point [#" + game_id + "]");
				}

			}

		}
		
		if (gameState == State.FINISHED) {
			// la partie est finie car l'un des joueurs a gagné
			if (player1 != null && player2 != null) {
				if ((int) player1.get("score") > (int) player2.get("score")) {
					System.out.println("Vainqueur : joueur 1 [#" + game_id + "]");
				}
				else if ((int) player1.get("score") < (int) player2.get("score")){
					System.out.println("Vainqueur : joueur 2 [#" + game_id + "]");
				}
				else {
					System.out.println("Match nul [#" + game_id + "]");
				}
			}			
			r.removeParty(this);
		}		
	}
	
	
	// Méthode éxecutée lorsqu'un joueur marque un point
	private void onPoint() {
		// on réinitialise la pos. de la balle et du miroir, ainsi que sa vitesse
		ball.setX(width / 2);
		ball.setY(height / 2);
		
		ballP2.setX(width / 2);
		ballP2.setY(height / 2);
		
		ballSpeed = 0.005;
		
		// On remet les raquettes au milieu
		updateRacketPos(1, Double.valueOf(height / 2));
		updateRacketPos(2, Double.valueOf(height / 2));
		
		// On signale aux joueurs que la raquette est réinitialisée et que la balle l'est également
		
		r.sendMessage((String) player1.get("ip"), (int) player1.get("port"), (int) player1.get("id"), "RESET_RACKET", ((Position) player1.get("racket")).getY(), 0.0, "null", false);
		r.sendMessage((String) player2.get("ip"), (int) player2.get("port"), (int) player2.get("id"), "RESET_RACKET", ((Position) player1.get("racket")).getY(), 0.0, "null", false);
				
		r.sendMessage((String) player1.get("ip"), (int) player1.get("port"), (int) player1.get("id"), "BALL_UPDATE", ball.getX(), ball.getY(), "null", false);
		r.sendMessage((String) player2.get("ip"), (int) player2.get("port"), (int) player2.get("id"), "BALL_UPDATE", ballP2.getX(), ballP2.getY(), "null", false);
		
		// On envoie aux joueurs le score
		r.sendMessage((String) player1.get("ip"), (int) player1.get("port"), (int) player1.get("id"), "SCORE_UPDATE", Double.valueOf((int) player1.get("score")), Double.valueOf((int) player2.get("score")), "null", false);
		r.sendMessage((String) player2.get("ip"), (int) player2.get("port"), (int) player2.get("id"), "SCORE_UPDATE", Double.valueOf((int) player2.get("score")), Double.valueOf((int) player1.get("score")), "null", false);
		
	}
	
	public void updateState() {
		if (player1 == null || player2 == null) {
			if (gameState == State.READY) gameState = State.WAITING;
			else if (gameState == State.PLAYING) gameState = State.FINISHED;
		}
		else if (player1 != null && player2 != null) {
			if (gameState == State.WAITING) gameState = State.READY;
			else if (gameState == State.READY) gameState = State.PLAYING;
			else if (gameState == State.PLAYING && ((int) player1.get("score") == 5
					|| (int) player2.get("score") == 5)) {
					gameState = State.FINISHED;			
			}
		}
		
		if (player1 != null) {
			r.sendMessage((String) player1.get("ip"), (int) player1.get("port"), (int) player1.get("id"), gameState.toString(), 0.0, 0.0, "null", false);
			
		}
		if (player2 != null) {
			r.sendMessage((String) player2.get("ip"), (int) player2.get("port"), (int) player2.get("id"), gameState.toString(), 0.0, 0.0, "null", false);
			
		}
	}
		
	// on met à jour la position raquette avec un objet Position
	public void updateRacketPos(int pl, Position pos) {
		switch(pl) {
		case 1:
			player1.put("racket", pos);
			break;
		case 2:
			player2.put("racket", pos);
			break;
		}
	}
	
	// on met à jour les coordonnées Y de la raquette en envoyant un message réseau aux joueurs
	public void updateRacketPos(int pl, Double pos) {
		switch(pl) {
		case 1:
			player1.put("racket", new Position (40, pos));
			r.sendMessage((String) player2.get("ip"), (int) player2.get("port"), (int) player2.get("id"), "RACKET_POS", pos, 0.0, "null", false);
			break;
		case 2:
			player2.put("racket", new Position(width - 40, pos));
			r.sendMessage((String) player1.get("ip"), (int) player1.get("port"), (int) player1.get("id"), "RACKET_POS", pos, 0.0, "null", false);
			break;
		}
	}
	
	public State getState() {
		return gameState;
	}
	
	public HashMap<String, Object> getPlayer1() {
		return player1;
	}
	
	public void setPlayer1(HashMap<String, Object> info) {
		player1 = info;
		System.out.println("MAJ Info : Joueur 1 [#" + game_id + "]");
	}
	
	public HashMap<String, Object> getPlayer2() {
		return player2;
	}
	
	public void setPlayer2(HashMap<String, Object> info) {
		player2 = info;
		System.out.println("MAJ Info : Joueur 2 [#" + game_id + "]");
	}
	
	public int getGameId() {
		return game_id;
	}
	
}
