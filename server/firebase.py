import datetime
import pyrebase
import CONST
import json
from collections import OrderedDict


class UnableToLogInException(Exception):pass


class AttemptToDuplicateFirebase(Exception):pass


class Firebase:
    instance = None

    def __init__(self, fb_config):
        if Firebase.instance is not None:
            raise AttemptToDuplicateFirebase
        else:
            Firebase.instance = self
        self.firebase = pyrebase.initialize_app(fb_config)
        self.db = self.firebase.database()
        self.auth = self.firebase.auth()
        self.status = {
            'logged': False,
            'user': None
        }

    def authenticate(self, email, pw):
        try:
            login = self.auth.sign_in_with_email_and_password(email, pw)
            return login
        except UnableToLogInException:
            return False

    def create_user(self, email, pw):
        user = self.auth.create_user_with_email_and_password(email, pw)
        res = self.push_record(id=user['localId'], idtk=user['idToken'], record="None")
        return res

    def push_record(self, id, idtk, record):
        data = {
            'time': str(datetime.datetime.now()),
            'record': record
        }
        res = self.db.child('records').child(id).push(record, idtk)
        return res

    def get_records(self, id, idtk, **kwargs):
        if 'time' in kwargs.keys():
            data = self.db.child('records').child(id).get(idtk).val()
            data.popitem(last=False)
            records = {}
            for key, rec in data.items():
                try:
                    tk = rec["Time"]
                    # TODO: check if within time
                    if 'Time' in rec: del rec['Time'] # K, V ==> Time, rec/Time
                    records[tk] = rec
                except:
                    pass
            return records

        return self.db.child('records').child(id).get(idtk).val()
    
    #
    # def send_pw_reset_email(self, email):
    #     return self.auth.send_password_reset_email(email)
    #
    # def encode_email(self, email):
    #     utf_email = email.encode('utf-8')
    #     return base64.b64encode(utf_email)
    #
    # def decode_email(self, encoded_email):
    #     return encoded_email.decode('utf-8')


if __name__ == '__main__':
    # test
    test_record = {
        "Food Chain": "Subway",
        "Category": "Sandwitch",
        "Item": "Pizza Sub",
        "Price": 14.56,
        "Feeling": 7,
        "time": str(datetime.datetime.now())
    }
    fb = Firebase(CONST.FB_CONFIG)
    print(fb.get_records('jeongwon412@gmail.com', 'test1234'))

    # res = get_records('jeongwon412@uwo.ca', 'test1234')
    # res = push_record('jeongwon412@gmail.com', 'test1234', test_record)

    # for key, value in res.items():
    #   print(key+": "+str(value))
    # print(res)
