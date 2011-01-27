/**
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Gustavo Scaramelli
 *
 */

package com.menki.moip;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;

import com.menki.moip.Constants.RemoteServer;
import com.menki.moip.Constants.PaymentType;
import com.menki.moip.connection.DataChannel;
import com.menki.moip.xml.XmlBuilder;


public class PaymentMgr 
{
	private static PaymentMgr _instance = null;

	DataChannel conn = null;
	
	private Handler handler = null;
	private PaymentDetails paymentDetails = PaymentDetails.getInstance();
	private PaymentType type = PaymentType.NONE;
	
	private PaymentMgr( ) 
	{ 
		conn = DataChannel.getInstance( ); 
	}
	
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

	
	public int performDirectPaymentTransaction( )
	{
		XmlBuilder builder = new XmlBuilder( );
		String msg = builder.getDirectPaymentMessage( );
		
		conn.postMessage(type, msg);//TODO: remover type e passar PAGAMENTO_DIRETO
		
		return 0;
	}
	
	public RemoteServer getServer( ) 
	{
		return conn.getServer( );
	}

	public void setServer(RemoteServer server) 
	{
		conn.setServer(server);
	}

	public String getKey( ) 
	{
		return conn.getKey( );
	}

	public void setKey(String key) 
	{
		conn.setKey(key);
	}

	public String getToken() 
	{
		return conn.getToken( );
	}

	public void setToken(String token) 
	{
		conn.setToken(token);
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
	public ArrayList<String> getErrors(){
		return paymentDetails.getErrors();
	}
}
