package com.example.cargo;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class CustSendFeedback extends Activity implements JsonResponse{

	Button b1;
	EditText e1;
	ListView l1;
	String feed;
	SharedPreferences sh;
	String[] fid,feedback,date,reply,alldata;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cust_send_feedback);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		l1=(ListView)findViewById(R.id.listView1);
		
		e1=(EditText)findViewById(R.id.editText1);
		b1=(Button)findViewById(R.id.button1);
		
		viewfeedback();
		
		b1.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				feed=e1.getText().toString();
				if(feed.equalsIgnoreCase(""))
				{
					e1.setError("Enter feedback");
					e1.setFocusable(true);
				}
				else
				{
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)CustSendFeedback.this;
					String q="/sendfeedback?feed="+feed+"&logid="+sh.getString("logid", "");
					jr.execute(q);
					
				}
			}
		});
	}
	public void viewfeedback(){
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)CustSendFeedback.this;
		String q="/viewfeedbackreply?logid="+sh.getString("logid", "");
		jr.execute(q);
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("viewfeedbackreply"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");

					fid=new String[ja.length()];
					feedback=new String[ja.length()];
					reply=new String[ja.length()];
					date=new String[ja.length()];
					alldata=new String[ja.length()];
				
					for(int i=0;i<ja.length();i++)
					{
						fid[i]=ja.getJSONObject(i).getString("fed_id");
						feedback[i]=ja.getJSONObject(i).getString("description");
						date[i]=ja.getJSONObject(i).getString("date");
						reply[i]=ja.getJSONObject(i).getString("reply_description");

						alldata[i]="\nFeedback : "+feedback[i]+"\nReply : "+reply[i]+"\nDated : "+date[i]+"\n";
					}
					l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list_data,alldata));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No reply", Toast.LENGTH_LONG).show();
					
				}
			}
			else if(method.equalsIgnoreCase("sendfeedback"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(), "success..", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(), CustHome.class));
					
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cust_send_feedback, menu);
		return true;
	}
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),CustHome.class);			
		startActivity(b);
		
	}
}
