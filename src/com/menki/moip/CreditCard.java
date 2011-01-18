/**
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Augusto Souza
 *
 */

package com.menki.moip;

import java.text.ParseException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class CreditCard extends Activity implements OnClickListener {
	private static final String TAG = "CreditCardActivity";
	
	private Spinner brand;
	private EditText creditCardNumber;
	private EditText expirationDate;
	private EditText secureCode;
	private EditText ownerName;
	private RadioGroup identificationType;
	private EditText identificationNumber;
	private EditText ownerPhoneNumber;
	private EditText bornDate;
	private EditText installments;
	private RadioGroup paymentType;
	private Button nextStep;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_card);
        
        setViews();
        setDefaultValues();
        setListeners();
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case(R.id.credit_card_next_step):
			// Set payment objects and persist them
			setPayment();

			// Go to Payer screen passing to its activity the payment object
			Intent intent = new Intent(this.getApplicationContext( ), Payer.class);
			Bundle b = getIntent( ).getExtras( );
			intent.putExtra("paymentType", b.getInt("paymentType"));
			this.startActivity(intent);
			break;
		}
	}
	
	private void setViews() {
		brand = (Spinner) findViewById(R.id.brand);
		creditCardNumber = (EditText) findViewById(R.id.credit_card_number);
		expirationDate = (EditText) findViewById(R.id.expiration_date);
		secureCode = (EditText) findViewById(R.id.secure_code);
		ownerName = (EditText) findViewById(R.id.owner_name);
		identificationType = (RadioGroup) findViewById(R.id.identification_type);
		identificationNumber = (EditText) findViewById(R.id.identification_number);
		ownerPhoneNumber = (EditText) findViewById(R.id.owner_phone_number);
		bornDate = (EditText) findViewById(R.id.born_date);
		installments = (EditText) findViewById(R.id.installments);
		paymentType = (RadioGroup) findViewById(R.id.payment_type);
		nextStep = (Button) findViewById(R.id.credit_card_next_step);
	}
	
	private void setDefaultValues() {
		RadioButton itemToCheck;
		PaymentMgr paymentMgr = PaymentMgr.getInstance();
		paymentMgr.restorePaymentDetails(this);
		PaymentDetails paymentDetails = paymentMgr.getPaymentDetails(); 
		
		
		for(int i=0; i < brand.getCount(); i++) {
			if (brand.getItemAtPosition(i).toString().equals(paymentDetails.getBrand())) {
				brand.setSelection(i);
				break;
			}
		}

		creditCardNumber.setText(paymentDetails.getCreditCardNumber());
		
		if (paymentDetails.getExpirationDate() != null)
			expirationDate.setText(Constants.MONTH_AND_YEAR.format(paymentDetails.getExpirationDate()));
		
		ownerName.setText(paymentDetails.getOwnerName());
		
		for(int i=0; i < identificationType.getChildCount(); i++) {
			itemToCheck = (RadioButton) identificationType.getChildAt(i);
			if (itemToCheck.getText().toString().equals(paymentDetails.getOwnerIdentificationType())) {
				itemToCheck.setChecked(true);
				break;
			}
		}
		
		identificationNumber.setText(paymentDetails.getOwnerIdentificationNumber());

		ownerPhoneNumber.setText(paymentDetails.getOwnerPhoneNumber());
		
		if (paymentDetails.getBornDate() != null)
			bornDate.setText(Constants.DAY_MONTH_AND_YEAR.format(paymentDetails.getBornDate()));
		
		if (paymentDetails.getInstallments() > -1)
			installments.setText(String.valueOf(paymentDetails.getInstallments()));

		for(int i=0; i < paymentType.getChildCount(); i++) {
			itemToCheck = (RadioButton) paymentType.getChildAt(i);
			if (itemToCheck.getText().toString().equals(paymentDetails.getPaymentType())) {
				itemToCheck.setChecked(true);
				break;
			}
		}
	}

	private void setListeners() {
		nextStep.setOnClickListener(this);
	}
	
	private void setPayment() {
		RadioButton checkedItem;
		
		PaymentMgr paymentMgr = PaymentMgr.getInstance();
		PaymentDetails paymentDetails = paymentMgr.getPaymentDetails();
		
		paymentDetails.setBrand(brand.getSelectedItem().toString());
		paymentDetails.setCreditCardNumber(creditCardNumber.getEditableText().toString());
		
		try {
			paymentDetails.setExpirationDate(Constants.MONTH_AND_YEAR.parse(expirationDate.getText().toString()));
		} catch (ParseException e) {
			Log.e(TAG, "Error while parsing date from field expiration date.");
		}
		
		paymentDetails.setSecureCode(secureCode.getEditableText().toString());
		paymentDetails.setOwnerName(ownerName.getEditableText().toString());
		
		checkedItem = (RadioButton) findViewById(identificationType.getCheckedRadioButtonId());
		Log.d(TAG, checkedItem.getText().toString());
		paymentDetails.setOwnerIdentificationType(checkedItem.getText().toString());
		
		paymentDetails.setOwnerIdentificationNumber(identificationNumber.getEditableText().toString());
		paymentDetails.setOwnerPhoneNumber(ownerPhoneNumber.getEditableText().toString());
		
		try {
			paymentDetails.setBornDate(Constants.DAY_MONTH_AND_YEAR.parse(bornDate.getText().toString()));
		} catch (ParseException e) {
			Log.e(TAG, "Error while parsing date from field expiration date.");
		}
		
		String installmentsStr = installments.getEditableText().toString().trim();
		if (installmentsStr.length() != 0) {
			int n = Integer.parseInt(installmentsStr);
			paymentDetails.setInstallments(n);
		}
		
		checkedItem = (RadioButton) findViewById(paymentType.getCheckedRadioButtonId());
		Log.d(TAG, checkedItem.getText().toString());
		paymentDetails.setPaymentType(checkedItem.getText().toString());		
		
		paymentMgr.savePaymentDetails(this);
	}
}