package modele;

import infrastructure.jaxrs.HyperLien;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public class RechercheAsynchroneStreamRx extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

    private NomAlgorithme nom;

    public RechercheAsynchroneStreamRx(String nom){
        this.nom = new ImplemNomAlgorithme(nom);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        return Observable.fromIterable(bibliotheques).flatMap(h -> Observable.fromFuture((rechercheAsync(h, l, client))).subscribeOn(Schedulers.io()))
                .filter(Optional::isPresent).blockingFirst(Optional.empty());
    }

    @Override
    public NomAlgorithme nom() {
        return nom;
    }
}
