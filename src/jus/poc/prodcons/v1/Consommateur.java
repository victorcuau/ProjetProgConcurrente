package jus.poc.prodcons.v1;

public class Consommateur extends Thread {
	
	Consommateur(){
	}

	public void run(ProdConsBuffer buffer, int Mavg){
		try {
		while(true) {
			buffer.get();
		}
	} catch (InterruptedException e) {
		e.printStackTrace();
}

	}
	
}
