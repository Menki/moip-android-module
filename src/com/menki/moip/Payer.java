package com.menki.moip;

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

public class Payer extends Activity implements OnClickListener {
	private static final String TAG = "PayerActivity";
	
	private EditText fullName;
	private EditText email;
	private EditText cellPhone;
	private RadioGroup identificationType;
	private EditText identificationNumber;
	private EditText streetAddress;
	private EditText streetNumber;
	private EditText streetComplement;
	private EditText neighborhood;
	private EditText city;
	private Spinner state;
	private EditText zipCode;
	private EditText fixedPhone;
	private Button nextStep;
	private Payment payment;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payer);
        
        payment = (Payment) this.getIntent( ).getSerializableExtra("payment");
        setViews();
        setListeners();  
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case(R.id.payer_next_step):
			// Set payment object attributes and persist it
			setPayment();
			payment.save();
			
			// Go to Summary screen passing to its activity the payment object
			Intent intent = new Intent(this.getApplicationContext( ), Payer.class); //TODO: Change Payer.class to the reference to summary activity 
			intent.putExtra("payment", payment);
			startActivity(intent);
			break;
		}
	}
    
	private void setViews() {
		fullName = (EditText) findViewById(R.id.full_name);
		email = (EditText) findViewById(R.id.email);
		cellPhone = (EditText) findViewById(R.id.cell_phone);
		identificationType = (RadioGroup) findViewById(R.id.payer_identification_type);
		identificationNumber = (EditText) findViewById(R.id.payer_identification_number);
		streetAddress = (EditText) findViewById(R.id.street_address);
		streetNumber = (EditText) findViewById(R.id.street_number);
		streetComplement = (EditText) findViewById(R.id.street_complement);
		neighborhood = (EditText) findViewById(R.id.neighborhood);
		city = (EditText) findViewById(R.id.city);
		state = (Spinner) findViewById(R.id.state);
		zipCode = (EditText) findViewById(R.id.zip_code);
		fixedPhone = (EditText) findViewById(R.id.fixed_phone);
		nextStep = (Button) findViewById(R.id.payer_next_step);		
	}
	
	private void setListeners() {
		nextStep.setOnClickListener(this);
	}
	
	private void setPayment() {
		RadioButton checkedItem;
		
		payment.setFullName(fullName.getEditableText().toString());
		payment.setEmail(email.getEditableText().toString());
		payment.setCellPhone(cellPhone.getEditableText().toString());
		
		checkedItem = (RadioButton) findViewById(identificationType.getCheckedRadioButtonId());
		payment.setPayerIdentificationType(checkedItem.getText().toString());
		
		payment.setPayerIdentificationNumber(identificationNumber.getEditableText().toString());
		payment.setStreetAddress(streetAddress.getEditableText().toString());
		
		String streeNumberStr = streetNumber.getEditableText().toString().trim();
		if (streeNumberStr.length() != 0) {
			int n = Integer.parseInt(streeNumberStr);
			payment.setStreetNumber(n);
		}
		
		payment.setStreetComplement(streetComplement.getEditableText().toString());
		payment.setNeighborhood(neighborhood.getEditableText().toString());
		payment.setCity(city.getEditableText().toString());
		
		payment.setState(state.getSelectedItem().toString());
		
		payment.setZipCode(zipCode.getEditableText().toString());
		payment.setFixPhone(fixedPhone.getEditableText().toString());
	}
}
