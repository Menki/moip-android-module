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

package com.menki.moip.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.menki.moip.models.PaymentMgr;
import com.menki.moip.utils.Config.PaymentType;
import com.menki.moip.utils.Config.RemoteServer;

public class PaymentButton extends Button implements OnClickListener
{
	private Context hostActivity = null;
	private PaymentType type = PaymentType.NONE;
	private Button button = null;
	
	public PaymentButton(Context context, int id, String t, String k, 
						 PaymentType pt, RemoteServer s) 
	{
		super(context);
			
		hostActivity = context;
		type = pt;
		
		button = (Button) ((Activity)context).findViewById(id);

		PaymentMgr mgr = PaymentMgr.getInstance( );
		mgr.setServer(s);
		mgr.setKey(k);
		mgr.setToken(t);
		mgr.setType(pt);
		mgr.setHostActivity(context);
		mgr.readPaymentDetails();
		
		//testing for a valid button
		try
		{
			button.setOnClickListener(this);
		}
		catch( NullPointerException e)
		{
			Log.e("MENKI [PaymentButton] ", e.getMessage());
			e.printStackTrace( );
		}		
	}

	@Override
	public void onClick(View v)
	{
		//calling Payment form depending on PaymentType
		switch(this.type)
		{
			case PAGAMENTO_DIRETO:
				Intent intent = new Intent(hostActivity, CreditCard.class);
				((Activity)hostActivity).startActivityForResult(intent, 0);
				break;
			default:
				Log.w("MENKI [PaymentButton] ", " onClick( ): Undefined payment type");
				break;
		}
	}
}
