package com.menki.moip.views;

import java.util.HashMap;

import com.menki.moip.models.PaymentMgr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class FormActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        
        setDefaultValues();
    }
    
    public void onPause() {
    	super.onPause();
        
    	setPayment();
    }
    
	protected abstract LinearLayout getForm();
	
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
			
			if (value != null) {
				if ((tag != null) && (tag.equals("TEXTVIEW_WITH_DATA") && (klass == LinearLayout.class))) {
					TextView textView = (TextView) ((LinearLayout) child).getChildAt(0);
					textView.setText(value);
				}
				else if (klass == EditText.class) {
					((EditText) child).setText(value);
				} 
				else if (klass == RadioGroup.class) {
					RadioButton itemToCheck = (RadioButton) findViewById(Integer.parseInt(value));
					itemToCheck.setChecked(true);
				}
				else if (klass == Spinner.class) {
					((Spinner) child).setSelection(Integer.parseInt(value));
				}
			}
		}
	}

	protected Boolean setPayment() {
		HashMap<Integer, String> payment = PaymentMgr.getInstance().getPaymentDetails();
		
		for(int i=0; i < getForm().getChildCount(); i++) {
			View child = getForm().getChildAt(i);
			Class<? extends View>klass = child.getClass();
			String tag = (String) child.getTag(); 
			
			if ((tag != null) && (tag.equals("TEXTVIEW_WITH_DATA") && (klass == LinearLayout.class))) {
				TextView textView = (TextView) ((LinearLayout) child).getChildAt(0);
				payment.put(child.getId(), textView.getText().toString());
			}
			else if (klass == EditText.class) {
				payment.put(child.getId(), ((EditText) child).getEditableText().toString());
			} 
			else if (klass == RadioGroup.class) {
				RadioButton checkedItem = (RadioButton) findViewById(((RadioGroup) child).getCheckedRadioButtonId());
				payment.put(child.getId(), ((Integer) checkedItem.getId()).toString());
			}
			else if (klass == Spinner.class) {
				payment.put(child.getId(), ((Integer) ((Spinner) child).getSelectedItemPosition()).toString());
			}
		}
		
		return PaymentMgr.getInstance().savePaymentDetails();
	}

}
