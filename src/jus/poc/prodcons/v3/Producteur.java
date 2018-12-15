package jus.poc.prodcons.v3;

public class Producteur extends Thread {
	
	Message ptitmot;
	ProdConsBuffer buffer;
	int numId; // Identifiant du producteur
	long ProdTime; // Temps moyen de production d'un message
	int nmes; // Nombre de messages à produire
	int nbC; // Nombre de thread consommateurs
	int nbP; // Nombre de thread producteurs
	
	Producteur(Message m, ProdConsBuffer buffer, int numId, int ProdTime, int nmes, int nbC, int nbP){
		this.ptitmot = m;
		this.buffer = buffer;
		this.numId = numId;
		this.ProdTime = ProdTime;
		this.nmes = nmes;
		this.ptitmot.idParent = this.numId;
		this.nbC = nbC;
		this.nbP = nbP;
	}

	public void run(){
		try {
			for (int i = 0 ; i < nmes ; i++) {
				ptitmot.nbExemplaires = (int)(Math.random()*((nbC/nbP)-1)+1); // Formule qui garantit un nombre d'exemplaires adapté
				buffer.put(ptitmot);
				sleep((int)Math.random()*ProdTime); // Temps de production du message
			}
		} catch (InterruptedException e) {
				e.printStackTrace();
		}

	}
	
}