# Bearch = Book + Search

## Bearch API
Bearch is a book search API that provides developers with a scalable solution for building book search functionality into their applications. By leveraging modern system development practices and implementing backing services such as Rate Limiting, Message Queuing, Caching, etc. Bearch's development end goal would for it to be a microservices architecture that can handle large volumes of queries.

## About Bearch
Bearch was born out of a desire to improve my development skills outside of my regular work hours. As a developer, I wanted to create a project that would challenge me to apply modern development practices in a practical and meaningful way. By creating an API for book search functionality, I saw an opportunity to own most or all of the features and really dive into developing them the best way I saw fit.

## Getting Started
Currently, Bearch uses an H2 in-memory database, but it can be easily switch over to a stateful relational database such as PostgreSQL

* Clone repository
* Setup OpenSSL
  * Download -> [OpenSSL](https://slproweb.com/products/Win32OpenSSL.html)
  * Environment variable setup -> [Click Here](https://www.stechies.com/installing-openssl-windows-10-11/)
  * Run in cmd: `openssl version` 
  

* Create RSA private/public token
    * `openssl genrsa -out keypair.pem 2048`
    * `openssl rsa -in keypair.pem -pubout -out public.pem`
    * `openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem`
    * Can delete keypair.pem
    * Rename files to have json extension and place somewhere safely on your local drive.


    
* Setup local environment variables in IDE(I use IntelliJ)
  * `RSA_PRIVATE=file:///D:/SDE/OpenSSL/bearchAuth/private.json`
  * `RSA_PUBLIC=file:///D:/SDE/OpenSSL/bearchAuth/public.json`
  * `AUTH_USER=username`
  * `AUTH_PASSWORD=password`


* DB Credentials
    * username: sa
    * password: 
    * url: http://localhost:8080/h2-console


* Run Configuration Setup

![img.png](img.png)

# REST Endpoints(Swagger implementation soon)
## POST
`http://localhost:8080/token` Basic Auth: Username = username, Password = password


`http://localhost:8080/api/book` Bearer Token: Use the generated token from `/token`

Use json formatted Request Body from `src/main/java/io/bearch/webapi/book_service/dto/BookDto.java`

## GET
`http://localhost:8080/api/book?isbn=9780439023481` Bearer Token: Use the generated token from `/token`


## DELETE
`http://localhost:8080/api/book/9780439023481` Bearer Token: Use generated token from `/token`


