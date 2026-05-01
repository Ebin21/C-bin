from flask import *
from database import *
admin=Blueprint('admin',__name__)
@admin.route('/adminhome',methods=['get','post'])
def adminhome():
	return render_template('adminhome.html')
@admin.route('/adm_reg_manager',methods=['get','post'])
def adm_reg_manager():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from branchemngr where branch_id='%s'"%(id)
		delete(q)
		

	if 'submit' in request.form:
		name=request.form['name']
		latitude=request.form['lat']
		longitude=request.form['lon']
		phone=request.form['phone']
		email=request.form['email']
		username=request.form['username']
		password=request.form['password']
		q1="insert into login values(null,'%s','%s','branchemngr')"%(username,password)
		idd=insert(q1)
		q="insert into branchemngr values(null,'%s','%s','%s','%s','%s','%s')"%(idd,name,latitude,longitude,phone,email)
		insert(q)
		return redirect(url_for('admin.adm_reg_manager'))
	q2= "select * from branchemngr"
	res=select(q2)
	data['branchemngr']=res
		
	return render_template('adm_reg_manager.html',data=data)
@admin.route('/adm_mng_cargo_price',methods=['get','post'])
def adm_mng_cargo_price():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from prices where price_id='%s'"%(id)
		delete(q)
	if action=="update":
		q="select * from prices where price_id='%s'"%(id)
		res=select(q)
		data['updatedata']=res
	if 'update' in request.form:
		max_weight=request.form['max_weight']
		max_height=request.form['max_height']
		max_width=request.form['max_width']
		max_distance=request.form['max_distance']
		min_price=request.form['min_price']
		q="update prices set max_weight='%s',max_height='%s',max_width='%s',max_distance='%s',min_price='%s'"%(max_weight,max_height,max_width,max_distance,min_price)
		update(q)
		
		
	if 'submit' in request.form:
		branch_id=request.form['branch_id']
		max_weight=request.form['max_weight']
		max_height=request.form['max_height']
		max_width=request.form['max_width']
		max_distance=request.form['max_distance']
		min_price=request.form['min_price']
		q="insert into prices values(null,'%s','%s','%s','%s','%s','%s')"%(branch_id,max_weight,max_height,max_width,max_distance,min_price)
		insert(q)
		return redirect(url_for('admin.adm_mng_cargo_price'))
	q1="SELECT * FROM branchemngr" 
	res=select(q1)
	data['view_branch']=res
	q2="SELECT * FROM `branchemngr`INNER JOIN `prices`USING(`branch_id`)"
	res=select(q2)
	data['view_price']=res
	return render_template('adm_mng_cargo_price.html',data=data)
@admin.route('/adm_view_staff',methods=['get','post'])
def adm_view_staff():
	data={}
	q="SELECT * FROM `staffs`INNER JOIN `branchemngr`USING(`branch_id`)"
	res=select(q)
	data['viewstaff']=res
	return render_template('adm_view_staff.html',data=data)
@admin.route('/adm_view_delboy',methods=['get','post'])
def adm_view_delboy():
	data={}
	q="select * from deliveryboys INNER JOIN `branchemngr`USING(`branch_id`)"
	res=select(q)
	data['viewboy']=res
	return render_template('adm_view_delboy.html',data=data)
@admin.route('/adm_View_review',methods=['get','post'])
def adm_View_review():
	data={}
	q="SELECT *,CONCAT(`f_name`,' ',`l_name`) AS CNAME FROM review_rating INNER JOIN customers USING(`customer_id`) INNER JOIN branchemngr USING(branch_id)"
	res=select(q)
	data['view_review']=res
	return render_template('adm_View_review.html',data=data)
@admin.route('/adm_view_feedback',methods=['get','post'])
def adm_view_feedback():
	data={}
	q="SELECT *,CONCAT(`f_name`,' ',`l_name`) AS NAME FROM `customers` INNER JOIN `feedback` USING(`customer_id`)"
	res=select(q)
	data['view']=res
	for i in range(1,len(res)+1):
		if 'replys'+str(i) in request.form:
			reply=request.form['reply'+str(i)]
			fed_id=request.form['fed_id'+str(i)]
			q="update feedback set reply_description='%s' where fed_id='%s'"%(reply,fed_id)
			c=update(q)
			if c>0:
				flash('Updated successfully !!!')
				return redirect(url_for('admin.adm_view_feedback'))
	return render_template('adm_view_feedback.html',data=data)

	