import requests
import requests.auth
from flask import request

# GET /users/:id
# GET /user?access_token=12342134234023437
# http://0.0.0.0:8000/oauth2/authorize?redirect_uri=http%3A%2F%2F192.168.99.101%3A8000%2Fauth&client_id=1817761f0f4d4aa495c8544551f980a2&response_type=code

class AuthFiware(object):
	"""docstring for ClassName"""
	def __init__(self):
		self.client_id = "7ef3ee8f4f0c4075a277bed2758e7f45"
		self.client_secret = "79838f02cfe9465285423bca2ad5d35d"

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
		return resp.url

	def access_token(self, code):
		p = {
		"grant_type": "authorization_code",
		"code": self.client_code,
		"redirect_uri": self.redirect_uri
		}
		resp = requests.post(self.token_url, p)
		return resp 

	def set_code(self, code):
		self.client_code = code
    
