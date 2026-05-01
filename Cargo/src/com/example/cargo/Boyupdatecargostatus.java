package com.example.cargo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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

public class Boyupdatecargostatus extends Activity implements JsonResponse{
	Button b1;
	EditText e1;
	ListView l1;
	String updates;
	SharedPreferences sh;
	String[] sid,place,date,alldata;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boyupdatecargostatus);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		l1=(ListView)findViewById(R.id.listView1);
		
		e1=(EditText)findViewById(R.id.editText1);
		b1=(Button)findViewById(R.id.button1);
		
		viewupdates();
		
		b1.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				updates=e1.getText().toString();
				if(updates.equalsIgnoreCase(""))
				{
					e1.setError("Enter updates");
					e1.setFocusable(true);
				}
				else
				{
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)Boyupdatecargostatus.this;
					String q="/boyupdatecargostatus?bid="+BoyViewAssigncargos.bid1+"&updates="+updates.replace(" ", "%20");
					jr.execute(q);
					
				}
			}
		});
	}
	public void viewupdates(){
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Boyupdatecargostatus.this;
		String q="/Boyviewcargoupdates?bid="+BoyViewAssigncargos.bid1;
		jr.execute(q);
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("Boyviewcargoupdates"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");

					sid=new String[ja.length()];
					place=new String[ja.length()];
					date=new String[ja.length()];
					alldata=new String[ja.length()];
				
					for(int i=0;i<ja.length();i++)
					{
						sid[i]=ja.getJSONObject(i).getString("status_id");
						place[i]=ja.getJSONObject(i).getString("place");
						date[i]=ja.getJSONObject(i).getString("datetime");

						alldata[i]="\n"+place[i]+"\nDated : "+date[i]+"\n";
					}
					l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list_data,alldata));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No updates", Toast.LENGTH_LONG).show();
					
				}
			}
			else if(method.equalsIgnoreCase("boyupdatecargostatus"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(), "success..", Toast.LENGTH_LONG).show();
					viewupdates();
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
		getMenuInflater().inflate(R.menu.boyupdatecargostatus, menu);
		return true;
	}
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),BoyViewAssigncargos.class);			
		startActivity(b);
		
	}
}
