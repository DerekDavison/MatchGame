package com.example.matchgame;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class DBHelper 
{
	public DBHelper() {}
	
	public ArrayList<String> readDBData(String phpFile, ArrayList<NameValuePair> nameValuePairs, String dbFieldNameForJSONObject)
	{
		ArrayList<String> results = new ArrayList<String>();
		String result = "";
		   
		try 
		{  
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://184.65.14.98/" + phpFile);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost); 
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
    	    
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) 
			{
				sb.append(line + "\n"); 
			}
			is.close();
    			
			result = sb.toString();
		}
		catch(Exception e)
		{
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
    	
		//parse json data
		try
		{
			JSONArray jArray = new JSONArray(result);
			for(int i=0;i<jArray.length();i++)
			{
				results.add(jArray.getJSONObject(i).getString(dbFieldNameForJSONObject));  
			}    
		}
		catch(JSONException e)
		{
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return results;
	}
	
	public void insertUser(ArrayList<NameValuePair> nameValuePairs, String phpFile)
	{
		try
		{ 
			HttpClient httpclient = new DefaultHttpClient(); 
		    HttpPost httppost = new HttpPost("http://184.65.14.98/" + phpFile); 
		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
		    HttpResponse response = httpclient.execute(httppost); 
		    HttpEntity entity = response.getEntity(); 
		    Log.i("postData", response.getStatusLine().toString());   
		} 
		catch(Exception e) 
		{ 
			Log.e("log_tag", "Error in http connection "+e.toString()); 
		}
	}
}
