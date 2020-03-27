import pytest
import server
from flask import json


@pytest.fixture(scope="module")
def test_client():
    testing_client = server.app.test_client()
    ctx = server.app.app_context()
    ctx.push()
    yield testing_client
    ctx.pop()


def test_test(test_client):
    response = test_client.get('/test')
    assert response.data == b'Successful!'


# def test_create_user(test_client):
#     response = test_client.put('/user', data={'email':'test@a123.com', 'password': 'test1234'})
#     assert response.status_code == 200


def test_auth(test_client):
    response = test_client.post('/login', data={'email': 'test@a123.com', 'password': 'test1234'})
    assert response.status_code == 200


def test_push_record(test_client):
    auth_response = test_client.post('/login', data={'email': 'test@a123.com', 'password': 'test1234'})
    assert auth_response.status_code == 200
    auth_json = json.loads(auth_response.data)
    idtk = auth_json['idToken']
    id = auth_json['userId']

    new_record = {
        'Category': 'Test Category',
        'Feeling': 3,
        'FoodChain': 'MC',
        'Item': 'Big Mac',
        'Price': 20.2,
        'Exercise': 'Running machine'
    }

    data_response = test_client.put('/data', data={'userId': id, 'idToken': idtk, **new_record})
    assert data_response.status_code == 200


def test_get_record(test_client):
    auth_response = test_client.post('/login', data={'email': 'test@a123.com', 'password': 'test1234'})
    assert auth_response.status_code == 200
    auth_json = json.loads(auth_response.data)
    idtk = auth_json['idToken']
    id = auth_json['userId']
    data_response = test_client.post('/data', data={'userId': id, 'idToken': idtk})
    assert data_response.status_code == 200



def test_get_goal(test_client):
    auth_response = test_client.post('/login', data={'email': 'test@a123.com', 'password': 'test1234'})
    assert auth_response.status_code == 200
    auth_json = json.loads(auth_response.data)
    idtk = auth_json['idToken']
    id = auth_json['userId']

    data_response = test_client.post('/data', data={'userId': id, 'idToken': idtk})
    assert data_response.status_code == 200


def test_push_goal(test_client):
    auth_response = test_client.post('/login', data={'email': 'test@a123.com', 'password': 'test1234'})
    assert auth_response.status_code == 200
    auth_json = json.loads(auth_response.data)
    idtk = auth_json['idToken']
    id = auth_json['userId']

    data_response = test_client.post('/data', data={'userId': id, 'idToken': idtk, 'Goal':'Push up every day'})
    assert data_response.status_code == 200


def test_get_graph(test_client):
    auth_response = test_client.post('/login', data={'email': 'test@a123.com', 'password': 'test1234'})
    assert auth_response.status_code == 200
    auth_json = json.loads(auth_response.data)
    idtk = auth_json['idToken']
    id = auth_json['userId']

    data_response = test_client.post('/data', data={'userId': id, 'idToken': idtk, 'Goal':'Push up every day', 'type'='r'})
    assert data_response.status_code == 200
