import requests
from CONST import URL, FB_CONFIG, FB_RECORDS_ENDPOINT
from auth import *


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
"""


def get_records(user):
    params = {'user': user}
    res = requests.get(url=FB_RECORDS_ENDPOINT, params=params)
    return res


# fbTest = requests.get(url='ç', params={'test2':'test from backend'})
# fbPut = requests.put(url='https://test-projec-5a491.firebaseio.com/users.json', data='{"id":"password2"}' )
# print(fbPut.text)
# print(fbTest.text)
# location given here
# test = "test"

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