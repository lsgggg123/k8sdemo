FROM java:8

MAINTAINER lsgggg123@gmail.com

RUN ["mkdir", "/opt/java"]

WORKDIR /opt/java

ADD ./target/k8s-demo.jar k8s-demo.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "/opt/java/k8s-demo.jar"]