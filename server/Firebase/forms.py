import requests
from CONST import URL, FB_CONFIG, FB_RECORDS_ENDPOINT
from auth import *
from __init__ import firebase
import datetime

db = firebase.database()
"""
{
  "rules": {
    "users": {
      "$uid": {
        ".read": "$uid === auth.uid",
        ".write": "$uid === auth.uid"
      }
    }
  }
}
{ "Food Chain": "Subway", "Category": "Sandwitch", "Item":"Pizza Sub", "Price":"14.56", "Feeling": "7" }
"""


def get_records(email, pw):
    res = sign_in(email, pw)
    print(res)
    url = 'https://test-projec-5a491.firebaseio.com/records.json?auth='+res['idToken']
    res = requests.get(url=url)
    db.child('records').get()
    return res

def push_record(email, pw, record={ "Food Chain": "Subway", "Category": "Sandwitch", "Item":"Pizza Sub", "Price":"14.56", "Feeling": "7" }):
    data = {
      'time': str(datetime.datetime.now()),
      'record': record
    }
    res = sign_in(email, pw)
    #db.child('records').child('mathieu').set(data
    db.child('records').child('mathieu').push(record)
    
    return res
    
#print(get_records('jeongwon412@gmail.com', 'test1234').text)
res = push_record('jeongwon412@gmail.com', 'test1234')

for key, value in res.items():
  print(key+": "+str(value))

# fbTest = requests.get(url='ç', params={'test2':'test from backend'})
# fbPut = requests.put(url='https://test-projec-5a491.firebaseio.com/users.json', data='{"id":"password2"}' )
# print(fbPut.text)
# print(fbTest.text)
# location given here
# test = "test"

# curl "https://<DATABASE_NAME>.firebaseio.com/users/ada/name.json?auth=<ID_TOKEN>"

# defining a params dict for the parameters to be sent to the API
#PARAMS = {'test': test}

# sending get request and saving the response as response object
#r = requests.get(url=URL, params=PARAMS)

#print(r.text)
# extracting data in json format
#data = r.json()

# extracting latitude, longitude and formatted address
# of the first matching location
"""
    latitude = data['results'][0]['geometry']['location']['lat']
longitude = data['results'][0]['geometry']['location']['lng']
formatted_address = data['results'][0]['formatted_address']

# printing the output
print("Latitude:%s\nLongitude:%s\nFormatted Address:%s" % (latitude, longitude, formatted_address))
"""