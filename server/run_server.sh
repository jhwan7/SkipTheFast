python3 -m venv venv
source venv/bin/activate
python3 -m pip install -r requirements.txt
export FLASK_APP=server.py

if [ $# -eq 0 ]
then
	echo "running flask on 0.0.0.0:8000"
	python3 server.py
else
	while [ -n "$1" ]; do
		case "$1" in
			-l)	echo "running flask in localhost:5000"
				flask run;;
			*)	echo "no %1 option recognized";;
		esac
		shift
	done
fi


