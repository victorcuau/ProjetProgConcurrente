package jus.poc.prodcons.v3;

public class Message {
	String content; // Contenu du message
	int idParent; // Identifiant du Producteur père du message
	int nbExemplaires;
	
	Message() {
		this.content = "Je cherche un chat à adopter.";
	}
}