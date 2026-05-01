from flask import Blueprint,render_template,redirect,url_for,session,request,flash
from database import *
import demjson
import uuid


api = Blueprint('api',__name__)


@api.route('/registration',methods=['get','post'])
def registration():
	data={}
	first_name = request.args['fname']
	last_name = request.args['lname']
	phone = request.args['phone']
	email = request.args['email']
	latitude = request.args['latitude']
	longitude=request.args['longitude']
	username = request.args['uname']
	password = request.args['pass']
	
	q = "insert into login values(null,'%s','%s','customer')" % (username,password)
	id = insert(q)
	q = "insert into customers values(null,'%s','%s','%s','%s','%s','%s','%s')" % (id,first_name,last_name,phone,email,latitude,longitude)
	id=insert(q)
	if id>0:
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method']="register"
	return demjson.encode(data)
@api.route('/login',methods=['get','post'])
def login():
	data={}
	username = request.args['username']
	password = request.args['password']
	q = "select * from login where username='%s' and password='%s'" % (username,password)
	res = select(q)
	print(res)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)

@api.route('/boyviewassignedcargos',methods=['get','post'])
def boyviewassignedcargos():
	data={}
	logid = request.args['logid']
	q = "SELECT * FROM bookings INNER JOIN customers USING(customer_id) WHERE  bookings.boy_id=(SELECT boy_id FROM deliveryboys WHERE login_id='%s')" % (logid)
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']="boyviewassignedcargos"
	return  demjson.encode(data)


@api.route('/Boyviewcargoupdates',methods=['get','post'])
def Boyviewcargoupdates():
	data={}
	bid = request.args['bid']
	q = "SELECT * FROM cargostatus WHERE booking_id='%s'"%(bid)
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']="Boyviewcargoupdates"
	return  demjson.encode(data)

@api.route('/boyupdatecargostatus',methods=['get','post'])
def boyupdatecargostatus():
	data={}
	bid= request.args['bid']
	updates = request.args['updates']
	q = "insert into cargostatus values(null,'%s','%s',CONCAT(curdate(),' ',curtime()))" % (bid,updates)
	id = insert(q)
	if id>0:
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method']="boyupdatecargostatus"
	return demjson.encode(data)

@api.route('/viewnearbycargoservice',methods=['get','post'])
def viewnearbycargoservice():
	data={}
	lati = request.args['lati']
	longi = request.args['longi']
	q = "SELECT *,(3959 * ACOS ( COS ( RADIANS('%s') ) * COS( RADIANS( latitude) ) * COS( RADIANS( longitude ) - RADIANS('%s') ) + SIN ( RADIANS('%s') ) * SIN( RADIANS(latitude ) ))) AS user_distance  FROM branchemngr HAVING user_distance<31.068 ORDER BY user_distance ASC" % (lati,longi,lati)
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method'] = 'viewnearbycargoservice'
	return  demjson.encode(data)

@api.route('/viewcargoprices',methods=['get','post'])
def viewcargoprices():
	data={}
	brid = request.args['brid']
	q = "select * from prices where branch_id='%s'" % (brid)
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method'] = 'viewcargoprices'
	return  demjson.encode(data)

@api.route('/viewreviewsratings',methods=['get','post'])
def viewreviewsratings():
	data={}
	brid = request.args['brid']
	q = "select * from review_rating where branch_id='%s'" % (brid)
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method'] = 'viewreviewsratings'
	return  demjson.encode(data)

@api.route('/bookcargo',methods=['get','post'])
def bookcargo():
	data={}
	logid= request.args['logid']
	brid= request.args['brid']
	weight = request.args['weight']
	height = request.args['height']
	width = request.args['width']
	fromloc = request.args['fromloc']
	toloc = request.args['toloc']
	q = "insert into bookings values(null,(SELECT customer_id FROM customers WHERE login_id='%s'),curdate(),'%s','%s','%s','%s','%s','%s','0','Pending','0')" % (logid,brid,weight,height,width,fromloc,toloc)
	id = insert(q)
	if id>0:
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method']="bookcargo"
	return demjson.encode(data)
