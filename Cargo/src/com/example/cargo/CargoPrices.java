package com.example.cargo;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CargoPrices extends Activity implements JsonResponse{
	ListView l1;
	SharedPreferences sh;
	String[] pid,mweight,mheight,mwidth,mdis,minprice,alldata;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cargo_prices);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		l1=(ListView)findViewById(R.id.listView1);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)CargoPrices.this;
		String q="/viewcargoprices?brid="+CustViewNearbyCargoservice.brid1;
		jr.execute(q);
		
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("viewcargoprices"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					pid=new String[ja.length()];
					mweight=new String[ja.length()];
					mheight=new String[ja.length()];
					mwidth=new String[ja.length()];
					mdis=new String[ja.length()];
					minprice=new String[ja.length()];
					alldata=new String[ja.length()];					

					for(int i=0;i<ja.length();i++)
					{
						pid[i]=ja.getJSONObject(i).getString("price_id");
						mweight[i]=ja.getJSONObject(i).getString("max_weight");
						mheight[i]=ja.getJSONObject(i).getString("max_height");
						mwidth[i]=ja.getJSONObject(i).getString("max_width");
						mdis[i]=ja.getJSONObject(i).getString("max_distance");
						minprice[i]=ja.getJSONObject(i).getString("min_price");
						
						alldata[i]="\nMax Weight : "+mweight[i]+"\nMax Height : "+mheight[i]+"\nMax width : "+mwidth[i]+"\nMax Distance : "+mdis[i]+"\nMin Price : "+minprice[i]+"\n";
					}
					l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list_data,alldata));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No prices...", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),CustViewNearbyCargoservice.class));
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cargo_prices, menu);
		return true;
	}
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),CustViewNearbyCargoservice.class);			
		startActivity(b);
		
	}
}
