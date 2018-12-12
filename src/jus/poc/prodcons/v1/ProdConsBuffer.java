package jus.poc.prodcons.v1;

import java.awt.Color;

public class ProdConsBuffer implements IProdConsBuffer {

	static int tete; // Indique la position du prochain élement à récupérer (FIFO)
	static int queue; // Indique la position où écrire la prochaine fois (FIFO)
	static int nbElem; // Indique le nombre de messages actuellement dans le buffer

	Message buffer[];
		
	ProdConsBuffer(int BufSz){
		buffer = new Message[BufSz];
	}
	
		/**
		* put m in the prodcons buffer
		**/
		public synchronized void put(Message m) throws InterruptedException {
			while(!(nbElem < buffer.length)) {
				wait();
			}
			buffer[queue] = m;
			System.out.println("Production : " + buffer[queue].content);
			queue = (queue+1)%buffer.length;
			nbElem++;
			notifyAll(); // A réflechir
		}
		
		
		/**
		* retrieve a message from the prodcons buffer, following a fifo order
		**/
		public synchronized Message get() throws InterruptedException{
			while(!(nbElem>0)) {
				wait();
			}
			System.out.println("Consommation : " + buffer[tete].content);
			tete = (tete+1)%buffer.length;
			nbElem--;
			notifyAll(); // A réflechir
			return buffer[tete]; // Pas de sens mais besoin d'un return
		}
		
		
		/**
		* returns the number of messages currently available in the prodcons buffer
		**/
		public int nmsg() {
			return nbElem;
		}
	
}
