/**
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Gustavo Scaramelli
 *
 */

package com.menki.moip;

import android.content.Context;
import android.os.Handler;

import com.menki.moip.Constants.RemoteServer;
import com.menki.moip.Constants.PaymentType;


public class PaymentMgr 
{
	private static PaymentMgr _instance = null;

	private RemoteServer server = RemoteServer.NONE;
	private String key, token;
	private Handler handler = null;
	private PaymentDetails paymentDetails = PaymentDetails.getInstance();
	private PaymentType type = PaymentType.NONE;
	
	private PaymentMgr() {}
	
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
}
