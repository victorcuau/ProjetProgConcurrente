package jus.poc.prodcons.v1;

public class Producteur extends Thread {
	
	Message ptitmot;
	ProdConsBuffer buffer;
	int Mavg;
	
	Producteur(Message m, ProdConsBuffer buffer, int Mavg){
		this.ptitmot = m;
		this.buffer = buffer;
		this.Mavg = Mavg;
	}

	public void run(){
		try {
			for (int i = 0 ; i < (int)(Math.random()*Mavg*2) ; i++) {
				ptitmot = new Message();
				buffer.put(ptitmot);
			}
		} catch (InterruptedException e) {
				e.printStackTrace();
		}

	}
	
}