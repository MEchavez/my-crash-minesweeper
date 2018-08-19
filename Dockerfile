FROM openjdk:8u171
COPY ./src /code/src
WORKDIR /code/src
RUN javac *.java
ENTRYPOINT ["java","Main"]