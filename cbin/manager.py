from flask import *
from database import *
manager=Blueprint('manager',__name__)
@manager.route('/managerhome',methods=['get','post'])
def managerhome():
	return render_template('managerhome.html')
@manager.route('/mngr_reg_staff',methods=['get','post'])
def mngr_reg_staff():

	data={}
	lid=session['login_id']
	
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from staffs where staff_id='%s'"%(id)
		delete(q)
		
	if 'submit' in request.form:
		fname=request.form['fname']
		lname=request.form['lname']
		branch_name=request.form['branch_name']
		email=request.form['email']
		phone=request.form['phone']
		username=request.form['username']
		password=request.form['password']
		q="insert into login values(null,'%s','%s','staff')"%(username,password)
		idd=insert(q)
		q1="insert into staffs values(null,'%s','%s','%s','%s','%s','%s')"%(idd,fname,lname,branch_name,email,phone,)
		insert(q1)
		return redirect(url_for('manager.mngr_reg_staff'))
	q2="SELECT * FROM branchemngr where `login_id`='%s'"%(lid)
	res=select(q2)
	data['view_branch']=res
	q3="select * from staffs INNER join branchemngr USING(branch_id)"
	res=select(q3)
	data['viewstaff']=res

	return render_template('mngr_reg_staff.html',data=data)
@manager.route('/mngr_reg_delboy',methods=['get','post'])
def mngr_reg_delboy():
	data={}
	lid=session['login_id']
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from deliveryboys where boy_id='%s'"%(id)
		delete(q)
		
	if 'submit' in request.form:
		fname=request.form['fname']
		lname=request.form['lname']
		branch_name=request.form['branch_name']
		phone=request.form['phone']
		email=request.form['email']
		username=request.form['username']
		password=request.form['password']
		q="insert into login values(null,'%s','%s','boy')"%(username,password)
		idd=insert(q)
		q1="insert into deliveryboys values(null,'%s','%s','%s','%s','%s','%s')"%(idd,fname,lname,branch_name,phone,email)
		insert(q1)
		return redirect(url_for('manager.mngr_reg_delboy'))
	q2="SELECT * FROM branchemngr where `login_id`='%s'"%(lid)
	res=select(q2)
	data['view_branch']=res
	q3="SELECT *,CONCAT(f_name,'',l_name) AS NAME FROM `deliveryboys`INNER JOIN `branchemngr`USING(`branch_id`)"
	res=select(q3)
	data['viewboy']=res

	return render_template('mngr_reg_delboy.html',data=data)
@manager.route('/mng_view_reg_cus',methods=['get','post'])
def mng_view_reg_cus():
	data={}
	lid=session['login_id']
	q="select * from customers"
	res=select(q)
	data['viewcus']=res
	return render_template('mng_view_reg_cus.html',data=data)
@manager.route('/mng_view_cargo_buk',methods=['get','post'])
def mng_view_cargo_buk():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="status":
		q="SELECT * FROM cargostatus where booking_id='%s'"%(id)
		res=select(q)
		data['view_status']=res
	q="select * from bookings inner join customers USING(customer_id) inner join branchemngr USING(branch_id)"
	res=select(q)
	data['viewbook']=res
	return render_template('mng_view_cargo_buk.html',data=data)
@manager.route('/mngr_view_payment',methods=['get','post'])
def mngr_view_payment():
	data={}
	q="SELECT *,CONCAT(f_name,'',l_name) AS NAME FROM `customers`INNER JOIN `bookings`USING(`customer_id`) INNER JOIN `payment`USING(`booking_id`)"
	res=select(q)
	data['viewpayment']=res
	return render_template('mngr_view_payment.html',data=data)
@manager.route('/mng_view_carstatus',methods=['get','post'])
def mmng_view_carstatus():
	data={}
	q="SELECT *,CONCAT(`f_name`,' ',`l_name`) AS NAME FROM `cargostatus` INNER JOIN `bookings` USING(`booking_id`) INNER JOIN `customers` USING(`customer_id`)"
	res=select(q)
	data['view_status']=res

	return render_template('mng_view_carstatus.html',data=data)
	
#  @manager.route('/mg_view_cargo_buk',methods=['get','post'])
# def mg_view_cargo_buk():
# 	data={}
# 	q="select * from bookings"
# 	res=select(q)
# 	data['viewbook']=res
# 	return render_template('mg_view_cargo_buk.html',data=data)
	
	