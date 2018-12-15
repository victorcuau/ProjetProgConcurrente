package jus.poc.prodcons.v2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

public class TestProdCons {

	public static void start() throws InvalidPropertiesFormatException, IOException {

		// Importation des propriétés depuis le .xml
		Properties properties = new Properties();
		properties.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("options.xml"));
		int nbP = Integer.parseInt(properties.getProperty("nbP")); // Nb de thread producteurs
		int nbC = Integer.parseInt(properties.getProperty("nbC")); // Nb de thread consommateurs
		int BufSz = Integer.parseInt(properties.getProperty("BufSz")); // Taille du buffer
		int ProdTime = Integer.parseInt(properties.getProperty("ProdTime")); // Temps moyen de production
		int ConsTime = Integer.parseInt(properties.getProperty("ConsTime")); // Temps moyen de consommation
		int Mavg = Integer.parseInt(properties.getProperty("Mavg")); // Nb moyen de messages produit par chaque consommateur
		
		System.out.println("nbP : " + nbP);
		System.out.println("nbC : " + nbC);
		System.out.println("BufSz : " + BufSz);
		System.out.println("ProdTime : " + ProdTime);
		System.out.println("ConsTime : " + ConsTime);
		System.out.println("Mavg : " + Mavg);
		System.out.println("----------- LANCEMENT DU PROGRAMME v2 : SEMAPHORES -----------");
		
		ProdConsBuffer buffer = new ProdConsBuffer(BufSz); // Création du buffer
		int nbTotalMsgProd = 0; // Nb total de messages à produire
		List<Producteur> ListeProd = new ArrayList<Producteur>();
		
		int ComptP = 0, ComptC = 0; // Nb de producteurs et consommateurs effectivement crées
		for (int i = 0 ; i < nbP+nbC ; i++) {
			float tirage = (float)(Math.random()); // Création aléatoire d'un producteur ou d'un consommateur
			if (tirage < 0.5F) {
				if (ComptP<nbP) {
					ComptP++;
					Producteur prod = new Producteur(new Message(), buffer, ComptP, ProdTime, (int)(Math.random()*Mavg*2));
					System.out.println("CREATION du producteur n°" + prod.numId);
					nbTotalMsgProd += prod.nmes;
					ListeProd.add(prod);
					prod.start();
				}
				else if (ComptC<nbC) {
					ComptC++;
					Consommateur cons = new Consommateur(buffer, ComptC, ConsTime);
					System.out.println("CREATION du consommateur n°" + cons.numId);
					cons.start();
				}
			}
			else if (tirage > 0.5) {
				if (ComptC<nbC) {
					ComptC++;
					Consommateur cons = new Consommateur(buffer, ComptC, ConsTime);
					System.out.println("CREATION du consommateur n°" + cons.numId);
					cons.start();
				}
				else if (ComptP<nbP) {
					ComptP++;
					Producteur prod = new Producteur(new Message(), buffer, ComptP, ProdTime, (int)(Math.random()*Mavg*2));
					System.out.println("CREATION du producteur n°" + prod.numId);
					nbTotalMsgProd += prod.nmes;
					ListeProd.add(prod);
					prod.start();
				}
			}
		}
		
		// Condition 1 de terminaison du programme : tous les thread producteurs sont morts
		for (Producteur item : ListeProd) {
			try {
				item.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Condition 2 de terminaison du programme : tous les messages produits ont été consommés et le buffer est vide
		while (!( (nbTotalMsgProd == buffer.nbMsgEffectivementConsommes) &&
							(buffer.nmsg() == 0))) {
		}

		System.out.println("-------------- J'ai trouvé mon chat ! --------------");
		
	}

	
	public static void main(String[] args) {
		long debut = System.currentTimeMillis();
		TestProdCons test = new TestProdCons();
		
		try {
			test.start();
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.print("Temps d'exécution v2 : ");
		System.out.print(System.currentTimeMillis()-debut);
		System.out.println(" ms");
	}

}