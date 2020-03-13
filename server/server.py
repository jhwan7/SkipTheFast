from flask import Flask, request # , send_file
from firebase import Firebase
import CONST
import datetime
import json

fb_server = Firebase(CONST.FB_CONFIG)

app = Flask(__name__)


@app.route('/test')
def test():
    return "Successful!"


@app.route('/user', methods=['PUT'])
def create_user():
    req = request.form
    return fb_server.create_user(req['email'], req['password'])



@app.route('/login', methods=['POST'])
def authenticate():
    req = request.form

    auth = fb_server.authenticate(req['email'], req['password'])
    res = {
        'idToken':auth['idToken'],
        'userId':auth['localId']
    }        
    return json.dumps(res)


@app.route('/data', methods=['POST'])
def get_records_by_day():
    req = request.form
    date = datetime.datetime(int(req['year']), int(req['month']), int(req['day']))
    data = fb_server.get_records(id=req['userId'], idtk=req['idToken'], time=date)
    return json.dumps(data)


@app.route('/data', methods=['PUT'])
def push_data():
    req = request.form
    record = {
        'Category': req['Category'],
        'Feeling': req['Feeling'],
        'Food Chain': req['FoodChain'],
        'Item': req['Item'],
        'Time': datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    }
    return fb_server.push_record(id=req['userId'], idtk=req['idToken'], record=record)



# @app.route('/analytic', methods=['POST'])
# def get_analytic():
#     req = request.form
#     records = fb_server.get_records(req['email'], req['password'])
#     diagram.plot(records)
#     return send_file('tmp.png')


if __name__ == '__main__':
    print("Running from __main__")
    app.run(host='0.0.0.0', debug=True, port=8000)



