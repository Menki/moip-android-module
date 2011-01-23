package com.menki.moip;

import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ValidationErrors extends Activity implements OnClickListener {
	private Button ok;
	private TextView errors;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.validation_errors);
		
		ok = (Button) findViewById(R.id.back);
		ok.setOnClickListener(this);
		
		errors = (TextView) findViewById(R.id.errors);
		
		StringBuilder errorsStr = new StringBuilder();
		Iterator<String> itr = PaymentMgr.getInstance().getErrors().iterator();
		while(itr.hasNext()){
			String error = itr.next();
			errorsStr.append(error + "\n");
		}
		
		errors.setText(errorsStr);
	}

	public void onClick(View v) {
		this.finish();
	}
}
