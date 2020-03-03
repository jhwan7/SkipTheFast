import requests
from CONST import URL, FB_CONFIG, FB_RECORDS_ENDPOINT
from auth import *
from __init__ import firebase
import datetime
import base64

db = firebase.database()

# TODO: b64 -> user_id

def encode_email(email:str):
  utf_email = email.encode('utf-8')
  return base64.b64encode(utf_email)


def decode_email(encoded_email):
  return encode_email.decode('utf-8')


def get_records(email, pw):
    res = sign_in(email, pw)
    # res = requests.get(url=FB_RECORDS_ENDPOINT+".json")
    return db.child('records').child(encode_email(email)).get().val()
    

def push_record(email, pw, record):
    data = {
      'time': str(datetime.datetime.now()),
      'record': record
    }

    res = sign_in(email, pw)
    #db.child('records').child(encode_email(email)).push(record)
    #db.child('records').child('mathieu').set(data)
    db.child('records').child(encode_email(email)).push(record, res['idToken'])
    
    return res

if __name__ == '__main__':
  # test
  test_record = { 
    "Food Chain": "Subway", 
    "Category": "Sandwitch", 
    "Item":"Pizza Sub", 
    "Price": 14.56, 
    "Feeling": 7,
    "time": str(datetime.datetime.now())
  }

  # res = get_records('jeongwon412@uwo.ca', 'test1234')
  res = push_record('jeongwon412@uwo.ca', 'test1234', test_record)

  # for key, value in res.items():
  #   print(key+": "+str(value))
  print(res)
