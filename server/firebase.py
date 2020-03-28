import datetime
import pyrebase
import CONST
import matplotlib.pyplot as plt
import matplotlib.dates as md

from matplotlib.figure import Figure
from io import BytesIO
import io
import base64

class AttemptToDuplicateFirebase(Exception): pass


def get_week():
    # date = datetime.datetime.now().date()
    # one_day = datetime.timedelta(days=1)
    # day_idx = date.weekday() % 7
    # monday = date - datetime.timedelta(days=day_idx)
    # date = monday
    # week = [date.isoformat()]
    # for n in range(6):
    #     date += one_day
    #     week.append(date.isoformat())
    today = datetime.date.today()
    week = []
    for i in range(7):
        week.append((today - datetime.timedelta(days=6-i)).isoformat())
        
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
        if res == None:
            return {
                "Goal":"None"
            }
        return res

    def graph_days_vs_record(self, id, idtk):
        records = self.db.child("records").child(id).get(idtk).val()

        dates = get_week()
        acc_counts = []

        total_daily_count = 0
        for day in dates:
            for record in records.values():
                try:
                    recorded_date = record['Time'].split(" ")[0]
                    if day == recorded_date:
                        total_daily_count += 1
                except:
                    pass
            acc_counts.append(total_daily_count)

        x = [datetime.datetime.strptime(d, "%Y-%m-%d").date() for d in dates]
        y = acc_counts
        

        fig = Figure()
        ax = fig.subplots()
        ax.set_facecolor('xkcd:salmon')
        formatter = md.DateFormatter("%d")
        ax.xaxis.set_major_formatter(formatter)
        locator = md.DayLocator()
        ax.xaxis.set_major_locator(locator)
        ax.set_xlabel("Week")
        ax.set_ylabel("Number of records")
        ax.set_title("Number of Records")
        ax.set_ylim(0, total_daily_count + 4)
        ax.set_axisbelow(True)
        ax.grid(color='w', linestyle='-', linewidth=0.5)
        ax.fill_between(x, y, facecolor='white')
        ax.fill_between(x, y, facecolor= 'yellow', alpha=0.5)
        ax.plot(x, y, color='yellow', alpha=0.7)

        buffer = BytesIO()
        fig.savefig(buffer, format="png")
        buffer.seek(0)
        return buffer
        
        
    def graph_days_vs_spent(self, id, idtk):
        records = self.db.child("records").child(id).get(idtk).val()
        dates = get_week()

        spents = []
        total_spent = 0
        
        for day in dates:
            for key, value in records.items():
                try:
                    if value['Time'].split(" ")[0] == day:
                        total_spent += value['Price']
                except:
                    pass
            spents.append(total_spent)
        
        x = [datetime.datetime.strptime(d, "%Y-%m-%d").date() for d in dates]
        y = spents
        
        fig = Figure()
        ax = fig.gca()
        ax.set_facecolor('xkcd:salmon')
        formatter = md.DateFormatter("%d")
        ax.xaxis.set_major_formatter(formatter)
        locator = md.DayLocator()
        ax.xaxis.set_major_locator(locator)
        ax.set_xlabel("Week")
        ax.set_ylabel("Total Spent")
        ax.set_title("Weekly Total Spent")
        ax.set_ylim(0, total_spent + 5)
        ax.set_axisbelow(True)
        ax.grid(color='w', linestyle='-', linewidth=0.5)
        ax.fill_between(x, y, facecolor='white')
        ax.fill_between(x, y, facecolor='green', alpha=0.5)
        ax.plot(x, y, color='green', alpha=0.7)

        buffer = BytesIO()
        fig.savefig(buffer, format="png")
        buffer.seek(0)
        return buffer
    
    def send_pw_reset_email(self, email):
        return self.auth.send_password_reset_email(email)
    
    def encode_email(self, email):
        utf_email = email.encode('utf-8')
        return base64.b64encode(utf_email)
    
    def decode_email(self, encoded_email):
        return encoded_email.decode('utf-8')


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
