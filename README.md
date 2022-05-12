### Getting started 

## Application build stack:  

Java  
SpringBoot  
H2 database  
Maven

### Application architecture:

The transfer application consists of a three-tierd architecture, namely account-transfer-ui and account-transfer-api and database
    
### Steps to start up:


Before start up we have to build the jars and execute docker compose
Please followow the steps below


1 - change directory to account-transfer-api directory

```
cd path/to/account-transfer-api 
```

2 - execute maven build

```
mvn clean package
```

3 - change directory to account-transfer-ui directory

```
cd path/to/account-transfer-ui
```

4 - execute maven build

```
mvn clean package
```

5 - change directory to locate docker-compose.yml

```
cd path/to/docker/file/docker-compose.yml
```

6 - exeute docker compose command  

```
sudo docker-compose up --build
```

7 - application start up

The account-transfer-ui and account-transfer-api runs on port 8080 and 8081 respectively

If needed these ports can be updated by editing application.properties in each server
 

### Testing: 
 

1 - Application can be accesed on 

```
http://localhost:8080/

```

2 - To update email for your payment notification edit the email field on the accounts table and click 'update email'


3 - The database has been pre-loaded with accounts with a balance of GBP 200 each

4 - data.sql contain the default enteries

5 - h2 database can be accessed at  

```
http://localhost:8081/h2-console/login.jsp 

```
6 - NOTE: Unit tests and integration tests can be found on the account-tranfer-api, account-transfer-ui tests have been omitted due to time constraints 