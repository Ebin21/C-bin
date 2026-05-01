package com.example.cargo;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class UpdateProfile extends Activity  implements JsonResponse
{
	Button b1;
	EditText e1,e2,e3,e4,e5,e6;
	ListView l1;
	String fname,lname,phone,email,pass;
	SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_profile);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)UpdateProfile.this;
		String q="/viewprofile?logid="+sh.getString("logid","");
		jr.execute(q);
		
		e1=(EditText)findViewById(R.id.editTextfname);
		e2=(EditText)findViewById(R.id.editTextlname);
		e3=(EditText)findViewById(R.id.editTextphone);
		e4=(EditText)findViewById(R.id.editTextemail);

		e6=(EditText)findViewById(R.id.editTextpass);
		
		b1=(Button)findViewById(R.id.button1);
		
		b1.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				fname=e1.getText().toString();
				lname=e2.getText().toString();
				phone=e3.getText().toString();
				email=e4.getText().toString();
			
				pass=e6.getText().toString();
				if(fname.equalsIgnoreCase(""))
				{
					e1.setError("Enter firstname");
					e1.setFocusable(true);
				}
				else if(lname.equalsIgnoreCase(""))
				{
					e2.setError("Enter lastname");
					e3.setFocusable(true);
				}
				else if(phone.equalsIgnoreCase(""))
				{
					e3.setError("Enter phone no");
					e3.setFocusable(true);
				}
				else if(phone.length()!=10)
				{
					
					e3.setError("enter valid phone number");
					e3.setFocusable(true);
				}
				else if(email.equalsIgnoreCase(""))
				{
					e4.setError("Enter email");
					e4.setFocusable(true);
				}
				else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
				{
					
					e4.setError("enter valid email address");
					e4.setFocusable(true);
				}
				
				else if(pass.equalsIgnoreCase(""))
				{
					e6.setError("Enter password");
					e6.setFocusable(true);
				}
				else
				{
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)UpdateProfile.this;
					String q="/updateprofile?logid="+sh.getString("logid","")+"&fname="+fname+"&lname="+lname+"&phone="+phone+"&email="+email+"&pass="+pass;
					jr.execute(q);
					
				}
			}
		});
	}
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),CustHome.class);			
		startActivity(b);
		
	}

	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("updateprofile"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(), "updated..", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(), UpdateProfile.class));
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
				}
			}
			else if(method.equalsIgnoreCase("viewprofile"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					
					e1.setText(ja.getJSONObject(0).getString("f_name"));
					e2.setText(ja.getJSONObject(0).getString("l_name"));
					e3.setText(ja.getJSONObject(0).getString("phone"));
					e4.setText(ja.getJSONObject(0).getString("email"));
					e6.setText(ja.getJSONObject(0).getString("password"));
				
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_profile, menu);
		return true;
	}

}
