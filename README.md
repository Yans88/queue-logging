# queue-logging
Kamis, 01 September 2022

## Arsitektur & Fitur

1. Database PostgreSQL
2. SpringBoot untuk REST API
3. RabbitMQ
4. Logging

Dokumentasi API : https://www.getpostman.com/collections/0c4548ed3f0d0d90370a

## HTTP Request Sample

### Register User(POST)

<pre>
curl --location --request POST 'http://localhost/import-xls/api/registrasi' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "test123@mail.com",
    "username": "test123",
    "password": "12345678"
}'
</pre>