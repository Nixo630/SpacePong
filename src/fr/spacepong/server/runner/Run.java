package fr.spacepong.server.runner;

import fr.spacepong.server.network.Network;
import fr.spacepong.server.network.Requests;

public class Run {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("[RUN] Démarrage du serveur Space Pong");
		Network n = new Network();
		Requests rq = new Requests(n);
		System.out.println("[RUN] Réseau prêt. Écoute messages OK. En attente de connexions...");
		while(true) {
			String[] response = n.listen(2);
			if (response != null) {
				rq.onMessageReceived(response);
			}
		}
	}

}
