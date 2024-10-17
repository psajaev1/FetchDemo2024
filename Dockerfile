From openjdk:17
copy ./build/libs/FetchDemo2024-0.0.1-SNAPSHOT.jar FetchDemo2024-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","FetchDemo2024-0.0.1-SNAPSHOT.jar"]