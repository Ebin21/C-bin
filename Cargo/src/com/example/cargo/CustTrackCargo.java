package com.example.cargo;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CustTrackCargo extends Activity implements JsonResponse
{
	ListView l1;
	SharedPreferences sh;
	String[] sid,place,date,alldata;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cust_track_cargo);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		l1=(ListView)findViewById(R.id.listView1);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)CustTrackCargo.this;
		String q="/custviewcargostatus?bid="+CustViewBookings.bid1;
		jr.execute(q);
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("custviewcargostatus"))
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
			
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cust_track_cargo, menu);
		return true;
	}

}
