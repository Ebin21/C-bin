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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BoyViewAssigncargos extends Activity implements OnItemClickListener,JsonResponse{
	ListView l1;
	SharedPreferences sh;
	String[] bid,cname,bdate,fromloc,toloc,amount,alldata;
	public static String bid1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boy_view_assigncargos);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		l1=(ListView)findViewById(R.id.listView1);
		l1.setOnItemClickListener(this);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)BoyViewAssigncargos.this;
		String q="/boyviewassignedcargos?logid="+sh.getString("logid", "");
		jr.execute(q);
		
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("boyviewassignedcargos"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					bid=new String[ja.length()];
					cname=new String[ja.length()];
					bdate=new String[ja.length()];
					fromloc=new String[ja.length()];
					toloc=new String[ja.length()];
					amount=new String[ja.length()];
					alldata=new String[ja.length()];					

					for(int i=0;i<ja.length();i++)
					{
						bid[i]=ja.getJSONObject(i).getString("booking_id");
						cname[i]=ja.getJSONObject(i).getString("f_name")+" "+ja.getJSONObject(i).getString("l_name");
						bdate[i]=ja.getJSONObject(i).getString("booking_date");
						fromloc[i]=ja.getJSONObject(i).getString("from_location");
						toloc[i]=ja.getJSONObject(i).getString("to_location");
						amount[i]=ja.getJSONObject(i).getString("amount");
						
						alldata[i]="\nbooked date : "+bdate[i]+"\nCustomer name : "+cname[i]+"\nFrom : "+fromloc[i]+"\nTo : "+toloc[i]+"\nAmount : "+amount[i]+"\n";
						
					}
					l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list_data,alldata));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No assigned cargos", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),BoyHome.class));
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
		getMenuInflater().inflate(R.menu.boy_view_assigncargos, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		bid1=bid[arg2];
		 final CharSequence[] items = {"Update cargo status" ,"Cancel"};

		  AlertDialog.Builder builder = new AlertDialog.Builder(BoyViewAssigncargos.this);
		  builder.setTitle("Select Option!");
		  builder.setItems(items, new DialogInterface.OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int item) {

		    if (items[item].equals("Update cargo status")) 
		    {  
		    	Intent i1=new Intent(getApplicationContext(),Boyupdatecargostatus.class);
				startActivity(i1);
		    }
		if (items[item].equals("Cancel")) {
                   dialog.dismiss();
               }
		    }
		   
		  });
		  builder.show();
		
	}
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),BoyHome.class);			
		startActivity(b);
		
	}
}
