package modele;

import infrastructure.jaxrs.HyperLien;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public class RechercheSynchroneSequentielle extends RechercheSynchroneAbstraite implements AlgorithmeRecherche{

    private NomAlgorithme nom;

    public RechercheSynchroneSequentielle(String nom){
        this.nom = new ImplemNomAlgorithme(nom);
    }
    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        for (HyperLien<Bibliotheque> bibliotheque : bibliotheques) {
            Optional<HyperLien<Livre>> result = rechercheSync(bibliotheque, l, client);
            if(result.isPresent()) return result;
        }

        return Optional.empty();
    }

    @Override
    public NomAlgorithme nom() {
        return nom;
    }
}
