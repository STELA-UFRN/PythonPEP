# Example Application Security 

Aplicação Básica para mostrar a utilização do FIWARE Identity Manager Generic Enabler, responsável pelo gerenciamento de identidade de usuarios, organizações e aplicações, das suas credenciais e da autenticação dessas entidades. A implementação de referência desta GE é o Keyrock.
 
 
Files
-----
A aplicação pode ser encontrada em duas versões: uma em Python e em Java (ambas em diretórios distintos). Existem um README especifico para cada projeto explicando possíveis especificações ou exigências.


Docker and Fiware/IdM
---------------------
Caso deseje utilizar a imagem do Fiware/IdM em uma máquina docker, basta utilizar os arquivos presentes no diretório ```fiware/pep-proxy```.

### Instruções básicas

Com a máquina docker ativa, entre no diretório ```fiware/pep-proxy``` e execute:

    $ docker-compose up

Agora acesse ```http://<ip da máquina>:8000/``` (ex.: ```http://192.168.99.102:8000/```).

*docker-compose.yml* : No arquivo docker-compose.yml temos:

```
idm:
    restart: always
    image: fiware/idm
    ports:
        - 8000:8000
        - 5000:5000

pep-proxy:
    restart: always
    image: fiware/pep-proxy
    ports:
        - 80:80
    volumes:
        - ~/Projetos/fiware/pep-proxy/config.js:/opt/fiware-pep-proxy/config.js
```


Altere a linha ```- ~/Projetos/fiware/pep-proxy/config.js:/opt/fiware-pep-proxy/config.js``` para indicar o caminho até o arquivo ```config.js``` em sua máquina.


*config.js.template* : este é o arquivo template para definir as configurações para a utilização do Pep. Um exemplo de configuração pode ser encontrada no arquivo ```config.js``` no mesmo diretório. 


