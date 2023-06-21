![safetynet](https://user.oc-static.com/upload/2019/10/21/15716555321092_image1.jpg)

# Safetynet Alerts!

Safetynet Alerts est une API Rest conceptualisé avec Spring Boot.

## Installation

Ces instructions vous permettront d'obtenir une copie du projet sur votre machine locale à des fins de développement et
de test.

### Prérequis

* Installer <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html">Java
  17+</a>
* Installer <a target="_blank" href="https://maven.apache.org/download.cgi">Maven 3.8.1+</a>
* Installer <a target="_blank" href="https://docs.docker.com/get-docker/">Docker</a>
  et <a target="_blank" href="https://docs.docker.com/compose/install/">Docker Compose</a>

### <p id="starting">Démarrage</p>

Démarrage de l'outil de gestion du projet :

```bash
java -jar manager.jar
```
Grâce à cet outil, vous pouvez : 
- Créer, lancer, éteindre et supprimer les containers Docker de l'application
- Générer et lancer la documentation Swagger de l'API
- Lancer les tests unitaires
- Générer le rapport Jacoco

## Documentation

### Swagger

cf. <a href="#starting">Démarrage</a>

### Postman

Vous pouvez également importer la collection Postman qui correspond à cette API afin d'effectuer les requêtes pour facilement (cf. <a target="_blank" href="https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-data-into-postman">Postman - Importing data into Postman</a>)
```
cd .postman/
```

