# jwt-sso

This is an example project of JWT (JSon Web Token) with SSO (Single Sign On) authentication

## Dependencies
Please make sure your environment have dependecies below :
<ul>
<li>Java (version 8.x or higher)</li>
<li>Apache Maven (version 3.x.x or higher)</li>
<li>SQL database (Postgres or others, need to change database driver in project properties if you use others)</li>
<li>Central Authentication Service</li>
<li>Java container (e.g. Tomcat, optional)</li>
</ul>

## Installation and How To Use
### Installation
You can choose to use embedded Jetty or others container (e.g. Apache Tomcat) to run the project.
Please set the value of environment variables below
```
JWT_DB_HOST=localhost                             // your database host
JWT_DB_PORT=5432                                  // your database port
JWT_DB_NAME=jwt_sso                               // your database name
JWT_DB_USERNAME=local                             // your database username
JWT_DB_PASSWORD=local                             // your database password

JWT_CAS_HOST=https://cas.local                    // your CAS host
JWT_CAS_SERVICE=http://localhost:8080/api/login
```
Run the command below inside the project directory
```
$ mvn clean spring-boot:run
```
or create the .war file then deploy it to others container

### How To Use
To access all API in this project you should include the token, which generated after login, in request header. Put the token in "Authorization" request header. Some API will ignored from security (no need to include token in request header), e.g. /api/login, /swagger-ui.html

## How It Works
![alt text](https://lh3.googleusercontent.com/TtdOxSicBd02Ekfu6CvgHvGChtIw8KKQa2eFlwSOeqAdMknIyew4D_5Ali6a9_N2AwtlhdgF9V6MJl5RM3LzsN6dYUDl0oOBNuEXomCoWypvxp_hESBX0EbCJWBflfRGNdWcfuG75Q=w961-h361-no)
