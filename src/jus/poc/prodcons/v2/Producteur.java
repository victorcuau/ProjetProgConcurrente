package jus.poc.prodcons.v2;

public class Producteur extends Thread {
	
	Message ptitmot;
	ProdConsBuffer buffer;
	int Mavg;
	int numId;
	long ProdTime;
	int nmes;
	
	Producteur(Message m, ProdConsBuffer buffer, int Mavg, int numId, int ProdTime, int nmes){
		this.ptitmot = m;
		this.buffer = buffer;
		this.Mavg = Mavg;
		this.numId = numId;
		this.ProdTime = ProdTime;
		this.nmes = nmes;
		this.ptitmot.idParent = this.numId;
	}

	public void run(){
		try {
			for (int i = 0 ; i < nmes ; i++) {
				sleep((int)Math.random()*ProdTime*2);
				buffer.put(ptitmot);
			}
		} catch (InterruptedException e) {
				e.printStackTrace();
		}

	}
	
}