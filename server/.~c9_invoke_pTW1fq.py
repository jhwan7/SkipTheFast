import datetime
import pyrebase
import CONST
import matplotlib.pyplot as plt
import matplotlib.dates as md


class AttemptToDuplicateFirebase(Exception): pass


def get_week():
    date = datetime.datetime.now().date()
    one_day = datetime.timedelta(days=1)
    day_idx = date.weekday() % 7
    monday = date - datetime.timedelta(days=day_idx)
    date = monday
    week = [date.isoformat()]
    for n in range(6):
        date += one_day
        week.append(date.isoformat())
    return week


class Firebase:
    instance = None

    def __init__(self, fb_config):
        # singleton
        if Firebase.instance is not None:
            raise AttemptToDuplicateFirebase
        else:
            Firebase.instance = self

        # pyrebase initialization
        self.firebase = pyrebase.initialize_app(fb_config)
        self.db = self.firebase.database()
        self.auth = self.firebase.auth()

    def authenticate(self, email, pw):
        login = self.auth.sign_in_with_email_and_password(email, pw)
        return login

    def create_user(self, email, pw):
        fb_res = self.auth.create_user_with_email_and_password(email, pw)
        res = self.push_record(id=fb_res['localId'], idtk=fb_res['idToken'], record="None")
        return res

    def push_record(self, id, idtk, record):
        res = self.db.child('records').child(id).push(record, idtk)
        return res

    def get_records(self, id, idtk, **kwargs):
        data = self.db.child('records').child(id).get(idtk).val()
        data.popitem(last=False)
        records = {}
        for key, rec in data.items():
            if 'Time' in rec:
                tk = rec['Time']
                del rec['Time']  # K, V ==> Time, rec/Time
                if 'time' in kwargs.keys():
                    if kwargs['time'].strftime("%Y-%m-%d %H:%M:%S").split(' ')[0] == tk.split(' ')[0]:
                        # stamp = re.findall(r"\d+", tk)   
                        records[tk] = rec
                else:
                    records[tk] = rec
            else:
                print("Passed - no time value")
        return records

    def push_goal(self, id, idtk, goal):
        res = self.db.child('users').child(id).child('goal').set(goal, idtk)
        return res

    def get_goal(self, id, idtk):
        res = self.db.child('users').child(id).child('goal').get(idtk).val()
        return res

    def dayVSrecords(self, id, idtk):
        records = self.db.child("records").child(id).get(idtk).val()
        dates = []
        for key, value in records.items():
            try:
                dates.append(value['Time'].split(" ")[0])
            except:
                pass
        week = get_week()

        x = [datetime.datetime.strptime(d, "%Y-%m-%d").date() for d in week]
        y = [dates.count(day) for day in x]

        ax = plt.gca()
        formatter = md.DateFormatter("%d")
        ax.xaxis.set_major_formatter(formatter)
        locator = md.DayLocator()
        ax.xaxis.set_major_locator(locator)
        plt.xlabel("Week")
        plt.ylabel("Number of records")
        plt.title("Number of Records")
        plt.ylim(0)
        plt.plot(x, y)
        plt.savefig("basket/r/%(id)s.png" % {'id': id})

    def dayVSmoney(self, id, idtk):
        records = self.db.child("records").child(id).get(idtk).val()
        week = get_week()
        x = []
        y = []
        totalSpent = 0
        for day in week:
            for key, value in records.items():
                try:
                    if value['Time'].split(" ")[0] == day:
                        totalSpent += value['Price']
                except:
                    pass
            x.append(day)
            y.append(totalSpent)

        ax = plt.gca()
        formatter = md.DateFormatter("%d")
        ax.xaxis.set_major_formatter(formatter)
        locator = md.DayLocator()
        ax.xaxis.set_major_locator(locator)
        plt.xlabel("Week")
        plt.ylabel("Total spent")
        plt.title("Total Spent")
        plt.ylim(0)
        plt.plot(x, y)
        plt.savefig("basket/m/%(id)s.png" % {'id': id})

    def dayVScalories(self, id, idtk):
        records = self.db.child("records").child(id).get(idtk).val()
        week = get_week()
        x = []
        y = []
        for day in week:
            dailyCalories = 0
            for key, value in records.items():
                if value['Time'].split(" ")[0] == day:
                    dailyCalories += value['Calories']
            x.append(day)
            y.append(dailyCalories)

        ax = plt.gca()
        formatter = md.DateFormatter("%d")
        ax.xaxis.set_major_formatter(formatter)
        locator = md.DayLocator()
        ax.xaxis.set_major_locator(locator)
        plt.xlabel("Week")
        plt.ylabel("Total spent")
        plt.title("Total Spent")
        plt.ylim(0)
        plt.plot(x, y)
        plt.savefig("basket/c/%(id)s.png" % {'id': id})

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
