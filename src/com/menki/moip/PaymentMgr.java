/**
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Gustavo Scaramelli
 *
 */

package com.menki.moip;

import android.os.Handler;
import android.widget.Button;

import com.menki.moip.Constants.RemoteServer;


public class PaymentMgr 
{
	private static PaymentMgr _instance = null;

	private RemoteServer server = RemoteServer.NONE;
	private String key, token;
	private Handler handler = null;
	private PaymentDetails paymentDetails;
	
	public PaymentMgr( ) 
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

	public String getToken() 
	{
		return token;
	}

	public void setToken(String token) 
	{
		this.token = token;
	}

	public Handler getHandler( ) 
	{
		return handler;
	}

	public void setHandler(Handler handler) 
	{
		this.handler = handler;
	}
	
	public PaymentDetails getPaymentDetails( ) 
	{
		return paymentDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) 
	{
		this.paymentDetails = paymentDetails;
	}
}
