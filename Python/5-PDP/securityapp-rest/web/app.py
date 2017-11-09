from config import BaseConfig
from flask import Flask, jsonify

app = Flask(__name__)
app.config.from_object(BaseConfig)


@app.route('/service1')
@app.route('/service1/<username>')
def hello(username=None):
    if not username:
        return jsonify(result='Hello, World', status=200)
    return jsonify(result='Hello, {0}'.format(username), status=200)


@app.route('/v1/layer/bairros')
def list():
    #list_users = ['gabi', 'lucas', 'felipe']
    return "ok"


@app.route('/v1/layer/bairros?from=3')
def username(username=None):
    #list_users = ['gabi', 'lucas', 'felipe']
    return "ok"

@app.route('/v1/layer')
def add(username=None):
    #list_users = ['gabi', 'lucas', 'felipe']
    return "ok"


if __name__ == '__main__':
    app.run(port=5056)

