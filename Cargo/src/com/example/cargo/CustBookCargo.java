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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class CustBookCargo extends Activity implements JsonResponse,OnItemSelectedListener{

	Button b1;
	EditText e1,e2,e3,e4;
	String weight,height,width,fromloc,toloc;
	SharedPreferences sh;
	String[] bid,bname,alldata;
	Spinner s1;
	public static String bid1,toloc1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cust_book_cargo);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	
		e1=(EditText)findViewById(R.id.editTextweight);
		e2=(EditText)findViewById(R.id.editTextheight);
		e3=(EditText)findViewById(R.id.editTextwidth);
		e4=(EditText)findViewById(R.id.editTextfloc);
		e4.setText(CustViewNearbyCargoservice.bname1);
		s1=(Spinner)findViewById(R.id.spinner1);
		b1=(Button)findViewById(R.id.button1);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)CustBookCargo.this;
		String q="/viewbranches";
		jr.execute(q);
		
		b1.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				weight=e1.getText().toString();
				height=e2.getText().toString();
				width=e3.getText().toString();
				fromloc=e4.getText().toString();
				toloc=s1.getSelectedItem().toString();
				
				if(weight.equalsIgnoreCase(""))
				{
					e1.setError("Enter weight");
					e1.setFocusable(true);
				}
				else if(height.equalsIgnoreCase(""))
				{
					e2.setError("Enter height");
					e2.setFocusable(true);
				}
				else if(width.equalsIgnoreCase(""))
				{
					e3.setError("Enter width");
					e3.setFocusable(true);
				}
				else if(fromloc.equalsIgnoreCase(""))
				{
					e4.setError("Enter from location");
					e4.setFocusable(true);
				}
//				else if(toloc.equalsIgnoreCase(""))
//				{
//					e5.setError("Enter to location");
//					e5.setFocusable(true);
//				}
				else
				{
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)CustBookCargo.this;
					String q="/bookcargo?logid="+sh.getString("logid","")+"&brid="+CustViewNearbyCargoservice.brid1+"&weight="+weight+"&height="+height+"&width="+width+"&fromloc="+CustViewNearbyCargoservice.bname1+"&toloc="+toloc;
					q=q.replace(" ", "%20");
					jr.execute(q);
					
				}
			}
		});
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("bookcargo"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(), "success..", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),CustViewNearbyCargoservice.class));
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
				}
			}
			if(method.equalsIgnoreCase("viewbranches"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					bid=new String[ja.length()];
					bname=new String[ja.length()];
									
					alldata=new String[ja.length()];
					
					for(int i=0;i<ja.length();i++)
					{
						bid[i]=ja.getJSONObject(i).getString("branch_id");
						bname[i]=ja.getJSONObject(i).getString("name");
						
						alldata[i]="\n"+bname[i]+"\n";
					}
					s1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list_data,bname));
				}
				else
				{
//					Toast.makeText(getApplicationContext(), "No reviews...", Toast.LENGTH_LONG).show();
//					startActivity(new Intent(getApplicationContext(),CustViewNearbyCargoservice.class));
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
		getMenuInflater().inflate(R.menu.cust_book_cargo, menu);
		return true;
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		bid1=bid[arg2];
		toloc1=bname[arg2];
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
