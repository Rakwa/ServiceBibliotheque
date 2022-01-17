package modele;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.Outils;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RechercheAsynchroneStreamParallele extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

    private NomAlgorithme nom;

    public RechercheAsynchroneStreamParallele(String nom){
        this.nom = new ImplemNomAlgorithme(nom);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        return bibliotheques.parallelStream().map(bibliotheque -> rechercheAsync(bibliotheque, l, client)).map(Outils::remplirPromesse).filter(Objects::nonNull).filter(Optional::isPresent).findAny().orElse(Optional.empty());
    }

    @Override
    public NomAlgorithme nom() {
        return nom;
    }
}
