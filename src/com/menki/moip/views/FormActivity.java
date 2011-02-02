package com.menki.moip.views;

import java.util.ArrayList;
import java.util.HashMap;

import com.menki.moip.models.PaymentMgr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class FormActivity extends Activity implements OnClickListener {
	private int requiredFieldsNum = 0;
	private Button nextStep;
	
	protected abstract LinearLayout getForm();
	
	protected abstract Class<? extends Activity> nextActivity();
	
	protected abstract ArrayList<Integer> nonRequiredFields();
	
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        
        setDefaultValues();
        addNextStepButton();
    }
	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_CANCELED) 
		{ /* Back button might have been pressed */ }
		else
			switch (requestCode) 
			{
				//just one Activity started:
				case 0: 
					// retrieve the data from intent (or bundle)
					String response = data.getStringExtra("response");
					Intent intent = new Intent( );
					intent.putExtra("response", response);
					// sets the result for the calling activity
					setResult( RESULT_OK, intent);
					finish( );
					break;
			}
	}
	
	public void onClick(View v) {
		if (v.equals(nextStep)){
			Class<? extends Activity> klass = null;
			klass = (!setPayment()) ? ValidationErrors.class : nextActivity(); 
	    	Intent intent = new Intent(this.getApplicationContext( ), klass);
	    	this.startActivityForResult(intent,0);
		}
	}
    
    private void addNextStepButton() {
    	nextStep = new Button(this);
        nextStep.setText(getString(R.string.next_step));
        nextStep.setOnClickListener(this);
        getForm().addView(nextStep);
        
	}

	protected void setDefaultValues() {
		HashMap<Integer, String> payment = PaymentMgr.getInstance().getPaymentDetails();
		View child;
		Class<? extends View> klass;
		String tag, value;		
		
		for(int i=0; i < getForm().getChildCount(); i++) {
			child = getForm().getChildAt(i);
			klass = child.getClass();
			tag = (String) child.getTag();
			value = payment.get(child.getId());
			
			if ((tag != null) && (tag.equals("TEXTVIEW_WITH_DATA") && (klass == LinearLayout.class))) {
				if (!nonRequiredFields().contains(child.getId()))
					requiredFieldsNum++;
				
				TextView textView = (TextView) ((LinearLayout) child).getChildAt(0);
				if (value != null)
					textView.setText(value);
			}
			else if (klass == EditText.class) {
				if (!nonRequiredFields().contains(child.getId()))
					requiredFieldsNum++;
				
				if (value != null)
					((EditText) child).setText(value);
			} 
			else if (klass == RadioGroup.class) {
				if (!nonRequiredFields().contains(child.getId()))
					requiredFieldsNum++;
				
				if (value != null) {
					RadioButton itemToCheck = (RadioButton) findViewById(Integer.parseInt(value));
					if (itemToCheck != null)
						itemToCheck.setChecked(true);
				}
			}
			else if (klass == Spinner.class) {
				if (!nonRequiredFields().contains(child.getId()))
					requiredFieldsNum++;
				
				if (value != null)
					((Spinner) child).setSelection(Integer.parseInt(value));
			}
		}
	}

	protected Boolean setPayment() {
		HashMap<Integer, String> payment = PaymentMgr.getInstance().getPaymentDetails();
		int fieldsWithData = 0;
		
		for(int i=0; i < getForm().getChildCount(); i++) {
			View child = getForm().getChildAt(i);
			Class<? extends View>klass = child.getClass();
			String tag = (String) child.getTag();
			String value = null;
			
			if ((tag != null) && (tag.equals("TEXTVIEW_WITH_DATA") && (klass == LinearLayout.class))) {
				TextView textView = (TextView) ((LinearLayout) child).getChildAt(0);
				value = textView.getText().toString();
				payment.put(child.getId(), value);
			}
			else if (klass == EditText.class) {
				value = ((EditText) child).getEditableText().toString();
				payment.put(child.getId(), value);
			} 
			else if (klass == RadioGroup.class) {
				RadioButton checkedItem = (RadioButton) findViewById(((RadioGroup) child).getCheckedRadioButtonId());
				if (checkedItem != null) {
					value = ((Integer) checkedItem.getId()).toString();
					payment.put(child.getId(), value);
				}
			}
			else if (klass == Spinner.class) {
				value = ((Integer) ((Spinner) child).getSelectedItemPosition()).toString();
				payment.put(child.getId(), value);
			}
			
			if ((value != null) && (value.length() > 0) && (!nonRequiredFields().contains(child.getId())))
				fieldsWithData++;
		}
		
		if (fieldsWithData < requiredFieldsNum)
			return false;
		else
			return PaymentMgr.getInstance().savePaymentDetails();
	}

}
