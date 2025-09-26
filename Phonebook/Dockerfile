FROM eclipse-temurin:21
WORKDIR /phonebook
COPY . .
LABEL version="2.0.6"
RUN apt-get update &&  \
    apt-get -y install dos2unix &&  \
    dos2unix mvnw &&  \
    ./mvnw clean package -DskipTests  &&  \
    apt-get clean
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "target/Phonebook-2.0.6-SNAPSHOT.jar"]
EXPOSE 8080


