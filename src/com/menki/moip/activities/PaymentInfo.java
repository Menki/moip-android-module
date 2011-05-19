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

import java.lang.reflect.Field;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.menki.moip.paymentmethods.PagamentoDireto;

public class PaymentInfo extends Activity implements OnClickListener {

	static final int BORN_DATE_DIALOG_ID = 0;
	static final int EXPIRATION_DATE_DIALOG_ID = 1;

	private LinearLayout creditCard;
	private LinearLayout owner;
	private LinearLayout payer;
	private LinearLayout address;
	private LinearLayout visible = null;

	private DatePicker expirationDate;
	
	private TextView value;

	private RadioButton installmentRadio;
	private RadioButton cashRadio;
	private TextView installmentsTextView;
	private EditText installmentsEditText;

	private Button nextStepButton;

	private PagamentoDireto pagamentoDireto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.paymentinfo);
		super.onCreate(savedInstanceState);

		final Calendar c = Calendar.getInstance();

		expirationDate = (DatePicker) findViewById(R.id.expiration_date);
		hideDayPicker(expirationDate);
		
		creditCard = (LinearLayout) findViewById(R.id.credit_card);
		owner = (LinearLayout) findViewById(R.id.owner);
		payer = (LinearLayout) findViewById(R.id.payer);
		address = (LinearLayout) findViewById(R.id.address);

		setCurrentLinearLayout(creditCard);

		pagamentoDireto = getIntent().getParcelableExtra("PagamentoDireto");
		pagamentoDireto = (PagamentoDireto) getIntent().getSerializableExtra("PagamentoDireto");

		value = (TextView)findViewById(R.id.value);
		//value.setText(value.getText() + " " + pagamentoDireto.getValue());

		nextStepButton = (Button)findViewById(R.id.next);
		nextStepButton.setOnClickListener(this);
	}

	private void hideDayPicker(DatePicker datePicker) {
		
		try {
			Field f[] = datePicker.getClass().getDeclaredFields();
			
			for (Field field : f) {
				if (field.getName().equals("mDayPicker")) {
					field.setAccessible(true);
					Object dayPicker = new Object();
					dayPicker = field.get(datePicker);
					((View) dayPicker).setVisibility(View.GONE);
				}
			}
		} catch (SecurityException e) {
			Log.d("ERROR", e.getMessage());
		} 
		catch (IllegalArgumentException e) {
			Log.d("ERROR", e.getMessage());
		} catch (IllegalAccessException e) {
			Log.d("ERROR", e.getMessage());
		} 
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

		return super.onKeyDown(keyCode, event);
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
