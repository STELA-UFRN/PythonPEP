var config = {};

config.pep_port = 80;

// Set this var to undefined if you don't want the server to listen on HTTPS
// config.https = {
//     enabled: false,
//     cert_file: 'cert/cert.crt',
//     key_file: 'cert/key.key',
//     port: 443
// };


config.https = undefined;

config.account_host = 'http://192.168.99.100:8000/'; // IDM

config.keystone_host = '192.168.99.100'; // IDM
config.keystone_port = 5000;

config.app_host = '127.0.0.1'; // REST 
config.app_port = '8000';
// Use true if the app server listens in https
config.app_ssl = false;

// Credentials obtained when registering PEP Proxy in Account Portal
config.username = 'pep_proxy_17b37a395491454fbd5094227c616786';
config.password = 'fda5210c116e4176b27dd276eb53488a';

// in seconds
config.chache_time = 300;

// if enabled PEP checks permissions with AuthZForce GE.
// only compatible with oauth2 tokens engine
//
// you can use custom policy checks by including programatic scripts
// in policies folder. An script template is included there
config.azf = {
    enabled: true,
    host: '192.168.99.100',
    port: 8080,
    path: '/authzforce/domains/',
    custom_policy: undefined // use undefined to default policy checks (HTTP verb + path).
};

// list of paths that will not check authentication/authorization
// example: ['/public/*', '/static/css/'] /Example-Application-Security-REST/Python/3-BasicPEPProxy/securityapp-rest/*
config.public_paths = [''];

// options: oauth2/keystone
config.tokens_engine = 'oauth2';

config.magic_key = undefined;

module.exports = config;
