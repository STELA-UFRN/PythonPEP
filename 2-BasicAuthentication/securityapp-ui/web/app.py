from flask import Flask, render_template, request, redirect, Response
from config import BaseConfig 
from oauth_fiware import OAuth2
import requests

try:
    import simplejson as json
except ImportError:
    import json

app = Flask(__name__)
app.config.from_object(BaseConfig)

code = ""
url_service = 'http://192.168.99.100:8000/'
auth_app = OAuth2()

@app.route('/reset')
@app.route('/')
def index():
	return render_template('index.html') 

@app.route("/authenticate")
def authenticate():
	auth_url = auth_app.authorize_url() 
	return redirect(auth_url)

@app.route("/access_token")
def access_token():
	content = auth_app.get_token(code)
	return render_template('index.html', content=content)

@app.route("/username", methods=['GET'])
def username(): 
	response = requests.get(url_service + "service1/" + request.args.get('username')) 
	return render_template('index.html', content=response.text)

@app.route("/list", methods=['GET'])
def list(): 
	response = requests.get(url_service + "service2/list")
	return render_template('index.html', content=response.text)

@app.route("/add", methods=['GET'])
def add(): 
	response = requests.get(url_service + "service2/add/" + request.args.get('name'))
	return render_template('index.html', content=response.url)

@app.route('/auth', methods=['GET', 'POST'])
def auth():
	error = request.args.get('error', '')
	if error:
		return "Error: " + error 

	code = request.args.get('code')  
	content = auth_app.fiware_login() 
	return render_template('index.html', content="content: " + content) 

if __name__ == '__main__':
    app.run()
