package com.example.cargo;

import org.json.JSONObject;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class CustRate extends Activity implements JsonResponse{

	SharedPreferences sh;
	RatingBar r;
	Button b;
	String review;
	float rate;
	EditText e1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cust_rate);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		r=(RatingBar)findViewById(R.id.ratingBar1);
		e1=(EditText)findViewById(R.id.editText1);
		b=(Button)findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				review=e1.getText().toString();
				rate=r.getRating();
				if(review.equalsIgnoreCase(""))
				{
					e1.setError("Enter review");
					e1.setFocusable(true);
				}
				else
				{
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)CustRate.this;
					String q="/ratecompany?brid=" +CustViewCargocampanies.brid1+"&logid="+sh.getString("logid","")+"&review="+review.replace(" ", "%20")+"&rating="+rate+"";
					jr.execute(q);
				}
				
			}
		});
	}
	@Override
	public void response(JSONObject jo) 
	{
		// TODO Auto-generated method stub
		try
		{
			String status=jo.getString("status");
			if(status.equalsIgnoreCase("success"))
			{
				 Toast.makeText(getApplicationContext(), "success ", Toast.LENGTH_LONG).show();
            	 startActivity(new Intent(getApplicationContext(),CustHome.class));
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Already rated...", Toast.LENGTH_LONG).show();
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
		getMenuInflater().inflate(R.menu.cust_rate, menu);
		return true;
	}

}
