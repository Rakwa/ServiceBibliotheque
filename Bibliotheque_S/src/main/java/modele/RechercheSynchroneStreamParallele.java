package modele;

import infrastructure.jaxrs.HyperLien;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public class RechercheSynchroneStreamParallele extends RechercheSynchroneAbstraite implements AlgorithmeRecherche{

    private NomAlgorithme nom;

    public RechercheSynchroneStreamParallele(String nom){
        this.nom = new ImplemNomAlgorithme(nom);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        return bibliotheques.parallelStream().map(bibliotheque -> rechercheSync(bibliotheque, l, client)).filter(Optional::isPresent).findAny().orElse(Optional.empty());
    }

    @Override
    public NomAlgorithme nom() {
        return nom;
    }
}
