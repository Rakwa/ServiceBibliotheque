package modele;

import java.util.Optional;
import java.util.concurrent.Future;

import configuration.JAXRS;
import infrastructure.jaxrs.HyperLien;
import infrastructure.langage.Types;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;

public abstract class RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	protected Future<Optional<HyperLien<Livre>>> rechercheAsync(HyperLien<Bibliotheque> h, Livre l, Client client){
		return client.target(h.getUri()).request().async().put(Entity.entity(l, JAXRS.TYPE_MEDIA), Types.typeRetourChercherAsync());

	};

	protected Future<Optional<HyperLien<Livre>>> rechercheAsyncAvecRappel(
			HyperLien<Bibliotheque> h, Livre l, Client client,  
			InvocationCallback<Optional<HyperLien<Livre>>> retour){
		return client.target(h.getUri()).request().async().put(Entity.entity(l, JAXRS.TYPE_MEDIA), retour);
	}
}
