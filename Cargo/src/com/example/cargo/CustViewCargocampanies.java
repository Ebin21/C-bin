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

public class CustViewCargocampanies extends Activity implements OnItemClickListener,JsonResponse{

	ListView l1;
	SharedPreferences sh;
	String[] brid,bname,phone,email,alldata;
	public static String brid1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cust_view_cargocampanies);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		l1=(ListView)findViewById(R.id.listView1);
		l1.setOnItemClickListener(this);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)CustViewCargocampanies.this;
		String q="/viewcargocompanies";
		jr.execute(q);
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("viewcargocompanies"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					brid=new String[ja.length()];
					bname=new String[ja.length()];
					phone=new String[ja.length()];
					email=new String[ja.length()];
					alldata=new String[ja.length()];					

					for(int i=0;i<ja.length();i++)
					{
						brid[i]=ja.getJSONObject(i).getString("branch_id");
						bname[i]=ja.getJSONObject(i).getString("name");
						phone[i]=ja.getJSONObject(i).getString("phone");
						email[i]=ja.getJSONObject(i).getString("email");
						
						alldata[i]="\nBranch : "+bname[i]+"\nPh : "+phone[i]+"\nEmail : "+email[i]+"\n";
					}
					l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list_data,alldata));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No cargo services here...", Toast.LENGTH_LONG).show();
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		brid1=brid[arg2];
		 final CharSequence[] items = {"Rate the company" ,"Cancel"};

		  AlertDialog.Builder builder = new AlertDialog.Builder(CustViewCargocampanies.this);
		  builder.setTitle("Select Option!");
		  builder.setItems(items, new DialogInterface.OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int item) {

		    if (items[item].equals("Rate the company")) 
		    {  
		    	Intent i1=new Intent(getApplicationContext(),CustRate.class);
				startActivity(i1);
		    }
		   
		if (items[item].equals("Cancel")) {
                   dialog.dismiss();
               }
		   }
		  });
		  builder.show();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cust_view_cargocampanies, menu);
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
