from flask import Flask, request, send_file
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
def get_records():
    req = request.form
    has_time = [
        "year" in req.keys(),
        "month" in req.keys(),
        "day" in req.keys()
    ]
    if all(has_time):
        date = datetime.datetime(int(req['year']), int(req['month']), int(req['day']))
        data = fb_server.get_records(id=req['userId'], idtk=req['idToken'], time=date)
        return json.dumps(data)
    else:
        data = fb_server.get_records(id=req['userId'], idtk=req['idToken'])
        return json.dumps(data)
    


@app.route('/data', methods=['PUT'])
def push_data():
    req = request.form
    record = {
        'Category': req['Category'],
        'Feeling': req['Feeling'],
        'Food Chain': req['FoodChain'],
        'Item': req['Item'],
        'Time': datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
        'Price': float(req['Price']),
        'Exercise': req['Exercise']
    }
    return fb_server.push_record(id=req['userId'], idtk=req['idToken'], record=record)


@app.route('/goal', methods=['PUT'])
def push_goal():
    req = request.form
    goal = {
        'Goal': req['Goal'],
        'Time': datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    }
    return fb_server.push_goal(id=req['userId'], idtk=req['idToken'], goal=goal)


@app.route('/goal', methods=['POST'])
def get_goal():
    req = request.form
    json_data = json.dumps(fb_server.get_goal(id=req['userId'], idtk=req['idToken']))
    print(json_data)
    return json_data


@app.route('/graph', methods=['POST'])
def get_graph_proper():
    req = request.form
    buffer = None
    if req['type'] == 'r':
        buffer = fb_server.graph_days_vs_record(id=req['userId'], idtk=req['idToken'])
    if req['type'] == 'm':
        buffer = fb_server.graph_days_vs_spent(id=req['userId'], idtk=req['idToken'])

    return send_file(buffer, as_attachment=True, attachment_filename='graph.png', mimetype = 'image/png')
    
    
    
if __name__ == '__main__':
    print("Running from __main__")
    app.run(host='0.0.0.0', debug=True, port=8000)

