package fr.spacepong.server.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import fr.spacepong.server.Position;
import fr.spacepong.server.State;
import fr.spacepong.server.runner.Game;

public class Requests {

	private ArrayList<Game> gameList = new ArrayList<Game>(); // contient la liste des parties en cours
	private static int NB_Players = 0; // nous servira à calculer les ID joueurs pour s'assurer qu'ils soient uniques
	
	private Network n;
	
	public Requests (Network n) {
		this.n = n;
	}
	
	// Recherche si une partie contient un joueur et la renvoie. Renvoie null sinon
	private Game searchGame(String ip, int id) { 
		for (Game g : gameList) {
			if (g.getPlayer1() != null && (ip.equals(g.getPlayer1().get("ip")) && id == (int) g.getPlayer1().get("id")) ||
					(g.getPlayer2() != null && (ip.equals(g.getPlayer2().get("ip")) && id == (int) g.getPlayer2().get("id")))) {
				return g;
			}
		}
		return null;
	}
	
	// Traitement> réception d'un message
	// Un message est composé de cette façon :
	// {IP_EMETTRICE; PORT; ID_MESSAGE; ID_JOUEUR; MESSAGE; ARGUMENT1; ARGUMENT2; ARGUMENT3; 0 (= pas d'accusé réception) ou 1 (= renvoyer accusé de réception)}
	public void onMessageReceived(String[] resp) {		
		HashMap<String, Object> rm = new HashMap<>(); // rm pour Response Map
		rm.put("ip", resp[0].substring(1));
		rm.put("port", Integer.valueOf(resp[1]));
		rm.put("idMsg", Integer.valueOf(resp[2]));
		rm.put("idPlayer", Integer.valueOf(resp[3]));
		rm.put("msg", resp[4]);
		rm.put("arg1", Double.valueOf(resp[5]));
		rm.put("arg2", Double.valueOf(resp[6]));
		rm.put("arg3", resp[7]);
		rm.put("AR", Integer.valueOf(resp[8]));
				
		if ((int) rm.get("AR") == 1) { // on demande d'émettre un AR
			sendMessage((String) rm.get("ip"), (int) rm.get("port"), (int) rm.get("idPlayer"), "AR", Double.valueOf((int)rm.get("idMsg")), 0.0, "null", false);
		}
		
		// si un joueur a un ID négatif (= pas d'id) on lui en transmet un 
		if ((int) rm.get("idPlayer") < 0) {
			int id = NB_Players;
			boolean received = sendMessage((String) rm.get("ip"), (int) rm.get("port"), (int) rm.get("idPlayer"), "SET_ID", Double.valueOf(id), 0.0, "null", true);
			if (received) {
				rm.put("idPlayer", id);
				NB_Players++;
				
			}
			else return;
		}
		
		Game g = searchGame((String) rm.get("ip"), (int) rm.get("idPlayer"));
		
		switch((String) rm.get("msg")) {
		case "PLAYER_JOINED": // Un joueur a rejoint le jeu
			System.out.println("[REQUEST] Un joueur a rejoint le jeu");
			if (g != null) return;
			HashMap<String, Object> playerInfo = new HashMap<>();
			playerInfo.put("ip", rm.get("ip"));
			playerInfo.put("port", rm.get("port"));
			playerInfo.put("pseudo", rm.get("arg3"));
			playerInfo.put("id", rm.get("idPlayer"));
			playerInfo.put("racket", new Position(0, Game.height / 2));
			playerInfo.put("score", 0);
									
			if (gameList.isEmpty() == false) {
				Game game = gameList.get(gameList.size() - 1); 
				if (game.getState() == State.WAITING) { // on regarde si la dernière partie de la liste est en attente de joueurs
					if (game.getPlayer1() == null) {						
						game.setPlayer1(playerInfo);
						if (game.getPlayer2() != null) {
							sendMessage((String) game.getPlayer2().get("ip"), (int) game.getPlayer2().get("port"), (int) game.getPlayer2().get("id"), "SET_PSEUDO_ADV", 0.0, 0.0, (String) game.getPlayer1().get("pseudo"), false);
							sendMessage((String) rm.get("ip"), (int) rm.get("port"), (int) rm.get("idPlayer"), "SET_PSEUDO_ADV", 0.0, 0.0, (String) game.getPlayer2().get("pseudo"), false);
						}
					}
					else {
						game.setPlayer2(playerInfo);						
						if (game.getPlayer1() != null) {
							sendMessage((String) game.getPlayer1().get("ip"), (int) game.getPlayer1().get("port"), (int) game.getPlayer1().get("id"), "SET_PSEUDO_ADV", 0.0, 0.0, (String) game.getPlayer2().get("pseudo"), false);
							sendMessage((String) rm.get("ip"), (int) rm.get("port"), (int) rm.get("idPlayer"), "SET_PSEUDO_ADV", 0.0, 0.0, (String) game.getPlayer1().get("pseudo"), false);
						
						}
					}
					game.updateState();
					if (game.getState() == State.READY) {
						Thread t = new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								game.gameStart();
							}
							
						});
						t.start();
					}
					return;
				}
			}
			Game newGame = new Game(playerInfo, null, n, this); // on crée une nouvelle partie
			gameList.add(newGame);
			break;
			
		case "PLAYER_QUITED": // Un joueur a quitté le jeu			
			if (g == null) return;
			// On vérifie que les adresses IP et identifiants concordent avec une partie existante
			if (rm.get("ip").equals(g.getPlayer1().get("ip")) && (int) rm.get("idPlayer") == (int) g.getPlayer1().get("id")) {
				System.out.println("[REQUEST] Le joueur 1 a quitté le jeu [#" + g.getGameId() + "]");
				g.setPlayer1(null);
				g.updateState();
				return;
			}
			else if (rm.get("ip").equals(g.getPlayer2().get("ip")) && (int) rm.get("idPlayer") == (int) g.getPlayer2().get("id")) {
				System.out.println("[REQUEST] Le joueur 2 a quitté le jeu [#" + g.getGameId() + "]");
				g.setPlayer2(null);
				g.updateState();
				return;
			}
			if (g.getPlayer1() == null && g.getPlayer2() == null) removeParty(g);
			break;
			
		case "RACKET_UPDATE": // un joueur fait bouger sa raquette
			if (g == null) return;
			
			if (rm.get("ip").equals(g.getPlayer1().get("ip")) && (int) rm.get("idPlayer") == (int) g.getPlayer1().get("id")) {
				g.updateRacketPos(1, (Double) rm.get("arg2"));
			}
			else if (rm.get("ip").equals(g.getPlayer2().get("ip")) && (int) rm.get("idPlayer") == (int) g.getPlayer2().get("id")) {
				g.updateRacketPos(2, (Double) rm.get("arg2"));
			}
			break;
		}
		
		
	}
	
	// Envoi d'un message aux joueurs
	public boolean sendMessage(String ip, int port, int id, String msg, Double arg1, Double arg2, String arg3, boolean AR) {
		try {
			InetAddress dst = InetAddress.getByName(ip);
			msg += " " + arg1 + " " + arg2 + " " + arg3;
			if (AR == false) n.send(dst, port, String.valueOf(id), msg);
			else {
				boolean AR_received = n.sendAR(dst, port, String.valueOf(id), msg);
				if (AR_received) System.out.println("[REQUEST] AR reçu");
				return AR_received;
			}
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("[REQUEST] Erreur : Adresse IP inconnue");
			e.printStackTrace();
			return false;
		}
	}

	public void removeParty(Game game) {
		// TODO Auto-generated method stub
		gameList.remove(game);
		System.out.println("[REQUEST] Partie supprimée");
	}
	
}
