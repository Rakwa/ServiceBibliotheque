package modele;

import infrastructure.jaxrs.HyperLien;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class RechercheAsynchroneSequentielle extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche{

    private NomAlgorithme nom;

    public RechercheAsynchroneSequentielle(String nom){
        this.nom = new ImplemNomAlgorithme(nom);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {

        List<Future<Optional<HyperLien<Livre>>>> hyperliens = bibliotheques.stream().map(bibliotheque -> rechercheAsync(bibliotheque,l, client)).collect(Collectors.toList());

        for(Future<Optional<HyperLien<Livre>>> hyperlien : hyperliens){
            try {
                if(hyperlien.get().isPresent()){
                    return hyperlien.get();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return Optional.empty();
    }

    @Override
    public NomAlgorithme nom() {
        return nom;
    }
}
