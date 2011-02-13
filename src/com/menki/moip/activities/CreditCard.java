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

package com.menki.moip.activities;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreditCard extends FormActivity {
	static final String TAG = "CreditCardActivity";
	static final int BORN_DATE_DIALOG_ID = 0;
	static final int EXPIRATION_DATE_DIALOG_ID = 1;
	
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
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.credit_card);
    	super.onCreate(savedInstanceState);
        
        setViews();
        updateDateButtons();
        setListeners();
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case(R.id.born_date_button):
			showDialog(BORN_DATE_DIALOG_ID);
			break;
		case(R.id.expiration_date_button):
			showDialog(EXPIRATION_DATE_DIALOG_ID);
			break;			
		}
		
		super.onClick(v);
	}
		
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case BORN_DATE_DIALOG_ID:
        	return new DatePickerDialog(this,
        			bornDateSetListener,
        			bornDateYear, bornDateMonth, bornDateDay);
        case EXPIRATION_DATE_DIALOG_ID:
        	return new DatePickerDialog(this,
        			expirationDateSetListener,
        			expirationDateYear, expirationDateMonth, expirationDateDay);        	
        }
        return null;
    }
    
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
        case BORN_DATE_DIALOG_ID:
            ((DatePickerDialog) dialog).updateDate(bornDateYear, bornDateMonth, bornDateDay);
            break;
        case EXPIRATION_DATE_DIALOG_ID:
            ((DatePickerDialog) dialog).updateDate(expirationDateYear, expirationDateMonth, expirationDateDay);
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

	private DatePickerDialog.OnDateSetListener expirationDateSetListener =
        new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            	expirationDateYear = year;
            	expirationDateMonth = monthOfYear;
            	expirationDateDay = dayOfMonth;
                updateDisplay(EXPIRATION_DATE_DIALOG_ID);
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
        	break;
        case EXPIRATION_DATE_DIALOG_ID:
        	expirationDateTextview.setText(
                    new StringBuilder()
                            // Month is 0 based so add 1
                            .append(pad(bornDateMonth + 1)).append("/")
                            .append(bornDateYear-2000));
        	break;        	
    	}
    	updateDateButtons();
	}
    
    private void updateDateButtons() {
		if (bornDateTextview.getText().length() > 0)
			bornDateButton.setText(getString(R.string.change));
		
		if (expirationDateTextview.getText().length() > 0)
			expirationDateButton.setText(getString(R.string.change));
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
	}
	
	private void setListeners() {
		bornDateButton.setOnClickListener(this);
		expirationDateButton.setOnClickListener(this);
	}
		
	private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

	@Override
	protected LinearLayout getForm() {
		LinearLayout form = (LinearLayout) findViewById(R.id.credit_card_layout);
		return form;
	}

	@Override
	protected Class<? extends Activity> nextActivity() {
		return Payer.class;
	}

	@Override
	protected ArrayList<Integer> nonRequiredFields() {
		ArrayList<Integer> fields = new ArrayList<Integer>();
		fields.add((Integer) R.id.installments);
		return fields;
	}
}