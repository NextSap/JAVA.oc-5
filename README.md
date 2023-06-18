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

### Démarrage

Démarrage de l'application et de la base de données :

```bash
docker-compose up -d --build
```

### Testing

Lancement des tests unitaires :

```bash
mvn test
```

## Documentation

### Swagger

Lorsque l'application est démarrée, vous pouvez accéder à la documentation de l'API à l'adresse
suivante : http://localhost:8080/docs

### Postman

Vous pouvez également importer la collection Postman qui correspond à cette API (cf. <a target="_blank" href="https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-data-into-postman">Postman - Importing data into Postman</a>)
```
cd .postman/
```

