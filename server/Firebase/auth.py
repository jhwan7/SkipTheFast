from __init__ import firebase

status = {
    logged: False,
    user: None
}

auth = firebase.auth()


class UnableToLogInException(Exception):
    pass


def sign_in(email, pw):
    try:
        login = auth.sign_in_with_email_and_password(email, pw)
        return login
    except UnableToLogInException:
        return False


def create_user(email, pw):
    user = auth.create_user_with_email_and_password(email, pw)
    return user


def send_pw_reset_email(email):
    res = auth.send_password_reset_email(email)
    return res


# test1234
# https://www.youtube.com/watch?v=rKuGCQda_Qo
#login = auth.sign_in_with_email_and_password(email, pw)
# auth.send_email_verification()
# auth.send_password_reset_email()