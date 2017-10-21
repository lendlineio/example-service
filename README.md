# example-service

An example restful service based on AKKA-HTTP and slick
 
##compile
`sbt compile`

##start the service
`sbt run`

if you run

`curl http://localhost:8090/echo/Test`

you should get `Test` back in the response

to POST (ADD) user:
 
 `curl -vvv -H "Content-Type: application/json" -X POST -d '{"name":"Yulia","id":0, "updated": "2017-07-11T20:46:38.858-04:00"}' http://localhost:8090/api/v1/user`

to GET user:

`curl -vvv http://localhost:8090/api/user?userid=1`


##continuous development
This project has sbt-revolver plugin, which allows sbt to detect code changes and automatically rebuild and restart the server

`sbt` then `~ re-start` to start

##Testing
`sbt test`

## jar packaging

`sbt assembly`

