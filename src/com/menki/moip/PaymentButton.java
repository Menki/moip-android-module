/**
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Gustavo Scaramelli
 *
 */

package com.menki.moip;

import com.menki.moip.Constants.PaymentType;
import com.menki.moip.Constants.RemoteServer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class PaymentButton extends Button implements OnClickListener
{
	private Context hostActivity = null;
	private PaymentType type = PaymentType.NONE;
	private RemoteServer server = RemoteServer.NONE;
	private String key, token;
	private Button button = null;
	private Handler handler = null;
	
	public PaymentButton(Context context, int id, String t, String k, 
									PaymentType pt, RemoteServer s, Handler h) 
	{
		super(context);
		
		hostActivity = context;
		type = pt;
		server = s;
		key = k;
		token = t;
		handler = h;
		
		button = (Button) ((Activity)context).findViewById(id);
		
		//testing for a valid button
		try
		{
			button.setOnClickListener(this);
		}
		catch( NullPointerException e)
		{
			Log.i("MENKI [PaymentButton] ", e.getMessage());
			e.printStackTrace( );
		}		
	}


	@Override
	public void onClick(View v)
	{
		Log.i("MENKI [PaymentButton]", "onClick( )");
		
		Intent intent = new Intent(hostActivity.getApplicationContext(), CreditCard.class);
		hostActivity.startActivity(intent);		
	}
}
