package jus.poc.prodcons.v2;

public class Producteur extends Thread {
	
	Message ptitmot;
	ProdConsBuffer buffer;
	int numId; // Identifiant du producteur
	long ProdTime; // Temps moyen de production d'un message
	int nmes; // Nombre de messages à produire
	
	Producteur(Message m, ProdConsBuffer buffer, int numId, int ProdTime, int nmes){
		this.ptitmot = m;
		this.buffer = buffer;
		this.numId = numId;
		this.ProdTime = ProdTime;
		this.nmes = nmes;
		this.ptitmot.idParent = this.numId;
	}

	public void run(){
		try {
			for (int i = 0 ; i < nmes ; i++) {
				sleep((int)Math.random()*ProdTime*2); // Temps de production du message
				buffer.put(ptitmot);
			}
		} catch (InterruptedException e) {
				e.printStackTrace();
		}

	}
	
}