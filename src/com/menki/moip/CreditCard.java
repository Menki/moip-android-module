/**
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Augusto Souza
 *
 */

package com.menki.moip;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class CreditCard extends Activity implements OnClickListener {
	static final String TAG = "CreditCardActivity";
	static final int BORN_DATE_DIALOG_ID = 0;
	
	private Spinner brand;
	private EditText creditCardNumber;
	private EditText expirationDate;
	private EditText secureCode;
	private EditText ownerName;
	private RadioGroup identificationType;
	private EditText identificationNumber;
	private EditText ownerPhoneNumber;
	private TextView bornDateTextview;
	private Button bornDateButton;
	private EditText installments;
	private RadioGroup paymentType;
	private Button nextStep;
	
	private int bornDateYear;
    private int bornDateMonth;
    private int bornDateDay;
	
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
			ArrayList<String> validationErrors = PaymentMgr.getInstance().getErrors();
		
			if (validationErrors.isEmpty()) {
				// Go to Payer screen passing to its activity the payment object
				Intent intent = new Intent(this.getApplicationContext( ), Payer.class);
				this.startActivity(intent);
			} else {
				//showErrorsDialog(validationErrors);
				Intent intent = new Intent(this.getApplicationContext( ), ValidationErrors.class);
				this.startActivity(intent);
			}
			break;
		case(R.id.born_date_button):
			showDialog(BORN_DATE_DIALOG_ID);
			break;
		}
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case BORN_DATE_DIALOG_ID:
            	return new DatePickerDialog(this,
            			bornDateSetListener,
            			bornDateYear, bornDateMonth, bornDateDay);
        }
        return null;
    }
    
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case BORN_DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(bornDateYear, bornDateMonth, bornDateDay);
                break;
        }
    }
	
	private DatePickerDialog.OnDateSetListener bornDateSetListener =
        new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            	bornDateYear = year;
            	bornDateMonth = monthOfYear;
            	bornDateDay = dayOfMonth;
                updateDisplay(BORN_DATE_DIALOG_ID);
            }
        };
    
    private void updateDisplay(int id) {
    	switch (id) {
        case BORN_DATE_DIALOG_ID:
        	bornDateTextview.setText(
                    new StringBuilder()
                            // Month is 0 based so add 1
                            .append(pad(bornDateDay)).append("/")
                            .append(pad(bornDateMonth + 1)).append("/")
                            .append(bornDateYear));
        	bornDateButton.setText(getString(R.string.change));
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
		bornDateTextview = (TextView) findViewById(R.id.born_date_textview);
		bornDateButton = (Button) findViewById(R.id.born_date_button);
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
		
		if (paymentDetails.getBornDate() != null) {
			bornDateTextview.setText(Constants.DAY_MONTH_AND_YEAR.format(paymentDetails.getBornDate()));
			bornDateButton.setText(getString(R.string.change));
		} else {
			bornDateButton.setText(getString(R.string.insert));
		}
		
		if (paymentDetails.getInstallments() > -1)
			installments.setText(String.valueOf(paymentDetails.getInstallments()));

		for(int i=0; i < paymentType.getChildCount(); i++) {
			itemToCheck = (RadioButton) paymentType.getChildAt(i);
			if (itemToCheck.getText().toString().equals(paymentDetails.getPaymentType())) {
				itemToCheck.setChecked(true);
				break;
			}
		}
		
		final Calendar c = Calendar.getInstance();
		bornDateYear = c.get(Calendar.YEAR);
		bornDateMonth = c.get(Calendar.MONTH);
		bornDateDay = c.get(Calendar.DAY_OF_MONTH);
	}

	private void setListeners() {
		bornDateButton.setOnClickListener(this);
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
		if (checkedItem != null)
			paymentDetails.setOwnerIdentificationType(checkedItem.getText().toString());
		else
			paymentDetails.setOwnerIdentificationType("");
		
		paymentDetails.setOwnerIdentificationNumber(identificationNumber.getEditableText().toString());
		
		paymentDetails.setOwnerPhoneNumber(ownerPhoneNumber.getEditableText().toString());
		
//		try {
//			paymentDetails.setBornDate(Constants.DAY_MONTH_AND_YEAR.parse(bornDateTextview.getText().toString()));
//		} catch (ParseException e) {
//			Log.e(TAG, "Error while parsing date from field expiration date.");
//		}
		
		String installmentsStr = installments.getEditableText().toString().trim();
		if (installmentsStr.length() != 0) {
			int n = Integer.parseInt(installmentsStr);
			paymentDetails.setInstallments(n);
		}
		
		checkedItem = (RadioButton) findViewById(paymentType.getCheckedRadioButtonId());
		if (checkedItem != null)
			paymentDetails.setPaymentType(checkedItem.getText().toString());
		else
			paymentDetails.setPaymentType("");
				
		
		paymentMgr.savePaymentDetails(this);
	}
	
	private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }	
}