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

public class CustViewReviews extends Activity implements JsonResponse
{
	ListView l1;
	SharedPreferences sh;
	String[] rid,review,rating,redate,alldata;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cust_view_reviews);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		l1=(ListView)findViewById(R.id.listView1);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)CustViewReviews.this;
		String q="/viewreviewsratings?brid="+CustViewNearbyCargoservice.brid1;
		jr.execute(q);
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("viewreviewsratings"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					rid=new String[ja.length()];
					review=new String[ja.length()];
					rating=new String[ja.length()];
					redate=new String[ja.length()];
				
					alldata=new String[ja.length()];					

					for(int i=0;i<ja.length();i++)
					{
						rid[i]=ja.getJSONObject(i).getString("review_id");
						review[i]=ja.getJSONObject(i).getString("review_coment");
						rating[i]=ja.getJSONObject(i).getString("rating_point");
						redate[i]=ja.getJSONObject(i).getString("review_date");
						//Toast.makeText(getApplicationContext(), redate[i], Toast.LENGTH_LONG).show();
						alldata[i]="\nReview : "+review[i]+"\nRating : "+rating[i]+"\nDated : "+redate[i]+"\n";
					}
					l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list_data,alldata));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No reviews...", Toast.LENGTH_LONG).show();
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
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cust_view_reviews, menu);
		return true;
	}

}
