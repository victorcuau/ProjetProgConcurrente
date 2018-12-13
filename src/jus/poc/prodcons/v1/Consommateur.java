package jus.poc.prodcons.v1;

public class Consommateur extends Thread {
	
	ProdConsBuffer buffer;
	int Mavg;
	int numId;
	int ConsTime;
	
	Consommateur(ProdConsBuffer buffer, int Mavg, int numId, int ConsTime){
		this.buffer = buffer;
		this.Mavg = Mavg;
		this.numId = numId;
		this.ConsTime = ConsTime;
	}

	public void run(){
		try {
			while(true) {
				sleep((int)Math.random()*ConsTime*2);
				buffer.get();
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
}
