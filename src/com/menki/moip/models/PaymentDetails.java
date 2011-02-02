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

package com.menki.moip.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.menki.moip.utils.Constants;

public class PaymentDetails {
	public ArrayList<String> getErrors() {
		return errors;
	}
	/**
	 * This class is a singleton
	 */
	private static PaymentDetails _instance = null;

	/**
	 * Constants
	 */
	private static final String TAG = "PaymentDetails";
	private static final String ATTR_BRAND = "brand";
	private static final String ATTR_CREDIT_CARD = "credit_card";
	private static final String ATTR_EXP_DATE = "expiration_date";
	private static final String ATTR_SECURE_CODE = "secure_code";
	private static final String ATTR_OWNER_NAME = "owner_name";
	private static final String ATTR_OWNER_ID_TYPE = "owner_identification_type";
	private static final String ATTR_OWNER_ID_NUM = "owner_identification_number";
	private static final String ATTR_OWNER_PHONE_NUM = "owner_phone_number";
	private static final String ATTR_BORN_DATE = "born_date";
	private static final String ATTR_INSTALLMENTS = "installments";
	private static final String ATTR_PAYMENT_TYPE = "payment_type";
	private static final String ATTR_FULL_NAME = "full_name";
	private static final String ATTR_EMAIL = "email";
	private static final String ATTR_CELL_PHONE = "cell_phone";
	private static final String ATTR_PAYER_ID_TYPE = "payer_identification_type";
	private static final String ATTR_PAYER_ID_NUM = "payer_identification_number";
	private static final String ATTR_ST_ADDRESS = "street_address";
	private static final String ATTR_ST_NUMBER = "street_number";
	private static final String ATTR_ST_COMPLEMENT = "street_complement";
	private static final String ATTR_NEIGHBORHOOD = "neighborhood";
	private static final String ATTR_CITY = "city";
	private static final String ATTR_STATE = "state";
	private static final String ATTR_ZIP_CODE = "zip_code";
	private static final String ATTR_FIXED_PHONE = "fixed_phone";

	/**
	 * Attributes
	 */
	private String brand;
	private String creditCardNumber;
	private Date expirationDate;
	private String secureCode;
	private String ownerName;
	private String ownerIdentificationType;
	private String ownerIdentificationNumber;
	private String ownerPhoneNumber;
	private Date bornDate;
	private int installments;
	private String paymentType;
	private String fullName;
	private String email;
	private String cellPhone;
	private String payerIdentificationType;
	private String payerIdentificationNumber;
	private String streetAddress;
	private int streetNumber;
	private String streetComplement;
	private String neighborhood;
	private String city;
	private String state;
	private String zipCode;
	private String fixedPhone;
	private ArrayList<String> changes = new ArrayList<String>();
	private ArrayList<String> errors = new ArrayList<String>();

	private PaymentDetails() {}

