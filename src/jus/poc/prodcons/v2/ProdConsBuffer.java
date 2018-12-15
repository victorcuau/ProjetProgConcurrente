package jus.poc.prodcons.v2;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer {

	static int tete; // Indique la position du prochain élement à récupérer (FIFO)
	static int queue; // Indique la position où écrire la prochaine fois (FIFO)
	static int nbElem; // Indique le nombre de messages actuellement dans le buffer

	Message buffer[];
	int nbMsgEffectivementConsommes = 0;
	
	Semaphore mutex; // Pour assurer l'exclusion mutuelle (on n'aura pas de threads qui agiront en même temps)
	Semaphore notEmpty; // Pour s'assurer de l'attente des Consommateurs
	Semaphore notFull; // Pour s'assurer de l'attente des Producteurs
	
	ProdConsBuffer(int BufSz){
		buffer = new Message[BufSz];
		mutex = new Semaphore(1); // Initialisé à 1 pour permettre le passage d'un seul thread
		notEmpty = new Semaphore(0); // Initialisé à 0 car il n'y pas de message à get
		notFull = new Semaphore(BufSz); // Initialisé à BufSz car il y a Bufsize places où put des messages
	}
	
		/**
		* put m in the prodcons buffer
		**/
		public void put(Message m) throws InterruptedException {
			notFull.acquire();
			mutex.acquire();
			
			buffer[queue] = m; // Ecriture du message dans le buffer
			System.out.println("PRODUCTION (n°" + buffer[queue].idParent + ") : " + buffer[queue].content);
			queue = (queue+1)%buffer.length;
			nbElem++;
			System.out.println("-> Il y a " + nmsg() + " message(s) dans le buffer");
			
			mutex.release();
			notEmpty.release();
		}
		
		
		/**
		* retrieve a message from the prodcons buffer, following a FIFO order
		**/
		public Message get() throws InterruptedException{
			notEmpty.acquire(); // Tant que le buffer est vide, on bloque
			mutex.acquire(); // On s'assure du passage d'1 thread
			
			Message temp = buffer[tete];
			System.out.println("CONSOMMATION : " + temp.content);
			tete = (tete+1)%buffer.length;
			nbElem--;
			nbMsgEffectivementConsommes++;
			System.out.println("-> Il y a " + nmsg() + " message(s) dans le buffer");
			
			mutex.release(); // On libère cette partie du code pour un autre thread
			notFull.release(); // Un producteur peut donc accéder au buffer pour put un message
			
			return temp; // Pas de sens ici mais besoin d'un return
		}
		
		
		/**
		* returns the number of messages currently available in the prodcons buffer
		**/
		public int nmsg() {
			return nbElem;
		}
	
}
