package jus.poc.prodcons.v1;

public class Consommateur extends Thread {
	
	ProdConsBuffer buffer;
	int numId; // Identifiant du consommateur
	int ConsTime; // Temps moyen de consommation d'un message
	
	Consommateur(ProdConsBuffer buffer, int numId, int ConsTime){
		this.buffer = buffer;
		this.numId = numId;
		this.ConsTime = ConsTime;
		this.setDaemon(true); // Permet de s'assurer que les processus consommateurs s'arrêtent
	}

	public void run(){
		try {
			while(true) {
				sleep((int)Math.random()*ConsTime*2); // Temps de consommation du message
				buffer.get();
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
}
