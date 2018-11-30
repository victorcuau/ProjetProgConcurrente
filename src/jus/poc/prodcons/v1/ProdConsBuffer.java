package jus.poc.prodcons.v1;

public class ProdConsBuffer {

	Message buffer[BufSz];
	static int tete; // Indique la position du prochain élement à récupérer (FIFO)
	static int queue; // Indique la position où écrire la prochaine fois (FIFO)
	static int nbElem; // Indique le nombre de messages actuellement dans le buffer
	
	public interface IProdConsBuffer {
		
		/**
		* put m in the prodcons buffer
		**/
		public void put(Message m) throws InterruptedException {
			while(!(nbElem < BufSz)) {
				wait();
			}
			buffer[queue] = m;
			queue = (queue+1)%BufSz;
			nbElem++;
			notifyAll(); // A réflechir
		}
		
		
		/**
		* retrieve a message from the prodcons buffer, following a fifo order
		**/
		public Message get() throws InterruptedException{
			while(!(nbElem>0)) {
				wait();
			}
			printf(buffer[tete]);
			tete = (tete+1)%BufSz;
			nbElem--;
			notifyAll(); // A réflechir
		}
		
		
		/**
		* returns the number of messages currently available in the prodcons buffer
		**/
		public int nmsg() {
			return nbElem;
		}
		
		}
	
}
