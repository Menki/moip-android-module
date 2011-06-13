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
package com.menki.moip;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
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
import com.menki.moip.paymentmethods.PagamentoDireto.OwnerIdType;

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
	private DatePicker birthDate;
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

	private Dialog summaryDialog;
	
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
		birthDate = (DatePicker) findViewById(R.id.birth_date);
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
		
		fakeData();
	}

	private void fakeData() {
		creditCardNumber.setText("345678901234564");
		secureCode.setText("1234");
		name.setText("Nome do Portador");
		identificationType.getChildAt(1).setEnabled(true);
		identificationNumber.setText("111.111.111-11");
		paymentType.getChildAt(0).setEnabled(true);
		installments.setText("2");
		email.setText("presidente@planalto.gov.br");
		cellPhone.setText("(11)1111-1111");
		street.setText("Luiz Inacio Lula da Silva");
		streetNumber.setText("123");
		neighborhood.setText("Bairro no Acre");
		city.setText("Rio Branco");
		zipCode.setText("70100-000");
		phone.setText("(11)1111-1111");
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
//				Intent intent = new Intent(this,Summary.class);
//				intent.putExtra("PagamentoDireto", this.pagamentoDireto);
//				startActivity(intent);

				summaryDialog = new Dialog(this);
				summaryDialog.setContentView(R.layout.summary);
				summaryDialog.findViewById(R.id.finish_button).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						(new PayTask()).execute();
					}
				});
				
				((TextView) summaryDialog.findViewById(R.id.SummaryTextView)).setText(summaryString());
				summaryDialog.show();
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
		
		pagamentoDireto.setBrand(brand.getSelectedItem().toString().replace(" ", ""));
		pagamentoDireto.setCreditCardNumber(creditCardNumber.getText().toString());
		pagamentoDireto.setExpirationDate(pad((Integer) expirationDate.getMonth()) + "/" +
				twoDigitsYear((Integer) expirationDate.getYear()));
		pagamentoDireto.setSecureCode(secureCode.getText().toString());
		pagamentoDireto.setOwnerName(name.getText().toString());
		
		checked = (RadioButton) findViewById(identificationType.getCheckedRadioButtonId());
		pagamentoDireto.setOwnerIdType(checked.getText().toString());
		
		pagamentoDireto.setOwnerIdNumber(identificationNumber.getText().toString());
		pagamentoDireto.setOwnerBirthDate(pad((Integer) birthDate.getDayOfMonth()) + "/" +
				pad((Integer) birthDate.getMonth()) + "/" + ((Integer) birthDate.getYear()).toString());
		
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

	private String twoDigitsYear(Integer year) {
		return year.toString().substring(2, 4);
	}

	private String pad(Integer i) {
		if (i < 10)
			return "0" + i.toString();
		else 
			return i.toString();
	}
	
	private CharSequence summaryString() {
		char separator1 = ' ';
		char separator2 = '\n';
		StringBuilder builder = new StringBuilder(separator2);
		
		//Set identification type string
		String idType = null;		
		if (pagamentoDireto.getOwnerIdType().equals(OwnerIdType.CPF)) {
			idType = getString(R.string.cpf);
		}
		else {
			idType = getString(R.string.rg);
		}
		
		//Set payment type string
		String paymentType = null;
		if (Integer.parseInt(this.pagamentoDireto.getInstallmentsQuantity()) == 1) {
			paymentType = getString(R.string.cash_payment);
		}
		else {
			paymentType = getString(R.string.installment_payment);
		}
		
		builder.
			// Value
			append(getString(R.string.value) + separator1).
			append(String.valueOf(this.pagamentoDireto.getValue()) + separator2).
			//Full Name
			append(getString(R.string.owner_name) + separator1).
			append(this.pagamentoDireto.getOwnerName() + separator2). 
			//CPF or RG
			append(idType + ':' + separator1).
			append(this.pagamentoDireto.getOwnerIdNumber() + separator2).
	      	//Brand
	      	append(getString(R.string.brand) + separator1).
	    	append(this.pagamentoDireto.getBrand() + separator2).
	    	//Credit card number
	    	append(getString(R.string.credit_card_number) + separator1).
	    	append(this.pagamentoDireto.getCreditCardNumber() + separator2).
	    	//Expiration date
	    	append(getString(R.string.expiration_date) + separator1).
	    	append(this.pagamentoDireto.getExpirationDate() + separator2).
	    	//Secure code
	    	append(getString(R.string.secure_code) + separator1).
	    	append(this.pagamentoDireto.getSecureCode() + separator2).
	    	//Payment type
	    	append(getString(R.string.payment_type) + separator1).
	    	append(paymentType + separator2);
		
			if (Integer.parseInt(this.pagamentoDireto.getInstallmentsQuantity()) > 1)
			{
				builder.append(getString(R.string.installments) + separator1).
		    	append(this.pagamentoDireto.getInstallmentsQuantity() + separator2);
			}
    	
    	return builder;
	}
	
	public PagamentoDireto getPagamentoDireto() {
		return pagamentoDireto;
	}
	
	public void finishIt() {
		summaryDialog.dismiss();
		finish();
	}
	
    private class PayTask extends AsyncTask<String, Void, Void>{
		private final ProgressDialog sendingDialog = new ProgressDialog(PaymentInfo.this);
		
		protected void onPreExecute(){
			this.sendingDialog.setMessage(Html.fromHtml("<font color='white'>Por favor, aguarde...</font>"));
			this.sendingDialog.show();
		}
		
		@Override
		protected Void doInBackground(String... params) {
			PaymentInfo.this.getPagamentoDireto().pay();
			
			return null;
		}
		
		protected void onPostExecute(final Void unused){
			if(this.sendingDialog.isShowing())
				this.sendingDialog.dismiss();
			
			finishIt();
		}
	}
}
