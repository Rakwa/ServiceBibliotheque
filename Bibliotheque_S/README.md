Projet réalisé par Clément NICOLAS, Théo LETOUZE, Julien RAQUOIS

#Analyse du code
- Repertoire 
```java
  @PUT
  @Produces(TYPE_MEDIA)
  @Consumes(TYPE_MEDIA)
  @ReponsesPUTOption
  // Requête (méthode http + url) : PUT http://localhost:8080/Projet/portail
  // Corps : <livre>
                 <titre>Services5.6</titre>
            </livre>
  // Réponses (à spécifier par code) :
  // - code : 200 OK
  // - code : 404 Not Found
  // - code : 500 Internal Server Error
  Optional<HyperLien<Livre>> chercher(Livre l);

  @PUT
  @ReponsesPUTOption
  @Path(JAXRS.SOUSCHEMIN_ASYNC)
  @Consumes(JAXRS.TYPE_MEDIA)
  @Produces(JAXRS.TYPE_MEDIA)
  // Requête (méthode http + url) : PUT http://localhost:8080/Projet/portail/async
  // Corps : <livre>
                 <titre>Services5.6</titre>
             </livre>
  // Réponses (à spécifier par code) :
  // - code : 200 OK
  // - code : 404 Not Found
  // - code : 500 Internal Server Error
  Future<Optional<HyperLien<Livre>>> chercherAsynchrone(Livre l, @Suspended final AsyncResponse ar);

  @GET
  @Path(SOUSCHEMIN_CATALOGUE)
  @Produces(TYPE_MEDIA)
  // Requête (méthode http + url) : GET http://localhost:8080/Projet/portail/catalogue
  // Corps : vide
  // Réponses (à spécifier par code) :
  // - code : 200 OK
  HyperLiens<Livre> repertorier();
```
- Archive
```java

  @Path("{id}")
  @ReponsesGETNullEn404
  // Adresse de la sous-ressource :
  // Requête sur la sous-ressource (méthode http + url) : GET http://localhost:8095/bib5/bibliotheque/6
  // Corps : vide
  // Réponses (à spécifier par code) :
  // - code : 200 OK
     - code : 400 Not Found
  Livre sousRessource(@PathParam("id") IdentifiantLivre id) ;

  @Path("{id}")
  @GET
  @Produces(JAXRS.TYPE_MEDIA)
  @ReponsesGETNullEn404
  // Requête (méthode http + url) : GET http://localhost:8095/bib5/bibliotheque/6
  // Corps : vide
  // Réponses (à spécifier par code) :
  // - code : 200 OK
     - code : 400 Not Found
  Livre getRepresentation(@PathParam("id") IdentifiantLivre id);

  @POST
  @ReponsesPOSTEnCreated
  @Consumes(JAXRS.TYPE_MEDIA)
  @Produces(JAXRS.TYPE_MEDIA)
  // Requête (méthode http + url) : POST http://localhost:8095/bib5/bibliotheque
  // Corps : <livre>
                <titre>Services5.64444</titre>
             </livre>
  // Réponses (à spécifier par code) :
  // - code : 201 Created
  HyperLien<Livre> ajouter(Livre l);
  }

```

- AdminAlgo
```java

  @PUT
  @Path(JAXRS.SOUSCHEMIN_ALGO_RECHERCHE)
  @Consumes(JAXRS.TYPE_MEDIA)
  // Requête (méthode http + url) : PUT http://localhost:8080/Projet/portail/admin/recherche
  // Corps : <?xml version="1.0" encoding="UTF-8" standalone="yes"?><algo nom="recherche async stream rx"/>
  // Réponses (à spécifier par code) :
  // - code : 204 No Content
  void changerAlgorithmeRecherche(NomAlgorithme algo);
```

#Données XML

```xml
<algo nom="recherche async stream rx"/>


<livre>
  <titre>Services5.64444</titre>
</livre>
```
#Résultat des tests

Le fichier de test est disponible dans src/main/java/client
```
- Algo recherche async seq
algo valide : true
Temps : 1576 ms
- Algo recherche async multi
algo valide : true
Temps : 66 ms
- Algo recherche async stream 8
algo valide : true
Temps : 1579 ms
- Algo recherche async stream rx
algo valide : true
Temps : 81 ms
```

La recherche multi est la plus efficace en terme de temps
