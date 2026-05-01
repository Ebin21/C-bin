package com.example.cargo;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class CustViewBookings extends Activity implements OnItemClickListener,JsonResponse{
	ListView l1;
	SharedPreferences sh;
	String[] bid,bname,bdate,weight,height,width,fromloc,toloc,amount,bstatus,alldata;
	public static String bid1,amount1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cust_view_bookings);

		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		l1=(ListView)findViewById(R.id.listView1);
		l1.setOnItemClickListener(this);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)CustViewBookings.this;
		String q="/viewbookings?logid="+sh.getString("logid", "");
		jr.execute(q);
		
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("viewbookings"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
				
					bid=new String[ja.length()];
					bname=new String[ja.length()];
					bdate=new String[ja.length()];
					weight=new String[ja.length()];
					height=new String[ja.length()];
					width=new String[ja.length()];
					fromloc=new String[ja.length()];
					toloc=new String[ja.length()];
					amount=new String[ja.length()];
					bstatus=new String[ja.length()];
					alldata=new String[ja.length()];					

					for(int i=0;i<ja.length();i++)
					{
						bid[i]=ja.getJSONObject(i).getString("booking_id");
						bname[i]=ja.getJSONObject(i).getString("name");
						bdate[i]=ja.getJSONObject(i).getString("booking_date");
						weight[i]=ja.getJSONObject(i).getString("Weight");
						height[i]=ja.getJSONObject(i).getString("height");
						width[i]=ja.getJSONObject(i).getString("width");
						fromloc[i]=ja.getJSONObject(i).getString("from_location");
						toloc[i]=ja.getJSONObject(i).getString("to_location");
						amount[i]=ja.getJSONObject(i).getString("amount");
						bstatus[i]=ja.getJSONObject(i).getString("booking_status");
						
						alldata[i]="\nbooked date : "+bdate[i]+"\nBranch name : "+bname[i]+"\nWeight : "+weight[i]+" kg\nHeight : "+height[i]+" m\nWidth : "+width[i]+" m\nFrom : "+fromloc[i]+"\nTo : "+toloc[i]+"\nAmount : "+amount[i]+"\nStatus : "+bstatus[i]+"\n";
						
					}
					l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list_data,alldata));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No bookings yet..", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),CustHome.class));
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		bid1=bid[arg2];
		amount1=amount[arg2];
		if(amount1.equalsIgnoreCase("0"))
		{
			
			 final CharSequence[] items = {"Track cargo status" ,"Cancel"};

			  AlertDialog.Builder builder = new AlertDialog.Builder(CustViewBookings.this);
			  builder.setTitle("Select Option!");
			  builder.setItems(items, new DialogInterface.OnClickListener() {
			   @Override
			   public void onClick(DialogInterface dialog, int item) {

			    if (items[item].equals("Track cargo status")) 
			    {  
			    	Intent i1=new Intent(getApplicationContext(),CustTrackCargo.class);
					startActivity(i1);
			    }
			if (items[item].equals("Cancel")) {
	                   dialog.dismiss();
	               }
			    }
			   
			  });
			  builder.show();
		}
		else
		{
			
			final CharSequence[] items = {"Make payment","Track cargo status" ,"Cancel"};

			  AlertDialog.Builder builder = new AlertDialog.Builder(CustViewBookings.this);
			  builder.setTitle("Select Option!");
			  builder.setItems(items, new DialogInterface.OnClickListener() {
			   @Override
			   public void onClick(DialogInterface dialog, int item) {

			    if (items[item].equals("Track cargo status")) 
			    {  
			    	Intent i1=new Intent(getApplicationContext(),CustTrackCargo.class);
					startActivity(i1);
			    }
			    if (items[item].equals("Make payment")) 
			    {  
			    	Intent i1=new Intent(getApplicationContext(),Custmakepayment.class);
					startActivity(i1);
			    }
			if (items[item].equals("Cancel")) {
	                   dialog.dismiss();
	               }
			    }
			   
			  });
			  builder.show();
		}
		
	}
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),CustHome.class);			
		startActivity(b);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cust_view_bookings, menu);
		return true;
	}

}
