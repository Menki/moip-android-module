package com.menki.moip;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	private Payment payment;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_card);
        
        payment = new Payment(this);
        setViews();
        setListeners();
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case(R.id.credit_card_next_step):
			// Set payment object attributes and persist it
			setPayment();
			payment.save();

			// Go to Payer screen passing to its activity the payment object
			Intent intent = new Intent(this.getApplicationContext( ), Payer.class);
			intent.putExtra("payment", payment);
			startActivity(intent);
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
	

	private void setListeners() {
		nextStep.setOnClickListener(this);
	}
	
	private void setPayment() {
		final SimpleDateFormat monthAndYear = new SimpleDateFormat("MM/yyyy");
		final SimpleDateFormat dayMonthAndYear = new SimpleDateFormat("dd/MM/yyyy");
		RadioButton checkedItem;
		
		payment.setBrand(brand.getSelectedItem().toString());
		payment.setCreditCardNumber(creditCardNumber.getEditableText().toString());
		
		try {
			payment.setExpirationDate(monthAndYear.parse(expirationDate.getText().toString()));
		} catch (ParseException e) {
			Log.e(TAG, "Error while parsing date from field expiration date.");
		}
		
		payment.setSecureCode(secureCode.getEditableText().toString());
		payment.setOwnerName(ownerName.getEditableText().toString());
		
		checkedItem = (RadioButton) findViewById(identificationType.getCheckedRadioButtonId());
		payment.setOwnerIdentificationType(checkedItem.getText().toString());
		
		payment.setOwnerIdentificationNumber(identificationNumber.getEditableText().toString());
		payment.setOwnerPhoneNumber(ownerPhoneNumber.getEditableText().toString());
		
		try {
			payment.setBornDate(dayMonthAndYear.parse(bornDate.getText().toString()));
		} catch (ParseException e) {
			Log.e(TAG, "Error while parsing date from field expiration date.");
		}
		
		String installmentsStr = installments.getEditableText().toString().trim();
		if (installmentsStr.length() != 0) {
			int n = Integer.parseInt(installmentsStr);
			payment.setInstallments(n);
		}
		
		checkedItem = (RadioButton) findViewById(paymentType.getCheckedRadioButtonId());
		payment.setOwnerIdentificationType(checkedItem.getText().toString());		
	}
}