@api.route('/viewbookings',methods=['get','post'])
def viewbookings():
	data={}
	logid = request.args['logid']
	q = "select * from bookings inner join branchemngr USING(branch_id) where customer_id=(SELECT customer_id FROM customers WHERE login_id='%s')" % (logid)
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method'] = 'viewbookings'
	return  demjson.encode(data)

@api.route('/custviewcargostatus',methods=['get','post'])
def custviewcargostatus():
	data={}
	bid = request.args['bid']
	q = "select * from cargostatus  where booking_id='%s'" % (bid)
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method'] = 'custviewcargostatus'
	return  demjson.encode(data)

@api.route('/makepayment',methods=['get','post'])
def makepayment():
	data={}
	amt= request.args['amt']
	bookid = request.args['bookid']
	q = "select * from payment where booking_id='%s'" % (bookid)
	res = select(q)
	if res:
		data['status'] = 'failed'
	else:
		q = "update bookings set booking_status='paid' where booking_id='%s'" % (bookid)
		id = update(q)
		q = "insert into payment values(null,'%s','%s',curdate())" % (bookid,amt)
		id = insert(q)

		if id>0:
			data['status'] = 'success'
		else:
			data['status'] = 'failed'
	data['method']="makepayment"
	return demjson.encode(data)

@api.route('/sendfeedback',methods=['get','post'])
def sendfeedback():
	data={}
	logid= request.args['logid']
	feed= request.args['feed']
	q = "insert into feedback values(null,(SELECT customer_id FROM customers WHERE login_id='%s'),'%s','Pending',curdate())" % (logid,feed)
	id = insert(q)
	if id>0:
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method']="sendfeedback"
	return demjson.encode(data)

@api.route('/viewfeedbackreply',methods=['get','post'])
def viewfeedbackreply():
	data={}
	logid= request.args['logid']
	q = "SELECT * FROM feedback where customer_id=(SELECT customer_id FROM customers WHERE login_id='%s')"%(logid)
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method'] = 'viewfeedbackreply'
	return  demjson.encode(data)

@api.route('/viewcargocompanies',methods=['get','post'])
def viewcargocompanies():
	data={}
	q = "SELECT * FROM branchemngr"
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method'] = 'viewcargocompanies'
	return  demjson.encode(data)

@api.route('/ratecompany',methods=['get','post'])
def ratecompany():
	data={}
	logid= request.args['logid']
	brid= request.args['brid']
	review= request.args['review']
	rating= request.args['rating']
	q = "select * from review_rating where customer_id=(SELECT customer_id FROM customers WHERE login_id='%s') and branch_id='%s'" % (logid,brid)
	res = select(q)
	if res:
		data['status'] = 'failed'
	else:
		q = "insert into review_rating values(null,(SELECT customer_id FROM customers WHERE login_id='%s'),'%s','%s','%s',curdate())" % (logid,brid,review,rating)
		id = insert(q)
		if id>0:
			data['status'] = 'success'
		else:
			data['status'] = 'failed'
	data['method']="ratecompany"
	return demjson.encode(data)

@api.route('/viewprofile',methods=['get','post'])
def viewprofile():
	data={}
	logid=request.args['logid']
	q = "SELECT * FROM customers inner join login on customers.login_id=login.login_id where customers.login_id='%s'"%(logid)
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method'] = 'viewprofile'
	return  demjson.encode(data)

@api.route('/updateprofile',methods=['get','post'])
def updateprofile():
	data={}
	logid = request.args['logid']
	first_name = request.args['fname']
	last_name = request.args['lname']
	phone = request.args['phone']
	email = request.args['email']
	password = request.args['pass']
	
	q = "update login set password='%s' where login_id='%s'" % (password,logid)
	id = update(q)
	q = "update customers set f_name='%s',l_name='%s',phone='%s',email='%s' where login_id='%s'" % (first_name,last_name,phone,email,logid)
	id=update(q)
	if id>0:
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method']="updateprofile"
	return demjson.encode(data)

@api.route('/viewbranches',methods=['get','post'])
def viewbranches():
	data={}

	q = "SELECT * FROM branchemngr"
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method'] = 'viewbranches'
	return  demjson.encode(data)