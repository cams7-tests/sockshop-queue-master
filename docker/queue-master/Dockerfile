FROM weaveworksdemos/msd-java:latest

WORKDIR /usr/src/app
COPY *.jar ./app.jar

ENV JAVA_OPTS "-Djava.security.egd=file:/dev/urandom"
ENTRYPOINT ["/usr/local/bin/java.sh","-jar","./app.jar", "--port=80"]

FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.14.1_1

WORKDIR /usr/src/app
COPY ./target/*.jar ./app.jar

#RUN	chown -R ${SERVICE_USER}:${SERVICE_GROUP} ./app.jar
#USER ${SERVICE_USER}

ARG BUILD_DATE
ARG BUILD_VERSION
ARG COMMIT

LABEL org.label-schema.vendor="Weaveworks" \
  org.label-schema.build-date="${BUILD_DATE}" \
  org.label-schema.version="${BUILD_VERSION}" \
  org.label-schema.name="Socks Shop: Queue Master" \
  org.label-schema.description="REST API for Queue Master service" \
  org.label-schema.url="https://github.com/microservices-demo/queue-master" \
  org.label-schema.vcs-url="github.com/microservices-demo/queue-master.git" \
  org.label-schema.vcs-ref="${COMMIT}" \
  org.label-schema.schema-version="1.0"

EXPOSE 80

ENV JAVA_OPTS "-Djava.security.egd=file:/dev/urandom"
ENTRYPOINT ["java","-jar","./app.jar", "--port=80"]
