from flask import *
from database import *
public=Blueprint('public',__name__)
@public.route('/',methods=['get','post'])
def home():
	return render_template('index.html')
	pass
@public.route('/login',methods=['get','post'])
def login():
	if 'login'in request.form:
		username=request.form['username']
		password=request.form['password']
		q="select * from login where username='%s' and password='%s'"%(username,password)
		res=select(q)
		if res:
			session['login_id']=res[0]['login_id']
			if res[0]['user_type']=="admin":
				return redirect(url_for('admin.adminhome'))
			if res[0]['user_type']=="branchemngr":
				return redirect(url_for('manager.managerhome'))
			if res[0]['user_type']=="staff":
				return redirect(url_for('staff.staffhome'))
				pass
				pass

			pass
		pass
	return render_template('login.html')
	pass
