# Getting Started

The Project is Automatic Irrigation System. To run the Object on you local machine there are 2 methods.

####1. IDE
####2. Command Line

####Using IDE 
For This you have to import the project in your IDE and after that the edit the properties of Mysql according 
to your machine in application.properties. 

You Should have Schema/Database name existed in your SQL. In my Case I use name Plot
So to create that run SQL query **CREATE SCHEMA schema_name**; and after that run application via IDE

####Using Command Line
You have to perform the same steps as above and  after performing and changing application.properties. Open CMD
and run command  **mvn clean install** in case the test are failing then use **mvn clean install -DskipTests** and after that type **java -jar target/plot-0.0.1-SNAPSHOT.jar**



## About Project and its features and Data

### Structure of  Project
The Structure follows MVC.
* There are Controllers
* The Business layer is implemented by Service Classes
* The Data layer the Spring Data JPA repository is implemented by repository Classes
* The Database is MYSQL
* The Scheduler for implementing the simulation
* The Seed Data for use cases.
* The test Coverage is present in test Package the test coverage of repository and controller.

### About Seed Data
The data used is combination of multiple scenarios.
it consists of data of crops and for plots it contain
the data for case in which all sensors are available there is a
case in which it consists of data of plot in which sensors are not
available there is a case in which there is Failed Transaction and also Succeeded transaction about irrigation of Plot.
The Data is in package Seed Data in class InitializeSeedData. 

### About Working and Features
* Allow to get the all plot details
* Allow the new Plot to be added to the system
* Allow to Configure the plot Configuration
* Allow to Edit the plot details
* Allow to get the plot details by Owner Name
### Simulation Feature
The Simulation time is defined in class Called Properties. 
#### There are three Schedulers 1 for Alert 1 For irrigation and 1 for the Failed irrigation.
#### The System will check after every 2 minutes If the irrigation of plot should be executed or not depending upon next irrigation time and handle the following cases
* If sensor available then irrigation will be executed, and the status will be updated to success and
  also the other details and time.
* If sensor is not available then irrigation will be executed, and the status will be updated to failed and retry is set to indicated that the irrigation is executed, and 
  the result is failed. 
  The System will check after every 2 minutes If the irrigation.
  
#### The system will check after every 2 minutes to execute Failed irrigation events that and execute the irrigation even again having value less the retry which is 5 and continue to execute the events until there value is equal to retry which is 5 already preconfigured and handle the following cases. 
* If sensor available then irrigation will be executed, and the status will be updated to success and
  also the other details and time.
* If sensor is not available then irrigation will be executed, and the status will be updated to failed and retry is incremented to indicated that the irrigation is executed, and
  the result is failed.
  The System will check after every 2 minutes If the irrigation.
  
#### The system will check after every 2 minutes to execute Alert Simulation to generate the alert having value of the retry which is 5 It handles the following cases.
* For the Failed events having value equal to the number of retries it will create the alert for that event



### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#data.sql.jpa-and-spring-data)
