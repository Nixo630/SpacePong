package network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import model.OnlineCourt;

public class Requests {
	
	public static int NB_Messages = 0; // nous servira à calculer l'ID message 
	
	private Network n;
	private OnlineCourt oc;
	
	private String serverIP;
	
	public Requests (Network n, OnlineCourt oc, String serverIP) {
		this.n = n;
		this.oc = oc;
		this.serverIP = serverIP;
	}
	
	public void setOnlineCourt(OnlineCourt oc) {
		this.oc = oc;
	}
		
	// Traitement> réception d'un message
	// Un message est composé de cette façon :
	// {ID_MESSAGE; IP_EMETTRICE; ID_JOUEUR; MESSAGE; ARGUMENT1; ARGUMENT2; 0 (= pas d'accusé réception) ou 1 (= renvoyer accusé de réception)}
	public void onMessageReceived(String[] resp) {
		HashMap<String, Object> rm = new HashMap<>(); // rm pour Response Map
		rm.put("ip", resp[0]);
		rm.put("idMsg", Integer.valueOf(resp[1]));		
		rm.put("idPlayer", Integer.valueOf(resp[2]));
		rm.put("msg", resp[3]);
		rm.put("arg1", Double.valueOf(resp[4]));
		rm.put("arg2", Double.valueOf(resp[5]));
		rm.put("AR", Integer.valueOf(resp[6]));
		
		if ((int) rm.get("idPlayer") != oc.getIdPlayer()) return;
		
		switch((String) rm.get("msg")) {
		
		case "SET_ID":
			oc.setId((int) rm.get("arg1"));
		
		case "STATE_UPDATE":
			switch((String) rm.get("arg1")) {
			case "WAITING":
				// On affiche un écran indiquant que l'on est en attente de joueurs
			case "READY":
				// On affiche un écran indiquant que la partie va commencer
			case "PLAYING":
				// On retire tout écran pouvant être présent
			case "FINISHED":
				oc.finish();
				// On affiche un écran demandant à l'utilisateur s'il veut rejouer ou retourner à l'accueil
			}
			
		case "BALL_POS": // mettre à jour la position de la balle dans le jeu
			double ballPosX = Double.valueOf(((String) rm.get("arg1")));
			double ballPosY = Double.valueOf(((String) rm.get("arg2")));
			oc.updateBall(ballPosX, ballPosY);
			
		case "RACKET_POS": // mettre à jour la position de la rackette adversaire dans le jeu
			double y = Double.valueOf((String) rm.get("arg1"));
			oc.setRacketB(y);
			
		case "SCORE": // mettre à jour le score dans le jeu
			int[] score = { Integer.valueOf((String) rm.get("arg1")), 
					Integer.valueOf((String) rm.get("arg2")) };
			if(oc.getScoreA() != score[0]) oc.setScoreA(score[0]);
			if(oc.getScoreB() != score[1]) oc.setScoreB(score[1]);
		}
		
		if ((int) rm.get("AR") == 1) { // on demande d'émettre un AR
			sendMessage((int) rm.get("idPlayer"), "AR", (Double) rm.get("idMsg"), null, false);
		}
	}
	
	// Envoi d'un message au serveur
	public boolean sendMessage(int id, String msg, Double arg1, Double arg2, boolean AR) {
		try {
			InetAddress dst = InetAddress.getByName(serverIP);
			msg += " " + arg1 + " " + arg2;
			if (AR == false) n.send(dst, 57085, String.valueOf(id), msg);
			else {
				boolean AR_received = n.sendAR(dst, 57085, String.valueOf(id), msg);
				System.out.println("[REQUEST] AR reçu");
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
}
