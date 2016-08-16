import requests
from urllib.parse import quote, urlencode
from urllib.parse import parse_qs
try:
    import simplejson as json
except ImportError:
    import json


class OAuth2(object): 
    def __init__(self): 
        self.client_id = "7ef3ee8f4f0c4075a277bed2758e7f45"
        self.client_secret = "79838f02cfe9465285423bca2ad5d35d"
        self.site = 'http://0.0.0.0:8000'
        self.redirect_uri = "http://192.168.99.101:8000/auth"
        self.authorization_url = '/oauth2/authorize'
        self.token_url = '/oauth2/token' 

    def authorize_url(self, **kwargs):
        oauth_params = {'response_type': 'code', 'redirect_uri': self.redirect_uri, 'client_id': self.client_id}
        oauth_params.update(kwargs)
        return "%s%s?%s" % (self.site, quote(self.authorization_url), urlencode(oauth_params))

    def get_token(self, code, **kwargs):
        url = "%s%s" % (self.site, quote(self.token_url))
        data = {'grant_type': 'authorization_code', 'redirect_uri': self.redirect_uri, 'client_id': self.client_id, 'client_secret': self.client_secret, 'code': code}
        data.update(kwargs)
        response = requests.post(url, data=data)
        content = response.content

        """if isinstance(response.content, str):
            try:
                content = json.loads(response.content)
            except ValueError:
                content = parse_qs(response.content)
        else:"""

        return content