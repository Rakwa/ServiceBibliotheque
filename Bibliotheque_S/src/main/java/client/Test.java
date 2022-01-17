package client;

import configuration.JAXRS;
import configuration.Orchestrateur;
import infrastructure.jaxrs.HyperLien;
import modele.ImplemLivre;
import modele.ImplemNomAlgorithme;
import modele.Livre;
import modele.Portail;
import org.glassfish.jersey.client.proxy.WebResourceFactory;

import javax.ws.rs.client.WebTarget;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {

        String[] algos = {
                "recherche sync seq",
                "recherche sync multi",
                "recherche sync stream 8",
                "recherche sync stream rx",
                "recherche async seq",
                "recherche async multi",
                "recherche async stream 8",
                "recherche async stream rx"
        };

        Portail portail = WebResourceFactory.newResource(Portail.class, Orchestrateur.clientJAXRS().target(JAXRS.ADRESSE_PORTAIL + "/" + JAXRS.CHEMIN_PORTAIL));
        HyperLien<Livre> livre = new HyperLien<Livre>("http://localhost:8095/bib5/bibliotheque/6");
        Livre recherche = new ImplemLivre("Services5.6");

        for (String algo : algos) {
            System.out.println("-----------------------------");
            portail.changerAlgorithmeRecherche(new ImplemNomAlgorithme(algo));
            long start = System.nanoTime();
            Optional<HyperLien<Livre>> result = portail.chercher(recherche);
            long end = System.nanoTime();
            System.out.println("Algo " + algo);
            System.out.println("algo valide : " + result.get().getUri().equals(livre.getUri()));
            System.out.println("Temps : " + TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");
        }
    }
}
