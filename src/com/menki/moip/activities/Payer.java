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

package com.menki.moip.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Payer extends FormActivity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.payer);
    	super.onCreate(savedInstanceState);
    }

	@Override
	public void onClick(View v) 
	{
//		switch(v.getId())
//		{
//				
//			case(R.id.finish_button):
//				PaymentMgr mgr = PaymentMgr.getInstance( );
//			
//				if(summary.isShowing( ))
//					summary.dismiss( );
//				
//				PaymentType type = mgr.getType( );
//				if(type == PaymentType.PAGAMENTO_DIRETO)
//				{
//					MoIPResponse response = mgr.performDirectPaymentTransaction(this);
//					Intent intent = new Intent( );
//					intent.putExtra("response", response);
//					// sets the result for the calling activity
//					setResult( RESULT_OK, intent);
//					finish( );				
//				}	
//				else
//					Log.e("MENKI [Payer] ", "Undefined Payment Method");
//				break;
//		}
		
		super.onClick(v);
	}
		
	@Override
	protected LinearLayout getForm() {
		LinearLayout form = (LinearLayout) findViewById(R.id.payer_layout);
		return form;
	}

	@Override
	protected Class<? extends Activity> nextActivity() {
		return Summary.class;
	}

	@Override
	protected ArrayList<Integer> nonRequiredFields() {
		ArrayList<Integer> fields = new ArrayList<Integer>();
		fields.add((Integer) R.id.street_complement);
		return fields;
	}
}
