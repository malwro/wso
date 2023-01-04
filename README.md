# wso
##
Already done:
```
mvn -B archetype:generate -DgroupId=sim -DartifactId=wso -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4
```

```
mvn install:install-file \
   -Dfile=jars/cloudsim-3.0.3.jar \
   -DgroupId=sim \
   -DartifactId=cloudsim \
   -Dversion=3.0.3 \
   -Dpackaging=jar \
   -DgeneratePom=true
```