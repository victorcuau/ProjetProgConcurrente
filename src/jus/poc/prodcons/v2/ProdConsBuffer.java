package jus.poc.prodcons.v2;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer {

	static int tete; // Indique la position du prochain élement à récupérer (FIFO)
	static int queue; // Indique la position où écrire la prochaine fois (FIFO)
	static int nbElem; // Indique le nombre de messages actuellement dans le buffer

	Message buffer[];
	int nbMsgEffectivementConsommes = 0;
	
	Semaphore mutexProd = new Semaphore(1);
	Semaphore mutexCons = new Semaphore(1);
	Semaphore case_occ = new Semaphore(0);
	Semaphore case_libre;
	
	ProdConsBuffer(int BufSz){
		buffer = new Message[BufSz];
		case_libre = new Semaphore(buffer.length);
	}
	
		/**
		* put m in the prodcons buffer
		**/
		public synchronized void put(Message m) throws InterruptedException {
			case_libre.acquire();
			mutexProd.acquire();
			
			buffer[queue] = m;
			System.out.println("PRODUCTION (n°" + buffer[queue].idParent + ") : " + buffer[queue].content);
			queue = (queue+1)%buffer.length;
			nbElem++;
			System.out.println("-> Il y a " + nmsg() + " message(s) dans le buffer");
			
			mutexProd.release();
			case_occ.release();
		}
		
		
		/**
		* retrieve a message from the prodcons buffer, following a FIFO order
		**/
		public synchronized Message get() throws InterruptedException{
			case_occ.acquire();
			mutexCons.acquire();
			
			System.out.println("CONSOMMATION : " + buffer[tete].content);
			tete = (tete+1)%buffer.length;
			nbElem--;
			nbMsgEffectivementConsommes++;
			System.out.println("-> Il y a " + nmsg() + " message(s) dans le buffer");
			
			mutexCons.release();
			case_libre.release();
			
			return buffer[tete]; // Pas de sens ici mais besoin d'un return
		}
		
		
		/**
		* returns the number of messages currently available in the prodcons buffer
		**/
		public int nmsg() {
			return nbElem;
		}
	
}
