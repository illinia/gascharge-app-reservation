FROM centos:centos8

RUN sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-Linux-*
RUN sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-Linux-*

RUN yum update -y
RUN yum install -y java-17-openjdk.aarch64

COPY ./gascharge-app-reservation-0.0.1-SNAPSHOT.jar ./gascharge-app-reservation-0.0.1-SNAPSHOT.jar
COPY ./application-oauth.yml ./application-oauth.yml

ENTRYPOINT ["java", "-jar", "-Dspring.server.port=8400", "-Dspring.profiles.active=dev", "-Dspring.config.location=application-oauth.yml,classpath:/application.yml", "gascharge-app-reservation-0.0.1-SNAPSHOT.jar" ]