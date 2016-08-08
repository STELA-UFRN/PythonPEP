## SecurityAPP-UI

Featuring:

- Docker v1.12.0
- Docker Compose v1.8.0
- Docker Machine v0.8.0


Commands: 

	```docker-machine start security-ui```

	```eval $(docker-machine env default)```

	```docker-compose build```

	```docker-compose up -d```


error: ```docker-machine regenerate-certs -f security-ui```


References:

	https://realpython.com/blog/python/dockerizing-flask-with-compose-and-machine-from-localhost-to-the-cloud/

	
	https://github.com/ging/fiware-idm/tree/master/extras/docker