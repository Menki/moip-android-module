/**
 * Copyright (c) 2011, MENKI MOBILE SOLUTIONS - http://www.menkimobile.com.br
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, 
 *   this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimer in the documentation and/or 
 *   other materials provided with the distribution.
 * * Neither the name of the MENKI MOBILE SOLUTIONS nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software without 
 *   specific prior written permission.
 *   
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
 *  SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 *  TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR 
 *  BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
 *  ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF 
 *  SUCH DAMAGE. 
 *  
 *  @version 0.0.1
 */

package com.menki.moip;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.menki.moip.utils.Base64;
import com.menki.moip.utils.Constants;
import com.menki.moip.utils.Constants.PaymentType;
import com.menki.moip.utils.Constants.RemoteServer;
import com.menki.moip.xml.MoIPXmlBuilder;
import com.menki.moip.xml.MoIPXmlParser;


public class PaymentMgr 
{
	private static PaymentMgr _instance = null;

	private RemoteServer server = RemoteServer.NONE;
	private String key, token;
	private PaymentDetails paymentDetails = PaymentDetails.getInstance();
	private PaymentType type = PaymentType.NONE;
	private Context hostActivity = null;
	
	private PaymentMgr( ) 
	{ }
	
	/**
	 * 
	 * @return _instance The PaymentMgr instance to be used
	 */
	public static synchronized PaymentMgr getInstance( ) 
	{
		if(_instance == null) 
		{
			_instance = new PaymentMgr( );
		}
		return _instance;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone( ) throws CloneNotSupportedException 
	{
		throw new CloneNotSupportedException( );
	}

	public String performDirectPaymentTransaction(Context ctx)
	{
		MoIPXmlBuilder builder = new MoIPXmlBuilder( );
		MoIPXmlParser parser = new MoIPXmlParser( );
		String msg = builder.getDirectPaymentMessage( );
		String xmlResponse = null;
		
		try
		{
			DefaultHttpClient client = new DefaultHttpClient( );
		 
			HttpPost post;
			if(server == RemoteServer.TEST)
				post = new HttpPost(Constants.TEST_SERVER);
			else
				post = new HttpPost(Constants.PRODUCTION_SERVER);
			
			byte[] auth = (token + ":" + key).getBytes( );
			post.addHeader("Authorization", "Basic " + new String(Base64.encodeBytes(auth)));
			StringEntity entity = new StringEntity(msg.trim( ), "UTF-8");
			entity.setContentType("application/x-www-formurlencoded");
			post.setEntity(entity);
			Log.i("MENKI [PostPaymentMessage] ",  entity.getContent( ).toString( ));
	
			ProgressDialog dialog = ProgressDialog.show(ctx,"Progress dialogue sample ", 
														"ceveni.com please wait....",	
														true);
			  
			HttpResponse response = client.execute(post);

			dialog.dismiss( );
			
			InputStream in = response.getEntity().getContent( ); 
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
				xmlResponse = "Error";
			else
				xmlResponse = parser.parseDirectPaymentResponse(in);
			
		}
		catch (ClientProtocolException e) 
		{
			Log.e("MENKI [performDirectPaymentTransaction] ", e.getMessage());
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			Log.e("MENKI [performDirectPaymentTransaction] ", e.getMessage());
			e.printStackTrace();
		}				
		return xmlResponse;	 
	}

	public Context getHostActivity( ) 
	{
		return hostActivity;
	}

	public void setHostActivity(Context hostActivity) 
	{
		this.hostActivity = hostActivity;
	}

	public RemoteServer getServer( ) 
	{
		return this.server;
	}

	public void setServer(RemoteServer server) 
	{
		this.server = server;
	}

	public String getKey( ) 
	{
		return this.key;
	}

	public void setKey(String key) 
	{
		this.key = key;
	}

	public String getToken() 
	{
		return this.token;
	}

	public void setToken(String token) 
	{
		this.token = token;
	}

	public PaymentDetails getPaymentDetails( ) 
	{
		return paymentDetails;
	}
	
	public PaymentType getType() 
	{
		return type;
	}

	public void setType(PaymentType type) 
	{
		this.type = type;
	}

	public void savePaymentDetails(Context context) {
		paymentDetails.save(context);
	}
	public void restorePaymentDetails(Context context) {
		paymentDetails.restore(context);
	}
	public ArrayList<String> getErrors(){
		return paymentDetails.getErrors();
	}
}
