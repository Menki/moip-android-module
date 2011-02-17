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

package com.menki.moip.models;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Log;

import com.menki.moip.utils.Base64;
import com.menki.moip.utils.Config;
import com.menki.moip.utils.Config.PaymentType;
import com.menki.moip.utils.Config.RemoteServer;
import com.menki.moip.utils.MoIPResponse;
import com.menki.moip.utils.MoIPXmlBuilder;
import com.menki.moip.utils.MoIPXmlParser;
import com.menki.moip.activities.R;

public class PaymentMgr 
{
	private static PaymentMgr _instance = null;

	private RemoteServer server = RemoteServer.NONE;
	private PaymentType type = PaymentType.NONE;
	private float value;
	private Context hostActivity = null;
	private HashMap<Integer, String> paymentDetails = null;
	
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

	@SuppressWarnings("unchecked")
	public Boolean readPaymentDetails() {
		if (hostActivity != null) {
			try {
				FileInputStream fis = hostActivity.openFileInput(Config.PAYMENT_DETAILS_FILENAME);
				ObjectInputStream ois = new ObjectInputStream(fis);
				setPaymentDetails((HashMap<Integer, String>) ois.readObject());
				ois.close();
				fis.close();
				
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		paymentDetails = new HashMap<Integer, String>();
		return false;
	}
	
	public Boolean savePaymentDetails() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, String> clone = (HashMap<Integer, String>) getPaymentDetails().clone();
		
		clone.remove(R.id.secure_code); // Secure code must never be persisted.
		
		if (hostActivity != null) {
			try {
				FileOutputStream fos = hostActivity.openFileOutput(Config.PAYMENT_DETAILS_FILENAME, Context.MODE_PRIVATE);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(clone);
				oos.close();
				fos.close();
				
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone( ) throws CloneNotSupportedException 
	{
		throw new CloneNotSupportedException( );
	}

	public MoIPResponse performDirectPaymentTransaction()
	{
		MoIPXmlBuilder builder = new MoIPXmlBuilder( );
		MoIPXmlParser parser = new MoIPXmlParser( );
		String msg = builder.getDirectPaymentMessage( );
		MoIPResponse moipResponse = new MoIPResponse( );
		
		try
		{
			DefaultHttpClient client = new DefaultHttpClient( );
		 
			HttpPost post;
			if(server == RemoteServer.TEST)
				post = new HttpPost(Config.TEST_SERVER);
			else
				post = new HttpPost(Config.PRODUCTION_SERVER);
			
			byte[] auth = (Config.TOKEN + ":" + Config.KEY).getBytes( );
			post.addHeader("Authorization", "Basic " + new String(Base64.encodeBytes(auth)));
			StringEntity entity = new StringEntity(msg.trim( ), "UTF-8");
			entity.setContentType("application/x-www-formurlencoded");
			post.setEntity(entity);
			Log.i("MENKI [PostPaymentMessage] ",  entity.getContent( ).toString( ));
			  
			HttpResponse response = client.execute(post);
			
			InputStream in = response.getEntity().getContent( ); 
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			{ 
				//TODO: handle server erros
				moipResponse.setResponseStatus("Server error");
				
			}
			else
				if(parser.parseDirectPaymentResponse(in))
					moipResponse = parser.getParsedResponse( );
				else
					Log.e("[MENKI]","[performDirectPaymentTransaction] - Error parsing response");
			
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
		catch (IllegalArgumentException e)
		{
			Log.e("MENKI [performDirectPaymentTransaction] ", e.getMessage( ));
			e.printStackTrace( );
		}

		return moipResponse;	 
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

	public PaymentType getType() 
	{
		return type;
	}

	public void setType(PaymentType type) 
	{
		this.type = type;
	}

	public void setPaymentDetails(HashMap<Integer, String> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public HashMap<Integer, String> getPaymentDetails() {
		return paymentDetails;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getValue() {
		return value;
	}
}
