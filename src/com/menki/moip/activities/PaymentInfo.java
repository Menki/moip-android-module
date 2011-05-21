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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.menki.moip.paymentmethods.PagamentoDireto;

public class PaymentInfo extends Activity implements OnClickListener {

	private LinearLayout creditCard;
	private LinearLayout owner;
	private LinearLayout payer;
	private LinearLayout address;
	private LinearLayout visible = null;

	private TextView value;
	private Spinner brand;
	private EditText creditCardNumber;
	private DatePicker expirationDate;
	private EditText secureCode;
	private EditText name;
	private RadioGroup identificationType;
	private EditText identificationNumber;
	private DatePicker bornDate;
	private RadioGroup paymentType;
	private EditText installments;
	private EditText email;
	private EditText cellPhone;
	private EditText street;
	private EditText streetNumber;
	private EditText complement;
	private EditText neighborhood;
	private EditText city;
	private Spinner state;
	private EditText zipCode;
	private EditText phone;

	private Button nextStepButton;

	private PagamentoDireto pagamentoDireto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paymentinfo);

		creditCard = (LinearLayout) findViewById(R.id.credit_card);
		owner = (LinearLayout) findViewById(R.id.owner);
		payer = (LinearLayout) findViewById(R.id.payer);
		address = (LinearLayout) findViewById(R.id.address);
		brand = (Spinner) findViewById(R.id.brand);
		creditCardNumber = (EditText) findViewById(R.id.credit_card_number);
		expirationDate = (DatePicker) findViewById(R.id.expiration_date);
		secureCode = (EditText) findViewById(R.id.secure_code);
		name = (EditText) findViewById(R.id.owner_name);
		identificationType = (RadioGroup) findViewById(R.id.identification_type);
		identificationNumber = (EditText) findViewById(R.id.identification_number);
		bornDate = (DatePicker) findViewById(R.id.born_date);
		paymentType = (RadioGroup) findViewById(R.id.payment_type);
		installments = (EditText) findViewById(R.id.installments);
		email = (EditText) findViewById(R.id.email);
		cellPhone = (EditText) findViewById(R.id.cell_phone);
		street = (EditText) findViewById(R.id.street_address);
		streetNumber = (EditText) findViewById(R.id.street_number);
		complement = (EditText) findViewById(R.id.street_complement);
		neighborhood = (EditText) findViewById(R.id.neighborhood);
		city = (EditText) findViewById(R.id.city);
		state = (Spinner) findViewById(R.id.state);
		zipCode = (EditText) findViewById(R.id.zip_code);
		phone = (EditText) findViewById(R.id.fixed_phone);
		nextStepButton = (Button) findViewById(R.id.next);

		hideDayPicker(expirationDate);
		setCurrentLinearLayout(creditCard);

		pagamentoDireto = getIntent().getParcelableExtra("PagamentoDireto");
		pagamentoDireto = (PagamentoDireto) getIntent().getParcelableExtra("PagamentoDireto");

		value = (TextView) findViewById(R.id.value);
		value.setText(value.getText() + " " + pagamentoDireto.getValue());

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
			else if (visible.equals(address)) {
				populatePagamentoDireto();
				Intent intent = new Intent(this,Summary.class);
				intent.putExtra("PagamentoDireto", this.pagamentoDireto);
				startActivity(intent);
			}
		break;
		}

	}

	/**
	 * Back key 
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

	private void populatePagamentoDireto() {
		RadioButton checked = null;
		
		pagamentoDireto.setBrand(brand.getSelectedItem().toString());
		pagamentoDireto.setCreditCardNumber(creditCardNumber.getText().toString());
		pagamentoDireto.setExpirationDate(pad((Integer) expirationDate.getMonth()) + "/" +
				((Integer) expirationDate.getYear()).toString());
		pagamentoDireto.setSecureCode(secureCode.getText().toString());
		pagamentoDireto.setOwnerName(name.getText().toString());
		
		checked = (RadioButton) findViewById(identificationType.getCheckedRadioButtonId());
		pagamentoDireto.setOwnerIdType(checked.getText().toString());
		
		pagamentoDireto.setOwnerIdNumber(identificationNumber.getText().toString());
		pagamentoDireto.setOwnerBirthDate(pad((Integer) bornDate.getDayOfMonth()) + "/" +
				pad((Integer) bornDate.getMonth()) + ((Integer) bornDate.getYear()).toString());
		
		checked = (RadioButton) findViewById(paymentType.getCheckedRadioButtonId());
		pagamentoDireto.setPaymentType(checked.getText().toString());
		
		pagamentoDireto.setInstallmentsQuantity(installments.getText().toString());
		pagamentoDireto.setEmail(email.getText().toString());
		pagamentoDireto.setCellPhone(cellPhone.getText().toString());
		pagamentoDireto.setStreetAddress(street.getText().toString());
		pagamentoDireto.setStreetNumberAddress(streetNumber.getText().toString());
		pagamentoDireto.setAddressComplement(complement.getText().toString());
		pagamentoDireto.setNeighborhood(neighborhood.getText().toString());
		pagamentoDireto.setCity(city.getText().toString());
		pagamentoDireto.setState(state.getSelectedItem().toString());
		pagamentoDireto.setZipCode(zipCode.getText().toString());
		pagamentoDireto.setFixedPhone(phone.getText().toString());
		
	}

	private String pad(Integer i) {
		if (i < 10)
			return "0" + i.toString();
		else 
			return i.toString();
	}
}
