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
        
        //TODO: verificar o que é necessário para autenticar no server
        String token = "XXXXXXXXXXXXXXXXXX";
        
        //Creating object from PaymentButton class
        //PaymentButton object will be bound to the resource id referenced
        PaymentButton payButton = new PaymentButton(this, R.id.PaymentButton, token,
        										PaymentType.PAGAMENTO_DIRETO, RemoteServer.TEST);
        
    }
}
