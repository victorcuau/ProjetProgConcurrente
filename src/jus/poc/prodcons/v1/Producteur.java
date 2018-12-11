package jus.poc.prodcons.v1;

public class Producteur extends Thread {
	
	Message ptitmot;
	
	Producteur(Message m){
		this.ptitmot = m;
	}

	public void run(ProdConsBuffer buffer, int Mavg){
		try {
			for (int i = 0 ; i < (int)(Math.random()*Mavg) ; i++) {
				ptitmot = new Message();
				buffer.put(ptitmot);
			}
		} catch (InterruptedException e) {
				e.printStackTrace();
		}

	}
	
}