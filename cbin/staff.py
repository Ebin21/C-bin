from flask import *
from database import *
import datetime
staff=Blueprint('staff',__name__)
@staff.route('/staffhome',methods=['get','post'])
def staffhome():
	return render_template('staffhome.html')
@staff.route('/staff_mng_cargo',methods=['get','post'])
def staff_mng_cargo():
	data={}
	lid=session['login_id']
	

	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="samount":
		q=" SELECT `amount` FROM `bookings` WHERE `booking_id`='%s'"%(id)
		res=select(q)
		data['samount']=res

	if 'submit' in request.form:
		amount=request.form['amount']
		q="UPDATE `bookings` SET `amount`='%s',booking_status='cost sended' WHERE `booking_id`='%s'"%(amount,id)
		update(q)
		return redirect(url_for('staff.staff_mng_cargo'))
	if action=="Accept":
		q1="UPDATE `bookings` SET booking_status='accepted' WHERE `booking_id`='%s'"%(id)
		update(q1)
		return redirect(url_for('staff.staff_mng_cargo'))
	
		
	if action=="update":
		q1="select * from bookings where booking_id='%s'"%(id)
		res=select(q1)
		data['update_cargo']=res

	if 'update' in request.form:
		weight=request.form['weight']
		height=request.form['height']
		width=request.form['width']
	
		q="update bookings set weight='%s',height='%s',width='%s' where booking_id='%s'"%(weight,height,width,id)
		update(q)
		return redirect(url_for('staff.staff_mng_cargo'))
	
	q=" SELECT branch_id FROM `staffs` WHERE `login_id`='%s'"%(lid)
	res=select(q)
	brid=res[0]['branch_id']
	
	q="select * from deliveryboys d inner join branchemngr b  where d.branch_id=b.branch_id"
	res=select(q)
	data['view_boy']=res


	q="select * from bookings inner join customers USING(customer_id) where branch_id=(select branch_id from staffs where login_id='%s')"%(lid)
	res=select(q)
	data['viewbook']=res
	
	for i in range(1,len(res)+1):
		if 'assign'+str(i) in request.form:
			boyid=request.form['boyname'+str(i)]
			bookingid=request.form['booking_id'+str(i)]
			print("boy....",boyid)
			print("book....",bookingid)
			q="update bookings set boy_id='%s',booking_status='assign' where booking_id='%s'"%(boyid,bookingid)
			c=update(q)
			if c>0:
				flash('Updated successfully !!!')
				return redirect(url_for('staff.staff_mng_cargo'))
		else:
			print("else part.......")
	return render_template('staff_mng_cargo.html',data=data)
@staff.route('/staff_up_cargo_sts',methods=['get','post'])
def staff_up_cargo_sts():
	data={}	
	if 'submit' in request.form:
		booking_id=request.form['booking_id']
		place=request.form['place']
		now = datetime.datetime.now()
		date=now.strftime("%Y-%m-%d %H:%M:%S")
		q="insert into cargostatus values(null,'%s','%s','%s')"%(booking_id,place,date)
		insert(q)

	q="SELECT *,CONCAT(`f_name`,' ',`l_name`) AS NAME FROM bookings INNER JOIN customers USING (customer_id)"
	res=select(q)
	data['bookings']=res
	
	return render_template('staff_up_cargo_sts.html',data=data)
	

	