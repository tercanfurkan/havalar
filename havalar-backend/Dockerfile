FROM openjdk:17
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/libs/fat.jar /app/havalar-core.jar
ENTRYPOINT ["java", "-jar", "app/havalar-core.jar"]