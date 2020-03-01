from flask import Flask, request
# import Firebase

app = Flask(__name__)
@app.route('/')
def index():
    return "Hello World!"


@app.route('/test')
def test():
    return "test"


@app.route('/post', methods=['POST'])
def post_example():
    print(request.get_data())
    return "some return"


if __name__ == "__main__":
    app.run(debug=True, port=5000)

"""

You use something like

from flask import send_file

@app.route('/get_image')
def get_image():
    if request.args.get('type') == '1':
       filename = 'ok.gif'
    else:
       filename = 'error.gif'
    return send_file(filename, mimetype='image/gif')"""
