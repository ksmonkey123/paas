FROM openjdk:17-alpine

ARG JAR_NAME
ENV JAR_NAME ${JAR_NAME}

COPY target/${JAR_NAME}.jar /

EXPOSE 8080

ENTRYPOINT java -jar ${JAR_NAME}.jar
