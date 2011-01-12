package com.menki.moip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Payer extends Activity implements OnClickListener {
	private Button nextStep;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payer);
        
        nextStep = (Button) findViewById(R.id.payer_next_step);
        nextStep.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case(R.id.payer_next_step):
			Intent intent = new Intent(this.getApplicationContext( ), Payer.class);
			this.startActivity(intent);
			break;
		}
	}
}
