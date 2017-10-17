Tutorial para Aplicações com Fiware
===================================

Aplicações Web
^^^^^^^^^^^^^^
   
- Aplicação 1

   A aplicação 1 suporta projetos que possuem a seguinte aplicação:

   .. image:: documentacao-vm/source/imagens/app1.png 

- Aplicação 2

   A aplicação 2 suporta projetos que possuem a seguinte aplicação:

   .. image:: documentacao-vm/source/imagens/app2.png 

- Aplicação 3

   A aplicação 3 suporta projetos que possuem a seguinte aplicação:

   .. image:: documentacao-vm/source/imagens/app3.png  

- Aplicação 4

   A aplicação 4 suporta projetos que possuem a seguinte aplicação:

   .. image:: documentacao-vm/source/imagens/app4.png
 
- Aplicação 5

   A aplicação 5 suporta projetos que possuem a seguinte aplicação:

   .. image:: documentacao-vm/source/imagens/app5.png 


Preparando o Ambiente:
^^^^^^^^^^^^^^^^^^^^^^

- Guia de Instalação

Após efetuar os passos da instalação é necessário configurar o ambiente para que possamos executar a aplicação desejada.

- No arquivo config.js disponível em Example-Application-Security/fiware/pep-proxy/Docker/fiware-pep-proxy/files mude as linhas.::

	config.app_host = 'IP-APP';
	config.username = 'ID-PEPPROXY-FIWARE';
	config.password = 'SENHA-PEPPROXY';

Lembrando que as informações do pepproxy é possível recuperar na interface do fiware lab

- Escolha a aplicação que possui as configurações que deseja utilizar
- Após escolhida a aplicação, abra o arquivo oauth_fiware.py disponível em Example-Application-Security/Python/APLICAÇÃO-ESCOLHIDA/securityapp-ui faça as seguintes modificações.::
	
	self.client_id = 'ID-APP-FIWARE'  
        self.client_secret = 'ID-SECRET-APP-FIWARE'

- Posteriormente é necessário mudar as seguintes linhas.::

	self.redirect_uri = 'http://IP-APP:5055/auth' ;

        self.proxy_address = "http://IP-APP:80/" ;
        self.idm_address = 'http://IP-APP:8000/' ;

- Guia de Configuração

Após efetuar os passos de Instalação e Configuração, para rodar a aplicação desejada é necessário que:

- Acessar a pasta Example-Application-Security/Python/APLICAÇÃO-DESEJA/securityapp-ui e efetuar o seguinte comando.::
	
	sudo docker-compose up

- Acessar a pasta Example-Application-Security/Python/APLICAÇÃO-DESEJA/securityapp-rest e efetuar o seguinte comando.::

	sudo docker-compose up

- Por fim, acessar a pasta Example-Application-Security/fiware/pep-proxy/ e efetuar o seguinte comando.::

	sudo docker-compose up
- Guia de Execução
Antes de utilizar uma aplicação desejada, é necessário efetuar a seguinte instalação de programas:

- Para a instalação do docker siga o tutorial disponível em: `Instalação do Docker <https://www.digitalocean.com/community/tutorials/como-instalar-e-usar-o-docker-no-ubuntu-16-04-pt>`_

- Para instalação do docker-compose siga o tutorial disponível em: `Instalação do docker-compose <https://www.digitalocean.com/community/tutorials/how-to-install-docker-compose-on-ubuntu-16-04>`_

- Para instalação do docker-machine siga o tutorial disponível em: `Instalação do docker-machine <https://www.digitalocean.com/community/tutorials/how-to-provision-and-manage-remote-docker-hosts-with-docker-machine-on-ubuntu-16-04>`_

Após a instalação é necessário fazer clone ou download no repositório que contém as aplicações:

- Faça clone do seguinte repositório: `Example-Application-Security <https://IreneGinani@projetos.imd.ufrn.br/SmartMetropolis-InfraestruturaGroup/SGeoL-Docker.git>`_



