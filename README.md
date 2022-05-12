### Getting started 

## Applcation build stack:  

Java  
SpringBoot  
H2 database  
Maven build tool  

### Applcation architecture:

The transfer application consists of a three-tierd architecture, namely account-transfer-ui and account-transfer-api and database
    
### Steps to start up:  

A docker compose file has been provided- see docker-compose.yml

1 - change directory to locate docker-compose.yml

```
cd pathtofile/docker-compose.yml
 
```
2 - run docker-compose command  

```
sudo docker-compose up

```
3 - application start up

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
