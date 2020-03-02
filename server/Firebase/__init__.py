# __init__.py
from CONST import FB_CONFIG
import pyrebase
firebase = pyrebase.initialize_app(FB_CONFIG)