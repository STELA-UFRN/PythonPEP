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

config.account_host = 'http://192.168.99.100:8000';

config.keystone_host = '192.168.99.100';
config.keystone_port = 5000;

config.app_host = '192.168.99.102';
config.app_port = '8080';
// Use true if the app server listens in https
config.app_ssl = false;

// Credentials obtained when registering PEP Proxy in Account Portal
config.username = 'pep_proxy_827d03dfe9d940ed93d719a27afb73b6';
config.password = 'c6a596489a0746a1911da82929fa7876';

// in seconds
config.chache_time = 300;

// if enabled PEP checks permissions with AuthZForce GE.
// only compatible with oauth2 tokens engine
//
// you can use custom policy checks by including programatic scripts
// in policies folder. An script template is included there
config.azf = {
    enabled: false,
    host: 'auth.lab.fiware.org',
    port: 6019,
    custom_policy: undefined // use undefined to default policy checks (HTTP verb + path).
};

// list of paths that will not check authentication/authorization
// example: ['/public/*', '/static/css/']
config.public_paths = [ '', '' ];

// options: oauth2/keystone
config.tokens_engine = 'oauth2';

config.magic_key = undefined;

module.exports = config;