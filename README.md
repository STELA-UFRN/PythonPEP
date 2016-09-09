# Security-app

Example Application Security in Python/Flask.


## Dockerizing the app


Test:
	
```$ docker-machine --version```

```$ docker-compose --version```


To start Docker Machine:

```$ docker-machine create -d virtualbox dev;```


Point the Docker client at the dev machine:
	
```$ eval "$(docker-machine env dev)"```

Check the machines:

```docker-machine ls```

### Docker Compose

Take a look at the docker-compose.yml file:
	
```
web:
  restart: always
  build: ./web
  ports:
    - "8000:8000" 
  volumes:
    - /usr/src/app/static
  env_file: .env
  	command: /usr/local/bin/gunicorn -w 2 -b :8000 app:app
```

Now, to get the containers running, build the images and then start the services:
	
```	
$ docker-compose build

$ docker-compose up -d
```

To view the logs:
	
```$ docker-compose logs```	

Open your browser and navigate to the IP address associated with Docker Machine (docker-machine-ip/port).

***error: ```docker-machine regenerate-certs -f security-ui```***

### References:

- [Dockerizing flask with compose and machine from localhost to the cloud](https://realpython.com/blog/python/dockerizing-flask-with-compose-and-machine-from-localhost-to-the-cloud/)

	
- [Fiware-idm](https://github.com/ging/fiware-idm/tree/master/extras/docker)