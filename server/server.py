from flask import Flask, request # , send_file
from firebase import Firebase
import CONST
import datetime
import json

fb_server = Firebase(CONST.FB_CONFIG)

app = Flask(__name__)


class FBException(Exception):
    def __init__(self, message):
        super().__init__(message)


@app.route('/test')
def test():
    return "Successful!"


@app.route('/user', methods=['PUT'])
def create_user():
    req = request.form
    try:
        return fb_server.create_user(req['email'], req['password'])
    except FBException:
        return "Unsuccessful"


@app.route('/login', methods=['POST'])
def authenticate():
    req = request.form
    try:
        auth = fb_server.authenticate(req['email'], req['password'])
        res = {
            'idToken':auth['idToken'],
            'userId':auth['localId']
        }
        return json.dumps(res)
    except FBException:
        return "Unsuccessful"


@app.route('/data', methods=['POST'])
def get_records_by_time():
    req = request.form
    try:
        date = datetime.datetime(req['year'], req['month'], req['day'])
        data = fb_server.get_records(id=req[id], idtk=req['idToken'], time=date)
        return json.dumps(data)
    except FBException:
        return "Unsuccessful"


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
    try:
        return fb_server.push_record(id=req['userId'], idtk=req['idToken'], record=record)
    except FBException:
        return "Unsuccessful"





# @app.route('/analytic', methods=['POST'])
# def get_analytic():
#     req = request.form
#     records = fb_server.get_records(req['email'], req['password'])
#     diagram.plot(records)
#     return send_file('tmp.png')

# @app.route('/authenticate', methods=['POST'])
# def authenticate():
#     req = request.form
#     auth = fb_server.sign_in(req['email'], req['password'])
#     return auth


app.run(host='0.0.0.0', debug=True, port=8000)

"""

You use something like

from flask import send_file

@app.route('/get_image')
def get_image():
    if request.args.get('type') == '1':
       filename = 'ok.gif'
    else:
       filename = 'error.gif'
    return send_file(filename, mimetype='image/gif')"""

# curl -X PUT -d '{ "Food Chain": "Subway", "Category": "Sandwitch", "Item":"Pizza Sub", "Price":"14.56", "Feeling": "7" }' \ 'https://test-projec-5a491.firebaseio.com"/records/jeongwon412@gmail.com.json'
# diagram = Diagram(type='plot')
# from diagram import Diagram
