package jus.poc.prodcons.v1;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class TestProdCons {

	public static void start() throws InvalidPropertiesFormatException, IOException {

		Properties properties = new Properties();
		properties.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("options.xml"));
		int nbP = Integer.parseInt(properties.getProperty("nbP")); // Nb de thread producteurs
		int nbC = Integer.parseInt(properties.getProperty("nbC")); // Nb de thread consommateurs
		int BufSz = Integer.parseInt(properties.getProperty("BufSz")); // Taille du buffer
		int ProdTime = Integer.parseInt(properties.getProperty("ProdTime")); // Temps moyen de production
		int ConsTime = Integer.parseInt(properties.getProperty("ConsTime")); // Temps moyen de consommation
		int Mavg = Integer.parseInt(properties.getProperty("Mavg")); // Nb moyen de messages produit par chaque consommateur
		
		ProdConsBuffer buffer = new ProdConsBuffer(BufSz);
		
		int ComptP = 0, ComptC = 0; // Nb de producteurs et consommateurs effectivement crées
		for (int i = 0 ; i < nbP+nbC ; i++) {
			float tirage = (float)(Math.random());
			if (tirage < 0.5F) {
				if (ComptP<nbP) {
					Producteur prod = new Producteur(new Message(), buffer, Mavg);
					ComptP++;
					//prod.run();
					prod.start();
				}
				else if (ComptC<nbC) {
					Consommateur cons = new Consommateur(buffer, Mavg);
					ComptC++;
					//cons.run();
					cons.start();
				}
			}
			else if (tirage > 0.5) {
				if (ComptC<nbC) {
					Consommateur cons = new Consommateur(buffer, Mavg);
					ComptC++;
					//cons.run();
					cons.start();
				}
				else if (ComptP<nbP) {
					Producteur prod = new Producteur(new Message(), buffer, Mavg);
					ComptP++;
					//prod.run();
					prod.start();
				}
			}
		}
		
	}

	
	public static void main(String[] args) {
		TestProdCons test = new TestProdCons();
		
		try {
			test.start();
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}