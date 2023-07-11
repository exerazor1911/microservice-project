FROM eclipse-temurin:17@sha256:2b47a8ea946ce1e5365a1562414ed576e378b7b670cadff3fb98ebecf2890cdc
COPY "./target/fraud-1.0-SNAPSHOT.jar" "fraud.jar"
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "fraud.jar"]