	/**
	 * 
	 * @return _instance The PaymentMgr instance to be used
	 */
	public static synchronized PaymentDetails getInstance() 
	{
		if(_instance == null) 
		{
			_instance = new PaymentDetails();
		}
		return _instance;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException 
	{
		throw new CloneNotSupportedException();
	}

	public Boolean save(Context context) {
		if (!isChangesValid()) return false;

		Editor editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();

		Iterator<String> itr = changes.iterator();
		while(itr.hasNext()){
			String currentAttr = itr.next();

			if (currentAttr.equals(ATTR_EXP_DATE) || currentAttr.equals(ATTR_BORN_DATE)){ // current is a date attribute
				Date date = (Date) getValueOf(currentAttr);
				SimpleDateFormat dateFormat = 
					(currentAttr.equals(ATTR_EXP_DATE)) ? Constants.MONTH_AND_YEAR : Constants.DAY_MONTH_AND_YEAR;
				editor.putString(currentAttr, dateFormat.format(date));
			} 
			else if (currentAttr.equals(ATTR_INSTALLMENTS) || currentAttr.equals(ATTR_ST_NUMBER)){ // current is a int attribute
				editor.putInt(currentAttr, (Integer) getValueOf(currentAttr));
			}
			else if (!currentAttr.equals(ATTR_SECURE_CODE)) { // current is a string attribute
				editor.putString(currentAttr, (String) getValueOf(currentAttr));
			}
		}

		editor.commit();
		changes.clear();

		return true;
	}

	public void restore(Context context) {
		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

		this.brand = prefs.getString(ATTR_BRAND, "");
		this.creditCardNumber = prefs.getString(ATTR_CREDIT_CARD, "");

		try {
			this.expirationDate = Constants.MONTH_AND_YEAR.parse(prefs.getString(ATTR_EXP_DATE, ""));
		} catch (ParseException e) {
			Log.e(TAG, "Error while parsing date from field expiration date.");
		}

		this.ownerName = prefs.getString(ATTR_OWNER_NAME, "");
		this.ownerIdentificationType = prefs.getString(ATTR_OWNER_ID_TYPE, "");
		this.ownerIdentificationNumber = prefs.getString(ATTR_OWNER_ID_NUM, "");
		this.ownerPhoneNumber = prefs.getString(ATTR_OWNER_PHONE_NUM, "");

		try {
			this.bornDate = Constants.DAY_MONTH_AND_YEAR.parse(prefs.getString(ATTR_BORN_DATE, ""));
		} catch (ParseException e) {
			Log.e(TAG, "Error while parsing date from field expiration date.");
		}

		this.installments = prefs.getInt(ATTR_INSTALLMENTS, -1);
		this.paymentType = prefs.getString(ATTR_PAYMENT_TYPE, "");
		this.fullName = prefs.getString(ATTR_FULL_NAME, "");
		this.email = prefs.getString(ATTR_EMAIL, "");
		this.cellPhone = prefs.getString(ATTR_CELL_PHONE, "");
		this.payerIdentificationType = prefs.getString(ATTR_PAYER_ID_TYPE, "");
		this.payerIdentificationNumber = prefs.getString(ATTR_PAYER_ID_NUM, "");
		this.streetAddress = prefs.getString(ATTR_ST_ADDRESS, "");
		this.streetNumber = prefs.getInt(ATTR_ST_NUMBER, -1);
		this.streetComplement = prefs.getString(ATTR_ST_COMPLEMENT, "");
		this.neighborhood = prefs.getString(ATTR_NEIGHBORHOOD, "");
		this.city = prefs.getString(ATTR_CITY, "");
		this.state = prefs.getString(ATTR_STATE, "");
		this.zipCode = prefs.getString(ATTR_ZIP_CODE, "");
		this.fixedPhone = prefs.getString(ATTR_FIXED_PHONE, "");
	}

	private Object getValueOf(String attr) {
		if (attr.equals(ATTR_BRAND)) 
			return getBrand();
		else if (attr.equals(ATTR_CREDIT_CARD)) 
			return getCreditCardNumber();
		else if (attr.equals(ATTR_EXP_DATE))
			return getExpirationDate();
		else if (attr.equals(ATTR_SECURE_CODE)) 
			return getSecureCode();
		else if (attr.equals(ATTR_OWNER_NAME)) 
			return getOwnerName();
		else if (attr.equals(ATTR_OWNER_ID_TYPE)) 
			return getOwnerIdentificationType();
		else if (attr.equals(ATTR_OWNER_ID_NUM)) 
			return getOwnerIdentificationNumber();
		else if (attr.equals(ATTR_OWNER_PHONE_NUM)) 
			return getOwnerPhoneNumber();
		else if (attr.equals(ATTR_BORN_DATE)) 
			return getBornDate();
		else if (attr.equals(ATTR_INSTALLMENTS)) 
			return getInstallments();
		else if (attr.equals(ATTR_PAYMENT_TYPE)) 
			return getPaymentType();
		else if (attr.equals(ATTR_FULL_NAME)) 
			return getFullName();
		else if (attr.equals(ATTR_EMAIL)) 
			return getEmail();
		else if (attr.equals(ATTR_CELL_PHONE)) 
			return getCellPhone();
		else if (attr.equals(ATTR_PAYER_ID_TYPE)) 
			return getPayerIdentificationType();
		else if (attr.equals(ATTR_PAYER_ID_NUM)) 
			return getPayerIdentificationNumber();
		else if (attr.equals(ATTR_ST_ADDRESS)) 
			return getStreetAddress();
		else if (attr.equals(ATTR_ST_NUMBER)) 
			return getStreetNumber();
		else if (attr.equals(ATTR_ST_COMPLEMENT)) 
			return getStreetComplement();
		else if (attr.equals(ATTR_NEIGHBORHOOD)) 
			return getNeighborhood();
		else if (attr.equals(ATTR_CITY)) 
			return getCity();
		else if (attr.equals(ATTR_STATE)) 
			return getState();
		else if (attr.equals(ATTR_ZIP_CODE)) 
			return getZipCode();
		else if (attr.equals(ATTR_FIXED_PHONE)) 
			return getFixedPhone();
		else 
			return null;
	}

	public Boolean isChangesValid() {
		errors.clear();

		Iterator<String> itr = changes.iterator();
		while(itr.hasNext()){
			String attr = itr.next();

			if (attr.equals(ATTR_CREDIT_CARD)) {
				if (getCreditCardNumber().length() != 16)
					errors.add("N�mero do cart�o de cr�dito deve conter 16 digitos.");
			} 
			else if (attr.equals(ATTR_SECURE_CODE)) {
				if (getSecureCode().length() != 3)
					errors.add("C�digo de seguran�a deve conter 3 digitos.");
			} 
			else if (attr.equals(ATTR_OWNER_NAME)) {
				if (getOwnerName().length() == 0)
					errors.add("Nome do portador � requerido.");
			}
			else if (attr.equals(ATTR_OWNER_ID_TYPE)) {
				if (getOwnerIdentificationType().length() == 0)
					errors.add("Tipo de identifica��o � requerido.");
			} 
			else if (attr.equals(ATTR_OWNER_ID_NUM)) {
				if (getOwnerIdentificationNumber().length() == 0)
					errors.add("N�mero do RG ou CPF do portador do cart�o � requerido.");
			}
			else if (attr.equals(ATTR_OWNER_PHONE_NUM)) {
				if (getOwnerPhoneNumber().length() == 0)
					errors.add("Telefone � requerido.");
			} 
			else if (attr.equals(ATTR_INSTALLMENTS)) {
				if (getInstallments() == 0)
					errors.add("Quantidades de parcelas � requerido.");
			} 
			else if (attr.equals(ATTR_PAYMENT_TYPE)) {
				if (getPaymentType().length() == 0)
					errors.add("Tipo de pagamento � requerido.");
			}
			else if (attr.equals(ATTR_FULL_NAME)) {
				if (getFullName().length() == 0)
					errors.add("Nome completo � requerido.");
			}
			else if (attr.equals(ATTR_EMAIL)) {
				if (getEmail().length() == 0)
					errors.add("Email � requerido.");
			}
			else if (attr.equals(ATTR_CELL_PHONE)) {
				if (getCellPhone().length() == 0)
					errors.add("Celular � requerido.");
			} 
			else if (attr.equals(ATTR_PAYER_ID_TYPE)) {
				if (getPayerIdentificationType().length() == 0)
					errors.add("Celular � requerido.");
			} 
			else if (attr.equals(ATTR_PAYER_ID_NUM)) {
				if (getPayerIdentificationNumber().length() == 0)
					errors.add("N�mero do RG ou CPF do pagador � requerido.");
			}
			else if (attr.equals(ATTR_ST_ADDRESS)) {
				if (getStreetAddress().length() == 0)
					errors.add("Endere�o � requerido.");
			}
			else if (attr.equals(ATTR_ST_NUMBER)) {
				if (getStreetNumber() == 0)
					errors.add("N�mero da resid�ncia � requerido.");
			}
			else if (attr.equals(ATTR_ST_COMPLEMENT)) {
				if (getStreetAddress().length() == 0)
					errors.add("Endere�o � requerido.");
			} 
			else if (attr.equals(ATTR_NEIGHBORHOOD)) {
				if (getNeighborhood().length() == 0)
					errors.add("Bairro � requerido.");
			}
			else if (attr.equals(ATTR_CITY)) {
				if (getCity().length() == 0)
					errors.add("Cidade � requerido.");
			}
			else if (attr.equals(ATTR_ZIP_CODE)) {
				if (getZipCode().length() == 0)
					errors.add("CEP � requerido.");
			} 
			else if (attr.equals(ATTR_FIXED_PHONE)) {
				if (getFixedPhone().length() == 0)
					errors.add("Telefone fixo � requerido.");
			} 
		}

		return errors.isEmpty();
	}

	/**
	 * Getters and Setters
	 */
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		changes.add(ATTR_BRAND);
		this.brand = brand;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		changes.add(ATTR_CREDIT_CARD);
		this.creditCardNumber = creditCardNumber;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		changes.add(ATTR_EXP_DATE);
		this.expirationDate = expirationDate;
	}
	public String getSecureCode() {
		return secureCode;
	}
	public void setSecureCode(String secureCode) {
		changes.add(ATTR_SECURE_CODE);
		this.secureCode = secureCode;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		changes.add(ATTR_OWNER_NAME);
		this.ownerName = ownerName;
	}
	public String getOwnerIdentificationType() {
		return ownerIdentificationType;
	}
	public void setOwnerIdentificationType(String ownerIdentificationType) {
		changes.add(ATTR_OWNER_ID_TYPE);
		this.ownerIdentificationType = ownerIdentificationType;
	}
	public String getOwnerIdentificationNumber() {
		return ownerIdentificationNumber;
	}
	public void setOwnerIdentificationNumber(String ownerIdentificationNumber) {
		changes.add(ATTR_OWNER_ID_NUM);
		this.ownerIdentificationNumber = ownerIdentificationNumber;
	}
	public String getOwnerPhoneNumber() {
		return ownerPhoneNumber;
	}
	public void setOwnerPhoneNumber(String ownerPhoneNumber) {
		changes.add(ATTR_OWNER_PHONE_NUM);
		this.ownerPhoneNumber = ownerPhoneNumber;
	}
	public Date getBornDate() {
		return bornDate;
	}
	public void setBornDate(Date bornDate) {
		changes.add(ATTR_BORN_DATE);
		this.bornDate = bornDate;
	}
	public int getInstallments() {
		return installments;
	}
	public void setInstallments(int installments) {
		changes.add(ATTR_INSTALLMENTS);
		this.installments = installments;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		changes.add(ATTR_PAYMENT_TYPE);
		this.paymentType = paymentType;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		changes.add(ATTR_FULL_NAME);
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		changes.add(ATTR_EMAIL);
		this.email = email;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		changes.add(ATTR_CELL_PHONE);
		this.cellPhone = cellPhone;
	}
	public String getPayerIdentificationType() {
		return payerIdentificationType;
	}
	public void setPayerIdentificationType(String payerIdentificationType) {
		changes.add(ATTR_PAYER_ID_TYPE);
		this.payerIdentificationType = payerIdentificationType;
	}
	public String getPayerIdentificationNumber() {
		return payerIdentificationNumber;
	}
	public void setPayerIdentificationNumber(String payerIdentificationNumber) {
		changes.add(ATTR_PAYER_ID_NUM);
		this.payerIdentificationNumber = payerIdentificationNumber;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		changes.add(ATTR_ST_ADDRESS);
		this.streetAddress = streetAddress;
	}
	public int getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(int streetNumber) {
		changes.add(ATTR_ST_NUMBER);
		this.streetNumber = streetNumber;
	}
	public String getStreetComplement() {
		return streetComplement;
	}
	public void setStreetComplement(String streetComplement) {
		changes.add(ATTR_ST_COMPLEMENT);
		this.streetComplement = streetComplement;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		changes.add(ATTR_NEIGHBORHOOD);
		this.neighborhood = neighborhood;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		changes.add(ATTR_CITY);
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		changes.add(ATTR_STATE);
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		changes.add(ATTR_ZIP_CODE);
		this.zipCode = zipCode;
	}
	public String getFixedPhone() {
		return fixedPhone;
	}
	public void setFixedPhone(String fixedPhone) {
		changes.add(ATTR_FIXED_PHONE);
		this.fixedPhone = fixedPhone;
	}
}
