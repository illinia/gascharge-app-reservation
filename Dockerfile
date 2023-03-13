FROM arm64v8/centos

RUN sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-Linux-*
RUN sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-Linux-*

# RUN yum -y update
# RUN yum -y install systemd

# VOLUME ["/sys/fs/cgroup"]

# RUN yum install -y openssh-server
# RUN sed 's/#PermitRootLogin yes/PermitRootLogin yes/' -i /etc/ssh/sshd_config
# RUN echo 'root:1212' | chpasswd
# RUN ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa
# RUN yum install -y openssh-clients

RUN yum update -y
RUN yum install -y java-17-openjdk.aarch64

COPY ./build/libs/gascharge-app-reservation-0.0.1-SNAPSHOT.jar ./gascharge-app-reservation-0.0.1-SNAPSHOT.jar
COPY ./application-oauth.yml ./application-oauth.yml

ENTRYPOINT ["java", "-jar", "-Dspring.server.port=8400", "-Dspring.profiles.active=dev", "-Dspring.config.location=application-oauth.yml,classpath:/application.yml", "gascharge-app-reservation-0.0.1-SNAPSHOT.jar" ]