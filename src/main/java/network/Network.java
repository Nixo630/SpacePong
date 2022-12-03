package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Network {
	
	public final int Lport; // port d'écoute
	public final static int Sport = 57086; // port d'envoi (serveur)
	
	private final static int size = 1024; // taille max. des informations reçues
	private byte buffer[] = new byte[size];
	private DatagramSocket ds; // utilisation de l'UDP
	
	// Classe contenant les méthodes "classiques" nécessaires au fonctionnement du réseau
	
	// Ouvre la communication sur le port 57086
	public Network() throws SocketException {
		
		ds = new DatagramSocket(0);
		Lport = ds.getLocalPort();
	}
	
	public String[] listen(int timeout) { // écoute sur un port pour un timeout défini (timeout 0 = infini)
		DatagramPacket packet = new DatagramPacket(buffer, size);
		try {
			ds.setSoTimeout(timeout);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ds.receive(packet);
			String[] strResponse = new String(packet.getData(), 0, packet.getLength()).split("_");
			String[] res = new String[strResponse.length + 1];
			res[0] = packet.getAddress().toString(); // la réponse contient en premier l'IP émettrice
			for (int i = 1; i < strResponse.length; i++) {
				res[i] = strResponse[i - 1]; // ensuite elle contient de façon scindée le message reçu
			}
			return res;
		}
		catch (SocketTimeoutException e){
			//System.out.println("Délai d'attente dépassé");
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur survenue lors de la réception du message");
			e.printStackTrace();
		}
		return null;
	}
	
	public void send(InetAddress ip, int p, String id, String s) { // envoi message sur réseau
		s = Requests.NB_Messages + " " + id + " " + s + " 0"; // on inclut l'ID message, l'ID joueur, le message, et pas d'accusé réception
		DatagramPacket packet = new DatagramPacket(s.getBytes(), s.length(),
				ip, p);
		try {
			ds.send(packet);
			Requests.NB_Messages++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur survenue lors de l'émission du message");
			e.printStackTrace();
		}
	}
	
	public boolean sendAR(InetAddress ip, int p, String id, String s) { // envoi message avec AR
		s = Requests.NB_Messages + " " + id + " " + s + " 1";
		DatagramPacket packet = new DatagramPacket(s.getBytes(), s.length(),
				ip, p);
		
		try {
			for (int i = 0; i < 5; i++) { // 5 essais pour recevoir l'AR avec 100 de timeout
				ds.send(packet);
				String[] accRec = listen(100);
				if (accRec != null && accRec[3].equals("AR")) {
					return true;
				}
			}
			return false;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur survenue lors de l'émission du message");
			e.printStackTrace();
			return false;
		}
	}	

}
