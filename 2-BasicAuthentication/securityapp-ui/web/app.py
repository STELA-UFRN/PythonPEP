from flask import Flask, render_template, request, redirect, Response
from config import BaseConfig
from oauth_config import AuthFiware
import requests

app = Flask(__name__)
app.config.from_object(BaseConfig)
  
url_service = 'http://192.168.99.100:8000/'
authfiware = AuthFiware()

@app.route('/reset')
@app.route('/')
def index():
	return render_template('index.html') 

@app.route("/authenticate")
def authenticate():
	r = authfiware.authorize()  
	return redirect(r)

@app.route("/username", methods=['GET'])
def username(): 
	r = requests.get(url_service + "service1/" + request.args.get('username')) 
	return render_template('index.html', content=r.text)

@app.route("/list", methods=['GET'])
def list(): 
	r = requests.get(url_service + "service2/list")
	return render_template('index.html', content=r.text)

@app.route("/add", methods=['GET'])
def add(): 
	r = requests.get(url_service + "service2/add/" + request.args.get('name'))
	return render_template('index.html', content=r.text)

@app.route('/auth')
def auth():
	error = request.args.get('error', '')
	if error:
		return "Error: " + error 
	code = request.args.get('code')
	r = authfiware.set_code(code)  
	return render_template('index.html') 

if __name__ == '__main__':
    app.run()
