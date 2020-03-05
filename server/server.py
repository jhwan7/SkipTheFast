from flask import Flask, request, send_file

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

@app.route('/get_image')
def send_img():
    return send_file('test.png')


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
    

# curl -X PUT -d '{ "Food Chain": "Subway", "Category": "Sandwitch", "Item":"Pizza Sub", "Price":"14.56", "Feeling": "7" }' \ 'https://test-projec-5a491.firebaseio.com"/records/jeongwon412@gmail.com.json'