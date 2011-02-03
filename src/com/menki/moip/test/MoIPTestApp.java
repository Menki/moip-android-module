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

package com.menki.moip.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.menki.moip.views.R;
import com.menki.moip.utils.Config.PaymentType;
import com.menki.moip.utils.Config.RemoteServer;
import com.menki.moip.views.PaymentButton;

public class MoIPTestApp extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moiptestapp);
        
        
        //TODO: retrieve token and key from file
        //key and token for authentication on MoIP server
        String token = "XXXXXXXXXXXXXXXXXX";
        String key = "XXXXXXXXXXXXXXXXXX";        
        
        /* Creating object from PaymentButton class
           PaymentButton object will be bound to the resource id referenced
           unused parameters must be set to null */
        PaymentButton payButton = new PaymentButton(this, R.id.PaymentButton, token, key,
        									PaymentType.PAGAMENTO_DIRETO, RemoteServer.TEST);
    }
   
        
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_CANCELED) 
		{ /* Back button might have been pressed*/ }
		else
			switch (requestCode) 
			{
				//just one Activity started:
				case 0: 
					//TODO: do something
					String response = data.getStringExtra("response");
					Log.i("MyApp", "Payment finished!!!!! - DATA: " + response);
					break;
			}
	}
}
