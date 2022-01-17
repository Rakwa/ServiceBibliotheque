package modele;

import infrastructure.jaxrs.HyperLien;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.InvocationCallback;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class RechercheAsynchroneMultiTaches extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

    private NomAlgorithme nom;

    public RechercheAsynchroneMultiTaches(String nom) {
        this.nom = new ImplemNomAlgorithme(nom);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        CountDownLatch countDownLatch = new CountDownLatch(bibliotheques.size());
        var wrapper = new Object() {
            Optional<HyperLien<Livre>> retour = Optional.empty();
        };

        for (HyperLien<Bibliotheque> bibliotheque : bibliotheques) {

            InvocationCallback<Optional<HyperLien<Livre>>> retour = new InvocationCallback<>() {
                @Override
                public void completed(Optional<HyperLien<Livre>> livreHyperLien) {
                    if (livreHyperLien.isPresent()) {
                        if (wrapper.retour.isEmpty()) {
                            wrapper.retour = livreHyperLien;

                            while(countDownLatch.getCount() > 0) {
                                countDownLatch.countDown();
                            }
                        }
                        else {
                            countDownLatch.countDown();
                        }

                    }
                }

                @Override
                public void failed(Throwable throwable) {
                }
            };

            rechercheAsyncAvecRappel(bibliotheque, l,  client, retour);


        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return wrapper.retour;
    }

    @Override
    public NomAlgorithme nom() {
        return nom;
    }
}
