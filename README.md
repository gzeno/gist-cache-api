# gist-cache-api

This is a simple back-end API that retrieves gists using the Github v3 API.  It uses the Java Spring Boot as the framework and the cache uses Mongodb.  The Gist domain only keeps some of the information from the actual gist but can be updated to add more fields when needed.  
A NoSql database was chosen because it is faster when we don't have complex relationships between different domains.  Gists is the only domain here and it has a variable number of files, making normal relational SQL harder to use.

Note: This does NOT handle the V3 API truncation case in the case of having over 300 files nor does it handle the truncation in the case of the content of a file being too large.

How to run + deploy using the included docker-compose file:

Required installs: Maven, docker, docker-compose

1. Compile using "maven package" at dir root.  There should now be a directory named "target" with a .jar file inside.  If there isn't the docker-compose file will fail.

2. Make sure you have a docker hub account ready and build the docker images using "docker-compose -f gist-cache-docker-compose.yml build"

3. Run docker-compose file using "docker-compose -f gist-cache-docker-compose.yml up"

Interact by sending http requests to localhost:8080.

Examples:

curl localhost:8080/gists/2841832

curl -X DELETE localhost:8080/gists/2841832

curl -X PUT localhost:8080/gists/2841832
