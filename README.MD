# Cart

#### Requirements

* [ Maven](https://maven.apache.org/)
    - [Windows](https://mkyong.com/maven/how-to-install-maven-in-windows/)
    - [Linux](https://maven.apache.org/install.html)
* [ Docker](https://www.docker.com/)

#### User manual

1. Run docker
    ````
    docker-compose up -d 
    ````

2. Run SureTime application
    ```
    mvn spring-boot:run
    ```
3. To turn on security in application.properties set this variable to true. 
    ```
    suretime.app.isSecureMode = true
   ```

