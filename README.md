# Description


The purpose of this application is to forecast Forex rates with the classifiers of Machine Learning


Source of the Forex rates - Yahoo.

The application is under continuous development.



### Functionalities

You could:
* get forecast bar estimated with Bayes Calssifier (white -price up/ black - price down)
* estimate performance of the classifier




### Prerequisities

You need to install MySQL and Maven



### Installing

Clone or download the project. Make sure to change username and password of your database in ../src/main/resources/application.properties file.
In terminal go to the folder with pom.xml file and invoke:



```
mvn install
```


# Running

In terminal go to the folder with pom.xml file and invoke:

```
mvn spring-boot:run
```

Application is started on localhost:8080


