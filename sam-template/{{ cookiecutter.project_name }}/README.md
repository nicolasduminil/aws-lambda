# {{ cookiecutter.project_name }}

Ceci est un template CookieCutter pour la création et le déploiement d'une fonction AWS Lambda en Java.

## Conditions préalables

* Vous devez être en possession d'un compte AWS valide ainsi que d'un compartiment S3 pour le stockage temporaire des artefacts à deployer
* Vous devez avoir installé AWS CLI, Maven et SAM CLI dans votre environnement de développement
* Votre environnement doit être configuré avec la paire de clés de sécurité AWS

## Utilisation

Pour compiler:

```
$ mvn package
```

Pour déployer:

```
$ sam deploy --s3-bucket <nom-compartiment-S3> --stack-name {{ cookiecutter.project_name }} --capabilities CAPABILITY_IAM
```

Pour tester:

```
aws lambda invoke
```

