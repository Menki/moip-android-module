/**
 * Test application for MoIP payment API for Android
 * 
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Gustavo Scaramelli
 */

package com.menki.moip.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.menki.moip.*;
import com.menki.moip.Constants.PaymentType;
import com.menki.moip.Constants.RemoteServer;

public class MoIPTestApp extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moiptestapp);
        
        //key and token for authentication on MoIP server
        String token = "XXXXXXXXXXXXXXXXXX";
        String key = "XXXXXXXXXXXXXXXXXX";
        
        //Handler for handling payment return callback (handleMessage)
        Handler handler = new Handler( )
        {
        	@Override
			public void handleMessage(Message msg) 
        	{
				//TODO: handles message after payment transaction
			}
        	
        };
        
        
        //Creating object from PaymentButton class
        //PaymentButton object will be bound to the resource id referenced
        //unused parameters must be set to null
        PaymentButton payButton = new PaymentButton(this, R.id.PaymentButton, token, key,
        									PaymentType.PAGAMENTO_DIRETO, RemoteServer.TEST, handler);
        
    }
}
