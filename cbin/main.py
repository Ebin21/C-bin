from flask import *
from public import public
from admin import admin
from manager import manager
from staff import staff
from api import api
app=Flask(__name__)
app.secret_key="aju"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(manager,url_prefix='/manager')
app.register_blueprint(staff,url_prefix='/staff')
app.register_blueprint(api,url_prefix='/api')
app.run(debug=True,host="192.168.43.148",port="5004")