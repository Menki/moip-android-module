package com.menki.moip.activities;

import java.util.Calendar;

import com.menki.moip.paymentmethods.PagamentoDireto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class PaymentInfo extends Activity implements OnClickListener
{
	
	static final int BORN_DATE_DIALOG_ID = 0;
	static final int EXPIRATION_DATE_DIALOG_ID = 1;
	
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
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.paymentinfo);
    	super.onCreate(savedInstanceState);
    	
    	this.pagamentoDireto = getIntent().getParcelableExtra("PagamentoDireto");


    	this.value = (TextView)findViewById(R.id.value);
    	this.value.setText(this.pagamentoDireto.getValue());
    	
    	this.nextStepButton = (Button)findViewById(R.id.button_next);
    	this.nextStepButton.setOnClickListener(this);
    	    	

    }

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case(R.id.button_next):			

			Intent intent = new Intent(this,Summary.class);
			intent.putExtra("PagamentoDireto", this.pagamentoDireto);
			startActivity(intent);
		break;

		case(R.id.born_date_button):
			showDialog(BORN_DATE_DIALOG_ID);
		break;
		case(R.id.expiration_date_button):
			showDialog(EXPIRATION_DATE_DIALOG_ID);
		break;
		case(R.id.radio_cash_payment):
		case(R.id.radio_installment_payment):
			updateInstallmentsInput();	
		break;
		}
		
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
	
	private void setViews() {
		final Calendar c = Calendar.getInstance();
		
		bornDateYear = c.get(Calendar.YEAR);
		bornDateMonth = c.get(Calendar.MONTH);
		bornDateDay = c.get(Calendar.DAY_OF_MONTH);
		bornDateTextview = (TextView) findViewById(R.id.born_date_textview);
		bornDateButton = (Button) findViewById(R.id.born_date_button);
		
		expirationDateYear = c.get(Calendar.YEAR);
		expirationDateMonth = c.get(Calendar.MONTH);
		expirationDateDay = c.get(Calendar.DAY_OF_MONTH);
		expirationDateTextview = (TextView) findViewById(R.id.expiration_date_textview);
		expirationDateButton = (Button) findViewById(R.id.expiration_date_button);
		
		installmentRadio = (RadioButton) findViewById(R.id.radio_installment_payment);
		cashRadio = (RadioButton) findViewById(R.id.radio_cash_payment);
		installmentsTextView = (TextView) findViewById(R.id.installments_textview);
		installmentsEditText = (EditText) findViewById(R.id.installments);
		
		//---
		
		this.nextStepButton = (Button)findViewById(R.id.button_next);
    	this.nextStepButton.setOnClickListener(this);    	
    	
	}
}
