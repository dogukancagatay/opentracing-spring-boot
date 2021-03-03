FROM openjdk:8-jdk-alpine
#FROM gradle:6.8.2-jdk8-alpine
ENV OPENTRACING_JAEGER_UDP_SENDER_HOST jaeger
ENV GRADLE_OPTS "-Dorg.gradle.daemon=false"
WORKDIR /app
COPY ./ /app/
RUN ./gradlew build --no-daemon
#RUN gradle build --no-daemon
#CMD gradle bootRun
CMD ./gradlew bootRun
