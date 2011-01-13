/**
 * @author Gustavo Scaramelli
 *
 */

package com.menki.moip;

import com.menki.moip.Constants.PaymentType;
import com.menki.moip.Constants.RemoteServer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class PaymentButton extends Button implements OnClickListener
{
	private Context hostActivity = null;
	private PaymentType type = PaymentType.NONE;
	private RemoteServer server = RemoteServer.NONE;
	private String key;
	
	public PaymentButton(Context context, int id, String token, PaymentType t, RemoteServer s) 
	{
		super(context);
		
		hostActivity = context;
		type = t;
		server = s;
		key = token;
		
		Button button = (Button) ((Activity)context).findViewById(id);
		
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

//	public PaymentButton(Context context, AttributeSet attrs) 
//	{
//		super(context, attrs);
//		// TODO Auto-generated constructor stub
//	}
//	
//	public PaymentButton(Context context, AttributeSet attrs, int defStyle) 
//	{
//		super(context, attrs, defStyle);
//		// TODO Auto-generated constructor stub
//	}

	@Override
	public void onClick(View v)
	{ 
		System.out.println("MENKI: PaymentoButton - onClick( )");
	}



}
