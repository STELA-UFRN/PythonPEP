import base64
from urllib.parse import urlencode

import requests

try:
    import simplejson as json
except ImportError:
    import json


class OAuth2(object):
    def __init__(self):
        self.client_id = '685ab58aafe542eca97f581ee3e91e6e'  # IDM APP CLIENT ID
        self.client_secret = '2b5ae85d28014ae4ab4dc25cb16ad9a5'  # IDM APP CLIENT SECRET

        raw_auth_code = '{}:{}'.format(self.client_id, self.client_secret)
        self.base_64_auth_code = base64.b64encode(raw_auth_code.encode('utf-8')).decode('utf-8')

        self.redirect_uri = 'http://192.168.99.101:8000/auth'  # CALLBACK URL REGISTERED ON IDM (UI APP AUTH ADDRESS)

        self.idm_address = 'http://192.168.99.100:8000/'  # IDM ADDRESS
        self.authorization_url = self.idm_address + 'oauth2/authorize'
        self.token_url = self.idm_address + 'oauth2/token'

    def authorize_url(self, **kwargs):
        oauth_params = {'response_type': 'code', 'redirect_uri': self.redirect_uri, 'client_id': self.client_id}
        oauth_params.update(kwargs)
        return '{}?{}'.format(self.authorization_url, urlencode(oauth_params))

    def get_token(self, code):
        headers = {'Authorization': 'Basic {}'.format(self.base_64_auth_code),
                   'Content-Type': 'application/x-www-form-urlencodeduser-agent'}
        data = {'grant_type': 'authorization_code', 'code': code, 'redirect_uri': self.redirect_uri}
        response = requests.post(self.token_url, headers=headers, data=data)

        str_response_content = response.content.decode('utf-8')
        return json.loads(str_response_content)
