package jus.poc.prodcons.v2;

public class Message {
	String content; // Contenu du message
	int idParent; // Identifiant du Producteur père du message
	
	Message() {
		this.content = "Je cherche un chat à adopter.";
	}
}