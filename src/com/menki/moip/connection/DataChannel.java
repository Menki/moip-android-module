/**
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Gustavo Scaramelli
 *
 */
package com.menki.moip.connection;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.menki.moip.Constants;
import com.menki.moip.Constants.PaymentType;
import com.menki.moip.Constants.RemoteServer;


public class DataChannel  
{
	private static DataChannel _instance = null;
	
	private RemoteServer server = RemoteServer.NONE;
	private String key, token;
	
	DataChannel( )
	{ }
	
	DataChannel(RemoteServer s, String k, String t)
	{
		server = s;
		key = k;
		token = t;
	}
	
	/**
	 * 
	 * @return _instance The DataChannel instance to be used
	 */
	public static synchronized DataChannel getInstance( ) 
	{
		if(_instance == null) 
		{
			_instance = new DataChannel( );
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
	
		
	public RemoteServer getServer( ) 
	{
		return server;
	}

	public void setServer(RemoteServer server) 
	{
		this.server = server;
	}

	public String getKey( )  
	{
		return key;
	}

	public void setKey(String key) 
	{
		this.key = key;
	}

	public String getToken( ) 
	{
		return token;
	}

	public void setToken(String token) 
	{
		this.token = token;
	}

	public void postMessage(PaymentType type, String message)
	{
		PostPaymentMessage postPayment = new PostPaymentMessage(message);
		Thread postPaymentThread = new Thread(postPayment);
		
		postPaymentThread.start( );	
	}

	public class PostPaymentMessage implements Runnable
	{
		private String message;
		
		PostPaymentMessage(String msg)
		{
			this.message = msg;
		}

		@Override
		public void run( ) 
		{
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
				 StringEntity entity = new StringEntity(this.message.trim( ), "UTF-8");
				 entity.setContentType("application/x-www-formurlencoded");
				 post.setEntity(entity);
				 Log.i("MENKI [PostPaymentMessage] ",  entity.getContent( ).toString( ));

				 HttpResponse response = client.execute(post);

				// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				// DocumentBuilder db = dbf.newDocumentBuilder();
				// Document doc = db.parse(response.getEntity().getContent());
				// NodeList nodeList = doc.getElementsByTagName("Token");
				// String responseToken = nodeList.item(0).getTextContent();

				 if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
				 {
					 return;
				 }
			
			}
			catch (ClientProtocolException e) 
			{
				Log.e("MENKI [PostPaymentMessage] ", e.getMessage());
				e.printStackTrace();
			}
			catch (IOException e) 
			{
				Log.e("MENKI [PostPaymentMessage] ", e.getMessage());
				e.printStackTrace();
			}
			 
		}
		 
	}
	 
	 

}
