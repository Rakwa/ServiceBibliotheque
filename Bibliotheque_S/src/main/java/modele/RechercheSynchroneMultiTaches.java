package modele;

import infrastructure.jaxrs.HyperLien;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RechercheSynchroneMultiTaches extends RechercheSynchroneAbstraite implements AlgorithmeRecherche{

    private NomAlgorithme nom;

    public RechercheSynchroneMultiTaches(String nom){
        this.nom = new ImplemNomAlgorithme(nom);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        CountDownLatch countDownLatch = new CountDownLatch(bibliotheques.size());
        var wrapper = new Object() {
            Optional<HyperLien<Livre>> retour = Optional.empty();
        };

        for (HyperLien<Bibliotheque> bibliotheque : bibliotheques) {
            ExecutorService executor = Executors.newCachedThreadPool();

            executor.submit(() -> {
                Optional<HyperLien<Livre>> result = rechercheSync(bibliotheque, l, client);
                if(result.isPresent()){
                    if(wrapper.retour.isEmpty()) {
                        wrapper.retour = result;

                        while(countDownLatch.getCount() > 0) {
                            countDownLatch.countDown();
                        }
                    }
                    else {
                        countDownLatch.countDown();
                    }

                }
            });
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
