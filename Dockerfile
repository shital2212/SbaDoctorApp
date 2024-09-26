FROM openjdk:17               
ADD doctor-service-HSBC-0.0.1-SNAPSHOT.war doctor-service-HSBC-0.0.1-SNAPSHOT.war 
ENTRYPOINT ["java","-jar","doctor-service-HSBC-0.0.1-SNAPSHOT.war"]   
EXPOSE 8001 