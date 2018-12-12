package jus.poc.prodcons.v1;

public class Consommateur extends Thread {
	
	ProdConsBuffer buffer;
	int Mavg;
	
	Consommateur(ProdConsBuffer buffer, int Mavg){
	}

	public void run(){
		try {
			for (int i = 0 ; i < (int)(Math.random()*Mavg*2) ; i++) {
				buffer.get();
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
}
