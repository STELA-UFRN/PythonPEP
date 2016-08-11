import requests
import requests.auth
from flask import request

# GET /users/:id
# GET /user?access_token=12342134234023437
# http://0.0.0.0:8000/oauth2/authorize?redirect_uri=http%3A%2F%2F192.168.99.101%3A8000%2Fauth&client_id=1817761f0f4d4aa495c8544551f980a2&response_type=code

class AuthFiware(object):
	"""docstring for ClassName"""
	def __init__(self):
		self.client_id = "1817761f0f4d4aa495c8544551f980a2"
		self.client_secret = "d3546b010ba441b8921c88b4669707f8"
		self.client_code = ""

		self.authorize_url = "http://0.0.0.0:8000/oauth2/authorize" 
		self.token_url = "http://0.0.0.0:8000/oauth2/token" 
		self.redirect_uri = "http://192.168.99.101:8000/auth"
	
	def authorize(self):
		p = {
		"response_type": "code",
		"client_id": self.client_id,
		"redirect_uri": self.redirect_uri
		}
		resp = requests.get(self.authorize_url, p)
		self.client_code = request.args.get('code')
		return resp

	def access_token(self):
		p = {
		"grant_type": "authorization_code",
		"code": self.client_code,
		"redirect_uri": self.redirect_uri
		}
		resp = requests.post(self.token_url, p)
		return resp