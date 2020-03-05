import datetime
import pyrebase


class UnableToLogInException(Exception):
    pass


class Firebase:
    def __init__(self, fb_config):
        self.firebase = pyrebase.initialize_app(fb_config)
        self.db = self.firebase.database()
        self.auth = self.firebase.auth()
        self.status = {
            'logged': False,
            'user': None
        }

    def sign_in(self, email, pw):
        try:
            login = self.auth.sign_in_with_email_and_password(email, pw)
            return login
        except UnableToLogInException:
            return False

    def create_user(self, email, pw):
        user = self.auth.create_user_with_email_and_password(email, pw)
        return user

    def send_pw_reset_email(self, email):
        return self.auth.send_password_reset_email(email)

    def get_records(self, email, pw):
        res = self.sign_in(email, pw)
        # res = requests.get(url=FB_RECORDS_ENDPOINT+".json")
        return self.db.child('records').child('jeongwon').get().val()

    def push_record(self, email, pw, record):
        data = {
            'time': str(datetime.datetime.now()),
            'record': record
        }

        res = self.sign_in(email, pw)
        self.db.child('records').child('jeongwon').push(record, res['idToken'])

        return res

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

    # res = get_records('jeongwon412@uwo.ca', 'test1234')
    # res = push_record('jeongwon412@gmail.com', 'test1234', test_record)

    # for key, value in res.items():
    #   print(key+": "+str(value))
    # print(res)
