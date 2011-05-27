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
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.menki.moip.activities.PaymentButton;
import com.menki.moip.activities.R;
import com.menki.moip.paymentmethods.OnPaymentListener;
import com.menki.moip.paymentmethods.PagamentoDireto;
import com.menki.moip.paymentmethods.PagamentoDireto.OwnerIdType;
import com.menki.moip.utils.Config.RemoteServer;
import com.menki.moip.utils.MoIPResponse;

public class MoIPTestApp extends Activity implements OnPaymentListener
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moiptestapp);
        
        PagamentoDireto pagDir = new PagamentoDireto();        
        pagDir.setOnPaymentListener(this);
        
        pagDir.setAddressComplement("x");
        pagDir.setBrand("Visa");
        pagDir.setCity("Sucupira");
        pagDir.setCountry("BRA");
        pagDir.setCreditCardNumber("3456789012345640");
        pagDir.setExpirationDate("08/11");
        pagDir.setInstallmentsQuantity("2");
        pagDir.setNeighborhood("Vila Vintem");
        pagDir.setOwnerBirthDate("01/01/1983");
        pagDir.setOwnerIdNumber("111.111.111-11");
        pagDir.setOwnerIdType(OwnerIdType.CPF);
        pagDir.setOwnerName("Lindolfo Pires");
        pagDir.setOwnerPhoneNumber("(11)1111-1111");
        pagDir.setSecureCode("1010");
        pagDir.setServerType(RemoteServer.TEST);
        pagDir.setState("AC");
        pagDir.setStreetAddress("Avenida Brasil");
        pagDir.setStreetNumberAddress("100");
        pagDir.setValue("213.25");
        pagDir.setZipCode("10100-100");
                
        //pagDir.pay();
               
        PaymentButton payButton = new PaymentButton(this, R.id.PaymentButton, pagDir);
    }
   
        

	@Override
	public void onPaymentFail(MoIPResponse response) 
	{
		
		Log.i("MoIP", "Falhou!");
		Toast.makeText(this, "Pagamento mal sucedido!", Toast.LENGTH_LONG).show();
	}


	@Override
	public void onPaymentSuccess(MoIPResponse response) 
	{
		Log.i("MoIP", "Funcionou!");
		Toast.makeText(this, "Pagamento bem sucedido!", Toast.LENGTH_LONG).show();
	}



}
