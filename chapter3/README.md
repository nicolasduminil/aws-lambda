# Chapitre 3

Ce projet présente quelques scénarios de base de fonctions AWS Lambda en Java

## Utilisation

Pour compiler:

```
$ mvn clean package
```

Pour déployer et exécuter:

```
$ ./deploy.sh
```

Allez dans la console CloudWatch et vérifiez vos groupes de journalisations 
(https://eu-west-3.console.aws.amazon.com/cloudwatch/home?region=eu-west-3#logsV2:log-groups/log-group)
Ouvrez les fichiers log et inspectez leur contenu

Pour netoyer l'environnement:

```
./cleanup.sh
```

