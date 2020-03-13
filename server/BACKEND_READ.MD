# Requirements
* python -v 3.6 >
* virtualenv
* pip3

# Running Backend
`pip3 install -r requirements.txt`
`export FLASK_APP=server.py`
`flask run`

# FB Data Schema
```json
root: {
    user_id:{
       push_key_initial: "None",
       push_key:{
          Category: %s,
          Feeling: %d,
          FoodChain: $s,
          Item: %s,
          Time: %YYYY:%MM:%DD %HH:%MM:%SS
       }    
    }
 }
 ```
# API
ip, port are provided upon running AWS server. 
### Create User
[PUT] http://{ip}:{port}/user <br/>
`curl --location --request PUT 'http://127.0.0.1:5000/user' \
--data-urlencode 'email={email}' \
--data-urlencode 'password={password}'`
### Authenticate
[POST] http://{ip}:{port}/login <br/>
`curl --location --request POST 'http://127.0.0.1:5000/login' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode 'email={email}' \
  --data-urlencode 'password={password}'`
### Get Data
[POST] http://{ip}:{port}/data <br/>
`curl --location --request PUT 'http://127.0.0.1:5000/data' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'userId={userId}' \
--data-urlencode 'idToken={idToken}' \
--data-urlencode 'Category={C}' \
--data-urlencode 'Feeling={F}' \
--data-urlencode 'FoodChain={FC}' \
--data-urlencode 'Item={I}'`
### Push data
[PUT] http://{ip}:{port}/login <br/>
`
curl --location --request POST 'http://127.0.0.1:5000/data' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'userId={userId}' \
--data-urlencode 'idToken={idToken}' \
--data-urlencode 'year={year}' \
--data-urlencode 'month={month}' \
--data-urlencode 'day={day}'
`
