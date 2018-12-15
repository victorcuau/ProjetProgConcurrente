package jus.poc.prodcons.v3;

public class ProdConsBuffer implements IProdConsBuffer {

	static int tete; // Indique la position du prochain élement à récupérer (FIFO)
	static int queue; // Indique la position où écrire la prochaine fois (FIFO)
	static int nbElem; // Indique le nombre de messages actuellement dans le buffer

	Message buffer[];
	int nbMsgEffectivementConsommes = 0;
	
	ProdConsBuffer(int BufSz){
		buffer = new Message[BufSz];
	}
	
		/**
		* put m in the prodcons buffer
		**/
		public synchronized void put(Message m) throws InterruptedException {
			while(!(nbElem < buffer.length)) { // Tant que le buffer est plein
				wait();
			}
			
			buffer[queue] = m; // Ecriture du message dans le buffer
			System.out.println("PRODUCTION (n°" + buffer[queue].idParent + ") : " + buffer[queue].content + " en " + buffer[queue].nbExemplaires + " exemplaire(s)");
			queue = (queue+1)%buffer.length;
			nbElem++;
			System.out.println("-> Il y a " + nmsg() + " message(s) dans le buffer");
			notifyAll(); 	// All car si le buffer est plein, on a absolument besoin
										// de réveiller un consommateur pour ne pas être bloqué
			
			/*
			 * Le thread producteur se bloque tant que tous les exemplaires du message
			 * qu'il a déposé n'ont pas été consommés
			 */
			while(m.nbExemplaires > 0) {
				wait();
			}
		}
		
		
		/**
		* retrieve a message from the prodcons buffer, following a FIFO order
		**/
		public synchronized Message get() throws InterruptedException{
			while(!(nbElem>0)) { // Tant que le buffer est vide
				wait();
			}
			Message temp = buffer[tete];
			temp.nbExemplaires --;
			System.out.println("CONSOMMATION : " + buffer[tete].content + " reste " + buffer[tete].nbExemplaires + " exemplaire(s)");
			if (temp.nbExemplaires < 1) {
				tete = (tete+1)%buffer.length;
				nbElem--;
				nbMsgEffectivementConsommes++;
				System.out.println("MESSAGE SUPPRIME");
				System.out.println("-> Il y a " + nmsg() + " message(s) dans le buffer");
				notifyAll(); 	// All car si le buffer est vide, on a absolument besoin
											// de réveiller un producteur pour ne pas être bloqué
			}
			else {
				/*
				 * Le thread consommateur se bloque tant que tous les exemplaires du message
				 * n'ont pas été consommés par d'autres consommateurs
				 */
				while(temp.nbExemplaires > 0) {
					wait();
				}
			}
			
			return temp;
		}
		
		
		/**
		* returns the number of messages currently available in the prodcons buffer
		**/
		public int nmsg() {
			return nbElem;
		}
	
}
