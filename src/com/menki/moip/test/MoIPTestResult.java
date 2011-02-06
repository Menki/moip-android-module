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

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.menki.moip.models.MoIPResponse;
import com.menki.moip.views.R;

public class MoIPTestResult extends Activity
{
	/** Called when the activity is first created. */
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
		// TODO Auto-generated method stub
    	 super.onCreate(savedInstanceState);
         setContentView(R.layout.moiptestresult);
		
         ArrayList<String> messageList;
         StringBuilder builder = new StringBuilder( );
        	 
         TextView statusTxtView = (TextView) this.findViewById(R.id.ResultStatusContentTextView);
         TextView messageTxtView = (TextView) this.findViewById(R.id.ResultMessageContentTextView);
         
         MoIPResponse response = (MoIPResponse)this.getIntent( ).getSerializableExtra("response");
       
         statusTxtView.setText(response.getResponseStatus( ));
         
         messageList = response.getMessagesList( );

         Iterator<String> iterator = messageList.iterator( );
         while (iterator.hasNext() )
         {
        	 builder.append(iterator.next( ));
        	 builder.append("\n");
         }
         messageTxtView.setText(builder.toString( ));
         
	}

}
