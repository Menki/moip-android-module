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
import java.util.Calendar;

import com.menki.moip.paymentmethods.PagamentoDireto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class PaymentInfo extends Activity implements OnClickListener {
	
	static final int BORN_DATE_DIALOG_ID = 0;
	static final int EXPIRATION_DATE_DIALOG_ID = 1;
	
	private LinearLayout creditCard;
	private LinearLayout owner;
	private LinearLayout payer;
	private LinearLayout address;
	private LinearLayout visible;
	
	private TextView value;
	
	private RadioButton installmentRadio;
	private RadioButton cashRadio;
	private TextView installmentsTextView;
	private EditText installmentsEditText;
	
	private TextView bornDateTextview;
	private Button bornDateButton;
	private int bornDateYear;
    private int bornDateMonth;
    private int bornDateDay;

    private TextView expirationDateTextview;
	private Button expirationDateButton;
    private int expirationDateYear;
    private int expirationDateMonth;
    private int expirationDateDay;
	
    private Button nextStepButton;
    
    private PagamentoDireto pagamentoDireto;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.paymentinfo);
    	super.onCreate(savedInstanceState);
    	
		final Calendar c = Calendar.getInstance();
		
		bornDateYear = c.get(Calendar.YEAR);
		bornDateMonth = c.get(Calendar.MONTH);
		bornDateDay = c.get(Calendar.DAY_OF_MONTH);
//		bornDateTextview = (TextView) findViewById(R.id.born_date_textview);
//		bornDateButton = (Button) findViewById(R.id.born_date_button);
		
		expirationDateYear = c.get(Calendar.YEAR);
		expirationDateMonth = c.get(Calendar.MONTH);
		expirationDateDay = c.get(Calendar.DAY_OF_MONTH);
		
		expirationDateTextview = (TextView) findViewById(R.id.expiration_date_textview);
		expirationDateButton = (Button) findViewById(R.id.expiration_date_button);
		
		creditCard = (LinearLayout) findViewById(R.id.credit_card);
		owner = (LinearLayout) findViewById(R.id.owner);
		payer = (LinearLayout) findViewById(R.id.payer);
		address = (LinearLayout) findViewById(R.id.address);

		setCurrentLinearLayout(creditCard);
		
//		installmentRadio = (RadioButton) findViewById(R.id.radio_installment_payment);
//		cashRadio = (RadioButton) findViewById(R.id.radio_cash_payment);
//		installmentsTextView = (TextView) findViewById(R.id.installments_textview);
//		installmentsEditText = (EditText) findViewById(R.id.installments);
//		
//		this.nextStepButton = (Button)findViewById(R.id.button_next);
//    	this.nextStepButton.setOnClickListener(this);    	
    	
    	pagamentoDireto = getIntent().getParcelableExtra("PagamentoDireto");
    	pagamentoDireto = (PagamentoDireto) getIntent().getSerializableExtra("PagamentoDireto");

    	value = (TextView)findViewById(R.id.value);
//    	value.setText(value.getText() + " " + pagamentoDireto.getValue());
    	
    	nextStepButton = (Button)findViewById(R.id.next);
    	nextStepButton.setOnClickListener(this);
    }

	private void setCurrentLinearLayout(LinearLayout current) {
		creditCard.setVisibility(View.GONE);
		owner.setVisibility(View.GONE);
		payer.setVisibility(View.GONE);
		address.setVisibility(View.GONE);
		
		current.setVisibility(View.VISIBLE);
		visible = current;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case(R.id.next):			
			if (visible.equals(creditCard))
				setCurrentLinearLayout(owner);
			else if (visible.equals(owner))
				setCurrentLinearLayout(payer);
			else if (visible.equals(payer))
				setCurrentLinearLayout(address);
			else if (visible.equals(payer)) {
				this.populatePagamentoDireto();
				Intent intent = new Intent(this,Summary.class);
				intent.putExtra("PagamentoDireto", this.pagamentoDireto);
				startActivity(intent);
			}
		break;

//		case(R.id.born_date_button):
//			showDialog(BORN_DATE_DIALOG_ID);
//		break;
//		case(R.id.expiration_date_button):
//			showDialog(EXPIRATION_DATE_DIALOG_ID);
//		break;
//		case(R.id.radio_cash_payment):
//		case(R.id.radio_installment_payment):
//			updateInstallmentsInput();	
//		break;
		}
		
	}
	
	/**
	 * Key back 
	 * */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (visible.equals(owner)) {
				setCurrentLinearLayout(creditCard);
				return true;
			}
			else if (visible.equals(payer)) {
				setCurrentLinearLayout(owner);
				return true;
			}
			else if (visible.equals(address)) {
				setCurrentLinearLayout(payer);
				return true;
			}		
		}
		
		return super.onKeyUp(keyCode, event);
	}
	
	private void updateInstallmentsInput() {
		if (installmentRadio.isChecked()) {
			// show installments text view and edit text
			installmentsTextView.setVisibility(View.VISIBLE);
			installmentsEditText.setVisibility(View.VISIBLE);
		} else {
			// hide installments text view and edit text
			installmentsTextView.setVisibility(View.GONE);
			installmentsEditText.setVisibility(View.GONE);			
		}
	}
	
	private void populatePagamentoDireto() {
		if (this.cashRadio.isChecked()) 
			this.pagamentoDireto.setInstallmentsQuantity("1");
		else 
			this.pagamentoDireto.setInstallmentsQuantity(this.installmentsEditText.getText().toString());
		
	}
}
